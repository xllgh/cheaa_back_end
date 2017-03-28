package com.gizwits.bsh.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhl on 2016/12/5.
 */
public class MapKit {

    public static Map<String,Object> covertListToMapWithKeyAttr(List list,String keyAttr){
        if(list==null||list.size()<=0)
            return null;
        Map resultMap = new HashMap();
        try{
            for (int i=0; i<list.size(); i++) {
                Class the = list.get(i).getClass();
                if(the.getDeclaredField(keyAttr)==null){
                    return null;
                }
                Field field = the.getDeclaredField(keyAttr);
                field.setAccessible(true);
                resultMap.put(field.get(list.get(i)), list.get(i));
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }

        return resultMap;
    }
}
