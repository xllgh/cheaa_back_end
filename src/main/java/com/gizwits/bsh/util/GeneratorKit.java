package com.gizwits.bsh.util;

import com.gizwits.bsh.common.config.SysConsts;

import java.util.*;

/**
 * Created by zhl on 2016/11/23.
 * 生成一些需要的id
 */
public class GeneratorKit {


    public static String[] chars = new String[] { "a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z" };

    public static String generPwd(String pwd){
        String result = MD5Kit.encode(pwd + SysConsts.SALT);
        return result;
    }

    /**
     * 生成用户id
     * @return
     */
    public static Long generatorID(){
        StringBuilder sb=new StringBuilder();
        String id= "";
        //当前时间戳
        Long now=System.currentTimeMillis();
        //四位的随机数
        String ranNum=getFourRandomCode();
        String StringResult=sb.append(id).append(now).append(ranNum).toString();
        Long result=Long.parseLong(StringResult);
        return result;
    }
    /**
     * 生成新鸿基SessionId
     * @return
     */
    public static Long generatorXHJID(){
        StringBuilder sb=new StringBuilder();
        //当前时间戳
        Long now=System.currentTimeMillis();
        //两位的随机数
        String ranNum=getTwoRandomCode();
        String StringResult=sb.append(now).append(ranNum).toString();
        Long result=Long.parseLong(StringResult);
        return result;
    }

    /**
     * 生成UUId
     * @return
     */
    public static String getUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    /**
     * 获得指定数目的UUID
     * @param number int 需要获得的UUID数量
     * @return String[] UUID数组
     */
    public static String[] getUUID(int number){
        if(number < 1){
            return null;
        }
        String[] ss = new String[number];
        for(int i=0;i<number;i++){
            ss[i] = getUUID();
        }
        return ss;
    }

    /**
     * 替换中间的“-”
     * @return
     */
    public static String getUUIDReplaceStr(){
        String s = UUID.randomUUID().toString();
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }

    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

    public static Long getID(){
        StringBuffer buffer = new StringBuffer();
        buffer.append(String.valueOf(System.currentTimeMillis()));
        buffer.append(RandomKit.getThreenumRandomCode());
        return Long.parseLong(buffer.toString());
    }

    public static String getFourRandomCode(){
        String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7","8", "9" };
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
    public static String getTwoRandomCode(){
        String[] beforeShuffle = new String[] { "0", "1", "2", "3", "4", "5", "6", "7","8", "9" };
        List list = Arrays.asList(beforeShuffle);
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        String result = afterShuffle.substring(7, 9);
        return result;
    }

    public static String getDateFormateID(){
        return new java.text.SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+getFourRandomCode();
    }

    public static void main(String[] args) {
        System.out.println(getFourRandomCode());
        System.out.println(getUUIDReplaceStr().length());
        System.out.println(getDateFormateID());
        System.out.println(getID());
        System.out.println((int)System.currentTimeMillis());

    }
}
