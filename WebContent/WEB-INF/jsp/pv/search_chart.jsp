<%@ include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="${basePath}static/jquery/jquery-ui.css"/>
<div id="search">
    <form id="search_form" action="${basePath}pv/chart" method="post"
          class="form-inline pull-right">
        <label>链接:</label>
        <select name="code" id="code_sel" class="input-small">
            <option value="all">所有链接</option>
            <c:forEach items="${linkNodes}" var="ln">
                <option value="${ln.code}">${ln.name}</option>
            </c:forEach>
        </select>
        <script type="text/javascript">
            document.getElementById("code_sel").value = '${pusher:defVal(param.code,"all")}';
        </script>
        <label>国家:</label>
        <select name="country" id="country_sel" class="input-small">
            <option value="all">所有国家</option>
            <c:forEach items="${countries}" var="c">
                <option value="${c.shortcut}">${c.firstLetterOfChineseName}-${c.chineseName}</option>
            </c:forEach>
        </select>
        <script type="text/javascript">
            document.getElementById("country_sel").value = '${pusher:defVal(param.country,"all")}';
        </script>
        <label>客户:</label>
        <input value="${empty param.from ? "all": param.from}" type="text" name="from" class="input-small"/>
        <label>开始时间:</label>
        <input value="${pusher:defVal(param.startTime,pusher:dateStringAboutNow("yyyy/MM/dd", -30))}" type="text"
               name="startTime" class="input-small" id="startTime"/>
        <label>截止时间:</label>
        <input value="${pusher:defVal(param.endTime,pusher:yesterdayString("yyyy/MM/dd"))}" type="text" name="endTime"
               class="input-small" id="endTime"/>
        <input type="submit" class="btn" value="显示"/>
    </form>
</div>
