<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="创建应用推送" scope="request"/>
<c:set var="_underPushTask" value="active" scope="request"/>
<c:set var="_activePush" value="active" scope="request"/>
<c:set var="_module" value="push" scope="request"/>
<jsp:useBean id="curTime" class="java.util.Date" scope="request" />
<fmt:formatDate pattern="yyyy/MM/dd" var="now" value="${curTime}"/>
<fmt:formatDate pattern="yyyy/MM/dd" var="startDate" value="${form.startDate}"/>
<fmt:formatDate pattern="yyyy/MM/dd" var="endDate" value="${form.endDate}"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>
<link rel="stylesheet" href="${basePath}static/jquery/jquery-ui.css" />
<!-- main content -->
		<div class="page-header"><h1>创建/修改应用推送</h1></div>
		<div id="pageContent">
			<c:import url="../../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}push/task/save" method="post" class="form-horizontal" enctype="multipart/form-data">
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
                        <input name="priority" value="${fn:escapeXml(empty form.priority? 0 : form.priority)}" type="text" class="input-large">
                        <span class="remark"> 小于0: 关闭推送，等于0：测试，大于0：开启推送，优先级越大越优先推送</span>
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">开始天数:</label>
                    <div class="controls">
                        <input name="startDays" value="${fn:escapeXml(empty form.startDays? 0 : form.startDays)}" type="text" class="input-large">
                        <span class="remark">距离用户安装后开始执行推送的时间</span>
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">天最大请求量:</label>
                    <div class="controls">
                        <input name="maxReqCount" value="${fn:escapeXml(empty form.maxReqCount?0:form.maxReqCount)}" type="text" class="input-large">
                        <div class="remark">0:表示不限制请求量; 大于0：表示按天来控制该任务的请求量</div>
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">展示时间:</label>
                    <div class="controls">
                        <input name="showTime" value="${fn:escapeXml(form.showTime)}" type="text" class="input-large">
                        <span class="remark">格式：12:00-13:00</span>
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">任务周期:</label>
                    <div class="controls">
                        <input value="${pusher:defVal(startDate,now)}" type="text" name="startDate" class="input-small" id="startDate"/>~
                        <input value="${pusher:defVal(endDate,now)}" type="text" name="endDate" class="input-small" id="endDate"/>
                    </div>
                </div>
				<div class="control-group required-field">
				  <label class="control-label">自动显示:</label>
				  <div class="controls">
                      <select id="sel_autoShow" name="autoShow" class="input-small">
                          <option value="n">否</option>
                          <option value="y">是</option>
                      </select>
                      <script>document.getElementById("sel_autoShow").value="${empty form.autoShow?'n':form.autoShow}"</script>
				  </div>
				</div>
				<div class="control-group required-field">
				  <label class="control-label">自动清除:</label>
				  <div class="controls">
                      <select id="sel_shutDown" name="shutDown" class="input-small">
                          <option value="n">用户点击清除</option>
                          <option value="y">自动清除消失</option>
                      </select>
                      <script>document.getElementById("sel_shutDown").value="${empty form.shutDown?'n':form.shutDown}"</script>
				  </div>
				</div>
                <div class="control-group required-field">
                    <label class="control-label">类型:</label>
                    <div class="controls">
                        <select id="sel_type" name="type" class="input-small" onchange="javascript:changeType();">
                            <option value="1002">单应用</option>
                            <option value="1003">多应用</option>
                            <option value="1001">链接</option>
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
                <div id="_cg_links" class="control-group required-field">
                    <label class="control-label">链接:</label>
                    <div class="controls">
                        <div class="control-search-bar">
                            <input id="_link_kw" name="_link_kw" class="input-medium">
                            <input class="btn" type="button" onclick="javascript:searchSelected('_link_kw','_links');" value="搜索">
                            <input class="btn" type="button" onclick="javascript:showSelected('_links');" value="选择项">
                            <input type="checkbox" onchange="javascript:toggleAllSelected('_links', this);">全选/全不选
                        </div>
                        <div id="_links">
                            <c:forEach var="link" items="${links}">
                                <span class="selLabel"><input ${pusher:contains(form.refIdsObject, link.id)?"checked=\"checked\"":""} type="checkbox" name="refIdsObject" value="${link.id}"><span>${link.name}</span></span>
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
                                <c:if test="${param.test=='1' || (country.shortcut!='cn' && country.shortcut!='tw' && country.shortcut!='hk' && country.shortcut!='mo')}">
                                    <span class="selLabel"><input ${pusher:contains(form.countriesObject, country.shortcut)?"checked=\"checked\"":""} type="checkbox" name="countriesObject" value="${country.shortcut}"><span>${country.chineseName}</span></span>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">运营商:</label>
                    <div class="controls">
                        <textarea name="mccmncs" class="input-large">${fn:escapeXml(form.mccmncs)}</textarea>
                        <span class="remark">留空表示不对运营商限制；运营商使用MCC+MNC表示,MNC使用00来表示多网合一，多个值使用逗号分隔，如：46001,46002</span>
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">提示:</label>
                    <div class="controls">
                        <input name="tips" value="${fn:escapeXml(form.tips)}" type="text" class="input-large">
                        <span class="remark">在手机最上方的说明显示</span>
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">标题:</label>
                    <div class="controls">
                        <input name="title" value="${fn:escapeXml(form.title)}" type="text" class="input-large">
                        <span class="remark">在通知栏里面的标题显示</span>
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
                    <label class="control-label">内容:</label>
                    <div class="controls">
                        <textarea name="content" class="input-large">${fn:escapeXml(form.content)}</textarea>
                        <span class="remark">在通知栏里面的内容显示</span>
                    </div>
                </div>
				<div class="control-group required-field">
				  <label class="control-label">小图标:</label>
				  <div class="controls">
				    <input name="simgFile" type="file"/>
				    <span class="remark">大小：96*96</span>
				  </div>
				</div>
				<div class="control-group required-field">
				  <label class="control-label">大图标:</label>
				  <div class="controls">
				    <input name="bimgFile" type="file"/>
				    <span class="remark">大小：750*200</span>
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
<script src="${basePath}static/jquery/jquery.ui.datepicker-zh-TW.js"></script>
<script>
    $(function() {
        $( "#startDate" ).datepicker( $.datepicker.regional[ "zh-TW" ] );
        $( "#endDate" ).datepicker( $.datepicker.regional[ "zh-TW" ] );
    });

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
