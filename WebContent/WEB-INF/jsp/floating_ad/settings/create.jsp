<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="创建横幅广告基本设置" scope="request"/>
<c:set var="_underFloatingAdSettings" value="active" scope="request"/>
<c:set var="_activeFloatingAd" value="active" scope="request"/>
<c:set var="_module" value="floatingAd" scope="request"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>
<link rel="stylesheet" href="${basePath}static/jquery/jquery-ui.css" />
<!-- main content -->
		<div class="page-header"><h1>创建/修改横幅广告基本设置</h1></div>
		<div id="pageContent">
			<c:import url="../../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}floating_ad/settings/save" method="post" onsubmit="return check()" class="form-horizontal">
				<input name="id" type="hidden" value="${form.id}">
				<input name="_queryString" type="hidden" value="${param.queryString}">
                <div class="control-group required-field">
                    <label class="control-label">百分比:</label>
                    <div class="controls">
                        <input id="percent" name="percent" value="${fn:escapeXml(form.percent)}" type="text" class="input-large">
	                    <div class="msg" style="color:red"></div>
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
<script type="text/javascript">
	function check(){
		$(".msg").html("");
		var flag = true;
		var percent = $("#percent").val();
		var reg = /^(?:0|[1-9][0-9]?|100)$/;
	    if(!reg.test(percent)){
	    	$(".msg").html("必须输入0到100的整数");
	    	flag = false;
	    }
	    return flag;
	}
</script>
<!--

//-->
</script>
<c:import url="../../theme/${_theme}/footer.jsp"></c:import>
