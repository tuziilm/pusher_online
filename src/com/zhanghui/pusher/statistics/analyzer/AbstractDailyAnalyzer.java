package com.zhanghui.pusher.statistics.analyzer;

import com.zhanghui.pusher.statistics.common.*;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public abstract class AbstractDailyAnalyzer<T,PPLE extends AbstractDailyAnalyzer.PreProcessLineEntry> extends AbstractAnalyzer<T>{
    protected Map<String, T> dataMap= new HashMap<>();
    private String workDir=Config.DIR_LOG+"/."+name();

    public AbstractDailyAnalyzer(Date date) {
        super(date, new NDay("LINK_NODE_DAY_1", Config.getPreNDaysLogFiles(1, date),Config.getPreNDaysStrings(1, date)));
    }

    public abstract static class PreProcessLineEntry extends LineEntry{
        public String hash;

        @Override
        public PreProcessLineEntry load(String line) {
            super.load(line);
            return this;
        }
        public abstract void write(BufferedWriter bw, int fIdx) throws IOException;
    }

    public void process() throws IOException {
        File dir = new File(workDir+"/"+nDay.dayStrings[0]);
        if(dir.exists()) {
            File[] files = dir.listFiles();
            AbstractLogFileHandler logFileHandler = processLogFileHandler();
            if(logFileHandler!=null) {
                logFileHandler.handleFile(files);
            }
            FileUtils.deleteDirectory(dir);
        }
    }

    protected abstract PPLE loadPreProcessLineEntry(String line);

    protected abstract AbstractLogFileHandler processLogFileHandler();

    /**
     * 预处理
     * 1，按IP Hash切分文件
     * 2，统计PV
     */
    protected void preprocess() throws IOException {
        //创建切分文件夹
        Path path=Paths.get(workDir+"/"+nDay.dayStrings[0]);
        FileUtils.deleteDirectory(path.toFile());
        Files.createDirectories(path);
        final Map<String, BufferedWriter> outFileMap=new HashMap<>();
        AbstractLogFileHandler handler = new AbstractLogFileHandler<PPLE>(){
            public int count=0;

            @Override
            public void postHandleMultiFiles() {
                System.out.println("count:"+count);
                for(Map.Entry<String, BufferedWriter> entry : outFileMap.entrySet()){
                    try {
                        entry.getValue().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }

            @Override
            public PPLE loadLineEntry(String line) {
                return loadPreProcessLineEntry(line);
            }

            @Override
            public void handleLine(int fIdx, PPLE entry) {
                if(entry.isValid()){
                    BufferedWriter bw = outFileMap.get(entry.hash);
                    if(bw ==null){
                        try {
                            bw=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(workDir+"/"+nDay.dayStrings[fIdx]+"/"+entry.hash+".log"),"UTF-8"));
                            outFileMap.put(entry.hash, bw);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }
                    }
                    try {
                        entry.write(bw, fIdx);
                        count++;
                        if(count%100000==0){
                            System.out.println("count:"+count);
                            bw.flush();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                }
            }
        };
        handler.handleFile(nDay.logFiles);
    }
}
