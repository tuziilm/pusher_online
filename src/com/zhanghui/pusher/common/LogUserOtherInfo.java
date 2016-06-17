package com.zhanghui.pusher.common;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhanghui.pusher.domain.UserOtherInfo;
/**
 * 收集用户的位置信息以及用户每天使用app的情况
 * @author tuziilm
 */
public final class LogUserOtherInfo {
	private final static Logger log = LoggerFactory.getLogger(LogUserOtherInfo.class);
	public final static char SEP='\u0001';

    public static void log(String uid, String shortcut, List<UserOtherInfo> list){
        log(log, uid, shortcut, list);
    }
    public static void log(Logger logger, String uuid, String shortcut, List<UserOtherInfo> list){
		if(list.size()>0){
            StringBuilder msg=new StringBuilder();
			for (UserOtherInfo userOtherInfo : list){
				msg.append(uuid);
				msg.append(SEP).append(userOtherInfo.getDate())
					.append(SEP).append(userOtherInfo.getApp())
					.append(SEP).append(userOtherInfo.getCount())
                    .append(SEP).append(shortcut)
                    .append("\n");
			}
            msg.deleteCharAt(msg.length()-1);
            logger.info(msg.toString());
		}
	}
}
