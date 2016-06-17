<%@ include file="../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" href="${basePath}static/jquery/jquery-ui.css"/>
<div id="search">
    <form id="search_form" method="post"
          class="form-inline pull-right" action="">
        <label>任务类型:</label>
        <select name="module" id="module_sel" class="input-medium" onchange="switchTask(this)">
            <option value="TASK_ACTION_DAY_1">Push任务</option>
            <option value="DESKTOP_ACTION_DAY_1">桌面快捷方式</option>
        </select>
        <script type="text/javascript">
            document.getElementById("module_sel").value = '${pusher:defVal(param.module,"TASK_ACTION_DAY_1")}';
        </script>
        <label>任务:</label>
        <select name="pushTaskId" id="pushTask_sel" class="input-small" style="display:${param.module=="DESKTOP_ACTION_DAY_1"?"none":"inline-block"}">
            <option value="">全部</option>
            <option value="all">合计</option>
            <c:forEach items="${tasks}" var="t">
                <option value="${t.id}">${t.name}</option>
            </c:forEach>
        </select>
        <script type="text/javascript">
            document.getElementById("pushTask_sel").value = '${pusher:defVal(param.pushTaskId,"")}';
        </script>
        <select name="desktopId" id="desktop_sel" class="input-small"  style="display: ${param.module=="DESKTOP_ACTION_DAY_1"?"inline-block":"none"}">
            <option value="">全部</option>
            <option value="all">合计</option>
            <c:forEach items="${desktops}" var="d">
                <option value="${d.id}">${d.name}</option>
            </c:forEach>
        </select>
        <script type="text/javascript">
            document.getElementById("desktop_sel").value = '${pusher:defVal(param.desktopId,"")}';
        </script>
        <label>广告主:</label>
        <select name="adOwnerId" id="adOwner_sel" class="input-small">
            <option value="">全部</option>
            <c:forEach items="${adOwners}" var="adOwner">
                <option value="${adOwner.id}">${adOwner.name}</option>
            </c:forEach>
        </select>
        <script type="text/javascript">
            document.getElementById("adOwner_sel").value = '${pusher:defVal(param.adOwnerId,"")}';
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
            document.getElementById("country_sel").value = '${pusher:defVal(param.country,"")}';
        </script>
        <label>用户操作:</label>
         <select id="actionType_sel" name="actionId" class="input-small">
         	<option value="">全部</option>
            <option value="all">合计</option>
             <c:forEach items="${statActionTypes}" var="t">
                 <option value="${t.id}">${t.name}</option>
             </c:forEach>
		</select>
		<script type="text/javascript">
            document.getElementById("actionType_sel").value = '${pusher:defVal(param.actionId,"")}';
        </script>
        <label>时间:</label>
        <input value="${pusher:defVal(param.startTime,pusher:yesterdayString("yyyy/MM/dd"))}" type="text" name="startTime" class="input-small" id="startTime"/>
        <input type="button" class="btn" value="查询" onclick="javascript:doQuery()"/>
        <input onclick="javascript:doExport()" type="button" class="btn" value="导出excel">
    </form>
</div>
<script>
    function switchTask(elem){
        if(elem.value=="DESKTOP_ACTION_DAY_1"){
            document.getElementById("pushTask_sel").style.display="none";
            document.getElementById("desktop_sel").style.display="inline-block";
        }else{
            document.getElementById("pushTask_sel").style.display="inline-block";
            document.getElementById("desktop_sel").style.display="none";
        }
        document.getElementById("pushTask_sel").value="";
        document.getElementById("desktop_sel").value="";
    }
</script>
