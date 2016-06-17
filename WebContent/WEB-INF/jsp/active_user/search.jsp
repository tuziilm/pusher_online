<%@ include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="${basePath}static/jquery/jquery-ui.css"/>
<div id="search">
    <form id="search_form" method="post"
          class="form-inline pull-right" action="">
         <label>国家:</label>
        <select name="country" id="country_sel" class="input-small">
        <option value="">全部</option>
            <option value="all">合计</option>
            <c:forEach items="${countries}" var="c">
                <option value="${c.shortcut}">${c.firstLetterOfChineseName}-${c.chineseName}</option>
            </c:forEach>
        </select>
        <script type="text/javascript">
            document.getElementById("country_sel").value = '${pusher:defVal(param.country,"")}';
        </script>
        <label>From:</label>
        <select name="from" id="from_sel" class="input-small">
            <option value="">全部</option>
            <option value="all">合计</option>
            <c:forEach items="${froms}" var="from">
                <option value="${from.code}">${from.name}</option>
            </c:forEach>
        </select>
        <script type="text/javascript">
            document.getElementById("from_sel").value = '${pusher:defVal(param.from,"")}';
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
