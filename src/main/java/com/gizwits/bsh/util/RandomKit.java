package com.gizwits.bsh.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mahone Wu on 2015/8/24.
 *
 * 生成随机数
 */
public class RandomKit {
    /**
     * 生成四位数的随机数
     * @return
     */
    public static String getThreenumRandomCode(){
        String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9" };
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(5, 9);
        return result;
    }

    public static String getnumRandomCode(Integer start,Integer end){
        String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7",
                "8", "9" };
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(start, end);
        return result;
    }

    public static String pageRandomData(){
        StringBuffer sb= new StringBuffer();
        return sb.append(getThreenumRandomCode()).append(System.currentTimeMillis()).toString();
    }

    public static void main(String[] args) {
        System.out.println(getThreenumRandomCode());
        System.out.println(System.currentTimeMillis());
    }
}
