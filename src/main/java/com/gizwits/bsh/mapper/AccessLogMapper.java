package com.gizwits.bsh.mapper;

import com.gizwits.bsh.bean.reqvo.web.OperationLogListReqVO;
import com.gizwits.bsh.model.entity.AccessLog;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface AccessLogMapper extends Mapper<AccessLog> {
    List<Map> selectLogList(OperationLogListReqVO vo);
    int selectLogListCount(OperationLogListReqVO vo);
}