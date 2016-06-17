package com.zhanghui.pusher.common;

import net.sourceforge.pinyin4j.PinyinHelper;

import java.util.*;

public final class Country {
    public final static List<Country> countries = initCountries();
    public final static Map<String,Country> shortcut2CountryMap= initShortcut2CountryMap();
    public final static Map<String,Country> mcc2CountryMap= initMcc2CountryMap();

    private String shortcut;
    private String chineseName;
    private String firstLetterOfChineseName;
    private String mcc;

    private static Map<String,Country> initMcc2CountryMap() {
        Map<String, Country> map=new HashMap<>(countries.size());
        for(Country country : countries){
            map.put(country.getMcc(), country);
        }
        return map;
    }


    private static Map<String,Country> initShortcut2CountryMap() {
        Map<String, Country> map=new HashMap<>(countries.size());
        for(Country country : countries){
            map.put(country.getShortcut(), country);
        }
        return map;
    }

    private static void addCountry(List<Country> countries, String shortcut, String mcc, String chineseName){
        String firstLetterOfChineseName= String.valueOf(PinyinHelper.toHanyuPinyinStringArray(chineseName.charAt(0))[0].charAt(0)).toUpperCase();
        countries.add(new Country(shortcut,mcc,chineseName, firstLetterOfChineseName));
    }

    private static List<Country> initCountries() {
        List<Country> _countries=new ArrayList<Country>(217);
        addCountry(_countries,"ae","431","阿联酋");
        addCountry(_countries,"af","412","阿富汗");
        addCountry(_countries,"ag","344","安提瓜和巴布达");
        addCountry(_countries,"ai","365","安圭拉");
        addCountry(_countries,"al","276","阿尔巴尼亚");
        addCountry(_countries,"am","283","亚美尼亚");
        addCountry(_countries,"ao","631","安哥拉");
        addCountry(_countries,"ar","722","阿根廷");
        addCountry(_countries,"as","544","美属萨摩亚");
        addCountry(_countries,"at","232","奥地利");
        addCountry(_countries,"au","505","澳大利亚");
        addCountry(_countries,"aw","363","阿鲁巴");
        addCountry(_countries,"az","400","阿塞拜疆");
        addCountry(_countries,"ba","218","波斯尼亚和黑塞哥维那");
        addCountry(_countries,"bb","342","巴巴多斯");
        addCountry(_countries,"bd","470","孟加拉");
        addCountry(_countries,"be","206","比利时");
        addCountry(_countries,"bf","613","布基纳法索");
        addCountry(_countries,"bg","284","保加利亚");
        addCountry(_countries,"bh","426","巴林");
        addCountry(_countries,"bi","642","布隆迪");
        addCountry(_countries,"bj","616","贝宁");
        addCountry(_countries,"bm","350","百慕大");
        addCountry(_countries,"bn","528","文莱");
        addCountry(_countries,"bo","736","玻利维亚");
        addCountry(_countries,"br","724","巴西");
        addCountry(_countries,"bs","364","巴哈马");
        addCountry(_countries,"bt","402","不丹");
        addCountry(_countries,"bw","652","博茨瓦纳");
        addCountry(_countries,"by","257","白俄罗斯");
        addCountry(_countries,"bz","702","伯利兹");
        addCountry(_countries,"ca","302","加拿大");
        addCountry(_countries,"cd","630","刚果");
        addCountry(_countries,"cf","623","中非");
        addCountry(_countries,"ch","228","瑞士");
        addCountry(_countries,"ci","612","科特迪瓦");
        addCountry(_countries,"ck","548","库克群岛");
        addCountry(_countries,"cl","730","智利");
        addCountry(_countries,"cm","624","喀麦隆");
        addCountry(_countries,"cn","461","中国");
        addCountry(_countries,"co","732","哥伦比亚");
        addCountry(_countries,"cr","712","哥斯达黎加");
        addCountry(_countries,"cu","368","古巴");
        addCountry(_countries,"cv","625","佛得角");
        addCountry(_countries,"cy","280","塞浦路斯");
        addCountry(_countries,"cz","230","捷克");
        addCountry(_countries,"de","262","德国");
        addCountry(_countries,"dj","638","吉布提");
        addCountry(_countries,"dk","238","丹麦");
        addCountry(_countries,"dm","366","多米尼克");
        addCountry(_countries,"do","370","多米尼加");
        addCountry(_countries,"dz","603","阿尔及利亚");
        addCountry(_countries,"ec","740","厄瓜多尔");
        addCountry(_countries,"ee","248","爱沙尼亚");
        addCountry(_countries,"eg","602","埃及");
        addCountry(_countries,"er","657","厄立特里亚");
        addCountry(_countries,"es","214","西班牙");
        addCountry(_countries,"et","636","埃塞俄比亚");
        addCountry(_countries,"fi","244","芬兰");
        addCountry(_countries,"fj","542","斐济");
        addCountry(_countries,"fm","550","密克罗尼西亚联邦");
        addCountry(_countries,"fo","288","法罗群岛");
        addCountry(_countries,"fr","208","法国");
        addCountry(_countries,"ga","628","加蓬");
        addCountry(_countries,"gb","234","英国");
        addCountry(_countries,"gd","352","格林纳达");
        addCountry(_countries,"ge","282","格鲁吉亚");
        addCountry(_countries,"gf","742","法属圭亚那");
        addCountry(_countries,"gh","620","加纳");
        addCountry(_countries,"gi","266","直布罗陀");
        addCountry(_countries,"gl","290","格陵兰");
        addCountry(_countries,"gm","607","冈比亚");
        addCountry(_countries,"gn","611","几内亚");
        addCountry(_countries,"gp","340","瓜德罗普");
        addCountry(_countries,"gq","627","赤道几内亚");
        addCountry(_countries,"gr","202","希腊");
        addCountry(_countries,"gt","704","危地马拉");
        addCountry(_countries,"gu","535","关岛");
        addCountry(_countries,"gy","738","圭亚那");
        addCountry(_countries,"hk","454","香港");
        addCountry(_countries,"hn","708","洪都拉斯");
        addCountry(_countries,"hr","219","克罗地亚");
        addCountry(_countries,"ht","372","海地");
        addCountry(_countries,"hu","216","匈牙利");
        addCountry(_countries,"id","510","印度尼西亚");
        addCountry(_countries,"ie","272","爱尔兰");
        addCountry(_countries,"il","425","以色列");
        addCountry(_countries,"in","406","印度");
        addCountry(_countries,"iq","418","伊拉克");
        addCountry(_countries,"ir","432","伊朗");
        addCountry(_countries,"is","274","冰岛");
        addCountry(_countries,"it","222","意大利");
        addCountry(_countries,"jm","338","牙买加");
        addCountry(_countries,"jo","416","约旦");
        addCountry(_countries,"jp","440","日本");
        addCountry(_countries,"ke","639","肯尼亚");
        addCountry(_countries,"kg","437","吉尔吉斯斯坦");
        addCountry(_countries,"kh","456","柬埔寨");
        addCountry(_countries,"ki","545","基里巴斯");
        addCountry(_countries,"km","654","科摩罗");
        addCountry(_countries,"kn","356","圣基茨和尼维斯");
        addCountry(_countries,"kp","467","朝鲜");
        addCountry(_countries,"kr","450","韩国");
        addCountry(_countries,"kw","419","科威特");
        addCountry(_countries,"ky","346","开曼群岛");
        addCountry(_countries,"kz","401","哈萨克斯坦");
        addCountry(_countries,"la","457","老挝");
        addCountry(_countries,"lb","415","黎巴嫩");
        addCountry(_countries,"lc","358","圣卢西亚");
        addCountry(_countries,"li","295","列支敦士登");
        addCountry(_countries,"lk","413","斯里兰卡");
        addCountry(_countries,"lr","618","利比里亚");
        addCountry(_countries,"ls","651","莱索托");
        addCountry(_countries,"lt","246","立陶宛");
        addCountry(_countries,"lv","247","拉脱维亚");
        addCountry(_countries,"ly","606","利比亚");
        addCountry(_countries,"ma","604","摩洛哥");
        addCountry(_countries,"mc","212","摩纳哥");
        addCountry(_countries,"md","259","摩尔多瓦");
        addCountry(_countries,"me","297","黑山");
        addCountry(_countries,"mg","646","马达加斯加");
        addCountry(_countries,"mh","551","马绍尔群岛");
        addCountry(_countries,"mk","294","卢森堡");
        addCountry(_countries,"ml","610","马里");
        addCountry(_countries,"mm","414","缅甸");
        addCountry(_countries,"mn","428","蒙古");
        addCountry(_countries,"mo","455","澳门");
        addCountry(_countries,"mp","534","北马里亚纳群岛");
        addCountry(_countries,"mq","340","马提尼克");
        addCountry(_countries,"mr","609","毛里塔尼亚");
        addCountry(_countries,"ms","354","蒙塞拉特岛");
        addCountry(_countries,"mt","278","马耳他");
        addCountry(_countries,"mu","617","毛里求斯");
        addCountry(_countries,"mv","472","马尔代夫");
        addCountry(_countries,"mw","650","马拉维");
        addCountry(_countries,"mx","334","墨西哥");
        addCountry(_countries,"my","502","马来西亚");
        addCountry(_countries,"mz","643","莫桑比克");
        addCountry(_countries,"na","649","纳米比亚");
        addCountry(_countries,"nc","546","新喀里多尼亚");
        addCountry(_countries,"ne","614","尼日尔");
        addCountry(_countries,"ng","621","尼日利亚");
        addCountry(_countries,"ni","710","尼加拉瓜");
        addCountry(_countries,"nl","204","荷兰");
        addCountry(_countries,"no","242","挪威");
        addCountry(_countries,"np","429","尼泊尔");
        addCountry(_countries,"nr","536","瑙鲁");
        addCountry(_countries,"nu","555","纽埃");
        addCountry(_countries,"nz","530","新西兰");
        addCountry(_countries,"om","422","阿曼");
        addCountry(_countries,"pa","714","巴拿马");
        addCountry(_countries,"pe","716","秘鲁");
        addCountry(_countries,"pf","547","法属波利尼西亚");
        addCountry(_countries,"pg","537","巴布亚新几内亚");
        addCountry(_countries,"ph","515","菲律宾");
        addCountry(_countries,"pk","410","巴基斯坦");
        addCountry(_countries,"pl","260","波兰");
        addCountry(_countries,"pm","308","圣皮埃尔和密克隆");
        addCountry(_countries,"pr","330","波多黎各");
        addCountry(_countries,"ps","425","巴勒斯坦");
        addCountry(_countries,"pt","268","葡萄牙");
        addCountry(_countries,"pw","552","帕劳");
        addCountry(_countries,"py","744","巴拉圭");
        addCountry(_countries,"qa","427","卡塔尔");
        addCountry(_countries,"re","647","留尼汪");
        addCountry(_countries,"ro","226","罗马尼亚");
        addCountry(_countries,"rs","220","塞尔维亚");
        addCountry(_countries,"ru","250","俄罗斯");
        addCountry(_countries,"rw","635","卢旺达");
        addCountry(_countries,"sa","420","沙特阿拉伯");
        addCountry(_countries,"sb","540","所罗门群岛");
        addCountry(_countries,"sc","633","塞舌尔");
        addCountry(_countries,"sd","634","苏丹");
        addCountry(_countries,"se","240","瑞典");
        addCountry(_countries,"sg","525","新加坡");
        addCountry(_countries,"si","293","斯洛文尼亚");
        addCountry(_countries,"sk","231","斯洛伐克");
        addCountry(_countries,"sl","619","塞拉利昂");
        addCountry(_countries,"sm","292","圣马力诺");
        addCountry(_countries,"sn","608","塞内加尔");
        addCountry(_countries,"so","637","索马里");
        addCountry(_countries,"sr","746","苏里南");
        addCountry(_countries,"st","626","圣多美和普林西比");
        addCountry(_countries,"sv","706","萨尔瓦多");
        addCountry(_countries,"sy","417","叙利亚");
        addCountry(_countries,"sz","653","斯威士兰");
        addCountry(_countries,"tc","376","特克斯和凯科斯群岛");
        addCountry(_countries,"td","622","乍得");
        addCountry(_countries,"tg","615","多哥");
        addCountry(_countries,"th","520","泰国");
        addCountry(_countries,"tj","436","塔吉克斯坦");
        addCountry(_countries,"tl","514","东帝汶");
        addCountry(_countries,"tm","438","土库曼斯坦");
        addCountry(_countries,"tn","605","突尼斯");
        addCountry(_countries,"to","539","汤加");
        addCountry(_countries,"tr","286","土耳其");
        addCountry(_countries,"tt","374","特立尼达和多巴哥");
        addCountry(_countries,"tw","466","台湾");
        addCountry(_countries,"tz","640","坦桑尼亚");
        addCountry(_countries,"ua","255","乌克兰");
        addCountry(_countries,"ug","641","乌干达");
        addCountry(_countries,"us","316","美国");
        addCountry(_countries,"uy","748","乌拉圭");
        addCountry(_countries,"uz","434","乌兹别克斯坦");
        addCountry(_countries,"va","225","梵蒂冈");
        addCountry(_countries,"vc","360","圣文森特和格林纳丁斯");
        addCountry(_countries,"ve","734","委内瑞拉");
        addCountry(_countries,"vg","348","英属维尔京群岛");
        addCountry(_countries,"vi","332","美属维尔京群岛");
        addCountry(_countries,"vn","452","越南");
        addCountry(_countries,"vu","541","瓦努阿图");
        addCountry(_countries,"wf","543","瓦利斯和富图纳");
        addCountry(_countries,"ws","549","萨摩亚");
        addCountry(_countries,"ye","421","也门");
        addCountry(_countries,"za","655","南非");
        addCountry(_countries,"zm","645","赞比亚");
        addCountry(_countries,"zw","648","津巴布韦");
        java.util.Collections.sort(_countries, new Comparator<Country>() {
            @Override
            public int compare(Country c1, Country c2) {
                return c1.firstLetterOfChineseName.compareTo(c2.firstLetterOfChineseName);
            }
        });
        return _countries;
    }


    public Country(String shortcut, String mcc, String chineseName, String firstLetterOfChineseName) {
        this.shortcut = shortcut;
        this.chineseName = chineseName;
        this.mcc = mcc;
        this.firstLetterOfChineseName = firstLetterOfChineseName;
    }

    public String getFirstLetterOfChineseName() {
        return firstLetterOfChineseName;
    }

    public void setFirstLetterOfChineseName(String firstLetterOfChineseName) {
        this.firstLetterOfChineseName = firstLetterOfChineseName;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }
}
