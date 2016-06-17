package com.zhanghui.pusher.persistence;

import com.zhanghui.pusher.common.Paginator;
import com.zhanghui.pusher.domain.PageView;

import java.util.List;

/**
 * Ò³ÃæÍ³¼ÆMapper
 *
 */
public interface PageViewMapper extends BaseMapper<PageView> {
    List<PageView> getChartDatas(Paginator paginator);
}
