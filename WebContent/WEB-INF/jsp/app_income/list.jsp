<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="应用收益明细" scope="request"/>
<c:set var="_underAppIncome" value="active" scope="request"/>
<c:set var="_activeStatistics" value="active" scope="request"/>
<c:set var="_module" value="statistics" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
	<div class="page-header"><h1>应用收益明细</h1></div>
	<c:import url="search.jsp">
	</c:import>
		<div id="list">
			<table class="table table-bordered table-striped">
				<c:choose>
					<c:when test="${not hasDatas}">
						<tr>
							<td>没有记录!</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th></th>
							<th>编号</th>
							<th>日期</th>
							<th>应用名</th>
							<th>广告方</th>
							<th>广告位</th>
							<th>请求次数</th>
                            <th>展示次数</th>
                            <th>点击次数</th>
                            <th>点击率</th>
                            <th>点击单价</th>
                            <th>收入(USD)</th>
                            <th>千次展示单价(USD)</th>
						</tr>
						<c:forEach var="data" items="${datas}">
							<tr>
								<td class="checkbox_td">
									<input type="checkbox" name="ids" value="${data.id}"/>
								</td>
								<td>${data.id}</td>
								<td>${data.date}</td>
								<td>${pkgMap[data.pkgName].appName==null?data.pkgName:pkgMap[data.pkgName].appName}</td>
								<td>${data.adOwner}</td>
								<td>${activitiesMap[data.pageName].name==null?data.pageName:activitiesMap[data.pageName].name}</td>
								<td>${data.requestTimes}</td>
								<td>${data.showTimes}</td>
								<td>${data.clickTimes}</td>
								<td>${data.clickRate}%</td>
								<td>${data.clickPrice}</td>
								<td>${data.income/100}</td>
								<td>${data.showPrice}</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<div class="row-fluid">
            <div class="span4 toolbar">
            	<c:import url="../theme/${_theme}/toolbar.jsp">
					<c:param name="modify">${basePath}app_income/modify</c:param>
				</c:import>
				<button type="button" class="btn" onclick="javascript:goSummary()">返回</button>
				</div>
			<div class="span8 paginator">
				<c:import url="../theme/${_theme}/paginator.jsp"></c:import>
			</div>
		</div>
<!-- end main content -->
<c:import url="../theme/${_theme}/footer.jsp">
</c:import>
<script src="${basePath}static/jquery/jquery-ui.js"></script>
<script src="${basePath}static/jquery/jquery.ui.datepicker-zh-TW.js"></script>
<script>
$(function() {
    $( "#startTime" ).datepicker( $.datepicker.regional[ "zh-TW" ] );
    $( "#endTime" ).datepicker( $.datepicker.regional[ "zh-TW" ] );
});
function goSummary(){
	location.href ="${basePath}app_income_summary/list";
}
function doExport(){
	document.getElementById("search_form").action="${basePath}app_income/export";
	document.getElementById("search_form").submit();
}
function doQuery(){
	document.getElementById("search_form").action="${basePath}app_income/list";
	document.getElementById("search_form").submit();
}
var pkgs={};
<c:forEach var="pkg" items="${pkgs}" varStatus="vs">
pkgs["${pkg.pkgName}"]=${pkg.jsonString};
</c:forEach>
function changeType(elem){
	var pageName = $("#pageName_sel");
	pageName.html("");
	var activities = pkgs[elem.value].activities;
	pageName.append("<option value=\"\">全部</option>");
	for(var i=0;i<activities.length;i++){
		pageName.append("<option value=\""+activities[i].className+"\">"+activities[i].name+"</option>");
	}
}
</script>
