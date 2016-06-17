<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="应用收益汇总" scope="request"/>
<c:set var="_underAppIncome" value="active" scope="request"/>
<c:set var="_activeStatistics" value="active" scope="request"/>
<c:set var="_module" value="statistics" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
	<div class="page-header"><h1>应用收益汇总</h1></div>
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
							<th>日期</th>
							<th>应用名</th>
							<th>请求次数</th>
                            <th>展示次数</th>
                            <th>点击次数</th>
                            <th>点击率</th>
                            <th>点击单价</th>
                            <th>收入(USD)</th>
                            <th>千次展示单价(USD)</th>
                            <th>操作</th>
						</tr>
						<c:forEach var="data" items="${datas}">
							<tr>
								<td>${data.date}</td>
								<td>${pkgMap[data.pkgName].appName==null?data.pkgName:pkgMap[data.pkgName].appName}</td>
								<td>${data.requestTimes}</td>
								<td>${data.showTimes}</td>
								<td>${data.clickTimes}</td>
								<td>${data.clickRate}%</td>
								<td>${data.clickPrice}</td>
								<td>${data.income/100}</td>
								<td>${data.showPrice}</td>
								<td>
									<a href="${basePath}app_income/list?pkgName=${data.pkgName}&startTime=${data.date}&endTime=${data.date}" class="btn btn-small btn-success"><i class="icon-plus-sign icon-white"></i>详情</a>
								</td>
							</tr>
						</c:forEach>
							<tr>
								<td>合计</td>
								<td>全部</td>
								<td>${sum.requestTimes}</td>
								<td>${sum.showTimes}</td>
								<td>${sum.clickTimes}</td>
								<td>${sum.clickRate}%</td>
								<td>${sum.clickPrice}</td>
								<td>${sum.income/100}</td>
								<td>${sum.showPrice}</td>
								<td></td>
							</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<div class="row-fluid">
            <div class="span4 toolbar"></div>
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
    function doExport(){
    	document.getElementById("search_form").action="${basePath}app_income_summary/export";
    	document.getElementById("search_form").submit();
    }
    function doQuery(){
    	document.getElementById("search_form").action="${basePath}app_income_summary/list";
    	document.getElementById("search_form").submit();
    }
    
</script>
