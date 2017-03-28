package com.gizwits.bsh.bean.reqvo.web;

/**
 * 设备列表请求对象
 */
public class DeviceListReqVO extends DataTableVO {
    /**
     * 搜索类型
     * 设备名称 "deviceName"
     * 平台名称 "platformName"
     * 设备ID "deivceId"
     */
    private String searchType;

    /**
     * 关键字
     */
    private String keyword;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
