package com.gizwits.bsh.service.impl;

import com.gizwits.bsh.bean.reqvo.web.AdministratorListReqVO;
import com.gizwits.bsh.bean.resvo.web.ReviewVO;
import com.gizwits.bsh.common.bean.BaseRespVO;
import com.gizwits.bsh.enums.ErrType;
import com.gizwits.bsh.mapper.SystemUserMapper;
import com.gizwits.bsh.model.entity.SystemUser;
import com.gizwits.bsh.service.AdministratorService;
import com.gizwits.bsh.util.GeneratorKit;
import com.gizwits.bsh.util.MD5Kit;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    SystemUserMapper systemUserMapper;

    @Override
    public ReviewVO<SystemUser> getAdministratorList(AdministratorListReqVO reqVO) {
        List users = systemUserMapper.selectSystemUserList(reqVO);
        ReviewVO result = new ReviewVO();
        result.setDraw(reqVO.getDraw());
        result.setData(users);
        result.setRecordsTotal(users.size());
        int total = systemUserMapper.selectSystemUserListCount(reqVO);
        result.setRecordsFiltered(total);
        return result;
    }

    @Override
    public boolean checkUsername(String username) {
        if(username==null||username.equals(""))
            return false;
        List<SystemUser> list = systemUserMapper.selectSysetemUserByUsername(username);
        if(list==null||list.size()<=0)
            return true;
        return false;
    }

    @Override
    public BaseRespVO saveNewAdministrator(SystemUser systemUser) {
        systemUser.setId(GeneratorKit.getID());
        systemUser.setCreateTime(new Date());
        systemUser.setPassword(MD5Kit.encodeSalt(systemUser.getPassword(), systemUser.getUserName()));
        if (systemUserMapper.insertSelective(systemUser) > 0) {
            return new BaseRespVO(ErrType.SUCCESS);
        } else {
            return new BaseRespVO(ErrType.FAIL);
        }
    }

    @Override
    public BaseRespVO deleteAdministrator(String id) {
        int rows = systemUserMapper.deleteSystemUser(id);
        if (rows == 0) {
            return new BaseRespVO(ErrType.FAIL);
        }
        return new BaseRespVO(ErrType.SUCCESS);
    }

    @Override
    public String getAdministratorRole() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            Session session = subject.getSession();
            if (session  != null) {
                Object userid = session.getAttribute("userid");
                if (userid != null) {
                    SystemUser user = getAdministrator(userid.toString());
                    return user.getRole();
                }
            }
        }
        return null;
    }

    private SystemUser getAdministrator(String id) {
        SystemUser record = new SystemUser();
        record.setId(Long.valueOf(id));
        List<SystemUser> administrators = systemUserMapper.select(record);
        if (administrators.size() > 0) {
            return administrators.get(0);
        }
        return null;
    }


}
