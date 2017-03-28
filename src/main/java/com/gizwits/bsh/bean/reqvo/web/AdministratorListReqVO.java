package com.gizwits.bsh.bean.reqvo.web;

/**
 * 管理员列表
 */
public class AdministratorListReqVO extends DataTableVO {
    /**
     * 搜索类型
     * 用户名 "userName"
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
