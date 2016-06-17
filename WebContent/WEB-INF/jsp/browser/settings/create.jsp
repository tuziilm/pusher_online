<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="创建浏览器基本设置" scope="request"/>
<c:set var="_underBrowserSettings" value="active" scope="request"/>
<c:set var="_activeBrowser" value="active" scope="request"/>
<c:set var="_module" value="browser" scope="request"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>
<link rel="stylesheet" href="${basePath}static/jquery/jquery-ui.css" />
<!-- main content -->
		<div class="page-header"><h1>创建/修改浏览器基本设置</h1></div>
		<div id="pageContent">
			<c:import url="../../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}browser/settings/save" method="post" class="form-horizontal">
				<input name="id" type="hidden" value="${form.id}">
				<input name="_queryString" type="hidden" value="${param.queryString}">
                <div class="control-group required-field">
                    <label class="control-label">主页:</label>
                    <div class="controls">
                        <input name="homePage" value="${fn:escapeXml(form.homePage)}" type="text" class="input-large">
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">访问时间间隔:</label>
                    <div class="controls">
                        <input name="accessDayInterval" value="${fn:escapeXml(form.accessDayInterval)}" type="text" class="input-large">
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
