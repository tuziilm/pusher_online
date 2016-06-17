<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="创建链接广告" scope="request"/>
<c:set var="_underAdvLink" value="active" scope="request"/>
<c:set var="_activeAdv" value="active" scope="request"/>
<c:set var="_module" value="adv" scope="request"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>

<!-- main content -->
		<div class="page-header"><h1>创建/修改链接广告</h1></div>
		<div id="pageContent">
			<c:import url="../../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}adv/link/save" method="post" class="form-horizontal" enctype="multipart/form-data">
				<input name="id" type="hidden" value="${form.id}">
				<input name="_queryString" type="hidden" value="${param.queryString}">
                <div class="control-group required-field">
                    <label class="control-label">名称:</label>
                    <div class="controls">
                        <input name="name" value="${fn:escapeXml(form.name)}" type="text" class="input-large">
                    </div>
                </div>
				<div class="control-group required-field">
				  <label class="control-label">链接:</label>
				  <div class="controls">
				    <input name="url" value="${fn:escapeXml(form.url)}" type="text" class="input-large">
				  </div>
				</div>
				<div class="control-group required-field">
				  <label class="control-label">图片路径:</label>
				  <div class="controls">
				    <input name="imgFile" type="file"/>
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
                    <label class="control-label">链接类型:</label>
                    <div class="controls">
                        <div id="_appTypes">
                            <c:forEach var="appType" items="${appTypes}">
                                <span class="selLabel"><input ${form.type==appType.id?"checked=\"checked\"":""} type="radio" name="type" value="${appType.id}"><span>${appType.name}</span></span>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="control-group required-field">
				  <label class="control-label">跳转类型:</label>
				  <div class="controls">
				    <select id="redirectType_sel" name="redirectType" class="input-large" onclick="javascript:show()">
					  		<option value="0" ${fn:escapeXml(form.redirectType) == 0 ?'selected="selected"':"" }>系统服务器</option>
					  		<option value="1" ${fn:escapeXml(form.redirectType) == 1 ?'selected="selected"':"" }>Gp服务器</option>
					  		<option value="2" ${fn:escapeXml(form.redirectType) == 2 ?'selected="selected"':"" }>浏览器</option>
					 		<option value="3" ${fn:escapeXml(form.redirectType) == 3 ?'selected="selected"':"" }>AdJust链接</option>
					 </select>
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

    showSelected("_countries");
    showSelected("_appTypes");
</script>  