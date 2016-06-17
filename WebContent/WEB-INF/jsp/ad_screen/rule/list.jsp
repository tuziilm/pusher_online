<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="插屏广告应用规则列表" scope="request"/>
<c:set var="_underAdScreenRule" value="active" scope="request"/>
<c:set var="_activeAdScreen" value="active" scope="request"/>
<c:set var="_module" value="adScreen" scope="request"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>
<script type="text/javascript" src="${basePath}static/theme/${_theme}/global.js"></script>
<!-- main content -->
		<div class="page-header"><h1>插屏广告应用规则列表</h1></div>
		<c:import url="../../theme/default/name_search.jsp">
            <c:param name="action" value="${basePath}ad_screen/rule/list"/>
        </c:import>
        <br/><br/>
		<div id="list">
			<table class="table table-bordered table-striped">
				<c:choose>
					<c:when test="${not hasDatas}">
						<tr>
							<td>没有插屏广告应用规则记录!</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th></th>
							<th>编号</th>
							<th>名称</th>
                            <th>包名</th>
                            <th>状态</th>
                            <th>备注</th>
						</tr>
						<c:forEach var="data" items="${datas}" varStatus="it">
							<tr>
								<td class="checkbox_td">
									<input type="checkbox" name="ids" value="${data.id}"/>
								</td>
								<td>${fn:escapeXml(data.id)}</td>
								<td>${fn:escapeXml(data.name)}</td>
                                <td>${fn:escapeXml(data.pkgName)}</td>
                                <td>${data.status==1?"推广":data.status==2?"测试":"暂停"}</td>
                                <td>${fn:escapeXml(data.remark)}</td>
							</tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		<div class="row-fluid">
			<div class="span4 toolbar">
				<c:import url="../../theme/${_theme}/toolbar.jsp">
					<c:param name="create">${basePath}ad_screen/rule/create</c:param>
                    <c:param name="delete">${basePath}ad_screen/rule/delete</c:param>
					<c:param name="modify">${basePath}ad_screen/rule/modify</c:param>
				</c:import>
			</div>
			<div class="span8 paginator">
				<c:import url="../../theme/${_theme}/paginator.jsp"></c:import>
			</div>
		</div>
<!-- end main content -->
<c:import url="../../theme/${_theme}/footer.jsp"></c:import>