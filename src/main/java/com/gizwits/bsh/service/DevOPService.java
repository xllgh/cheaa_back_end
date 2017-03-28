package com.gizwits.bsh.service;

import com.gizwits.bsh.bean.ResultMap;
import com.gizwits.domain.opBean.RsqBasicOP;

/**
 * Created by neil on 2016/11/6.
 */
public interface DevOPService {

    ResultMap bindDev();

    ResultMap unBindDev();

    ResultMap opDevice(RsqBasicOP basicOP);
}
