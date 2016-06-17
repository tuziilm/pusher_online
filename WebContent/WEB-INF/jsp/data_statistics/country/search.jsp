<%@ include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="${basePath}static/jquery/jquery-ui.css"/>
<div id="search">
    <form id="search_form" action="" method="post"
          class="form-inline pull-right">
        <label>广告方:</label>
        <select name="adOwner" id="adOwner_sel" class="input-small">
            <option value="">全部</option>
            <c:forEach items="${adOwners}" var="owner">
                <option value="${owner.code}">${owner.name}</option>
            </c:forEach>
        </select>
        <script type="text/javascript">
            document.getElementById("adOwner_sel").value = '${pusher:defVal(param.adOwner,"")}';
        </script>
        <label>国家:</label>
        <select name="country" id="country_sel" class="input-small">
            <option value="">全部</option>
            <c:forEach items="${countries}" var="c">
                <option value="${c.shortcut}">${c.firstLetterOfChineseName}-${c.chineseName}</option>
            </c:forEach>
        </select>
        <script type="text/javascript">
            document.getElementById("country_sel").value = '${pusher:defVal(param.country,"")}';
        </script>
		<label>开始时间:</label>
        <input value="${pusher:defVal(param.startTime,pusher:yesterdayString("yyyy/MM/dd"))}" type="text"
               name="startTime" class="input-small" id="startTime"/>
        <label>截止时间:</label>
        <input value="${pusher:defVal(param.endTime,pusher:yesterdayString("yyyy/MM/dd"))}" type="text" name="endTime"
               class="input-small" id="endTime"/>
        <input type="button" class="btn" value="查询" onclick="javascript:doQuery()"/>
        <input onclick="javascript:doExport()" type="button" class="btn" value="导出excel">
    </form>
</div>
