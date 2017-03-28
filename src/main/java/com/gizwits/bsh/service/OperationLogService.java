package com.gizwits.bsh.service;

import com.gizwits.bsh.bean.reqvo.web.OperationLogListReqVO;
import com.gizwits.bsh.bean.resvo.web.ReviewVO;

import java.util.Map;

public interface OperationLogService {
    ReviewVO<Map> getLogList(OperationLogListReqVO reqVO);
}
