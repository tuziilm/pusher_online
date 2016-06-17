<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="广告方数据" scope="request"/>
<c:set var="_underAdOwnerData" value="active" scope="request"/>
<c:set var="_activeStatistics" value="active" scope="request"/>
<c:set var="_module" value="statistics" scope="request"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
	<div class="page-header"><h1>广告方数据</h1></div>
	<c:import url="search.jsp">
	</c:import>
    <form style="display: none" id="link_node_form" action="${basePath}data_statistics/adOwner/list" method="POST">
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
							<th></th>
							<th>日期</th>
							<th>广告方</th>
							<th>方式</th>
							<th>from</th>
							<th>日活</th>
							<th>有效日活</th>
							<th>请求</th>
                            <th>展示</th>
                            <th>请求率</th>
                            <th>点击</th>
                            <th>点击率</th>
                            <th>收入</th>
                            <th>单价</th>
                            <th>ECPM</th>
                            <th>详情</th>
						</tr>
						<c:forEach var="data" items="${datas}"  varStatus="it">
							<tr>
								<td class="checkbox_td">
									<input type="checkbox" name="ids" value="${data.id}"/>
								</td>
								<td>${data.date}</td>
                                <td>${data.adOwner}</td>
                                <td>${data.showWay=="all"?"合计":(data.showWay=="banner"?"横幅":(data.showWay=="screen"?"插屏":(data.showWay=="video"?"视屏":"开锁屏广告")))}</td>
                              	<td>${data.from}</td>
                                <td>${data.activeUser}</td>
                                <td>${data.validActiveUser}</td>
								<td>${data.requestTimes}</td>
								<td>${data.showTimes}</td>
								<td>${data.requestSuccessRate}%</td>
                                <td>${data.clickTimes}</td>
                                <td>${data.clickRate}%</td>
                                <td>${data.income/100}</td>
                                <td>${data.clickPrice}</td>
                                <td>${data.showPrice}</td>
                                <td class="operation operand1">
									<a class="btn btn-small btn-info" onclick="javascript:showDetail(${it.count},this);return false;"><i class="icon-plus-sign icon-white"></i>详情</a>
								</td>
							</tr>
							<tr id="detail_${it.count }" style="display: none">
								<td></td>
								<td colspan="16">
									<ul>
										<li><strong>对方请求：</strong>${data.otherRequestTimes}</li>
										<li><strong>对方展示：</strong>${data.otherShowTimes}</li>
										<li><strong>对方点击：</strong>${data.otherClickTimes}</li>
									</ul>
								</td>
							</tr>
						</c:forEach>
							<tr>
								<td>
								</td>
								<td>合计</td>
                                <td>全部</td>
                                <td>全部</td>
                                <td>全部</td>
                                <td></td>
                                <td></td>
								<td>${sum.requestTimes}</td>
								<td>${sum.showTimes}</td>
								<td>${sum.requestSuccessRate}%</td>
                                <td>${sum.clickTimes}</td>
                                <td>${sum.clickRate}%</td>
                                <td>${sum.income/100}</td>
                                <td>${sum.clickPrice}</td>
                                <td>${sum.showPrice}</td>
                                 <td class="operation operand1">
									<a class="btn btn-small btn-info" onclick="javascript:showDetail('sum',this);return false;"><i class="icon-plus-sign icon-white"></i>详情</a>
								</td>
							</tr>
							<tr id="detail_sum" style="display: none">
								<td></td>
								<td colspan="16">
									<ul>
										<li><strong>对方请求：</strong>${sum.otherRequestTimes}</li>
										<li><strong>对方展示：</strong>${sum.otherShowTimes}</li>
										<li><strong>对方点击：</strong>${sum.otherClickTimes}</li>
									</ul>
								</td>
							</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<div class="row-fluid">
            <div class="span4 toolbar">
            	<c:import url="../../theme/${_theme}/toolbar.jsp">
					<c:param name="modify">${basePath}data_statistics/adOwner/modify</c:param>
				</c:import></div>
			<div class="span8 paginator">
				<c:import url="../../theme/${_theme}/paginator.jsp"></c:import>
			</div>
		</div>
<!-- end main content -->
<c:import url="../../theme/${_theme}/footer.jsp"></c:import>
<script src="${basePath}static/jquery/jquery-ui.js"></script>
<script src="${basePath}static/jquery/jquery.ui.datepicker-zh-TW.js"></script>
<script>
    $(function() {
        $( "#startTime" ).datepicker( $.datepicker.regional[ "zh-TW" ] );
        $( "#endTime" ).datepicker( $.datepicker.regional[ "zh-TW" ] );
    });
    function doExport(){
    	document.getElementById("search_form").action="${basePath}data_statistics/adOwner/export";
    	document.getElementById("search_form").submit();
    }
    function doQuery(){
    	document.getElementById("search_form").action="${basePath}data_statistics/adOwner/list";
    	document.getElementById("search_form").submit();
    }
</script>
