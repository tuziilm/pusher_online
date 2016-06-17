<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="用户情况一览" scope="request"/>
<c:set var="_underActiveUser" value="active" scope="request"/>
<c:set var="_activeStatistics" value="active" scope="request"/>
<c:set var="_module" value="statistics" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
	<div class="page-header"><h1>用户情况一览</h1></div>
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
							<th>国家</th>
							<th>from</th>
							<th>日活跃用户</th>
							<th>有效日活</th>
                            <th>激活用户数</th>
                            <th>留存率</th>
						</tr>
						<c:forEach var="data" items="${results}">
							<tr>
								<td>${data.date}</td>
								<td>${data.country=="all"?"所有国家":countryMap[data.country].chineseName}</td>
								<td>${data.from}</td>
								<td>${data.activeUser}</td>
								<td>${data.validActiveUser}</td>
								<td>${data.addUser}</td>
								<td>${data.retentionRate}%</td>
							</tr>
						</c:forEach>
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
    	document.getElementById("search_form").action="${basePath}active_user/export";
    	document.getElementById("search_form").submit();
    }
    function doQuery(){
    	document.getElementById("search_form").action="${basePath}active_user/list";
    	document.getElementById("search_form").submit();
    }
    
</script>
