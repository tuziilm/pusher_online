package com.zhanghui.pusher.statistics.analyzer;

import com.zhanghui.pusher.statistics.common.DataHolder;

import java.io.IOException;
import java.util.*;

public abstract class AbstractAnalyzer<T> implements Analyzer<T> {
    protected Date date;
    protected NDay nDay;


    public AbstractAnalyzer(Date date, NDay nDay) {
        this.date = date;
        this.nDay = nDay;
    }

    public static class NDay {
        public String[] logFiles;
        public String[] dayStrings;
        public String module;

        NDay(String module, String[] logFiles, String[] dayStrings) {
            this.module = module;
            this.logFiles = logFiles;
            this.dayStrings = dayStrings;
        }
    }

    public abstract DataHolder<T> packData();

    public DataHolder<T> analyze() throws Exception {
        System.out.print("pre processing...");
        preprocess();
        System.out.println("...done");
        System.out.print("processing...");
        process();
        System.out.println("...done");
        return packData();
    }

    protected abstract void process() throws IOException;

    protected abstract void preprocess() throws IOException;
}
