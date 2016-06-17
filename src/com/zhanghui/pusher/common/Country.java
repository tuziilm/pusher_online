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
        addCountry(_countries,"ae","431","������");
        addCountry(_countries,"af","412","������");
        addCountry(_countries,"ag","344","����ϺͰͲ���");
        addCountry(_countries,"ai","365","������");
        addCountry(_countries,"al","276","����������");
        addCountry(_countries,"am","283","��������");
        addCountry(_countries,"ao","631","������");
        addCountry(_countries,"ar","722","����͢");
        addCountry(_countries,"as","544","������Ħ��");
        addCountry(_countries,"at","232","�µ���");
        addCountry(_countries,"au","505","�Ĵ�����");
        addCountry(_countries,"aw","363","��³��");
        addCountry(_countries,"az","400","�����ݽ�");
        addCountry(_countries,"ba","218","��˹���Ǻͺ�����ά��");
        addCountry(_countries,"bb","342","�ͰͶ�˹");
        addCountry(_countries,"bd","470","�ϼ���");
        addCountry(_countries,"be","206","����ʱ");
        addCountry(_countries,"bf","613","�����ɷ���");
        addCountry(_countries,"bg","284","��������");
        addCountry(_countries,"bh","426","����");
        addCountry(_countries,"bi","642","��¡��");
        addCountry(_countries,"bj","616","����");
        addCountry(_countries,"bm","350","��Ľ��");
        addCountry(_countries,"bn","528","����");
        addCountry(_countries,"bo","736","����ά��");
        addCountry(_countries,"br","724","����");
        addCountry(_countries,"bs","364","�͹���");
        addCountry(_countries,"bt","402","����");
        addCountry(_countries,"bw","652","��������");
        addCountry(_countries,"by","257","�׶���˹");
        addCountry(_countries,"bz","702","������");
        addCountry(_countries,"ca","302","���ô�");
        addCountry(_countries,"cd","630","�չ�");
        addCountry(_countries,"cf","623","�з�");
        addCountry(_countries,"ch","228","��ʿ");
        addCountry(_countries,"ci","612","���ص���");
        addCountry(_countries,"ck","548","���Ⱥ��");
        addCountry(_countries,"cl","730","����");
        addCountry(_countries,"cm","624","����¡");
        addCountry(_countries,"cn","461","�й�");
        addCountry(_countries,"co","732","���ױ���");
        addCountry(_countries,"cr","712","��˹�����");
        addCountry(_countries,"cu","368","�Ű�");
        addCountry(_countries,"cv","625","��ý�");
        addCountry(_countries,"cy","280","����·˹");
        addCountry(_countries,"cz","230","�ݿ�");
        addCountry(_countries,"de","262","�¹�");
        addCountry(_countries,"dj","638","������");
        addCountry(_countries,"dk","238","����");
        addCountry(_countries,"dm","366","�������");
        addCountry(_countries,"do","370","�������");
        addCountry(_countries,"dz","603","����������");
        addCountry(_countries,"ec","740","��϶��");
        addCountry(_countries,"ee","248","��ɳ����");
        addCountry(_countries,"eg","602","����");
        addCountry(_countries,"er","657","����������");
        addCountry(_countries,"es","214","������");
        addCountry(_countries,"et","636","���������");
        addCountry(_countries,"fi","244","����");
        addCountry(_countries,"fj","542","쳼�");
        addCountry(_countries,"fm","550","�ܿ�������������");
        addCountry(_countries,"fo","288","����Ⱥ��");
        addCountry(_countries,"fr","208","����");
        addCountry(_countries,"ga","628","����");
        addCountry(_countries,"gb","234","Ӣ��");
        addCountry(_countries,"gd","352","�����ɴ�");
        addCountry(_countries,"ge","282","��³����");
        addCountry(_countries,"gf","742","����������");
        addCountry(_countries,"gh","620","����");
        addCountry(_countries,"gi","266","ֱ������");
        addCountry(_countries,"gl","290","������");
        addCountry(_countries,"gm","607","�Ա���");
        addCountry(_countries,"gn","611","������");
        addCountry(_countries,"gp","340","�ϵ�����");
        addCountry(_countries,"gq","627","���������");
        addCountry(_countries,"gr","202","ϣ��");
        addCountry(_countries,"gt","704","Σ������");
        addCountry(_countries,"gu","535","�ص�");
        addCountry(_countries,"gy","738","������");
        addCountry(_countries,"hk","454","���");
        addCountry(_countries,"hn","708","�鶼��˹");
        addCountry(_countries,"hr","219","���޵���");
        addCountry(_countries,"ht","372","����");
        addCountry(_countries,"hu","216","������");
        addCountry(_countries,"id","510","ӡ��������");
        addCountry(_countries,"ie","272","������");
        addCountry(_countries,"il","425","��ɫ��");
        addCountry(_countries,"in","406","ӡ��");
        addCountry(_countries,"iq","418","������");
        addCountry(_countries,"ir","432","����");
        addCountry(_countries,"is","274","����");
        addCountry(_countries,"it","222","�����");
        addCountry(_countries,"jm","338","�����");
        addCountry(_countries,"jo","416","Լ��");
        addCountry(_countries,"jp","440","�ձ�");
        addCountry(_countries,"ke","639","������");
        addCountry(_countries,"kg","437","������˹˹̹");
        addCountry(_countries,"kh","456","����կ");
        addCountry(_countries,"ki","545","�����˹");
        addCountry(_countries,"km","654","��Ħ��");
        addCountry(_countries,"kn","356","ʥ���ĺ���ά˹");
        addCountry(_countries,"kp","467","����");
        addCountry(_countries,"kr","450","����");
        addCountry(_countries,"kw","419","������");
        addCountry(_countries,"ky","346","����Ⱥ��");
        addCountry(_countries,"kz","401","������˹̹");
        addCountry(_countries,"la","457","����");
        addCountry(_countries,"lb","415","�����");
        addCountry(_countries,"lc","358","ʥ¬����");
        addCountry(_countries,"li","295","��֧��ʿ��");
        addCountry(_countries,"lk","413","˹������");
        addCountry(_countries,"lr","618","��������");
        addCountry(_countries,"ls","651","������");
        addCountry(_countries,"lt","246","������");
        addCountry(_countries,"lv","247","����ά��");
        addCountry(_countries,"ly","606","������");
        addCountry(_countries,"ma","604","Ħ���");
        addCountry(_countries,"mc","212","Ħ�ɸ�");
        addCountry(_countries,"md","259","Ħ������");
        addCountry(_countries,"me","297","��ɽ");
        addCountry(_countries,"mg","646","����˹��");
        addCountry(_countries,"mh","551","���ܶ�Ⱥ��");
        addCountry(_countries,"mk","294","¬ɭ��");
        addCountry(_countries,"ml","610","����");
        addCountry(_countries,"mm","414","���");
        addCountry(_countries,"mn","428","�ɹ�");
        addCountry(_countries,"mo","455","����");
        addCountry(_countries,"mp","534","����������Ⱥ��");
        addCountry(_countries,"mq","340","�������");
        addCountry(_countries,"mr","609","ë��������");
        addCountry(_countries,"ms","354","�������ص�");
        addCountry(_countries,"mt","278","�����");
        addCountry(_countries,"mu","617","ë����˹");
        addCountry(_countries,"mv","472","�������");
        addCountry(_countries,"mw","650","����ά");
        addCountry(_countries,"mx","334","ī����");
        addCountry(_countries,"my","502","��������");
        addCountry(_countries,"mz","643","Īɣ�ȿ�");
        addCountry(_countries,"na","649","���ױ���");
        addCountry(_countries,"nc","546","�¿��������");
        addCountry(_countries,"ne","614","���ն�");
        addCountry(_countries,"ng","621","��������");
        addCountry(_countries,"ni","710","�������");
        addCountry(_countries,"nl","204","����");
        addCountry(_countries,"no","242","Ų��");
        addCountry(_countries,"np","429","�Ჴ��");
        addCountry(_countries,"nr","536","�³");
        addCountry(_countries,"nu","555","Ŧ��");
        addCountry(_countries,"nz","530","������");
        addCountry(_countries,"om","422","����");
        addCountry(_countries,"pa","714","������");
        addCountry(_countries,"pe","716","��³");
        addCountry(_countries,"pf","547","��������������");
        addCountry(_countries,"pg","537","�Ͳ����¼�����");
        addCountry(_countries,"ph","515","���ɱ�");
        addCountry(_countries,"pk","410","�ͻ�˹̹");
        addCountry(_countries,"pl","260","����");
        addCountry(_countries,"pm","308","ʥƤ�������ܿ�¡");
        addCountry(_countries,"pr","330","�������");
        addCountry(_countries,"ps","425","����˹̹");
        addCountry(_countries,"pt","268","������");
        addCountry(_countries,"pw","552","����");
        addCountry(_countries,"py","744","������");
        addCountry(_countries,"qa","427","������");
        addCountry(_countries,"re","647","������");
        addCountry(_countries,"ro","226","��������");
        addCountry(_countries,"rs","220","����ά��");
        addCountry(_countries,"ru","250","����˹");
        addCountry(_countries,"rw","635","¬����");
        addCountry(_countries,"sa","420","ɳ�ذ�����");
        addCountry(_countries,"sb","540","������Ⱥ��");
        addCountry(_countries,"sc","633","�����");
        addCountry(_countries,"sd","634","�յ�");
        addCountry(_countries,"se","240","���");
        addCountry(_countries,"sg","525","�¼���");
        addCountry(_countries,"si","293","˹��������");
        addCountry(_countries,"sk","231","˹�工��");
        addCountry(_countries,"sl","619","��������");
        addCountry(_countries,"sm","292","ʥ����ŵ");
        addCountry(_countries,"sn","608","���ڼӶ�");
        addCountry(_countries,"so","637","������");
        addCountry(_countries,"sr","746","������");
        addCountry(_countries,"st","626","ʥ��������������");
        addCountry(_countries,"sv","706","�����߶�");
        addCountry(_countries,"sy","417","������");
        addCountry(_countries,"sz","653","˹��ʿ��");
        addCountry(_countries,"tc","376","�ؿ�˹�Ϳ���˹Ⱥ��");
        addCountry(_countries,"td","622","է��");
        addCountry(_countries,"tg","615","���");
        addCountry(_countries,"th","520","̩��");
        addCountry(_countries,"tj","436","������˹̹");
        addCountry(_countries,"tl","514","������");
        addCountry(_countries,"tm","438","������˹̹");
        addCountry(_countries,"tn","605","ͻ��˹");
        addCountry(_countries,"to","539","����");
        addCountry(_countries,"tr","286","������");
        addCountry(_countries,"tt","374","�������Ͷ�͸�");
        addCountry(_countries,"tw","466","̨��");
        addCountry(_countries,"tz","640","̹ɣ����");
        addCountry(_countries,"ua","255","�ڿ���");
        addCountry(_countries,"ug","641","�ڸɴ�");
        addCountry(_countries,"us","316","����");
        addCountry(_countries,"uy","748","������");
        addCountry(_countries,"uz","434","���ȱ��˹̹");
        addCountry(_countries,"va","225","��ٸ�");
        addCountry(_countries,"vc","360","ʥ��ɭ�غ͸����ɶ�˹");
        addCountry(_countries,"ve","734","ί������");
        addCountry(_countries,"vg","348","Ӣ��ά����Ⱥ��");
        addCountry(_countries,"vi","332","����ά����Ⱥ��");
        addCountry(_countries,"vn","452","Խ��");
        addCountry(_countries,"vu","541","��Ŭ��ͼ");
        addCountry(_countries,"wf","543","����˹�͸�ͼ��");
        addCountry(_countries,"ws","549","��Ħ��");
        addCountry(_countries,"ye","421","Ҳ��");
        addCountry(_countries,"za","655","�Ϸ�");
        addCountry(_countries,"zm","645","�ޱ���");
        addCountry(_countries,"zw","648","��Ͳ�Τ");
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
