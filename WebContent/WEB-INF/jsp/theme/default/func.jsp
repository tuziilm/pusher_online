<%@ include file="../../include/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:choose>
    <c:when test="${_module=='push' }">
        <li class="${_underPushTask}"><a href="${basePath}push/task/list">Push任务列表</a></li>
        <li class="${_underPushDesktop}"><a href="${basePath}push/desktop/list">桌面任务列表</a></li>
    </c:when>
    <c:when test="${_module=='adv' }">
        <li class="${_underAdvApp}"><a href="${basePath}adv/app/list">应用广告</a></li>
        <li class="${_underAdvLink}"><a href="${basePath}adv/link/list">链接广告</a></li>
        <li class="${_underAdvOwner}"><a href="${basePath}adv/owner/list">广告主</a></li>
    </c:when>
    <c:when test="${_module=='user' }">
        <li class="${_underUser}"><a href="${basePath}user/list">用户列表</a></li>
    </c:when>
    <c:when test="${_module=='browser' }">
        <li class="${_underBrowserSettings}"><a href="${basePath}browser/settings/list">基本设置</a></li>
        <li class="${_underBrowserRule}"><a href="${basePath}browser/rule/list">规则设置</a></li>
    </c:when>
    <c:when test="${_module=='floatingAd' }">
        <li class="${_underFloatingAdSettings}"><a href="${basePath}floating_ad/settings/list">基本设置</a></li>
        <li class="${_underFloatingAdAppRule}"><a href="${basePath}floating_ad/app_rule/list">规则设置</a></li>
    </c:when>
    <c:when test="${_module=='adScreen' }">
        <li class="${_underAdScreenRule}"><a href="${basePath}ad_screen/rule/list">规则设置</a></li>
    </c:when>
	<c:when test="${_module=='system' }">
		<c:if test="${pusher:isAdmin()}">
			<li class="${_underSysUser}"><a href="${basePath}sysuser/list">系统用户</a></li>
		</c:if>
		<li class="${_underUserInfo}"><a href="${basePath}sysuser/${isUnderUserInfo?'info_modify':'modify'}/${pusher:uid()}">信息修改</a></li>
	</c:when>
	<c:when test="${_module=='statistics' }">
		<li class="${_underTaskAction}"><a href="${basePath}ta/list">任务操作统计</a></li>
        <li class="${_underStatistics}"><a href="${basePath}pv/list">链接PV/UV</a></li>
        <li class="${_underStatisticsChart}"><a href="${basePath}pv/chart">链接PV/UV展示</a></li>
        <li class="${_underTotalData}"><a href="${basePath}data_statistics/total/list">广告数据总览</a></li>
        <li class="${_underAdOwnerData}"><a href="${basePath}data_statistics/adOwner/list">广告方数据</a></li>
        <li class="${_underCountryData}"><a href="${basePath}data_statistics/country/list">国家区域数据</a></li>
        <li class="${_underActiveUser}"><a href="${basePath}active_user/list">用户情况</a></li>
        <li class="${_underTopApp}"><a href="${basePath}top_app/list">TopN应用</a></li>
        <li class="${_underAppIncome}"><a href="${basePath}app_income_summary/list">应用收益</a></li>
	</c:when>
</c:choose>
