package com.gizwits.bsh.service.impl;

import com.gizwits.bsh.bean.reqvo.web.OperationLogListReqVO;
import com.gizwits.bsh.bean.resvo.web.ReviewVO;
import com.gizwits.bsh.mapper.AccessLogMapper;
import com.gizwits.bsh.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OperationLogServiceImpl implements OperationLogService {

    @Autowired
    AccessLogMapper accessLogMapper;

    @Override
    public ReviewVO<Map> getLogList(OperationLogListReqVO reqVO) {
        List logs = accessLogMapper.selectLogList(reqVO);
        ReviewVO result = new ReviewVO();
        result.setDraw(reqVO.getDraw());
        result.setData(logs);
        result.setRecordsTotal(logs.size());
        int total = accessLogMapper.selectLogListCount(reqVO);
        result.setRecordsFiltered(total);
        return result;
    }
}
