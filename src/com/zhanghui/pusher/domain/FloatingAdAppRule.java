package com.zhanghui.pusher.domain;

public class FloatingAdAppRule extends RemarkStatusId{
    private String name;
    private String pkgName;
    private FloatingAdAppPageRule3V[] pageRules;
    private InnerFloatingAdAppPageRule[] innerPageRules;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public FloatingAdAppPageRule3V[] getPageRulesObject() {
        return pageRules;
    }
    public void setPageRulesObject(FloatingAdAppPageRule3V[] pageRules) {
        this.pageRules = pageRules;
        if(pageRules==null || pageRules.length<1){
            return;
        }
        innerPageRules = new InnerFloatingAdAppPageRule[pageRules.length];
        for(int i=0;i<pageRules.length;i++){
            innerPageRules[i]=new InnerFloatingAdAppPageRule(pageRules[i]);
        }
    }

    public InnerFloatingAdAppPageRule[] getInnerPageRulesObject() {
        return innerPageRules;
    }

    public String getInnerPageRules() {
        return InnerFloatingAdAppPageRule.toJsonWithNoException(innerPageRules);
    }

    public String getPageRules() {
        return FloatingAdAppPageRule3V.toJsonWithNoException(pageRules);
    }

    public void setPageRules(String pageRuleJson) {
        setPageRulesObject(FloatingAdAppPageRule3V.nullOnExceptionValueOf(pageRuleJson, FloatingAdAppPageRule3V[].class));
    }

    public void setInnerPageRules(String pageRuleJson) {
        setPageRules(pageRuleJson);
    }
}
