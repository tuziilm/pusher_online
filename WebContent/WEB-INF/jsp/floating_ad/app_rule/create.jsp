<%@include file="../../include/common.jsp" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="_pageTitle" value="创建横幅广告应用规则" scope="request"/>
<c:set var="_underFloatingAdAppRule" value="active" scope="request"/>
<c:set var="_activeFloatingAd" value="active" scope="request"/>
<c:set var="_module" value="floatingAd" scope="request"/>
<c:import url="../../theme/${_theme}/header.jsp"></c:import>
<link rel="stylesheet" href="${basePath}static/jquery/jquery-ui.css" />
<!-- main content -->
		<div class="page-header"><h1>创建/修改横幅广告应用规则</h1></div>
		<div id="pageContent">
			<c:import url="../../theme/${_theme}/errors.jsp"></c:import>
			<form action="${basePath}floating_ad/app_rule/save" onsubmit="javascript:onSubmitAppRuleForm()" method="post" class="form-horizontal">
				<input name="id" type="hidden" value="${form.id}">
                <input name="pageRules" type="hidden" id="pageRules">
				<input name="_queryString" type="hidden" value="${param.queryString}">
                <div class="control-group required-field">
                    <label class="control-label">名称:</label>
                    <div class="controls">
                        <input name="name" value="${fn:escapeXml(form.name)}" type="text" class="input-large">
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">包名:</label>
                    <div class="controls">
                        <input name="pkgName" value="${fn:escapeXml(form.pkgName)}" type="text" class="input-large">
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">页面规则:</label>
                    <div class="controls">
                        <div id="_div_page_rules">
                            <c:forEach var="pageRule" items="${form.pageRulesObject}" varStatus="vs">
                                <div class="page-rules">
                                    <div class="alert alert-success" style="display: inline-block;min-width: 300px;">
                                        <span class="close" data-dismiss="alert" onclick="javascript:deletePageRule(${vs.index})" ><i class="icon-trash"></i></span>
                                        <span class="close" onclick="javascript:editPageRule(${vs.index})" ><i class="icon-edit" style="margin-right: 10px;"></i></span>
                                        <ul>
                                            <li><span>页面类：</span><span id="_pr_activity_${vs.index}">${pageRule.activity}</span></li>
                                            <li><span>坐标：</span><span id="_pr_x_y_${vs.index}">(${pageRule.x},${pageRule.y})</span></li>
                                            <li><span>from：</span><span id="_pr_from_${vs.index}">${pageRule.from}</span></li>
                                            <li><span>App版本：</span><span id="_pr_appVer_${vs.index}">${pageRule.app_verAsString}</span></li>
                                            <li><span>分辨率：</span><span id="_pr_resolution_${vs.index}">${pageRule.resolutionAsString}</span></li>
                                       		<li><span>广告类型：</span><span id="_pr_sdkType_${vs.index}">${pageRule.sdk_type==1?"google admob":(pageRule.sdk_type==2?"yeahmobile":(pageRule.sdk_type==3?"inmobi":(pageRule.sdk_type==4?"airpush":"startApp")))}</span></li>
                                        	<li ${pageRule.sdk_type==1?"":'style="display:none"'}><span>admob_key：</span><span id="_pr_admob_key_${vs.index}">${pageRule.ad.key}</span></li>
                                        	<li ${pageRule.sdk_type==2?"":'style="display:none"'}><span>yeah_mobile_app_id：</span><span id="_pr_yeah_mobile_app_id_${vs.index}">${pageRule.ad.app_id}</span></li>
                                        	<li ${pageRule.sdk_type==2?"":'style="display:none"'}><span>yeah_mobile_slot_id：</span><span id="_pr_yeah_mobile_slot_id_${vs.index}">${pageRule.ad.slot_id}</span></li>
                                        	<li ${pageRule.sdk_type==3?"":'style="display:none"'}><span>inmobi_id：</span><span id="_pr_inmobi_id_${vs.index}">${pageRule.ad.id}</span></li>
                                        	<li ${pageRule.sdk_type==4?"":'style="display:none"'}><span>app_id：</span><span id="_pr_app_id_${vs.index}">${pageRule.ad.appid}</span></li>
                                        	<li ${pageRule.sdk_type==4?"":'style="display:none"'}><span>api_key：</span><span id="_pr_api_key_${vs.index}">${pageRule.ad.apikey}</span></li>
                                        	<li ${pageRule.sdk_type==5?"":'style="display:none"'}><span>start_appid：</span><span id="_pr_start_appid_${vs.index}">${pageRule.ad.start_appid}</span></li>
                                        	<li ${pageRule.sdk_type==5?"":'style="display:none"'}><span>start_developerid：</span><span id="_pr_start_developerid_${vs.index}">${pageRule.ad.start_developerid}</span></li>
                                        	<li><span>国家：</span><span id="_pr_countries_${vs.index}">${pageRule.countriesAsString}</span></li>
                                        </ul>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                        <a href="#_div_page_rule_modal" role="button" class="btn" data-toggle="modal" onclick="javascript:addPageRule()">新增页面规则</a>
                        <div id="_div_page_rule_modal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                <span class="remark">新增/修改页面规则</span>
                            </div>
                            <div class="modal-body">
                                <div class="control-group required-field">
                                    <label class="control-label">页面类:</label>
                                    <div class="controls">
                                        <input id="_pr_activity" name="activity" type="text" class="input-medium">
                                        <span class="remark">如：com.qq.weixin</span>
                                    </div>
                                </div>
                                <div class="control-group required-field">
                                    <label class="control-label">X坐标:</label>
                                    <div class="controls">
                                        <input id="_pr_x" name="x" type="text" class="input-small">
                                        <span class="remark">正整数，如：100</span>
                                    </div>
                                </div>
                                <div class="control-group required-field">
                                    <label class="control-label">Y坐标:</label>
                                    <div class="controls">
                                        <input id="_pr_y" name="y" type="text" class="input-small">
                                        <span class="remark">正整数，如：100</span>
                                    </div>
                                </div>
                                <div class="control-group required-field">
                                    <label class="control-label">from:</label>
                                    <div class="controls">
                                        <input id="_pr_from" name="from" type="text" class="input-small">
                                        <span class="remark">留空默认全推</span>
                                    </div>
                                </div>
                                <div class="control-group required-field">
                                    <label class="control-label">App版本:</label>
                                    <div class="controls">
                                        <input id="_pr_appVer" name="appVer" type="text" class="input-large">
                                        <div class="remark">多个版本以“,”分隔，'x'匹配多个版本，如：1.5.2,1.6.x,表示1.5.2版本和1.6的任意版本</div>
                                    </div>
                                </div>
                                <div class="control-group required-field">
                                    <label class="control-label">分辨率:</label>
                                    <div class="controls">
                                        <input id="_pr_resolution" name="resolution" type="text" class="input-large">
                                        <div class="remark">多个版本以“,”分隔，如：320x400,480x800</div>
                                    </div>
                                </div>
                                 <div class="control-group required-field">
                                    <label class="control-label">广告类型:</label>
                                    <div class="controls">
                                        <select name="sdkType" id="_pr_sdkType" class="input-large" onchange="javascript:changeType();">
								            <option value="1">google admob</option>
								            <option value="2">yeahmobile</option>
								            <option value="3">inmobi</option>
								            <option value="4">airpush</option>
								            <option value="5">startApp</option>
								        </select>
                                        <div class="remark">google admob,yeahmobile,inmobi,airpush和startApp</div>
                                    </div>
                                </div>
                                <div id="_cg_admob_key" class="control-group required-field">
				                    <label class="control-label">admob_key:</label>
				                    <div class="controls">
				                        <input id="_pr_admob_key" name="admob_key" type="text" class="input-large">
				                        <span class="remark"></span>
				                    </div>
				                </div>
				                <div id="_cg_yeah_mobile_app_id" class="control-group required-field">
				                    <label class="control-label">yeah_mobile_app_id:</label>
				                    <div class="controls">
				                        <input id="_pr_yeah_mobile_app_id" name="yeah_mobile_app_id" type="text" class="input-large">
				                        <span class="remark"></span>
				                    </div>
				                </div>
				                <div id="_cg_yeah_mobile_slot_id" class="control-group required-field">
				                    <label class="control-label">yeah_mobile_slot_id:</label>
				                    <div class="controls">
				                        <input id="_pr_yeah_mobile_slot_id" name="yeah_mobile_slot_id" type="text" class="input-large">
				                        <span class="remark"></span>
				                    </div>
				                </div>
				                <div id="_cg_inmobi_id" class="control-group required-field">
				                    <label class="control-label">inmobi_id:</label>
				                    <div class="controls">
				                        <input id="_pr_inmobi_id" name="inmobi_id" type="text" class="input-large">
				                        <span class="remark"></span>
				                    </div>
				                </div>
				                <div id="_cg_app_id" class="control-group required-field">
				                    <label class="control-label">app_id:</label>
				                    <div class="controls">
				                        <input id="_pr_app_id" name="app_id" type="text" class="input-large">
				                        <span class="remark"></span>
				                    </div>
				                </div>
				                <div id="_cg_api_key" class="control-group required-field">
				                    <label class="control-label">api_key:</label>
				                    <div class="controls">
				                        <input id="_pr_api_key" name="api_key" type="text" class="input-large">
				                        <span class="remark"></span>
				                    </div>
				                </div>
				                <div id="_cg_start_appid" class="control-group required-field">
				                    <label class="control-label">start_appid:</label>
				                    <div class="controls">
				                        <input id="_pr_start_appid" name="start_appid" type="text" class="input-large">
				                        <span class="remark"></span>
				                    </div>
				                </div>
				                <div id="_cg_start_developerid" class="control-group required-field">
				                    <label class="control-label">start_developerid:</label>
				                    <div class="controls">
				                        <input id="_pr_start_developerid" name="start_developerid" type="text" class="input-large">
				                        <span class="remark"></span>
				                    </div>
				                </div>
                                <div class="control-group required-field">
				                    <label class="control-label">国家:</label>
				                    <div class="controls">
				                        <a id="selectCountryLink" href="#selectCountryModal" role="button" class="btn" data-toggle="modal" onclick="javascript:onSearchCountry()">选择</a>
                                        <span class="remark">留空默认为通用所有国家</span>
				                        <input name="supportCountries" type="hidden">
				                        <div id="selectedCountriesDiv">
				                        </div>
				                        <div id="selectCountryModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				                            <div class="modal-header">
				                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
				                                <div class="input-append">
				                                    <input class="input-large" name="countryKeyword" id="countryKeyword" type="text">
				                                    <button class="btn" type="button" onclick="javascript:onSearchCountry()">搜索</button>
				                                </div>
				                                <span class="remark">空的关键字可查询所有国家</span>
				                            </div>
				                            <div class="modal-body">
				                                <div id="countryUl"> 
				                                </div>
				                            </div>
				                            <div class="modal-footer">
				                                <span class="pull-left">
				                                    <a class="btn" onclick="onSelectAllCountry()">全选</a>
				                                    <a class="btn" onclick="onUnSelectAllCountry()">重置</a>
				                                </span>
				                                <button type="button" class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
				                                <button class="btn btn-primary" type="button" onclick="javascript:onAddCountry()">确定</button>
				                            </div>
				                        </div>
				                    </div>
				                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
                                <button class="btn btn-primary" type="button" onclick="javascript:onSavePageRule()">确定</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="control-group required-field">
                    <label class="control-label">状态:</label>
                    <div class="controls">
                        <select id="sel_status" name="status" class="input-small">
                            <option value="1">推广</option>
                            <option value="0">暂停</option>
                            <option value="2">测试</option>
                        </select>
                        <script>document.getElementById("sel_status").value="${empty form.status?'1':form.status}"</script>
                    </div>
                </div>
				<div class="control-group required-field">
				  <label class="control-label">备注:</label>
				  <div class="controls">
				    <input name="remark" value="${fn:escapeXml(form.remark)}" type="text" class="input-large">
				  </div>
				</div>
				<div class="form-actions">
				  <input class="btn btn-primary" type="submit" value="保存">
				  <button type="button" class="btn" onclick="javascript:history.go(-1)">取消</button>
				</div>
			</form>
        </div>
<!-- end main content -->
<c:import url="../../theme/${_theme}/footer.jsp"></c:import>
<script>
var countries=[];
countries.push({code:"ae", chineseName: "阿联酋"});
countries.push({code:"af", chineseName: "阿富汗"});
countries.push({code:"ag", chineseName: "安提瓜和巴布达"});
countries.push({code:"ai", chineseName: "安圭拉"});
countries.push({code:"al", chineseName: "阿尔巴尼亚"});
countries.push({code:"am", chineseName: "亚美尼亚"});
countries.push({code:"ao", chineseName: "安哥拉"});
countries.push({code:"ar", chineseName: "阿根廷"});
countries.push({code:"as", chineseName: "美属萨摩亚"});
countries.push({code:"at", chineseName: "奥地利"});
countries.push({code:"au", chineseName: "澳大利亚"});
countries.push({code:"aw", chineseName: "阿鲁巴"});
countries.push({code:"az", chineseName: "阿塞拜疆"});
countries.push({code:"ba", chineseName: "波斯尼亚和黑塞哥维那"});
countries.push({code:"bb", chineseName: "巴巴多斯"});
countries.push({code:"bd", chineseName: "孟加拉"});
countries.push({code:"be", chineseName: "比利时"});
countries.push({code:"bf", chineseName: "布基纳法索"});
countries.push({code:"bg", chineseName: "保加利亚"});
countries.push({code:"bh", chineseName: "巴林"});
countries.push({code:"bi", chineseName: "布隆迪"});
countries.push({code:"bj", chineseName: "贝宁"});
countries.push({code:"bm", chineseName: "百慕大"});
countries.push({code:"bn", chineseName: "文莱"});
countries.push({code:"bo", chineseName: "玻利维亚"});
countries.push({code:"br", chineseName: "巴西"});
countries.push({code:"bs", chineseName: "巴哈马"});
countries.push({code:"bt", chineseName: "不丹"});
countries.push({code:"bw", chineseName: "博茨瓦纳"});
countries.push({code:"by", chineseName: "白俄罗斯"});
countries.push({code:"bz", chineseName: "伯利兹"});
countries.push({code:"ca", chineseName: "加拿大"});
countries.push({code:"cd", chineseName: "刚果"});
countries.push({code:"cf", chineseName: "中非"});
countries.push({code:"ch", chineseName: "瑞士"});
countries.push({code:"ci", chineseName: "科特迪瓦"});
countries.push({code:"ck", chineseName: "库克群岛"});
countries.push({code:"cl", chineseName: "智利"});
countries.push({code:"cm", chineseName: "喀麦隆"});
countries.push({code:"cn", chineseName: "中国"});
countries.push({code:"co", chineseName: "哥伦比亚"});
countries.push({code:"cr", chineseName: "哥斯达黎加"});
countries.push({code:"cu", chineseName: "古巴"});
countries.push({code:"cv", chineseName: "佛得角"});
countries.push({code:"cy", chineseName: "塞浦路斯"});
countries.push({code:"cz", chineseName: "捷克"});
countries.push({code:"de", chineseName: "德国"});
countries.push({code:"dj", chineseName: "吉布提"});
countries.push({code:"dk", chineseName: "丹麦"});
countries.push({code:"dm", chineseName: "多米尼克"});
countries.push({code:"do", chineseName: "多米尼加"});
countries.push({code:"dz", chineseName: "阿尔及利亚"});
countries.push({code:"ec", chineseName: "厄瓜多尔"});
countries.push({code:"ee", chineseName: "爱沙尼亚"});
countries.push({code:"eg", chineseName: "埃及"});
countries.push({code:"er", chineseName: "厄立特里亚"});
countries.push({code:"es", chineseName: "西班牙"});
countries.push({code:"et", chineseName: "埃塞俄比亚"});
countries.push({code:"fi", chineseName: "芬兰"});
countries.push({code:"fj", chineseName: "斐济"});
countries.push({code:"fm", chineseName: "密克罗尼西亚联邦"});
countries.push({code:"fo", chineseName: "法罗群岛"});
countries.push({code:"fr", chineseName: "法国"});
countries.push({code:"ga", chineseName: "加蓬"});
countries.push({code:"gb", chineseName: "英国"});
countries.push({code:"gd", chineseName: "格林纳达"});
countries.push({code:"ge", chineseName: "格鲁吉亚"});
countries.push({code:"gf", chineseName: "法属圭亚那"});
countries.push({code:"gh", chineseName: "加纳"});
countries.push({code:"gi", chineseName: "直布罗陀"});
countries.push({code:"gl", chineseName: "格陵兰"});
countries.push({code:"gm", chineseName: "冈比亚"});
countries.push({code:"gn", chineseName: "几内亚"});
countries.push({code:"gp", chineseName: "瓜德罗普"});
countries.push({code:"gq", chineseName: "赤道几内亚"});
countries.push({code:"gr", chineseName: "希腊"});
countries.push({code:"gt", chineseName: "危地马拉"});
countries.push({code:"gu", chineseName: "关岛"});
countries.push({code:"gy", chineseName: "圭亚那"});
countries.push({code:"hk", chineseName: "香港"});
countries.push({code:"hn", chineseName: "洪都拉斯"});
countries.push({code:"hr", chineseName: "克罗地亚"});
countries.push({code:"ht", chineseName: "海地"});
countries.push({code:"hu", chineseName: "匈牙利"});
countries.push({code:"id", chineseName: "印度尼西亚"});
countries.push({code:"ie", chineseName: "爱尔兰"});
countries.push({code:"il", chineseName: "以色列"});
countries.push({code:"in", chineseName: "印度"});
countries.push({code:"iq", chineseName: "伊拉克"});
countries.push({code:"ir", chineseName: "伊朗"});
countries.push({code:"is", chineseName: "冰岛"});
countries.push({code:"it", chineseName: "意大利"});
countries.push({code:"jm", chineseName: "牙买加"});
countries.push({code:"jo", chineseName: "约旦"});
countries.push({code:"jp", chineseName: "日本"});
countries.push({code:"ke", chineseName: "肯尼亚"});
countries.push({code:"kg", chineseName: "吉尔吉斯斯坦"});
countries.push({code:"kh", chineseName: "柬埔寨"});
countries.push({code:"ki", chineseName: "基里巴斯"});
countries.push({code:"km", chineseName: "科摩罗"});
countries.push({code:"kn", chineseName: "圣基茨和尼维斯"});
countries.push({code:"kp", chineseName: "朝鲜"});
countries.push({code:"kr", chineseName: "韩国"});
countries.push({code:"kw", chineseName: "科威特"});
countries.push({code:"ky", chineseName: "开曼群岛"});
countries.push({code:"kz", chineseName: "哈萨克斯坦"});
countries.push({code:"la", chineseName: "老挝"});
countries.push({code:"lb", chineseName: "黎巴嫩"});
countries.push({code:"lc", chineseName: "圣卢西亚"});
countries.push({code:"li", chineseName: "列支敦士登"});
countries.push({code:"lk", chineseName: "斯里兰卡"});
countries.push({code:"lr", chineseName: "利比里亚"});
countries.push({code:"ls", chineseName: "莱索托"});
countries.push({code:"lt", chineseName: "立陶宛"});
countries.push({code:"lv", chineseName: "拉脱维亚"});
countries.push({code:"ly", chineseName: "利比亚"});
countries.push({code:"ma", chineseName: "摩洛哥"});
countries.push({code:"mc", chineseName: "摩纳哥"});
countries.push({code:"md", chineseName: "摩尔多瓦"});
countries.push({code:"me", chineseName: "黑山"});
countries.push({code:"mg", chineseName: "马达加斯加"});
countries.push({code:"mh", chineseName: "马绍尔群岛"});
countries.push({code:"mk", chineseName: "卢森堡"});
countries.push({code:"ml", chineseName: "马里"});
countries.push({code:"mm", chineseName: "缅甸"});
countries.push({code:"mn", chineseName: "蒙古"});
countries.push({code:"mo", chineseName: "澳门"});
countries.push({code:"mp", chineseName: "北马里亚纳群岛"});
countries.push({code:"mq", chineseName: "马提尼克"});
countries.push({code:"mr", chineseName: "毛里塔尼亚"});
countries.push({code:"ms", chineseName: "蒙塞拉特岛"});
countries.push({code:"mt", chineseName: "马耳他"});
countries.push({code:"mu", chineseName: "毛里求斯"});
countries.push({code:"mv", chineseName: "马尔代夫"});
countries.push({code:"mw", chineseName: "马拉维"});
countries.push({code:"mx", chineseName: "墨西哥"});
countries.push({code:"my", chineseName: "马来西亚"});
countries.push({code:"mz", chineseName: "莫桑比克"});
countries.push({code:"na", chineseName: "纳米比亚"});
countries.push({code:"nc", chineseName: "新喀里多尼亚"});
countries.push({code:"ne", chineseName: "尼日尔"});
countries.push({code:"ng", chineseName: "尼日利亚"});
countries.push({code:"ni", chineseName: "尼加拉瓜"});
countries.push({code:"nl", chineseName: "荷兰"});
countries.push({code:"no", chineseName: "挪威"});
countries.push({code:"np", chineseName: "尼泊尔"});
countries.push({code:"nr", chineseName: "瑙鲁"});
countries.push({code:"nu", chineseName: "纽埃"});
countries.push({code:"nz", chineseName: "新西兰"});
countries.push({code:"om", chineseName: "阿曼"});
countries.push({code:"pa", chineseName: "巴拿马"});
countries.push({code:"pe", chineseName: "秘鲁"});
countries.push({code:"pf", chineseName: "法属波利尼西亚"});
countries.push({code:"pg", chineseName: "巴布亚新几内亚"});
countries.push({code:"ph", chineseName: "菲律宾"});
countries.push({code:"pk", chineseName: "巴基斯坦"});
countries.push({code:"pl", chineseName: "波兰"});
countries.push({code:"pm", chineseName: "圣皮埃尔和密克隆"});
countries.push({code:"pr", chineseName: "波多黎各"});
countries.push({code:"ps", chineseName: "巴勒斯坦"});
countries.push({code:"pt", chineseName: "葡萄牙"});
countries.push({code:"pw", chineseName: "帕劳"});
countries.push({code:"py", chineseName: "巴拉圭"});
countries.push({code:"qa", chineseName: "卡塔尔"});
countries.push({code:"re", chineseName: "留尼汪"});
countries.push({code:"ro", chineseName: "罗马尼亚"});
countries.push({code:"rs", chineseName: "塞尔维亚"});
countries.push({code:"ru", chineseName: "俄罗斯"});
countries.push({code:"rw", chineseName: "卢旺达"});
countries.push({code:"sa", chineseName: "沙特阿拉伯"});
countries.push({code:"sb", chineseName: "所罗门群岛"});
countries.push({code:"sc", chineseName: "塞舌尔"});
countries.push({code:"sd", chineseName: "苏丹"});
countries.push({code:"se", chineseName: "瑞典"});
countries.push({code:"sg", chineseName: "新加坡"});
countries.push({code:"si", chineseName: "斯洛文尼亚"});
countries.push({code:"sk", chineseName: "斯洛伐克"});
countries.push({code:"sl", chineseName: "塞拉利昂"});
countries.push({code:"sm", chineseName: "圣马力诺"});
countries.push({code:"sn", chineseName: "塞内加尔"});
countries.push({code:"so", chineseName: "索马里"});
countries.push({code:"sr", chineseName: "苏里南"});
countries.push({code:"st", chineseName: "圣多美和普林西比"});
countries.push({code:"sv", chineseName: "萨尔瓦多"});
countries.push({code:"sy", chineseName: "叙利亚"});
countries.push({code:"sz", chineseName: "斯威士兰"});
countries.push({code:"tc", chineseName: "特克斯和凯科斯群岛"});
countries.push({code:"td", chineseName: "乍得"});
countries.push({code:"tg", chineseName: "多哥"});
countries.push({code:"th", chineseName: "泰国"});
countries.push({code:"tj", chineseName: "塔吉克斯坦"});
countries.push({code:"tl", chineseName: "东帝汶"});
countries.push({code:"tm", chineseName: "土库曼斯坦"});
countries.push({code:"tn", chineseName: "突尼斯"});
countries.push({code:"to", chineseName: "汤加"});
countries.push({code:"tr", chineseName: "土耳其"});
countries.push({code:"tt", chineseName: "特立尼达和多巴哥"});
countries.push({code:"tw", chineseName: "台湾"});
countries.push({code:"tz", chineseName: "坦桑尼亚"});
countries.push({code:"ua", chineseName: "乌克兰"});
countries.push({code:"ug", chineseName: "乌干达"});
countries.push({code:"us", chineseName: "美国"});
countries.push({code:"uy", chineseName: "乌拉圭"});
countries.push({code:"uz", chineseName: "乌兹别克斯坦"});
countries.push({code:"va", chineseName: "梵蒂冈"});
countries.push({code:"vc", chineseName: "圣文森特和格林纳丁斯"});
countries.push({code:"ve", chineseName: "委内瑞拉"});
countries.push({code:"vg", chineseName: "英属维尔京群岛"});
countries.push({code:"vi", chineseName: "美属维尔京群岛"});
countries.push({code:"vn", chineseName: "越南"});
countries.push({code:"vu", chineseName: "瓦努阿图"});
countries.push({code:"wf", chineseName: "瓦利斯和富图纳"});
countries.push({code:"ws", chineseName: "萨摩亚"});
countries.push({code:"ye", chineseName: "也门"});
countries.push({code:"za", chineseName: "南非"});
countries.push({code:"zm", chineseName: "赞比亚"});
countries.push({code:"zw", chineseName: "津巴布韦"});

var countryCode2ChName={};
for(var idx =0;idx<countries.length;idx++){
    var c = countries[idx];
    countryCode2ChName[c.code]= c.chineseName;
}
function onSearchCountry(){
    var kw = $("#countryKeyword").val();
        var ul = $("#countryUl");
        ul.html("");
        var idx=0;
        var countryInput=$("input[name=supportCountries]");
        var supportedCountries = countryInput.val()==""?[]:countryInput.val().split(",");
        for(;idx<countries.length;idx++){
            var c= countries[idx];
            var flag=true;
            if(kw == ""){
                if(supportedCountries.length>0){
                    for(var i=0;i<supportedCountries.length;i++){
	                    if(supportedCountries[i]==c.code){
		                    ul.append($("<label class=\"checkbox inline\"> <input type=checkbox value=\""+ c.code+"\" checked=true>"+ c.chineseName+"</label>"));
	                    	flag=false;
	                    	break;
	                    }
                    }
                    if(flag){
                    	ul.append($("<label class=\"checkbox inline\"> <input type=checkbox value=\""+ c.code+"\" >"+ c.chineseName+"</label>"));
                    	flag = true;
                    }
                }else{
                	 ul.append($("<label class=\"checkbox inline\"> <input type=checkbox value=\""+ c.code+"\" >"+ c.chineseName+"</label>"));
                }
            }else{
            	if(supportedCountries.length>0){
                    for(var i=0;i<supportedCountries.length;i++){
	                    if(supportedCountries[i]==c.code&&c.chineseName.indexOf(kw)>=0){
		                    ul.append($("<label class=\"checkbox inline\"> <input type=checkbox value=\""+ c.code+"\" checked=true>"+ c.chineseName+"</label>"));
	                    	flag=false;
	                    	break;
	                    }else if(flag&&c.chineseName.indexOf(kw)>=0){
	                    	ul.append($("<label class=\"checkbox inline\"> <input type=checkbox value=\""+ c.code+"\" >"+ c.chineseName+"</label>"));
	                    	flag = false;
	                    	break;
	                    }
                    }
                }else{
                	if(flag&&c.chineseName.indexOf(kw)>=0){
                    	ul.append($("<label class=\"checkbox inline\"> <input type=checkbox value=\""+ c.code+"\" >"+ c.chineseName+"</label>"));
                    	flag = true;
                    }
                }
            }
            }
	}
	function onAddCountry(){
	    var countryInput=$("input[name=supportCountries]");
	    countryInput.val('');
	    var countries = countryInput.val()==""?[]:countryInput.val().split(",");
	    $("#countryUl input").each(function(idx, elem){
	        if(elem.checked){
	            var val = elem.value
	            if ($.inArray(val, countries) == -1){
	                countries.push(val);
	            }
	        }
	    });
	    countryInput.val(countries.join(","));
	    renderSelectedCountries(countries);
	    $('#selectCountryModal').modal('hide');
	}
	
	function renderSelectedCountries(countries){
	    var scd = $("#selectedCountriesDiv");
	    scd.html("");
	    var idx=0;
	    for(;idx<countries.length;idx++){
	        var c = countries[idx];
	        scd.append("<span class=\"delLable\">"+countryCode2ChName[c]+"<a href=\"#\" onclick=\"javascript:delCountry(this, '"+c+"')\" class=\"delIcon\">×</a></span>");
	    }
	}
	
	function initSelectedCountries(){
	    var countryInput=$("input[name=supportCountries]");
	    var countries = countryInput.val()==""?[]:countryInput.val().split(",");
	    renderSelectedCountries(countries);
	}
	
	function delCountry(elem, country){
	    var countryInput=$("input[name=supportCountries]");
	    var countries = countryInput.val()==""?[]:countryInput.val().split(",");
	    countries.splice($.inArray(country, countries),1);
	    countryInput.val(countries.join(","));
	    $(elem).parent("span").remove();
	}
	
	function onSelectAllCountry(){
	    $("#countryUl input").each(function(idx, elem){
	        elem.checked=true;
	    });
	}
	
	function onUnSelectAllCountry(){
	    $("#countryUl input").each(function(idx, elem){
	        elem.checked=false;
	    });
	}
	$("#countryKeyword").keypress(function(event) {
	    if ( event.which == 13 ) {
	        event.preventDefault();
	        onSearchCountry();
	    }
	});
	$.ready(initSelectedCountries());
    var editIdx=-1;//用于标志当前编辑的page rule
    var pageRules=[];
    <c:forEach var="pageRule" items="${form.innerPageRulesObject}" varStatus="vs">
    pageRules.push(${pageRule.jsonString});
    </c:forEach>
    function onSavePageRule(){
        var activity=$("#_pr_activity").val();
        var x=$("#_pr_x").val();
        var y=$("#_pr_y").val();
        var from=$("#_pr_from").val();
        var appVer=$("#_pr_appVer").val();
        var resolution=$("#_pr_resolution").val();
        var sdkType=$("#_pr_sdkType").val();
        var sdkTypeName= sdkType==1?"google admob":(sdkType==2?"yeahmobile":(sdkType==3?"inmobi":(sdkType==4?"airpush":"startApp")));
        var _countries=$("input[name=supportCountries]").val();
        var countries = _countries==""?[]:_countries.split(",");
        var countries2ChineseName="";
        var admob_key=$("#_pr_admob_key").val();
        var yeah_mobile_app_id=$("#_pr_yeah_mobile_app_id").val();
        var yeah_mobile_slot_id=$("#_pr_yeah_mobile_slot_id").val();
        var inmobi_id=$("#_pr_inmobi_id").val();
        var app_id=$("#_pr_app_id").val();
        var api_key=$("#_pr_api_key").val();
        var start_appid=$("#_pr_start_appid").val();
        var start_developerid=$("#_pr_start_developerid").val();
	    for(idx=0;idx<countries.length;idx++){
	        var c = countries[idx];
	        if(countries2ChineseName!=""){
	        	countries2ChineseName = countries2ChineseName+","+countryCode2ChName[c];
	        }else{
	        	countries2ChineseName =countryCode2ChName[c];
	        }
	    }
        if(!/^[a-zA-Z0-9][a-zA-Z0-9\.]+[a-zA-Z0-9]$/.test(activity)){
            alert("页面类格式不正确");
            return;
        }
        if(!/^\d+$/.test(x) || !/\d+/.test(y)){
            alert("X或Y坐标格式不正确");
            return;
        }
        if(!/^((\d+\.)*((\d+)|x))+(,(\d+\.)*((\d+)|x))*$/.test(appVer)){
            alert("App版本格式不正确");
            return;
        }
        if(!/^(\d+x\d+)+(,\d+x\d+)*$/.test(resolution)){
            alert("分辨率格式不正确");
            return;
        }
        if(sdkType == 1){
        	if(/^\s*$/.test(admob_key)){
                alert("admob_key不能为空");
                return;
            }
        }else if(sdkType == 2){
        	if(/^\s*$/.test(yeah_mobile_app_id)){
                alert("yeah_mobile_app_id不能为空");
                return;
            }
        	if(/^\s*$/.test(yeah_mobile_slot_id)){
                alert("yeah_mobile_slot_id不能为空");
                return;
            }
        }else if(sdkType == 3){
        	if(/^\s*$/.test(inmobi_id)){
                alert("inmobi_id不能为空");
                return;
            }
        }else if(sdkType == 4){
        	if(/^\s*$/.test(app_id)){
                alert("app_id不能为空");
                return;
            }
        	if(/^\s*$/.test(api_key)){
                alert("api_key不能为空");
                return;
            }
        }else{
        	if(/^\s*$/.test(start_appid)){
                alert("start_appid不能为空");
                return;
            }
        	if(/^\s*$/.test(start_developerid)){
                alert("start_developerid不能为空");
                return;
            }
        }
        var idx=editIdx==-1?pageRules.length:editIdx;
        var html='<div class="page-rules">'+
                '<div class="alert alert-success" style="display: inline-block;min-width: 300px;">'+
                '<span class="close" data-dismiss="alert" onclick="javascript:deletePageRule('+idx+')" ><i class="icon-trash"></i></span>'+
                '<span class="close" onclick="javascript:editPageRule('+idx+')" ><i class="icon-edit" style="margin-right: 10px;"></i></span>'+
                '<ul>'+
                '<li><span>页面类：</span><span id="_pr_activity_'+idx+'">'+activity+'</span></li>'+
                '<li><span>坐标：</span><span id="_pr_x_y_'+idx+'">('+x+','+y+')</span></li>'+
                '<li><span>from：</span><span id="_pr_from_'+idx+'">'+from+'</span></li>'+
                '<li><span>App版本：</span><span id="_pr_appVer_'+idx+'">'+appVer+'</span></li>'+
                '<li><span>分辨率：</span><span id="_pr_resolution_'+idx+'">'+resolution+'</span></li>'+
                '<li><span>广告类型：</span><span id="_pr_sdkType_'+idx+'">'+sdkTypeName+'</span></li>';
        if(sdkType == 1){
        	html=html+'<li '+admob_key+' eq ""?"style="display:none"":"style="display:block""><span>admob_key：</span><span id="_pr_admob_key_'+idx+'">'+admob_key+'</span></li>';
        }else if(sdkType == 2){
        	html=html+'<li '+yeah_mobile_app_id+' eq ""?"style="display:none"":"style="display:block""><span>yeah_mobile_app_id：</span><span id="_pr_yeah_mobile_app_id_'+idx+'">'+yeah_mobile_app_id+'</span></li>'+
            	'<li '+yeah_mobile_slot_id+' eq ""?"style="display:none"":"style="display:block""><span>yeah_mobile_slot_id：</span><span id="_pr_yeah_mobile_slot_id_'+idx+'">'+yeah_mobile_slot_id+'</span></li>';
        	
        }else if(sdkType == 3){
        	html=html+'<li '+inmobi_id+' eq ""?"style="display:none"":"style="display:block""><span>yeah_inmobi_id：</span><span id="_pr_inmobi_id_'+idx+'">'+inmobi_id+'</span></li>';
        }else if(sdkType == 4){
            html=html+'<li '+app_id+' eq ""?"style="display:none"":"style="display:block""><span>app_id：</span><span id="_pr_app_id_'+idx+'">'+app_id+'</span></li>'+
            	'<li '+api_key+' eq ""?"style="display:none"":"style="display:block""><span>api_key：</span><span id="_pr_api_key_'+idx+'">'+api_key+'</span></li>';
        }else{
        	html=html+'<li '+start_appid+' eq ""?"style="display:none"":"style="display:block""><span>start_appid：</span><span id="_pr_start_appid_'+idx+'">'+start_appid+'</span></li>'+
        	'<li '+start_developerid+' eq ""?"style="display:none"":"style="display:block""><span>start_developerid：</span><span id="_pr_start_developerid_'+idx+'">'+start_developerid+'</span></li>';
        }   
        html = html+'<li><span>国家：</span><span id="_pr_countries_'+idx+'">'+countries2ChineseName+'</span></li>'+
                '</ul>'+
                '</div>'+
                '</div>';
        var _appVer = appVer.split(",");
        var _resolution = resolution.split(",");
        var pageRule;
        if(sdkType == 1){
	        pageRule = {"activity":activity,"x":parseInt(x),"y":parseInt(y),"from":from,"app_ver":_appVer,"resolution":_resolution,"sdk_type":parseInt(sdkType),"countries":countries,"ad":{"key":admob_key}};
        }else if(sdkType == 2){
	        pageRule = {"activity":activity,"x":parseInt(x),"y":parseInt(y),"from":from,"app_ver":_appVer,"resolution":_resolution,"sdk_type":parseInt(sdkType),"countries":countries,"ad":{"app_id":yeah_mobile_app_id,"slot_id":yeah_mobile_slot_id}};
        }else if(sdkType == 3){
	        pageRule = {"activity":activity,"x":parseInt(x),"y":parseInt(y),"from":from,"app_ver":_appVer,"resolution":_resolution,"sdk_type":parseInt(sdkType),"countries":countries,"ad":{"id":inmobi_id}};
        }else if(sdkType == 4){
	        pageRule = {"activity":activity,"x":parseInt(x),"y":parseInt(y),"from":from,"app_ver":_appVer,"resolution":_resolution,"sdk_type":parseInt(sdkType),"countries":countries,"ad":{"appid":app_id,"apikey":api_key}};
        }else{
	        pageRule = {"activity":activity,"x":parseInt(x),"y":parseInt(y),"from":from,"app_ver":_appVer,"resolution":_resolution,"sdk_type":parseInt(sdkType),"countries":countries,"ad":{"start_appid":start_appid,"start_developerid":start_developerid}};
        }
        if(editIdx==-1){
            pageRules.push(pageRule);
            $("#_div_page_rules").append($(html));
        }else{
            pageRules[editIdx]=pageRule;
            $("#_pr_activity_"+editIdx).html(activity);
            $("#_pr_x_y_"+editIdx).html("("+x+","+y+")")
            $("#_pr_from_"+editIdx).html(from);
            $("#_pr_appVer_"+editIdx).html(appVer);
            $("#_pr_resolution_"+editIdx).html(resolution);
            $("#_pr_sdkType_"+editIdx).html(sdkTypeName);
            $("#_pr_countries_"+editIdx).html(countries2ChineseName);
            controlCss(sdkType,editIdx);
            if(sdkType == 1){
	            $("#_pr_admob_key_"+editIdx).html(admob_key);
            }else if(sdkType == 2){
	            $("#_pr_yeah_mobile_app_id_"+editIdx).html(yeah_mobile_app_id);
	            $("#_pr_yeah_mobile_slot_id_"+editIdx).html(yeah_mobile_slot_id);
            }else if(sdkType == 3){
	            $("#_pr_inmobi_id_"+editIdx).html(inmobi_id);
            }else if(sdkType == 4){
	            $("#_pr_app_id_"+editIdx).html(app_id);
	            $("#_pr_api_key_"+editIdx).html(api_key);
            }else{
            	$("#_pr_start_appid_"+editIdx).html(start_appid);
	            $("#_pr_start_developerid_"+editIdx).html(start_developerid);
            }
        }
        $('#_div_page_rule_modal').modal('hide');
    }
    function deletePageRule(idx){
        pageRules[idx]=null;
    }
    function editPageRule(idx){
        editIdx = idx;
        var pr=pageRules[idx];
        $("#_pr_activity").val(pr.activity);
        $("#_pr_x").val(pr.x);
        $("#_pr_y").val(pr.y);
        $("#_pr_from").val(pr.from);
        $("#_pr_appVer").val(pr.app_ver);
        $("#_pr_resolution").val(pr.resolution);
        $("#_pr_sdkType").val(pr.sdk_type);
        changeType();
        $("#_pr_admob_key").val(pr.ad.key);
        $("#_pr_yeah_mobile_app_id").val(pr.ad.app_id);
        $("#_pr_yeah_mobile_slot_id").val(pr.ad.slot_id);
        $("#_pr_inmobi_id").val(pr.ad.id);
        $("#_pr_app_id").val(pr.ad.appid);
        $("#_pr_api_key").val(pr.ad.apikey);
        $("#_pr_start_appid").val(pr.ad.start_appid);
        $("#_pr_start_developerid").val(pr.ad.start_developerid);
        $("input[name=supportCountries]").val(pr.countries);
        initSelectedCountries();
        $('#_div_page_rule_modal').modal('show');
    }
    function addPageRule(){
        editIdx = -1;
        $("#_pr_activity").val("");
        $("#_pr_x").val("");
        $("#_pr_y").val("");
        $("#_pr_from").val("");
        $("#_pr_appVer").val("");
        $("#_pr_resolution").val("");
        $("#_pr_sdkType").val("1");
        setValue();
        changeType();
        $("input[name=supportCountries]").val("");
        initSelectedCountries();
    }
    function onSubmitAppRuleForm(){
        for(var i=0;i<pageRules.length;i++){
            if(pageRules[i]==null){
                pageRules.splice(i,1);
            }
        }
        $("#pageRules").val(JSON.stringify(pageRules));
    }
    function changeType(){
        var type=$("#_pr_sdkType").val();
        if(type=="1"){
            $("#_cg_admob_key").show();
            $("#_cg_yeah_mobile_app_id").hide();
            $("#_cg_yeah_mobile_slot_id").hide();
            $("#_cg_inmobi_id").hide();
            $("#_cg_app_id").hide();
            $("#_cg_api_key").hide();
            $("#_cg_start_appid").hide();
            $("#_cg_start_developerid").hide();
        }else if(type=="2"){
        	$("#_cg_admob_key").hide();
            $("#_cg_yeah_mobile_app_id").show();
            $("#_cg_yeah_mobile_slot_id").show();
            $("#_cg_inmobi_id").hide();
            $("#_cg_app_id").hide();
            $("#_cg_api_key").hide();
            $("#_cg_start_appid").hide();
            $("#_cg_start_developerid").hide();
        }else if(type=="3"){
        	$("#_cg_admob_key").hide();
            $("#_cg_yeah_mobile_app_id").hide();
            $("#_cg_yeah_mobile_slot_id").hide();
            $("#_cg_inmobi_id").show();
            $("#_cg_app_id").hide();
            $("#_cg_api_key").hide();
            $("#_cg_start_appid").hide();
            $("#_cg_start_developerid").hide();
        }else if(type=="4"){
        	$("#_cg_admob_key").hide();
            $("#_cg_yeah_mobile_app_id").hide();
            $("#_cg_yeah_mobile_slot_id").hide();
            $("#_cg_inmobi_id").hide();
            $("#_cg_app_id").show();
            $("#_cg_api_key").show();
            $("#_cg_start_appid").hide();
            $("#_cg_start_developerid").hide();
        }else{
        	$("#_cg_admob_key").hide();
            $("#_cg_yeah_mobile_app_id").hide();
            $("#_cg_yeah_mobile_slot_id").hide();
            $("#_cg_inmobi_id").hide();
            $("#_cg_app_id").hide();
            $("#_cg_api_key").hide();
            $("#_cg_start_appid").show();
            $("#_cg_start_developerid").show();
        }
        setValue();
    }
    function setValue(){
       $("#_pr_admob_key").val("");
       $("#_pr_yeah_mobile_app_id").val("");
       $("#_pr_yeah_mobile_slot_id").val("");
       $("#_pr_inmobi_id").val("");
       $("#_pr_app_id").val("");
       $("#_pr_api_key").val("");
       $("#_pr_start_appid").val("");
       $("#_pr_start_developerid").val("");
    }
    function controlCss(type,editIdx){
    	if(type==1){
    		$("#_pr_admob_key_"+editIdx).parent().css('display','');
            $("#_pr_yeah_mobile_app_id_"+editIdx).parent().css('display','none');
            $("#_pr_yeah_mobile_slot_id_"+editIdx).parent().css('display','none');
            $("#_pr_inmobi_id_"+editIdx).parent().css('display','none');
            $("#_pr_app_id_"+editIdx).parent().css('display','none');
            $("#_pr_api_key_"+editIdx).parent().css('display','none');
            $("#_pr_start_appid_"+editIdx).parent().css('display','none');
            $("#_pr_start_developerid_"+editIdx).parent().css('display','none');
    	}else if(type==2){
    		$("#_pr_admob_key_"+editIdx).parent().css('display','none');
            $("#_pr_yeah_mobile_app_id_"+editIdx).parent().css('display','');
            $("#_pr_yeah_mobile_slot_id_"+editIdx).parent().css('display','');
            $("#_pr_inmobi_id_"+editIdx).parent().css('display','none');
            $("#_pr_app_id_"+editIdx).parent().css('display','none');
            $("#_pr_api_key_"+editIdx).parent().css('display','none');
            $("#_pr_start_appid_"+editIdx).parent().css('display','none');
            $("#_pr_start_developerid_"+editIdx).parent().css('display','none');
    	}else if(type==3){
    		$("#_pr_admob_key_"+editIdx).parent().css('display','none');
            $("#_pr_yeah_mobile_app_id_"+editIdx).parent().css('display','none');
            $("#_pr_yeah_mobile_slot_id_"+editIdx).parent().css('display','none');
            $("#_pr_inmobi_id_"+editIdx).parent().css('display','');
            $("#_pr_app_id_"+editIdx).parent().css('display','none');
            $("#_pr_api_key_"+editIdx).parent().css('display','none');
            $("#_pr_start_appid_"+editIdx).parent().css('display','none');
            $("#_pr_start_developerid_"+editIdx).parent().css('display','none');
    	}else if(type==4){
    		$("#_pr_admob_key_"+editIdx).parent().css('display','none');
            $("#_pr_yeah_mobile_app_id_"+editIdx).parent().css('display','none');
            $("#_pr_yeah_mobile_slot_id_"+editIdx).parent().css('display','none');
            $("#_pr_inmobi_id_"+editIdx).parent().css('display','none');
            $("#_pr_app_id_"+editIdx).parent().css('display','');
            $("#_pr_api_key_"+editIdx).parent().css('display','');
            $("#_pr_start_appid_"+editIdx).parent().css('display','none');
            $("#_pr_start_developerid_"+editIdx).parent().css('display','none');
    	}else{
    		$("#_pr_admob_key_"+editIdx).parent().css('display','none');
            $("#_pr_yeah_mobile_app_id_"+editIdx).parent().css('display','none');
            $("#_pr_yeah_mobile_slot_id_"+editIdx).parent().css('display','none');
            $("#_pr_inmobi_id_"+editIdx).parent().css('display','none');
            $("#_pr_app_id_"+editIdx).parent().css('display','none');
            $("#_pr_api_key_"+editIdx).parent().css('display','none');
            $("#_pr_start_appid_"+editIdx).parent().css('display','');
            $("#_pr_start_developerid_"+editIdx).parent().css('display','');
    	}
    }
</script>
