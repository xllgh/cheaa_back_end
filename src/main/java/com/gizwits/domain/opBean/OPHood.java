package com.gizwits.domain.opBean;

/**
 * 油烟机的操作类
 * Created by neil on 2016/11/6.
 */
public class OPHood implements BasicOP {

    private String onOffStatus;//	String	开关机控制 	"false"	关机 "true"	开机
    private Integer lighLevel;//	String	"0"	关	照明控制 "1"	弱亮 "2"	中亮 "3"	高亮
    private Integer windLevel;//	String	"0"	关	风速控制 "1"	柔速 "2"	低速 "3"	高速

    public String getOnOffStatus() {
        return onOffStatus;
    }

    public void setOnOffStatus(String onOffStatus) {
        this.onOffStatus = onOffStatus;
    }

    public Integer getLighLevel() {
        return lighLevel;
    }

    public void setLighLevel(Integer lighLevel) {
        this.lighLevel = lighLevel;
    }

    public Integer getWindLevel() {
        return windLevel;
    }

    public void setWindLevel(Integer windLevel) {
        this.windLevel = windLevel;
    }
}
