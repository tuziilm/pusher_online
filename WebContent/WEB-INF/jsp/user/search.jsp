<%@ include file="../include/common.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="curTime" class="java.util.Date" scope="request" />
<fmt:formatDate pattern="yyyy/MM/dd" var="now" value="${curTime}"/>
<link rel="stylesheet" href="${basePath}static/jquery/jquery-ui.css" />
<div id="search">
	<form id="search_form" action="${basePath}user/list" method="post" class="form-inline pull-right">
        <label>时间:</label>
        <input value="${pusher:defVal(param.startTime,now)}" type="text" name="startTime" class="input-small" id="startTime"/>~
        <input value="${pusher:defVal(param.endTime,now)}" type="text" name="endTime" class="input-small" id="endTime"/>
        <label>ID:</label>
        <input type="text" name="identity" value="${fn:escapeXml(param.identity)}" class="input-medium"/>
        <label>客户:</label>
        <input type="text" name="from" value="${fn:escapeXml(param.from)}" class="input-small"/>
        <label>版本:</label>
        <input type="text" name="version" value="${fn:escapeXml(param.version)}" class="input-small"/>
        <label>国家:</label>
        <input type="text" name="simCountry" value="${fn:escapeXml(param.simCountry)}" class="input-small"/>
        <input type="submit" class="btn" value="查询"/>
	</form>
</div>