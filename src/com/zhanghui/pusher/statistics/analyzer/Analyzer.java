package com.zhanghui.pusher.statistics.analyzer;

import com.zhanghui.pusher.statistics.common.DataHolder;

public interface Analyzer<T> {
    String name();
    DataHolder<T> analyze() throws Exception;
}
