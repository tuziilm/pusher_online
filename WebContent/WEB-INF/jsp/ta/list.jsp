<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="任务操作统计" scope="request"/>
<c:set var="_underTaskAction" value="active" scope="request"/>
<c:set var="_activeStatistics" value="active" scope="request"/>
<c:set var="_module" value="statistics" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
	<div class="page-header"><h1>任务操作统计</h1></div>
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
							<th>任务名</th>
							<th>广告主</th>
							<th>国家</th>
							<th>操作</th>
                            <th>次数</th>
						</tr>
						<c:forEach var="data" items="${datas}">
							<tr>
								<td>${data.taskId=="all"?"所有任务":data.module=="TASK_ACTION_DAY_1"?tasksMap[pusher:atoi(data.taskId,0)].name:desktopMap[pusher:atoi(data.taskId,0)].name}</td>
                                <td>${adOwnerId==null?"全部":adOwnerMap[adOwnerId].name}</td>
                                <td>${data.country=="all"?"所有国家":countryMap[data.country].chineseName}</td>
								<td>${statActionTypeMap[data.actionId].name}</td>
								<td>${data.count}</td>
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
    	document.getElementById("search_form").action="${basePath}ta/export";
    	document.getElementById("search_form").submit();
    }
    function doQuery(){
    	document.getElementById("search_form").action="${basePath}ta/list";
    	document.getElementById("search_form").submit();
    }
    
</script>
