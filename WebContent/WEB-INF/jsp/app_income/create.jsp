<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="修改应用收益数据" scope="request"/>
<c:set var="_underAppIncome" value="active" scope="request"/>
<c:set var="_activeStatistics" value="active" scope="request"/>
<c:set var="_module" value="statistics" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
		<div class="page-header"><h1>修改应用收益数据</h1></div>
		<div id="pageContent">
			<c:import url="../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}app_income/save" method="post" class="form-horizontal">
				<input name="id" type="hidden" value="${form.id}">
				<input name="_queryString" type="hidden" value="${param.queryString}">
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
<c:import url="../theme/${_theme}/footer.jsp"></c:import>
