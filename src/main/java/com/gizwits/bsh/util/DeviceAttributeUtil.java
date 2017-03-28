package com.gizwits.bsh.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gizwits.bsh.common.config.SysConsts;
import com.gizwits.bsh.enums.TranslateRule;
import com.gizwits.bsh.model.entity.DeviceAttributeAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhl on 2016/12/5.
 */
public class DeviceAttributeUtil {
    private final static Logger logger = LoggerFactory.getLogger(DeviceAttributeUtil.class);

    /**
     * 将指定平台的设备属性及属性值进行转换
     * @param platId  设备平台ID
     * @param deviceType 设备类型
     * @param sourceJson 待处理属性Json
     * @param isReverse false:第三方转换为标准;true:标准转换为第三方;
     * @return
     */
    public static JSONObject switchDeviceAttribute2Satisfy(String platId,String deviceType,JSONObject sourceJson,boolean isReverse){
        if(sourceJson==null||sourceJson.size()<=0){
            logger.info("===Enter param JSONObject cannot be null===");
            return sourceJson;
        }
        if((SysConsts.DeviceAttrAdapterMap==null||SysConsts.DeviceAttrAdapterMap.size()<=0)&&
                (SysConsts.DeviceAttrAdapterReverseMap==null||SysConsts.DeviceAttrAdapterReverseMap.size()<=0)){
            logger.info("===System have not init config or does not have any device attribute adapter====");
            return sourceJson;
        }
        if((SysConsts.DeviceAttrAdapterMap.get(platId+deviceType)==null&&!isReverse)||
                (SysConsts.DeviceAttrAdapterReverseMap.get(platId+deviceType)==null&&isReverse)){
            logger.info("===Device Attribute Adapter does contains identify by platId:{} and deviceType:{}====",platId,deviceType);
            return sourceJson;
        }

        JSONObject resultJson = new JSONObject();

        Map adapterMap = isReverse?SysConsts.DeviceAttrAdapterReverseMap.get(platId+deviceType):SysConsts.DeviceAttrAdapterMap.get(platId+deviceType);
        Iterator ite =  sourceJson.keySet().iterator();
        while (ite.hasNext()){
            translateAttrAndValue(resultJson,sourceJson,adapterMap,ite.next().toString(),isReverse);
        }
        return resultJson;
    }

    private static void translateAttrAndValue(JSONObject resultJson,JSONObject sourceJson, Map adapterMap, String oldKey,boolean isReverse){
        if(!adapterMap.containsKey(oldKey)){
            logger.info("===DeviceAttrAdapter Map does have key:{}",oldKey);
            return;
        }
        if(adapterMap.get(oldKey)==null){
            logger.info("===Current DeviceAttributeAdapter does not have AdapterMap value====");
            return;
        }
        JSONObject ruleJson = null;
        if(isReverse){//根据isReverse判断是正向还是反向转译,true:反向;false:正向;
            ruleJson = JSONObject.parseObject(((DeviceAttributeAdapter)adapterMap.get(oldKey)).getReverseAdapterMap());
        }else {
            ruleJson = JSONObject.parseObject(((DeviceAttributeAdapter)adapterMap.get(oldKey)).getAdapterMap());
        }

        if(ruleJson.getString("rule").equalsIgnoreCase(TranslateRule.ONE_TO_ONE.getRule())||
                ruleJson.getString("rule").equalsIgnoreCase(TranslateRule.ONE_TO_MANY.getRule())){
            processOneToManyRule(resultJson,ruleJson,sourceJson.getString(oldKey));
        }else if(ruleJson.getString("rule").equalsIgnoreCase(TranslateRule.MANY_TO_MANY.getRule())||
                ruleJson.getString("rule").equalsIgnoreCase(TranslateRule.MANY_TO_ONE.getRule())){
            processManyToManyRule(resultJson,ruleJson,sourceJson);
        }
    }

    /**
     *  处理OneToOne和OneToMany
     * {
     *    "rule":"OneToMany",
     *    "data":[
     *        {"name":"1","value":[{"name":"windDirectionHorizontal","value":"0"},{"name":"windDirectionVertical","value":"0"}]},
     *        {"name":"2","value":[{"name":"windDirectionHorizontal","value":"0"},{"name":"windDirectionVertical","value":"1"}]},
     *        {"name":"3","value":[{"name":"windDirectionHorizontal","value":"1"},{"name":"windDirectionVertical","value":"0"}]},
     *        {"name":"4","value":[{"name":"windDirectionHorizontal","value":"1"},{"name":"windDirectionVertical","value":"1"}]}
     *    ]
     * }
     * @param resultJson
     * @param ruleJson
     * @param oldValue
     */
    private static void processOneToManyRule(JSONObject resultJson,JSONObject ruleJson,String oldValue){
        JSONArray attrValueArray = ruleJson.getJSONArray("data");
        for(int i=0; i<attrValueArray.size(); i++){
            JSONObject tmpObj = attrValueArray.getJSONObject(i);
            if(tmpObj.getString("name").equalsIgnoreCase(oldValue)){
                if(!tmpObj.containsKey("value"))
                    return;
                JSONArray valueArr = tmpObj.getJSONArray("value");//value值是一个数组
                for(int j=0; j<valueArr.size(); j++){
                    JSONObject valueObj = valueArr.getJSONObject(j);
                    resultJson.put(valueObj.getString("name"),valueObj.getString("value"));
                }
                break;
            }
        }
    }

    /**
     *
     * 处理ManyToOne和ManyToMany
     * {
     *    "rule":"ManyToOne",
     *    "collection":[{"name":"windDirectionHorizontal"},{"name":"windDirectionVertical"}],
     *    "data":[
     *       {
     *          "name":[{"name":"windDirectionHorizontal","value":"0"},{"name":"windDirectionVertical","value":"0"}],
     *          "hashName":"windDirectionHorizontal_0_windDirectionVertical_0_",
     *          "value":[{"name":"windDirect","value":"1"}]
     *       },{
     *          "name":[{"name":"windDirectionHorizontal","value":"0"},{"name":"windDirectionVertical","value":"1"}],
     *          "hashName":"windDirectionHorizontal_0_windDirectionVertical_1_",
     *          "value":[{"name":"windDirect","value":"2"}]
     *       },{
     *          "name":[{"name":"windDirectionHorizontal","value":"1"},{"name":"windDirectionVertical","value":"0"}],
     *          "hashName":"windDirectionHorizontal_1_windDirectionVertical_0_",
     *          "value":[{"name":"windDirect","value":"3"}]
     *       },{
     *          "name":[{"name":"windDirectionHorizontal","value":"1"},{"name":"windDirectionVertical","value":"1"}],
     *          "hashName":"windDirectionHorizontal_1_windDirectionVertical_1_",
     *          "value":[{"name":"windDirect","value":"4"}]
     *       }
     *    ]
     *  }
     *
     * @param resultJson
     * @param ruleJson
     * @param sourceJson
     */
    private static void processManyToManyRule(JSONObject resultJson,JSONObject ruleJson,JSONObject sourceJson){
        if(!ruleJson.containsKey("collection")){
            logger.info("===We are start to translate MANY_TO_ONE, but can't found relate attrs in AdapterMap.collection====");
            return ;
        }
        //从配置中获取相关联的所有属性名
        JSONArray needAttrs = ruleJson.getJSONArray("collection");
        if(needAttrs.size()<=0){
            return;
        }
        //根据关联的属性名和传递过来的属性值生成hashName
        StringBuilder hashName = new StringBuilder();
        for(int i=0; i<needAttrs.size(); i++){
            JSONObject object = needAttrs.getJSONObject(i);
            hashName.append(object.getString("name")+"_"+sourceJson.getString(object.getString("name"))+"_");
        }
        JSONArray attrValueArray = ruleJson.getJSONArray("data");
        for(int i=0; i<attrValueArray.size(); i++){
            JSONObject tmpObj = attrValueArray.getJSONObject(i);
            if(tmpObj.getString("hashName").equalsIgnoreCase(hashName.toString())){
                JSONArray valueArr = tmpObj.getJSONArray("value");//value值是一个数组
                for(int j=0; j<valueArr.size(); j++){
                    JSONObject valueObj = valueArr.getJSONObject(j);
                    resultJson.put(valueObj.getString("name"),valueObj.getString("value"));
                }
                break;
            }
        }
    }

}
