package com.zhanghui.pusher.statistics.analyzer;

import com.google.common.base.Strings;
import com.zhanghui.pusher.statistics.common.*;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class TaskActionDataAnalyzer extends AbstractDailyAnalyzer<TaskActionData, TaskActionDataAnalyzer.PreProcessLineEntry> {
	private Map<String, TaskActionData> pushDataMap = new HashMap<>();
    private Map<String, TaskActionData> desktopDataMap = new HashMap<>();

	public TaskActionDataAnalyzer(Date date) {
        super(date);
	}

    @Override
    public String name() {
        return "TaskActionDataAnalyzer";
    }

    public class PreProcessLineEntry extends AbstractDailyAnalyzer.PreProcessLineEntry{
        public String module;
		public String country;
		public String taskId;
		public String actionId;
		public String hash;
		public String uid;
        public Map<String, TaskActionData> map;

		@Override
		public PreProcessLineEntry load(String line) {
			super.load(line);
			if (!"callback/stat".equals(identity) || entries.length < 38) {
                setNotSupport();
				return this;
			}
			country = entries[4];
			if (Strings.isNullOrEmpty(country)) {
				country = "00";
			}
			uid=entries[6];
	        if(Strings.isNullOrEmpty(uid)){
                uid="00";
            }
	        hash=String.valueOf(Math.abs(Objects.hashCode(uid))%256);

            taskId = entries[36];
			actionId = entries[37];
            if(actionId.startsWith("1")){
                map = pushDataMap;
                module = "TASK_ACTION_DAY_1";
            }else if(actionId.startsWith("2")){
                map=desktopDataMap;
                module = "DESKTOP_ACTION_DAY_1";
            }else{
                setInvalid();
            }
			return this;
		}

        @Override
        public void write(BufferedWriter bw, int fIdx) throws IOException {
            String[] keys = Keys.toKeys(String.valueOf(taskId), country, actionId);
            for(String key : keys) {
                TaskActionData taskActionData = map.get(key);
                if (taskActionData == null) {
                    taskActionData = new TaskActionData(module, nDay.dayStrings[fIdx], key, 1);
                    map.put(key, taskActionData);
                } else {
                    taskActionData.setCount(taskActionData.getCount() + 1);
                }
            }
            bw.write(taskId);
            bw.write(Config.SEP);
            bw.write(country);
            bw.write(Config.SEP);
            bw.write(actionId);
            bw.write(Config.SEP);
            bw.write(uid);
            bw.write("\n");
        }
	}

    @Override
	public DataHolder<TaskActionData> packData() {
		final List<TaskActionData> taskActionDatas = new ArrayList<>(pushDataMap.size()+desktopDataMap.size());
        for (Map.Entry<String, TaskActionData> entry : pushDataMap.entrySet()) {
            taskActionDatas.add(entry.getValue());
        }
		for (Map.Entry<String, TaskActionData> entry : desktopDataMap.entrySet()) {
			taskActionDatas.add(entry.getValue());
		}
        return new DataHolder<TaskActionData>() {
            @Override
            public String sql() {
                return "insert into task_view(module, task_id, country, action_id, `schedule`, count, gmt_create) values(?,?,?,?,?,?,now()) on duplicate key update count =? ";
            }

            @Override
            public List<TaskActionData> datas() {
                return taskActionDatas;
            }

            @Override
            public void setPstmt(TaskActionData data, PreparedStatement pstmt) throws SQLException {
                TaskActionData.KeyEntry keyEntry = data.getKeyEntry();
                pstmt.setString(1, data.getModule());
                pstmt.setString(2, keyEntry.taskId);
                pstmt.setString(3, keyEntry.country);
                pstmt.setString(4, keyEntry.actionId);
                pstmt.setString(5, data.getDate());
                pstmt.setInt(6, data.getCount());
                pstmt.setInt(7, data.getCount());
            }
        };
	}

    @Override
    protected PreProcessLineEntry loadPreProcessLineEntry(String line) {
        PreProcessLineEntry entry= new PreProcessLineEntry().load(line);
        if (entry.isInvalid()){
            System.out.println("invalid line:"+line);
        }
        return entry;
    }

    @Override
    protected AbstractLogFileHandler processLogFileHandler() {
        return null;
    }
}
