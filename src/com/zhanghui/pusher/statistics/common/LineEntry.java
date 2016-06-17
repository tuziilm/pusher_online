package com.zhanghui.pusher.statistics.common;

public class LineEntry extends ValidLineEntry{
    public String name;
    public String identity;
    public long time;
    public String ip;
    protected String[] entries;

    public LineEntry load(String line){
        entries = line.split(Config.SEP,-1);
        if(entries.length>3){
            name=entries[0];
            identity=entries[1];
            try{
                time=Long.valueOf(entries[2]);
            }catch(Exception e){
                setInvalid();
            }
            ip=entries[3];
        }else{
            setInvalid();
        }
        if(isInvalid()){
            System.out.println("invalid line: "+line);
        }
        return this;
    }
}
