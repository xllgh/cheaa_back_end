package com.gizwits.bsh.enums;

/**
 * Created by neil on 2016/11/5.
 */
public enum MultiCloudHost {

    HAIXIN("121.40.132.152"),
    HAIER("203.187.186.136:8180");

    MultiCloudHost(String host) {
        this.host = host;
    }

    String host;

    public String getHost() {
        return host;
    }
}
