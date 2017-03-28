package com.gizwits.bsh.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WriterKit {
    private static Logger logger = LoggerFactory.getLogger(WriterKit.class);

    public static void renderJson(HttpServletResponse response,String jsonStr){
		response.setContentType("application/json;charset = utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(jsonStr);
			out.flush();
			out.close();
		} catch (IllegalStateException e){
            logger.warn("可能是调用了两次response",e);
        }catch (IOException e) {
            logger.warn("在OUTJSON中获时异常",e);
		}
	}
	public static void render(HttpServletResponse response,String renderStr){
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(renderStr);
			out.flush();
			out.close();
		} catch (IllegalStateException e){
			logger.warn("可能是调用了两次response",e);
		}catch (IOException e) {
			logger.warn("在render中抛出异常",e);
		}

	}
	public static void outForException(HttpServletResponse response,String result,Exception ex){
		response.setContentType("application/json ; charset = utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
            if(ex.getMessage().equalsIgnoreCase("java.io.IOException")){
                logger.warn("response Io异常，可忽略");
            }else {
                logger.error("系统抛出未知异常。请攻城狮们速度解决",ex);
            }
		} catch (IllegalStateException e){
            logger.warn("可能是调用了两次response",e);
        }catch (IOException e) {
            logger.warn("在OUTJSON中获时异常",e);
		}

	}

	//写出数据
	public static void outString(HttpServletResponse response,String jsonStr){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		try {
			response.getWriter().write(jsonStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
