package com.zhanghui.pusher.statistics.common;

import java.sql.*;
import java.util.List;

/**
 * 数据库访问帮助类
 * 
 * 
 */
public final class DatabaseHelper {
    public final static int BATCH_SIZE=1000;
	/**
	 * 获取数据库链接
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws java.sql.SQLException
	 */
	public final static Connection getDatabaseConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		return DriverManager.getConnection(Config.getProperty("mysql.url"),
				Config.getProperty("mysql.username"),
				Config.getProperty("mysql.password"));
	}

	/**
	 * 关闭数据链接
	 * 
	 * @param rs
	 * @param pstmt
	 * @param conn
	 */
	public final static void closeDatabaseComponent(ResultSet rs, PreparedStatement pstmt, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			pstmt = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}

    public final static <T> void persistToDatabase(DataHolder<T> holder) throws SQLException, ClassNotFoundException {
        List<T> datas = holder.datas();
        if(datas==null || datas.isEmpty()){
            return;
        }
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = getDatabaseConnection();
            pstmt = conn.prepareStatement(holder.sql());
            int count=0;
            for (T data : datas) {
                holder.setPstmt(data, pstmt);
                pstmt.addBatch();
                count++;
                if(count>=BATCH_SIZE){
                    pstmt.executeBatch();
                    pstmt.clearBatch();
                    count=0;
                }
            }
            if(count>0) {
                pstmt.executeBatch();
            }
        } finally {
            closeDatabaseComponent(null, pstmt, conn);
        }
    }
}
