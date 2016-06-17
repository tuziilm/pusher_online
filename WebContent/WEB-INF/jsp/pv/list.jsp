<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="链接PV/UV统计" scope="request"/>
<c:set var="_underStatistics" value="active" scope="request"/>
<c:set var="_activeStatistics" value="active" scope="request"/>
<c:set var="_module" value="statistics" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
	<div class="page-header"><h1>链接PV/UV统计</h1></div>
	<c:import url="search.jsp">
	</c:import>
    <form style="display: none" id="link_node_form" action="${basePath}pv/list" method="POST">
    </form>
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
							<th>名称</th>
							<th>国家</th>
							<th>客户</th>
                            <th>PV</th>
                            <th>UV</th>
						</tr>
						<c:forEach var="data" items="${datas}">
							<tr>
								<td>${data.code=="all"?"合计":linkNodeMap[data.code].name}</td>
                                <td>${data.country=="all"?"合计":countryMap[data.country].chineseName}</td>
								<td>${data.from=="all"?"合计":data.from}</td>
								<td>${data.pv}</td>
                                <td>${data.uv}</td>
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
<c:import url="../theme/${_theme}/footer.jsp"></c:import>
<script src="${basePath}static/jquery/jquery-ui.js"></script>
<script src="${basePath}static/jquery/jquery.ui.datepicker-zh-TW.js"></script>
<script>
    $(function() {
        $( "#startTime" ).datepicker( $.datepicker.regional[ "zh-TW" ] );
        $( "#endTime" ).datepicker( $.datepicker.regional[ "zh-TW" ] );
    });
    function doExport(){
    	document.getElementById("search_form").action="${basePath}pv/export";
    	document.getElementById("search_form").submit();
    }
    function doQuery(){
    	document.getElementById("search_form").action="${basePath}pv/list";
    	document.getElementById("search_form").submit();
    }
</script>
