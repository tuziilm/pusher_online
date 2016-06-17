package com.zhanghui.pusher.common;

import brut.androlib.AndrolibException;
import brut.androlib.ApkDecoder;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.zhanghui.pusher.exception.ParserException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.im4java.core.*;
import org.im4java.process.ArrayListOutputConsumer;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 解释apk文件
 *
 */
public class ApkParser {
	private String apkPath;
	private Integer versionCode;
	private String versionName;
	private String packageName;
	private Integer minSdkVersion;
	private String icon;
    private String label;
	public ApkParser(String apkPath){
		Preconditions.checkNotNull(apkPath, "apk should be set!");
		this.apkPath=apkPath;
	}

    /**
     * 反编译apk文件
     * @throws com.zhanghui.pusher.exception.ParserException
     */
    private void decode() throws ParserException {
        ProcessBuilder pb=new ProcessBuilder(Config.AAPT, "d", "badging", Config.UPLOAD_ROOT_DIR+"/"+apkPath);
        Process process=null;
        try {
            process = pb.start();
            List<String> lines = IOUtils.readLines(process.getInputStream());
            boolean packageParsed=false;
            boolean sdkVersionParsed=false;
            boolean applicationParsed=false;
            for (String line : lines) {
                if(!packageParsed && line.startsWith("package:")){
                    String[] fields = line.split("'");
                    if(fields.length!=6){
                        throw new ParserException("Apk解析出错！");
                    }
                    packageName=fields[1];
                    versionCode=Integer.valueOf(fields[3]);
                    versionName=fields[5];
                    packageParsed=true;
                }else if(!sdkVersionParsed && line.startsWith("sdkVersion:")){
                    String[] fields = line.split("'");
                    if(fields.length!=2){
                        throw new ParserException("Apk解析出错！");
                    }
                    minSdkVersion=Integer.valueOf(fields[1]);
                    sdkVersionParsed=true;
                }else if (!applicationParsed && line.startsWith("application:")){
                    String[] fields = line.split("'");
                    if(fields.length!=4){
                        throw new ParserException("Apk解析出错！");
                    }
                    label=fields[1];
                    icon=fields[3];
                    applicationParsed=true;
                }
                if (packageParsed && sdkVersionParsed && applicationParsed){
                    break;
                }
            }
            if (!packageParsed || !sdkVersionParsed || !applicationParsed){
                throw new ParserException("Apk解析出错！");
            }
        }catch (Exception e){
            throw new ParserException(e);
        }finally {
            if (process!=null) {
                process.destroy();
            }
        }
    }

    public String[] saveIcon(String baseDir, String path, IconType ...iconTypes) throws ParserException {
        ZipFile zipFile=null;
        try{
            //解析icon
            zipFile=new ZipFile(Config.UPLOAD_ROOT_DIR+"/"+apkPath);
            ZipEntry entry = zipFile.getEntry(icon);
            if (entry==null){
                throw new ParserException("Apk解析出错, Icon获取不到！");
            }
            try (InputStream is =zipFile.getInputStream(entry); FileOutputStream fos=new FileOutputStream(baseDir+"/"+path)){
                ByteStreams.copy(is, fos);
            }
        }catch (Exception e){
            throw new ParserException(e);
        }finally {
            if (zipFile!=null) {
                try {
                    zipFile.close();
                }catch (Exception e){
                    throw new ParserException(e);
                }
            }
        }
        String[] paths = new String[1+iconTypes.length];
        paths[0]=path;
        for(int i=0;i<iconTypes.length;i++) {
            paths[i+1] = resizeIcon(baseDir, path, iconTypes[i]);
        }
        return paths;
    }
    public String resizeIcon(String baseDir, String path, IconType iconType) throws ParserException {
        String absPath=baseDir+"/"+path;
        int minLen = iconType.getMinLen();
        try {
            IdentifyCmd localIdentifyCmd= new IdentifyCmd();
            localIdentifyCmd.setSearchPath(Config.IMAGE_MAGICK_PATH);
            IMOperation localIMOperation = new IMOperation();
            localIMOperation.ping();
            localIMOperation.format("%w\n%h");
            localIMOperation.addImage(absPath);
            ArrayListOutputConsumer identifyOutput= new ArrayListOutputConsumer();
            localIdentifyCmd.setOutputConsumer(identifyOutput);
            localIdentifyCmd.run(localIMOperation);
            ArrayList<String> info= identifyOutput.getOutput();
            int width = Integer.valueOf(info.get(0));
            int height = Integer.valueOf(info.get(1));
            if(width>minLen){
                height = minLen * width/height;
                width = minLen;
            }
            if(height>minLen){
                width=minLen*height/width;
                height=minLen;
            }
            ConvertCmd cmd = new ConvertCmd(false);
            cmd.setSearchPath(Config.IMAGE_MAGICK_PATH);
            IMOperation op = new IMOperation();
            op.addImage(absPath);
            op.resize(width, height);
            String ext = Files.getFileExtension(path);
            String newPath = path.substring(0,path.length()-ext.length()-1)+"_"+width+"x"+height+"."+ext;
            op.addImage(baseDir+"/"+newPath);
            cmd.run(op);
            return newPath;
        }catch (Exception e){
            throw new ParserException(e);
        }
    }

	public static void main(String[] args) throws AndrolibException, ParserException, IOException, ParserConfigurationException, SAXException {
		String apkPath="apk\\2014\\11\\apus.apk";
		ApkParser parser=new ApkParser(apkPath);
        parser.parse();
        parser.saveIcon("D:/tmp/","abc.png",IconType.M300);
        System.out.println(parser.getIcon());
    }

	public void parse() throws ParserException, AndrolibException, ParserConfigurationException, SAXException, IOException{
		decode();
	}
	
	public void clean() throws IOException{
	}
	
	//getters and setters
	public String getApkPath() {
		return apkPath;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersionName() {
		return versionName;
	}
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Integer getMinSdkVersion() {
		return minSdkVersion;
	}
	public void setMinSdkVersion(Integer minSdkVersion) {
		this.minSdkVersion = minSdkVersion;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
