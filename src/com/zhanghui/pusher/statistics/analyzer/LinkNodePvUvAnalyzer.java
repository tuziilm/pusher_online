package com.zhanghui.pusher.statistics.analyzer;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.zhanghui.pusher.common.LogModule;
import com.zhanghui.pusher.statistics.common.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class LinkNodePvUvAnalyzer extends AbstractDailyAnalyzer<ChartPvUvData, LinkNodePvUvAnalyzer.PreProcessLineEntry> {
    public final static Set<String> NAME_SET= Sets.newHashSet(LogModule.UPDATE.name(), LogModule.FLOATING_AD_GET_APP_RULE.name(),
            LogModule.PUSH_STAT.name(),LogModule.GET_PUSH_DATA.name(), LogModule.HOME_PAGE.name(), LogModule.PUSH_REGISTER.name(),
            LogModule.USER_OTHER_INFO.name(), LogModule.AD_SCREEN_RULE.name(),LogModule.SHORTCUT.name());

    public LinkNodePvUvAnalyzer(Date date) {
        super(date);
    }

    @Override
    public String name() {
        return "LinkNodePvUvAnalyzer";
    }

    public class PreProcessLineEntry extends AbstractDailyAnalyzer.PreProcessLineEntry{
        public String country;
        public String from;
        public String linkNode;
        public String uid;
        @Override
        public PreProcessLineEntry load(String line) {
            super.load(line);
            if (!NAME_SET.contains(name) || entries.length < 8) {
                setNotSupport();
                return this;
            }
            country=entries[4];
            if(Strings.isNullOrEmpty(country)){
                country = "00";
            }
            uid=entries[6];
            if(Strings.isNullOrEmpty(uid)){
                uid="00";
            }
            hash=String.valueOf(Math.abs(Objects.hashCode(uid))%256);

            linkNode=ChartPvUvData.mappedLinkNode(name);
            from=entries[7];
            from=from.length()>10?from.substring(0,10).trim():from.trim();//兼容线上from参数的错误
            if(Strings.isNullOrEmpty(from)){
                from="0000";
            }
            return this;
        }

        @Override
        public void write(BufferedWriter bw, int fIdx) throws IOException {
            String[] keys=Keys.toKeys(linkNode, country, from);
            for(String key : keys) {
                ChartPvUvData chartPvUvData = dataMap.get(key);
                if (chartPvUvData == null) {
                    chartPvUvData = new ChartPvUvData("LINK_NODE_DAY_1", nDay.dayStrings[fIdx], key, 1);
                    dataMap.put(key, chartPvUvData);
                } else {
                    chartPvUvData.setPv(chartPvUvData.getPv() + 1);
                }
            }
            bw.write(linkNode);
            bw.write(Config.SEP);
            bw.write(country);
            bw.write(Config.SEP);
            bw.write(from);
            bw.write(Config.SEP);
            bw.write(uid);
            bw.write("\n");
        }
    }

    @Override
    protected PreProcessLineEntry loadPreProcessLineEntry(String line) {
        PreProcessLineEntry entry = new PreProcessLineEntry().load(line);
        if (entry.isInvalid()){
            System.out.println("invalid line:"+line);
        }
        return entry;
    }

    public class ProcessLineEntry extends ValidLineEntry{
        public String linkNode;
        public String country;
        public String from;
        public String uid;

        public ProcessLineEntry load(String line){
            Iterator<String> fields = Splitter.on(Config.SEP).split(line).iterator();
            linkNode = fields.next();
            country = fields.next();
            from = fields.next();
            uid = fields.next();
            setValid();
            return this;
        }

        public String[] toKeys(){
            return Keys.toKeys(linkNode, country, from);
        }

        @Override
        public String toString() {
            return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    @Override
    public DataHolder<ChartPvUvData> packData() {
        final List<ChartPvUvData> datas = new ArrayList<>(dataMap.size());
        for(Map.Entry<String, ChartPvUvData> entry : dataMap.entrySet()){
            datas.add(entry.getValue());
        }
        return new DataHolder<ChartPvUvData>() {
            @Override
            public String sql() {
                return "insert into page_view(module, code, country, `from`, `schedule`,pv, uv, gmt_create) values(?,?,?,?,?,?,?,now()) on duplicate key update pv =? ,uv =?";
            }

            @Override
            public List<ChartPvUvData> datas() {
                return datas;
            }

            @Override
            public void setPstmt(ChartPvUvData data, PreparedStatement pstmt ) throws SQLException {
                ChartPvUvData.KeyEntry keyEntry = data.getKeyEntry();
                pstmt.setString(1, data.getModule());
                pstmt.setString(2, keyEntry.linkNode);
                pstmt.setString(3, keyEntry.country);
                pstmt.setString(4, keyEntry.from);
                pstmt.setString(5, data.getDate());
                pstmt.setInt(6, data.getPv());
                pstmt.setInt(7, data.getUv());
                pstmt.setInt(8, data.getPv());
                pstmt.setInt(9, data.getUv());
            }
        };
    }

    @Override
    protected AbstractLogFileHandler processLogFileHandler() {
        return new AbstractLogFileHandler<ProcessLineEntry>() {
            private Map<String, Set<String>> uvMap;

            @Override
            public ProcessLineEntry loadLineEntry(String line){
                return new ProcessLineEntry().load(line);
            }

            @Override
            public void preHandleSingleFile() {
                uvMap = new HashMap<>();
            }

            @Override
            public void postHandleSingleFile() {
                for(Map.Entry<String, Set<String>> entry : uvMap.entrySet()){
                    ChartPvUvData chartPvUvData = dataMap.get(entry.getKey());
                    chartPvUvData.setUv(chartPvUvData.getUv() + entry.getValue().size());
                }
            }

            @Override
            public void handleLine(int fIdx, ProcessLineEntry entry) {
                if (entry.isValid()) {
                    String[] keys = entry.toKeys();
                    for(String key : keys) {
                        Set<String> ipSet = uvMap.get(key);
                        if (ipSet == null) {
                            ipSet = new HashSet<>();
                            uvMap.put(key, ipSet);
                        }
                        ipSet.add(entry.uid);
                    }
                }
            }
        };
    }
}
