<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="应用列表管理" scope="request"/>
<c:set var="_underAdvApp" value="active" scope="request"/>
<c:set var="_activeAdv" value="active" scope="request"/>
<c:set var="_module" value="adv" scope="request"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>
<script type="text/javascript" src="${basePath}static/theme/${_theme}/global.js"></script>
<!-- main content -->
		<div class="page-header"><h1>应用</h1></div>
		<c:import url="../../theme/default/name_search.jsp">
            <c:param name="action" value="${basePath}adv/app/list"/>
        </c:import>
        <br/><br/>
		<div id="list">
			<table class="table table-bordered table-striped">
				<c:choose>
					<c:when test="${not hasDatas}">
						<tr>
							<td>没有应用记录!</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th></th>
							<th>编号</th>
							<th>名称</th>
							<th>显示名称</th>
                            <th>下载量</th>
							<th>版本号</th>
							<th>广告主</th>
							<th>单价</th>
							<th>应用类型</th>
							<th>操作</th>
						</tr>
						<c:forEach var="data" items="${datas}" varStatus="it">
							<tr>
								<td class="checkbox_td">
									<input type="checkbox" name="ids" value="${data.id}"/>
									<input name="appPath" type="hidden" value="${data.appPath}">
								</td>
								<td>${fn:escapeXml(data.id)}</td>
								<td>${fn:escapeXml(data.name)}</td>
								<td>${fn:escapeXml(data.appLabel)}</td>
                                <td>${fn:escapeXml(data.dlNum)}</td>
								<td>${fn:escapeXml(data.version)}</td>
								<td>${fn:escapeXml(adOwnersMap[data.adOwner].name)}</td>
								<td>${fn:escapeXml(data.price)}</td>
								<td>${fn:escapeXml(appTypeMap[data.type].name)}</td>
								<td class="operation operand2">
									<a class="btn btn-small btn-info" onclick="javascript:showDetail(${it.count},this);return false;"><i class="icon-plus-sign icon-white"></i>详情</a>
									<a href="${basePath}public/file/${data.appPath}?ofn=${fn:escapeXml(data.appFileName)}" class="btn btn-small btn-success"><i class="icon-download-alt icon-white"></i>下载</a>
								</td>
							</tr>
							<tr id="detail_${it.count }" style="display: none">
								<td></td>
								<td colspan="9">
									<ul>
										<li><strong>文件大小：</strong>${data.appSize}</li>
                                        <li><strong>创建时间：</strong><fmt:formatDate value="${data.gmtCreate}" pattern="yyyy/MM/dd HH:mm:ss" var="gmtCreate"/>${gmtCreate}</li>
                                        <li><strong>更新时间：</strong><fmt:formatDate value="${data.gmtModified}" pattern="yyyy/MM/dd HH:mm:ss" var="gmtModified"/>${gmtModified}</li>
										<li>
                                            <strong>国家：</strong>
                                            <c:forEach var="country" items="${data.supportCountriesObject}" varStatus="vs">
                                                <span><c:if test="${not vs.first}">,</c:if>${countryMap[country].chineseName}</span>
                                            </c:forEach>
                                        </li>
										<li><strong>备注：</strong>${fn:escapeXml(data.remark )}</li>
                                        <li><strong>描述：</strong>${fn:escapeXml(data.desc)}</li>
                                        <li><strong>自动下载：</strong>${data.autoDl=='1'?'是':'否'}</li>
                                        <li><strong>自动打开：</strong>${data.autoOpen=='1'?'是':'否'}</li>
                                        <li><strong>网络环境：</strong>${data.netEnvironment=='WIFI'?'WIFI':(data.netEnvironment=='GPRS'?'GPRS':'全部')}</li>
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
				<c:import url="../../theme/${_theme}/toolbar.jsp">
					<c:param name="create">${basePath}adv/app/create</c:param>
					<c:param name="delete">${basePath}adv/app/delete</c:param>
					<c:param name="modify">${basePath}adv/app/modify</c:param>
				</c:import>
			</div>
			<div class="span8 paginator">
				<c:import url="../../theme/${_theme}/paginator.jsp"></c:import>
			</div>
		</div>
<!-- end main content -->
<c:import url="../../theme/${_theme}/footer.jsp"></c:import>