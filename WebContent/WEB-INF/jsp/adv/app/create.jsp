<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="创建应用" scope="request"/>
<c:set var="_underAdvApp" value="active" scope="request"/>
<c:set var="_activeAdv" value="active" scope="request"/>
<c:set var="_module" value="adv" scope="request"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
		<div class="page-header"><h1>创建/修改应用</h1></div>
		<div id="pageContent">
			<c:import url="../../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}adv/app/save" method="post" class="form-horizontal" enctype="multipart/form-data" onsubmit="return onSubmitApp(this);">
				<input name="id" type="hidden" value="${form.id}">
				<input name="_queryString" type="hidden" value="${param.queryString}">
                <div class="control-group required-field">
                    <label class="control-label">名称:</label>
                    <div class="controls">
                        <input name="name" value="${fn:escapeXml(form.name)}" type="text" class="input-large">
                    </div>
                </div>
				<div class="control-group required-field">
				  <label class="control-label">下载量:</label>
				  <div class="controls">
				    <input name="dlNum" value="${fn:escapeXml(form.dlNum)}" type="text" class="input-large">
				  </div>
				</div>
				<div class="control-group required-field">
				  <label class="control-label">跳转类型:</label>
				  <div class="controls">
				    <select id="redirectType_sel" name="redirectType" class="input-large" onchange="javascript:changeType();">
					  		<option value="0" ${fn:escapeXml(form.redirectType) == 0 ?'selected="selected"':"" }>系统服务器</option>
					  		<option value="1" ${fn:escapeXml(form.redirectType) == 1 ?'selected="selected"':"" }>Gp服务器</option>
					  		<option value="2" ${fn:escapeXml(form.redirectType) == 2 ?'selected="selected"':"" }>浏览器</option>
					 		<option value="3" ${fn:escapeXml(form.redirectType) == 3 ?'selected="selected"':"" }>AdJust链接</option>
					 </select>
				   </div>
				</div>
				<div id="_cg_file" class="control-group required-field">
				  <label class="control-label">应用路径:</label>
				  <div class="controls">
				    <input name="file" type="file"/>
				  </div>
				</div>
				<div id="_cg_img" class="control-group required-field">
				  <label class="control-label">图片路径:</label>
				  <div class="controls">
				    <input name="imgFile" type="file"/>
				  </div>
				</div>
				<div id="_cg_size" class="control-group required-field">
				  <label class="control-label">应用大小:</label>
				  <div class="controls">
				    <input id="appSize" name="appSize" value="${fn:escapeXml(form.appSize)}" type="text" class="input-large">
				  	<span class="remark" id="redirectType_remark"></span>
				  </div>
				</div>
				<div id="_cg_link" class="control-group required-field">
				  <label class="control-label">应用链接:</label>
				  <div class="controls">
				    <input id="appPath" name="appPath" value="${fn:escapeXml(form.appPath)}" type="text" class="input-large">
				  </div>
				</div>
				<div id="_cg_pkg" class="control-group required-field">
				  <label class="control-label">包名:</label>
				  <div class="controls">
				    <input id="appPackageName" name="appPackageName" value="${fn:escapeXml(form.appPackageName)}" type="text" class="input-large">
				  </div>
				</div>
				<div id="_cg_version" class="control-group required-field">
				  <label class="control-label">版本号:</label>
				  <div class="controls">
				    <input id="version" name="version" value="${fn:escapeXml(form.version)}" type="text" class="input-large">
				  </div>
				</div>
				<div id="_cg_label" class="control-group required-field">
				  <label class="control-label">手机上显示的应用名:</label>
				  <div class="controls">
				    <input id="appLabel" name="appLabel" value="${fn:escapeXml(form.appLabel)}" type="text" class="input-large">
				  </div>
				</div>
				<div class="control-group required-field">
					  <label class="control-label">广告主:</label>
					  <div class="controls">
					  	<select id="adOwner_sel" name="adOwner" class="input-large">
					  		<c:forEach var="adOwner" items="${adOwners}">
					  			<option value="${adOwner.id}" ${form.adOwner==adOwner.id?'selected="selected"':"" }>${adOwner.name}</option>
					  		</c:forEach>
					  	</select>
					  </div>
				</div>
				<div class="control-group required-field">
                    <label class="control-label">单价:</label>
                    <div class="controls">
                        <input name="price" value="${fn:escapeXml(form.price)}" type="text" class="input-large">
                    </div>
                </div>
                 <div class="control-group required-field">
                    <label class="control-label">应用类型:</label>
                    <div class="controls">
                        <div id="_appTypes">
                            <c:forEach var="appType" items="${appTypes}">
                                <span class="selLabel"><input ${form.type==appType.id?"checked=\"checked\"":""} type="radio" name="type" value="${appType.id}"><span>${appType.name}</span></span>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="control-group required-field">
				  <label class="control-label">自动下载:</label>
				  <div class="controls">
                      <select id="sel_autoDl" name="autoDl" class="input-small">
                          <option value="0">否</option>
                          <option value="1">是</option>
                      </select>
                      <script>document.getElementById("sel_autoDl").value="${empty form.autoDl?0:form.autoDl}"</script>
				  </div>
				</div>
				<div class="control-group required-field">
				  <label class="control-label">自动打开:</label>
				  <div class="controls">
                      <select id="sel_autoOpen" name="autoOpen" class="input-small">
                          <option value="0">否</option>
                          <option value="1">是</option>
                      </select>
                      <script>document.getElementById("sel_autoOpen").value="${empty form.autoOpen?0:form.autoOpen}"</script>
				  </div>
				</div>
				<div class="control-group required-field">
				  <label class="control-label">网络环境:</label>
				  <div class="controls">
                      <select id="sel_netEnvironment" name="netEnvironment" class="input-small">
                          <option value="ALL">全部</option>
                          <option value="WIFI">WIFI</option>
                          <option value="GPRS">GPRS</option>
                      </select>
                      <script>document.getElementById("sel_netEnvironment").value="${empty form.netEnvironment?'ALL':form.netEnvironment}"</script>
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
                                <span class="selLabel"><input ${pusher:contains(form.supportCountriesObject, country.shortcut)?"checked=\"checked\"":""} type="checkbox" name="supportCountriesObject" value="${country.shortcut}"><span>${country.chineseName}</span></span>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">描述:</label>
                    <div class="controls">
                        <textarea name="desc" class="input-large">${fn:escapeXml(form.desc)}</textarea>
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
<script>
	function onSubmitApp(){
		var type = $("#redirectType_sel").val();
		var appSize = $("#appSize").val();
		var appPath = $("#appPath").val();
		var appPackageName = $("#appPackageName").val();
		var version = $("#version").val(); 
		var appLabel = $("#appLabel").val();
		if(type!=0){
			if(/^\s*$/.test(appSize)){
                alert("大小不能为空");
                return false;
            }
			if(/^\s*$/.test(appPath)){
                alert("应用链接不能为空");
                return false;
            }
			if(/^\s*$/.test(appPackageName)){
                alert("包名不能为空");
                return false;
            }
			if(/^\s*$/.test(version)){
                alert("版本不能为空");
                return false;
            }
			if(/^\s*$/.test(appLabel)){
                alert("手机上显示的应用名不能为空");
                return false;
            }
		}
	}
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
        var type=$("#redirectType_sel").val();
        if(type=="0"){
            $("#_cg_file").show();
            $("#_cg_size").hide();
            $("#_cg_img").hide();
            $("#_cg_link").hide();
            $("#_cg_pkg").hide();
            $("#_cg_label").hide();
            $("#_cg_version").hide();
        }else {
        	 $("#_cg_file").hide();
             $("#_cg_size").show();
             $("#_cg_img").show();
             $("#_cg_link").show();
             $("#_cg_pkg").show();
             $("#_cg_label").show();
             $("#_cg_version").show();
        }
        if(type=="3"){
        	$("#redirectType_remark").html("必须填写真实大小");
        }else{
        	$("#redirectType_remark").html("");
        }
    }
    changeType();
    showSelected("_countries");
    showSelected("_appTypes");
</script> 