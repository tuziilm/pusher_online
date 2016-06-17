<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="创建浏览器规则" scope="request"/>
<c:set var="_underBrowserRule" value="active" scope="request"/>
<c:set var="_activeBrowser" value="active" scope="request"/>
<c:set var="_module" value="browser" scope="request"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>
<link rel="stylesheet" href="${basePath}static/jquery/jquery-ui.css" />
<!-- main content -->
		<div class="page-header"><h1>创建/修改浏览器规则</h1></div>
		<div id="pageContent">
			<c:import url="../../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}browser/rule/save" method="post" class="form-horizontal">
				<input name="id" type="hidden" value="${form.id}">
				<input name="_queryString" type="hidden" value="${param.queryString}">
                <div class="control-group required-field">
                    <label class="control-label">名称:</label>
                    <div class="controls">
                        <input name="name" value="${fn:escapeXml(form.name)}" type="text" class="input-large">
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">包名:</label>
                    <div class="controls">
                        <input name="packageName" value="${fn:escapeXml(form.packageName)}" type="text" class="input-large">
                    </div>
                </div>
                <div id="_cg_mtitle" class="control-group required-field">
                    <label class="control-label">主Activity:</label>
                    <div class="controls">
                        <input name="mainActivity" value="${fn:escapeXml(form.mainActivity)}" type="text" class="input-large">
                    </div>
                </div>
				<div class="control-group required-field">
				  <label class="control-label">备注:</label>
				  <div class="controls">
				    <input name="remark" value="${fn:escapeXml(form.remark)}" type="text" class="input-large">
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
