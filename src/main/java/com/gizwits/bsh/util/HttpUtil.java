package com.gizwits.bsh.util;

import com.gizwits.bsh.bean.HttpRspObject;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by neil on 2016/11/5.
 */
public class HttpUtil {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    public static HttpRspObject get(String url, Map<String, String> headersMap) {
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(1000).setConnectTimeout(30000)
                .setSocketTimeout(30000).build();
        httpGet.setConfig(requestConfig);
        if (headersMap != null && headersMap.size() > 0) {
            httpGet.setHeaders(assemblyHeader(headersMap));
        }
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = null;
        HttpRspObject httpRspObject = null;
        try {
            httpClient = SSLUtil.createSSLClientDefault();
            httpResponse = httpClient.execute(httpGet);
            httpRspObject = handleResponse(httpResponse);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.warn(e.getMessage(), e);
            }
        }
        return httpRspObject;
    }

    public static HttpRspObject post(String url, Map<String, String> headersMap, String body, String contentType) {
        HttpPost httpPost = new HttpPost(url);
        HttpRspObject httpRspObject = request(httpPost, headersMap, body, contentType);
        return httpRspObject;
    }

    public static HttpRspObject put(String url, Map<String, String> headersMap, String body, String contentType) {
        HttpPut httpPut = new HttpPut(url);
        HttpRspObject httpRspObject = request(httpPut, headersMap, body, contentType);
        return httpRspObject;
    }

    private static HttpRspObject request(HttpEntityEnclosingRequestBase http, Map<String, String> headersMap, String body, String contentType) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(1000).setConnectTimeout(30000)
                .setSocketTimeout(30000).build();
        http.setConfig(requestConfig);
        if (headersMap != null && headersMap.size() > 0) {
            http.setHeaders(assemblyHeader(headersMap));
        }
        CloseableHttpResponse httpResponse = null;
        CloseableHttpClient httpClient = null;
        HttpRspObject httpRspObject = null;
        try {
            StringEntity sEntity = new StringEntity(body);
            sEntity.setContentEncoding("UTF-8");
            sEntity.setContentType(contentType);
            http.setEntity(sEntity);

            httpClient = SSLUtil.createSSLClientDefault();
            httpResponse = httpClient.execute(http);
            httpRspObject = handleResponse(httpResponse);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        } finally {
            try {
                if (httpResponse != null) {
                    httpResponse.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                logger.warn(e.getMessage(), e);
            }
        }
        return httpRspObject;
    }

    /**
     * map to header[]
     *
     * @param headersMap
     * @return
     */
    private static Header[] assemblyHeader(Map<String, String> headersMap) {
        Header[] allHeader = new BasicHeader[headersMap.size()];
        int i = 0;
        for (String str : headersMap.keySet()) {
            allHeader[i] = new BasicHeader(str, headersMap.get(str));
            i++;
        }
        return allHeader;
    }

    private static Map<String, String> assmblyMapWithHeader(Header[] headers) {
        if (headers == null || headers.length < 1) {
            return null;
        }
        Map<String, String> heaersMap = new HashMap<>();
        for(Header header : headers) {
            heaersMap.put(header.getName(), header.getValue());
        }
        return heaersMap;
    }

    public static HttpRspObject handleResponse(final HttpResponse response)
            throws IOException {
        final StatusLine statusLine = response.getStatusLine();
        final HttpEntity entity = response.getEntity();
//        if (statusLine.getStatusCode() >= 300) {
//            EntityUtils.consume(entity);
//            throw new HttpResponseException(statusLine.getStatusCode(),
//                    statusLine.getReasonPhrase());
//        }
        return new HttpRspObject(response.getStatusLine().getStatusCode(), assmblyMapWithHeader(response.getAllHeaders()),
                entity == null ? null : EntityUtils.toString(entity, "UTF-8"));
    }

    /**
     * InputStream to string
     *
     * @param is
     * @return
     */
    private static String inputStream2Str(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            // TODO: handle exception
        }
        return buffer.toString();
    }

}
