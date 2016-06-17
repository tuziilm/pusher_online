<%@ include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="${basePath}static/jquery/jquery-ui.css"/>
<div id="search">
    <form id="search_form" action="${basePath}pv/list" method="post"
          class="form-inline pull-right">
        <label>链接:</label>
        <select name="code" id="code_sel" class="input-small">
            <option value="">全部</option>
            <option value="all">合计</option>
            <c:forEach items="${linkNodes}" var="ln">
                <option value="${ln.code}">${ln.name}</option>
            </c:forEach>
        </select>
        <script type="text/javascript">
            document.getElementById("code_sel").value = '${pusher:defVal(param.code,"all")}';
        </script>
        <label>国家:</label>
        <select name="country" id="country_sel" class="input-small">
            <option value="">全部</option>
            <option value="all">合计</option>
            <c:forEach items="${countries}" var="c">
                <option value="${c.shortcut}">${c.firstLetterOfChineseName}-${c.chineseName}</option>
            </c:forEach>
        </select>
        <script type="text/javascript">
            document.getElementById("country_sel").value = '${pusher:defVal(param.country,"all")}';
        </script>
        <label>客户:</label>
        <input value="${empty param.from ? "all": param.from}" type="text" name="from" class="input-small"/>
        <label>时间:</label>
        <input value="${pusher:defVal(param.startTime,pusher:yesterdayString("yyyy/MM/dd"))}" type="text" name="startTime" class="input-small" id="startTime"/>
        <input type="button" class="btn" value="查询" onclick="javascript:doQuery()"/>
        <input onclick="javascript:doExport()" type="button" class="btn" value="导出excel">
    </form>
</div>
