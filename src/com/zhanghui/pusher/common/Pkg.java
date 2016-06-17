package com.zhanghui.pusher.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.collect.Maps;

public class Pkg extends AbstractJsonObject{
	public final static List<Pkg> pkgs = initTypes();
    public final static Map<String,Pkg> pkgMap= initPkgMap();
    
    private static Map<String,Pkg> initPkgMap() {
        Map<String, Pkg> map=new HashMap<>(pkgs.size());
        for(Pkg p : pkgs){
            map.put(p.pkgName, p);
        }
        return map;
    }

    private static List<Pkg> initTypes() {
        List<Pkg> types=new ArrayList<Pkg>();
        types.add(new Pkg("δ֪","unknown",new Activity[]{new Activity("δ֪","unknown")}));
        types.add(new Pkg("������","com.sad",new Activity[]{new Activity("������","com.sad.luncher")}));
        types.add(new Pkg("��׿�Ż���ʦ","com.dianxinos.optimizer.duplay",new Activity[]{new Activity("��ҳ","com.dianxinos.optimizer.MainActivity")}));
        types.add(new Pkg("����ʡ��","com.dianxinos.dxbs",new Activity[]{new Activity("��ҳ","com.dianxinos.powermanager.PowerMgrTabActivity")}));
        types.add(new Pkg("cleanmaster","com.cleanmaster.mguard",new Activity[]{new Activity("��ҳ","com.keniu.security.main.MainActivity"),new Activity("�ֻ�����","com.cleanmaster.boost.main.ProcessManagerActivity"),
        		new Activity("�������","com.cleanmaster.ui.app.activity.AppManagerActivity"),new Activity("������ɱ","com.cleanmaster.security.scan.result.SecurityMainActivity")}));
        types.add(new Pkg("BBM","com.bbm",new Activity[]{new Activity("��ҳ","com.bbm.ui.activities.MainActivity")}));
        types.add(new Pkg("Accupedo","com.corusen.accupedo.te",new Activity[]{new Activity("��ҳ","com.corusen.accupedo.te.Pedometer"),new Activity("��ʷ","com.corusen.accupedo.te.database.History")}));
        types.add(new Pkg("beetalk","com.beetalk",new Activity[]{new Activity("��ҳ","com.beetalk.ui.view.home.BTHomeMenuActivity")}));
        types.add(new Pkg("baidu.browser","com.baidu.browser.inter",new Activity[]{new Activity("��ҳ","com.baidu.browser.inter.BrowserActivity"),new Activity("����ҳ","com.baidu.browser.news.BdNewsActivity"),
        		new Activity("ͼƬҳ","com.baidu.browser.picture.BdPictureListActivity")}));
        types.add(new Pkg("Runkeeper","com.fitnesskeeper.runkeeper.pro",new Activity[]{new Activity("��ʼҳ","com.fitnesskeeper.runkeeper.RunKeeperActivity")}));
        types.add(new Pkg("fecebook","com.facebook.katana",new Activity[]{new Activity("��ҳ","com.facebook.composer.activity.ComposerActivity"),new Activity("��½ҳ","com.facebook.katana.FacebookLoginActivity"),
        		new Activity("��������","com.facebook.katana.activity.FbMainTabActivity")}));
        types.add(new Pkg("Candy crush","com.king.candycrushsaga",new Activity[]{new Activity("��ҳ","com.king.candycrushsaga.CandyCrushSagaActivity")}));
        types.add(new Pkg("farm heroes","com.king.farmheroessaga",new Activity[]{new Activity("��ҳ","com.king.farmheroessaga.FarmHeroesSagaActivity")}));
        types.add(new Pkg("subwaysurf","com.kiloo.subwaysurf",new Activity[]{new Activity("��ҳ","com.kiloo.unityutilities.UnityPluginActivity")}));
        types.add(new Pkg("pou","me.pou.app",new Activity[]{new Activity("��ҳ","me.pou.app.App")}));
        types.add(new Pkg("wechat","com.tencent.mm",new Activity[]{new Activity("΢��ҳ","com.tencent.mm.ui.LauncherUI"),new Activity("����Ȧ","com.tencent.mm.plugin.sns.ui.SnsTimeLineUI"),
        		new Activity("��Ϸ","com.tencent.mm.plugin.game.ui.GameCenterUI")}));
        types.add(new Pkg("Psafe","com.psafe.msuite",new Activity[]{new Activity("��ҳ","com.psafe.msuite.main.HomeActivity")}));
        types.add(new Pkg("Dragon city","home.myandroid.dcguide",new Activity[]{new Activity("��ҳ","home.myandroid.dcguide.MainActivity")}));
        types.add(new Pkg("twitter","com.twitter.android",new Activity[]{new Activity("��ҳ","com.twitter.android.MainActivity"),new Activity("˽��","com.twitter.android.RootDMActivity"),
        		new Activity("��½ҳ","com.twitter.android.StartActivity")}));
        
        return types;
    }


    public Pkg(String appName, String pkgName, Activity[] activities) {
        this.pkgName = pkgName;
        this.appName = appName;
        this.activities = activities;
    }
	
	private String pkgName;
	private String appName;
	private Activity[] activities;
	
	@JsonIgnore
	public String getJsonString(){
		return toJsonWithNoException(this);
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public Activity[] getActivities() {
		return activities;
	}

	public void setActivities(Activity[] activities) {
		this.activities = activities;
	}
	public static void main(String[] args) {
		System.out.println(pkgMap);
		System.out.println(pkgMap.get("unknown").appName);
	}
}
