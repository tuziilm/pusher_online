<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="任务列表管理" scope="request"/>
<c:set var="_underPushTask" value="active" scope="request"/>
<c:set var="_activePush" value="active" scope="request"/>
<c:set var="_module" value="push" scope="request"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>
<script type="text/javascript" src="${basePath}static/theme/${_theme}/global.js"></script>
<!-- main content -->
		<div class="page-header"><h1>任务列表</h1></div>
		<c:import url="../../theme/default/name_search.jsp">
            <c:param name="action" value="${basePath}push/task/list"/>
        </c:import>
        <br/><br/>
		<div id="list">
			<table class="table table-bordered table-striped">
				<c:choose>
					<c:when test="${not hasDatas}">
						<tr>
							<td>没有任务记录!</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<th></th>
							<th>编号</th>
							<th>名称</th>
                            <th>优先级</th>
                            <th>开始天数</th>
                            <th>天最大量</th>
                            <th>天请求量</th>
							<th>类型</th>
                            <th>展示时间</th>
                            <th>任务周期</th>
                            <th>大图片</th>
                            <th>图标</th>
							<th>操作</th>
						</tr>
						<c:forEach var="data" items="${datas}" varStatus="it">
							<tr>
								<td class="checkbox_td">
									<input type="checkbox" name="ids" value="${data.id}"/>
								</td>
								<td>${fn:escapeXml(data.id)}</td>
								<td>${fn:escapeXml(data.name)}</td>
                                <td>${fn:escapeXml(data.priority)}</td>
                                <td>${data.startDays}</td>
                                <td>${data.maxReqCount}</td>
                                <td>${data.maxReqCount >0 ? usedReqCountMap[data.id] : "不限量"}</td>
								<td>${data.type==1001?"链接":(data.type==1002?"单应用":"多应用")}</td>
                                <td>${fn:escapeXml(data.showTime)}</td>
                                <td><fmt:formatDate value="${data.startDate}" pattern="yyyy/MM/dd" var="startDate"/>${startDate}~<fmt:formatDate value="${data.endDate}" pattern="yyyy/MM/dd" var="endDate"/>${endDate}</td>
                                <td><a href="${basePath}public/file/${data.picPath}" target="_blank">查看</a></td>
                                <td><a href="${basePath}public/file/${data.iconPath}" target="_blank">查看</a></td>
								<td class="operation operand1">
									<a class="btn btn-small btn-info" onclick="javascript:showDetail(${it.count},this);return false;"><i class="icon-plus-sign icon-white"></i>详情</a>
								</td>
							</tr>
							<tr id="detail_${it.count }" style="display: none">
								<td></td>
								<td colspan="9">
									<ul>
                                        <li><strong>标题：</strong>${fn:escapeXml(data.title)}</li>
                                        <c:if test="${data.type==1003}">
                                            <li><strong>多应用标题：</strong>${fn:escapeXml(data.mtitle)}</li>
                                        </c:if>
                                        <li><strong>提示：</strong>${fn:escapeXml(data.tips)}</li>
                                        <li><strong>内容：</strong>${fn:escapeXml(data.content)}</li>
                                        <li><strong>自动显示：</strong>${data.autoShow=='y'?'是':'否'}</li>
                                        <li><strong>自动关闭：</strong>${data.shutDown=='y'?'是':'否'}</li>
                                        <li>
                                        <c:choose>
                                            <c:when test="${data.type==1001}">
                                                <strong>链接：</strong>
                                                <c:forEach var="linkId" items="${data.refIdsObject}" varStatus="vs">
                                                    <span><c:if test="${not vs.first}">,</c:if>${linkMap[linkId].name}</span>
                                                </c:forEach>
                                            </c:when>
                                            <c:otherwise>
                                                <strong>应用：</strong>
                                                <c:forEach var="appId" items="${data.refIdsObject}" varStatus="vs">
                                                    <span><c:if test="${not vs.first}">,</c:if>${appMap[appId].name}</span>
                                                </c:forEach>
                                            </c:otherwise>
                                        </c:choose>
                                        </li>
                                        <li>
                                            <strong>国家：</strong>
                                            <c:forEach var="country" items="${data.countriesObject}" varStatus="vs">
                                                <span><c:if test="${not vs.first}">,</c:if>${countryMap[country].chineseName}</span>
                                            </c:forEach>
                                        </li>
                                        <li>
                                            <strong>运营商：</strong>
                                            <c:forEach var="mm" items="${data.mccmncsObject}" varStatus="vs">
                                                <span><c:if test="${not vs.first}">,</c:if>${mm}</span>
                                            </c:forEach>
                                        </li>
                                        <li><strong>创建时间：</strong><fmt:formatDate value="${data.gmtCreate}" pattern="yyyy/MM/dd HH:mm:ss" var="gmtCreate"/>${gmtCreate}</li>
                                        <li><strong>更新时间：</strong><fmt:formatDate value="${data.gmtModified}" pattern="yyyy/MM/dd HH:mm:ss" var="gmtModified"/>${gmtModified}</li>
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
					<c:param name="create">${basePath}push/task/create</c:param>
                    <c:param name="delete">${basePath}push/task/delete</c:param>
					<c:param name="modify">${basePath}push/task/modify</c:param>
				</c:import>
			</div>
			<div class="span8 paginator">
				<c:import url="../../theme/${_theme}/paginator.jsp"></c:import>
			</div>
		</div>
<!-- end main content -->
<c:import url="../../theme/${_theme}/footer.jsp"></c:import>