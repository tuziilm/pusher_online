<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="应用数据相关" scope="request"/>
<c:set var="_underTopApp" value="active" scope="request"/>
<c:set var="_activeStatistics" value="active" scope="request"/>
<c:set var="_module" value="statistics" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
	<div class="page-header"><h1>TopN应用</h1></div>
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
							<th>应用名</th>
							<th>日活跃用户数</th>
                            <th>日使用次数</th>
						</tr>
						<c:forEach var="data" items="${datas}">
							<tr>
								<td>${data.appName}</td>
								<td>${data.activeUser}</td>
								<td>${data.useTimes}</td>
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
    function doExport(){
    	document.getElementById("search_form").action="${basePath}top_app/export";
    	document.getElementById("search_form").submit();
    }
    function doQuery(){
    	document.getElementById("search_form").action="${basePath}top_app/list";
    	document.getElementById("search_form").submit();
    }
    
</script>
