<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="链接广告管理" scope="request"/>
<c:set var="_underAdvLink" value="active" scope="request"/>
<c:set var="_activeAdv" value="active" scope="request"/>
<c:set var="_module" value="adv" scope="request"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>
<script type="text/javascript" src="${basePath}static/theme/${_theme}/global.js"></script>
<!-- main content -->
		<div class="page-header"><h1>链接广告</h1></div>
		<c:import url="../../theme/default/name_search.jsp">
            <c:param name="action" value="${basePath}adv/link/list"/>
        </c:import>
        <br/><br/>
		<div id="list">
			<table class="table table-bordered table-striped">
				<c:choose>
					<c:when test="${not hasDatas}">
						<tr>
							<td>没有链接广告记录!</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th></th>
							<th>编号</th>
							<th>名称</th>
							<th>链接</th>
							<th>广告主</th>
							<th>单价</th>
							<th>类型</th>
                            <th>操作</th>
						</tr>
						<c:forEach var="data" items="${datas}" varStatus="it">
							<tr>
								<td class="checkbox_td">
									<input type="checkbox" name="ids" value="${data.id}"/>
								</td>
								<td>${fn:escapeXml(data.id)}</td>
								<td>${fn:escapeXml(data.name)}</td>
								<td>
                                    <a href="${fn:escapeXml(data.url)}" target="_blank">链接</a>|
                                    <c:choose>
                                        <c:when test="${not empty data.imgPath}">
                                            <a href="${basePath}public/file/${fn:escapeXml(data.imgPath)}" target="_blank">图片</a>
                                        </c:when>
                                        <c:otherwise>
                                            无图
                                        </c:otherwise>
                                    </c:choose>
                                </td>
								<td>${fn:escapeXml(adOwnersMap[data.adOwner].name)}</td>
								<td>${fn:escapeXml(data.price)}</td>
								<td>${fn:escapeXml(appTypeMap[data.type].name)}</td>
								<td class="operation operand1">
									<a class="btn btn-small btn-info" onclick="javascript:showDetail(${it.count},this);return false;"><i class="icon-plus-sign icon-white"></i>详情</a>
								</td>
							</tr>
							<tr id="detail_${it.count }" style="display: none">
								<td></td>
								<td colspan="8">
									<ul>
                                        <li><strong>创建时间：</strong><fmt:formatDate value="${data.gmtCreate}" pattern="yyyy/MM/dd HH:mm:ss" var="gmtCreate"/>${gmtCreate}</li>
                                        <li><strong>更新时间：</strong><fmt:formatDate value="${data.gmtModified}" pattern="yyyy/MM/dd HH:mm:ss" var="gmtModified"/>${gmtModified}</li>
										<li>
                                            <strong>国家：</strong>
                                            <c:forEach var="country" items="${data.supportCountriesObject}" varStatus="vs">
                                                <span><c:if test="${not vs.first}">,</c:if>${countryMap[country].chineseName}</span>
                                            </c:forEach>
                                        </li>
										<li><strong>备注：</strong>${fn:escapeXml(data.remark )}</li>
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
					<c:param name="create">${basePath}adv/link/create</c:param>
					<c:param name="delete">${basePath}adv/link/delete</c:param>
					<c:param name="modify">${basePath}adv/link/modify</c:param>
				</c:import>
			</div>
			<div class="span8 paginator">
				<c:import url="../../theme/${_theme}/paginator.jsp"></c:import>
			</div>
		</div>
<!-- end main content -->
<c:import url="../../theme/${_theme}/footer.jsp"></c:import>