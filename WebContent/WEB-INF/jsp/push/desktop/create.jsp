<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="创建桌面快捷方式" scope="request"/>
<c:set var="_underPushDesktop" value="active" scope="request"/>
<c:set var="_activePush" value="active" scope="request"/>
<c:set var="_module" value="push" scope="request"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>
<link rel="stylesheet" href="${basePath}static/jquery/jquery-ui.css" />
<!-- main content -->
		<div class="page-header"><h1>创建/修改桌面快捷方式</h1></div>
		<div id="pageContent">
			<c:import url="../../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}push/desktop/save" method="post" class="form-horizontal" enctype="multipart/form-data">
				<input name="id" type="hidden" value="${form.id}">
				<input name="_queryString" type="hidden" value="${param.queryString}">
                <div class="control-group required-field">
                    <label class="control-label">名称:</label>
                    <div class="controls">
                        <input name="name" value="${fn:escapeXml(form.name)}" type="text" class="input-large">
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">优先级:</label>
                    <div class="controls">
                        <input name="priority" value="${fn:escapeXml(form.priority)}" type="text" class="input-large">
                        <span class="remark"> 小于0: 关闭推送，等于0：测试，大于0：开启推送，优先级越大越优先推送</span>
                    </div>
                </div> 
                <div class="control-group required-field">
                    <label class="control-label">类型:</label>
                    <div class="controls">
                        <select id="sel_type" name="type" class="input-small" onchange="javascript:changeType();">
                            <option value="1002">单应用</option>
                            <option value="1003">多应用</option>
                        </select>
                        <script>document.getElementById("sel_type").value="${form.type==null?'1002':form.type}"</script>
                    </div>
                </div>
                <div id="_cg_apps" class="control-group required-field">
                    <label class="control-label">应用:</label>
                    <div class="controls">
                        <div class="control-search-bar">
                            <input id="_app_kw" name="_app_kw" class="input-medium">
                            <input class="btn" type="button" onclick="javascript:searchSelected('_app_kw','_apps');" value="搜索">
                            <input class="btn" type="button" onclick="javascript:showSelected('_apps');" value="选择项">
                            <input type="checkbox" onchange="javascript:toggleAllSelected('_apps', this);">全选/全不选
                        </div>
                        <div id="_apps">
                            <c:forEach var="app" items="${apps}">
                                <span class="selLabel"><input ${pusher:contains(form.refIdsObject, app.id)?"checked=\"checked\"":""} type="checkbox" name="refIdsObject" value="${app.id}"><span>${app.name}</span></span>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">国家:</label>
                    <div class="controls">
                        <div class="control-search-bar">
                            <input id="_country_kw" name="_country_kw" class="input-medium">
                            <input class="btn" type="button" onclick="javascript:searchSelected('_country_kw','_countries');" value="搜索">
                            <input class="btn" type="button" onclick="javascript:showSelected('_countries');" value="选择项">
                            <input type="checkbox" onchange="javascript:toggleAllSelected('_countries', this);">全选/全不选
                        </div>
                        <div id="_countries">
                            <c:forEach var="country" items="${countries}">
                                <span class="selLabel"><input ${pusher:contains(form.countriesObject, country.shortcut)?"checked=\"checked\"":""} type="checkbox" name="countriesObject" value="${country.shortcut}"><span>${country.chineseName}</span></span>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div id="_cg_mtitle" class="control-group required-field">
                    <label class="control-label">多应用标题:</label>
                    <div class="controls">
                        <input id="_mtitle" name="mtitle" value="${fn:escapeXml(form.mtitle)}" type="text" class="input-large">
                        <span class="remark">在应用列表里面的标题显示</span>
                    </div>
                </div>
                <div class="control-group required-field">
				  <label class="control-label">图标:</label>
				  <div class="controls">
				    <input name="imgFile" type="file"/>
				    <span class="remark">大小：96*96</span>
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
<script src="${basePath}static/jquery/jquery-ui.js"></script>
<script>
    function searchSelected(kwElemId, resultDivId){
        var kw = $("#"+kwElemId).val();
        $("#"+resultDivId+" .selLabel").each(function(){
            if(($("span",this).html()+"").indexOf(kw)==-1){
                $(this).hide();
            }else{
                $(this).show();
            }
        });
    }
    function showSelected(resultDivId){
        $("#"+resultDivId+" .selLabel").each(function(){
            if($("input[type=checkbox]",this)[0].checked){
                $(this).show();
            }else{
                $(this).hide();
            }
        });
    }
    function toggleAllSelected(resultDivId, elem){
        $("#"+resultDivId+" .selLabel").each(function(){
            if($(this).is(":visible")){
                $($("input[type=checkbox]",this)[0]).attr("checked",elem.checked);
            }
        });
    }

    function changeType(){
        var type=$("#sel_type").val();
        if(type=="1001"){
            $("#_cg_apps").hide();
            $("#_apps .selLabel").each(function(){
                $("input[type=checkbox]",this).each(function(){
                    $(this).attr("checked",false);
                });
            });
            $("#_cg_links").show();
        }else {
            $("#_cg_links").hide();
            $("#_links .selLabel").each(function(){
                $("input[type=checkbox]",this).each(function(){
                    $(this).attr("checked",false);
                });
            });
            $("#_cg_apps").show();
        }
        if(type=="1003"){
            $("#_cg_mtitle").show();
        }else{
            $("#_mtitle").val("");
            $("#_cg_mtitle").hide();
        }
    }
    changeType();
    showSelected("_apps");
    showSelected("_links");
    showSelected("_countries");
</script>
