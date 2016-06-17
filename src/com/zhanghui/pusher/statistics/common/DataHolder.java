package com.zhanghui.pusher.statistics.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public interface DataHolder<T> {
    String sql();
    List<T> datas();
    void setPstmt(T data, PreparedStatement pstmt) throws SQLException;
}
