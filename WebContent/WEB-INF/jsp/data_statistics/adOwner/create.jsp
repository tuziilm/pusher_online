<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="修改广告方数据" scope="request"/>
<c:set var="_underAdOwnerData" value="active" scope="request"/>
<c:set var="_activeStatistics" value="active" scope="request"/>
<c:set var="_module" value="statistics" scope="request"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
		<div class="page-header"><h1>修改广告方数据</h1></div>
		<div id="pageContent">
			<c:import url="../../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}data_statistics/adOwner/save" method="post" class="form-horizontal">
				<input name="id" type="hidden" value="${form.id}">
				<input name="_queryString" type="hidden" value="${param.queryString}">
				<div class="control-group">
                    <label class="control-label">对方请求次数:</label>
                    <div class="controls">
                        <input name="otherRequestTimes" value="${fn:escapeXml(form.otherRequestTimes)}" type="text" class="input-large">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">对方展示次数:</label>
                    <div class="controls">
                        <input name="otherShowTimes" value="${fn:escapeXml(form.otherShowTimes)}" type="text" class="input-large">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">对方点击次数:</label>
                    <div class="controls">
                        <input name="otherClickTimes" value="${fn:escapeXml(form.otherClickTimes)}" type="text" class="input-large">
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label">收入:</label>
                    <div class="controls">
                        <input name="income" value="${fn:escapeXml(form.income/100)}" type="text" class="input-large">
                    </div>
                </div>
				<div class="form-actions">
				  <input class="btn btn-primary" type="submit" value="保存">
				  <button type="button" class="btn" onclick="javascript:history.go(-1)">取消</button>
				</div>
			</form>
        </div>
<!-- end main content -->
<c:import url="../../theme/${_theme}/footer.jsp"></c:import>
