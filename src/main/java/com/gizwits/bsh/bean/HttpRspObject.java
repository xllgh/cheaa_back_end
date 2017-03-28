package com.gizwits.bsh.bean;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.Header;

import java.util.Map;

/**
 * Created by neil on 2016/11/5.
 */
public class HttpRspObject {

    private Map<String, String> headersMap;
    private String body;
    private int statusCode;

    public HttpRspObject(int statusCode, Map<String, String> headersMap, String body) {
        this.statusCode = statusCode;
        this.headersMap = headersMap;
        this.body = body;
    }

    public Map<String, String> getHeadersMap() {
        return headersMap;
    }

    public void setHeadersMap(Map<String, String> headersMap) {
        this.headersMap = headersMap;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
