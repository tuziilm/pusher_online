package com.zhanghui.pusher.statistics;

import com.zhanghui.pusher.statistics.analyzer.Analyzer;
import com.zhanghui.pusher.statistics.analyzer.LinkNodePvUvAnalyzer;
import com.zhanghui.pusher.statistics.analyzer.TaskActionDataAnalyzer;
import com.zhanghui.pusher.statistics.common.ChartPvUvData;
import com.zhanghui.pusher.statistics.common.DataHolder;
import com.zhanghui.pusher.statistics.common.DatabaseHelper;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.SQLException;
import java.util.Date;

public final class LogStatisticsRunner {
	public final static void persistToDatabase(Analyzer analyzer) throws Exception {
		try {
			System.out.printf("[%s]analyzing....\n", analyzer.name());
			DataHolder<ChartPvUvData> data = analyzer.analyze();
			System.out.printf("[%s]ready for persist result to database\n", analyzer.name());
			DatabaseHelper.persistToDatabase(data);
		} catch (SQLException e) {
			System.out.printf("[%s]failed to persist result to database\n", analyzer.name());
			e.printStackTrace();
		}
		System.out.printf("[%s]work done.\n", analyzer.name());
	}
//	public static void excute(String[] args) throws Exception {
	public void excute() throws Exception {
        Date date = new Date();
//        if(args.length>0){
//            date = DateUtils.parseDate(args[0], "yyyy-MM-dd");
//        }
        String type="all";
//        if(args.length>1){
//            type=args[1];
//        }
        Analyzer[] analyzers = new Analyzer[0];
        switch (type){
            case "all":
                analyzers = new Analyzer[]{new LinkNodePvUvAnalyzer(date), new TaskActionDataAnalyzer(date)};
                break;
            case "pvuv":
                analyzers = new Analyzer[]{new LinkNodePvUvAnalyzer(date)};
                break;
            case "task":
                analyzers = new Analyzer[]{new TaskActionDataAnalyzer(date)};
                break;
        }
        for(Analyzer analyzer: analyzers) {
            persistToDatabase(analyzer);
        }
    }
}
