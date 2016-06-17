<%@include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="终端用户查询" scope="request"/>
<c:set var="_underUser" value="active" scope="request"/>
<c:set var="_activeUser" value="active" scope="request"/>
<c:set var="_module" value="user" scope="request"/>
<c:import url="../theme/${_theme}/header.jsp"></c:import>
<!-- main content -->
		<div class="page-header"><h1>终端用户</h1></div>
		<c:import url="search.jsp"/>
		<div id="list">
			<table class="table table-bordered table-striped">
				<c:choose>
					<c:when test="${not hasDatas}">
						<tr>
							<td>没有相应的终端用户!</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th>ID</th>
							<th>客户</th>
							<th>Mac</th>
							<th>WIFI</th>
							<th>应用版本</th>
                            <th>安卓版本</th>
                            <th>SDK</th>
                            <th>国家</th>
							<th>平板</th>
							<th>操作</th>
						</tr>
						<c:forEach var="data" items="${datas}" varStatus="status">
							<tr>
								<td>${data.identity}</td>
								<td>${fn:escapeXml(data.from)}</td>
								<td>${fn:escapeXml(data.mac)}</td>
								<td>${data.network}</td>
								<td>${fn:escapeXml(data.version)}</td>
                                <td>${fn:escapeXml(data.androidVer)}</td>
                                <td>${fn:escapeXml(data.androidLevel)}</td>
                                <td>${fn:escapeXml(data.simCountry)}</td>
								<td>${fn:escapeXml(data.pad?"是":"否")}</td>
								<td class="operation operand1">
									<a class="btn btn-small btn-info" onclick="javascript:showDetail(${status.count},this);return false;"><i class="icon-plus-sign icon-white"></i>详情</a>
								</td>
							</tr>
							<tr id="detail_${status.count}" style="display: none">
								<td colspan="10">
									<ul>
										<li><strong>定位：</strong>${fn:escapeXml(data.loc)}</li>
                                        <li><strong>imei：</strong>${fn:escapeXml(data.imei)}</li>
										<li><strong>androidId：</strong>${fn:escapeXml(data.androidId)}</li>
                                        <li><strong>SDCARD空间：</strong>${fn:escapeXml(data.sdcardCountSpare)}</li>
                                        <li><strong>SDCARD剩余空间：</strong>${fn:escapeXml(data.sdcardAvailableSpare)}</li>
                                        <li><strong>系统空间：</strong>${fn:escapeXml(data.systemCountSpare)}</li>
                                        <li><strong>系统剩余空间：</strong>${fn:escapeXml(data.systemAvailableSpare)}</li>
                                        <li><strong>品牌：</strong>${fn:escapeXml(data.brand)}</li>
                                        <li><strong>型号：</strong>${fn:escapeXml(data.model)}</li>
									</ul>
								</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<div class="row-fluid">
			<div class="span4 toolbar">
			</div>
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
</script>
