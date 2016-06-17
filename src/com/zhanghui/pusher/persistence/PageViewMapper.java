package com.zhanghui.pusher.persistence;

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.PageView;

import java.util.List;

/**
 * ҳ��ͳ��Mapper
 *
 */
public interface PageViewMapper extends BaseMapper<PageView> {
    List<PageView> getChartDatas(Paginator paginator);
}
