package com.gizwits.bsh.bean;

import java.util.List;

/**
 * 博西设备程序
 */
public class HomeApplianceProgram {
    private String program;
    private List<HomeApplianceStatus> options;

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public List<HomeApplianceStatus> getOptions() {
        return options;
    }

    public void setOptions(List<HomeApplianceStatus> options) {
        this.options = options;
    }
}
