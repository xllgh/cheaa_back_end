package com.gizwits.bsh.filter;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import org.apache.commons.io.output.TeeOutputStream;
import org.apache.commons.lang.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.DelegatingServletOutputStream;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class TranslatorRequestFilter extends CommonsRequestLoggingFilter {
    private static final Logger logger = LoggerFactory.getLogger(TranslatorRequestFilter.class);

    private ThreadLocal<ByteArrayOutputStream> byteArrayOutputStreamThreadLocal = new ThreadLocal() {

    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        byteArrayOutputStreamThreadLocal.set(new ByteArrayOutputStream());
        HttpServletResponse responseWrapper = loggingResponseWrapper(response);
        super.doFilterInternal(request, responseWrapper, filterChain);
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        super.beforeRequest(request, message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        super.afterRequest(request, message);

        String uri = request.getRequestURI();
        String requestMsg = getPayload(message);
        String header = getRequestHeader(request);
        Date now = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String time = dateFormat.format(now);

        ByteArrayOutputStream outputStream = byteArrayOutputStreamThreadLocal.get();
        String responseMsg = getResponseMessage(request, outputStream);
        responseMsg = JSON.toJSONString(JSON.parse(responseMsg), true);

        String log = time + "\n" + uri + "\n" + header + "\n\n" + requestMsg + "\n\n" + responseMsg;
        logger.info(log);
    }

    private String getPayload(String message) {
        String payloadStart = ";payload=";
        String payloadEnd = ")";
        int startIndex = message.indexOf(payloadStart);
        if (startIndex == -1) {
            return null;
        }
        return message.substring(startIndex + payloadStart.length(), message.lastIndexOf(payloadEnd));
    }

    private String getRequestHeader(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();

        List<String> headers = new ArrayList<>();
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String header = headerName + ": " + request.getHeader(headerName);
                headers.add(header);
            }
        }
        return Joiner.on(", ").join(headers);
    }

    private String getResponseMessage(HttpServletRequest request, ByteArrayOutputStream baos) {
        try {
            return baos.toString(getCharacterEncoding(request));
        } catch (UnsupportedEncodingException e) {

        }
        return null;
    }

    private String getCharacterEncoding(HttpServletRequest request) {
        return CharEncoding.UTF_8;
    }

    private HttpServletResponse loggingResponseWrapper(HttpServletResponse response) {

        return new HttpServletResponseWrapper(response) {
            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                return new DelegatingServletOutputStream(
                        new TeeOutputStream(super.getOutputStream(), byteArrayOutputStreamThreadLocal.get())
                );
            }
        };
    }

}
