package com.gizwits.bsh.controller.app;

import com.gizwits.bsh.bean.setting.TokenSetting;
import com.gizwits.bsh.enums.MultiCloudHost;
import com.gizwits.bsh.mapper.TranslatorLogMapper;
import com.gizwits.bsh.model.entity.TranslatorLog;
import com.mangofactory.swagger.annotations.ApiIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * Created by neil on 2016/11/5.
 */
@Controller
@RequestMapping("/test")
@ApiIgnore
public class TestController {

    @ApiIgnore
    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    @ResponseBody
    public String getToken() {
        return TokenSetting.getTokenStr(MultiCloudHost.HAIXIN);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        return "Hello BSH!";
    }

    @Autowired
    TranslatorLogMapper translatorLogMapper;

    @RequestMapping(value = "/log", method = RequestMethod.GET)
    @ResponseBody
    public List<TranslatorLog> getLog(@RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "from", required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date fromDate,
                                      @RequestParam(value = "to", required = false) @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") Date toDate) {
        Example example = new Example(TranslatorLog.class);
        Example.Criteria criteria = example.createCriteria();
        if (fromDate != null) {
            criteria.andGreaterThan("startTime", fromDate);
        }
        if (toDate != null) {
            criteria.andLessThan("startTime", toDate);
        }
        String clause = "start_time desc limit 20";
        if (page != null) {
            clause += " offset " + String.valueOf(page * 20);
        }
        example.setOrderByClause(clause);
        List<TranslatorLog> logs = translatorLogMapper.selectByExample(example);
        return logs;
    }
}
