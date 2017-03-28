package com.gizwits.bsh.service.impl;

import com.gizwits.bsh.mapper.PlatMapper;
import com.gizwits.bsh.mapper.PlatUserMapper;
import com.gizwits.bsh.model.entity.Plat;
import com.gizwits.bsh.model.entity.PlatUser;
import com.gizwits.bsh.service.PlatService;
import com.gizwits.bsh.util.GeneratorKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhl on 2016/11/23.
 */
@Service
public class PlatServiceImpl extends BaseServiceImpl<Plat,Long> implements PlatService {

    @Autowired
    private PlatMapper platMapper;

    @Autowired
    private PlatUserMapper platUserMapper;

    @Override
    public void refreshToken(String platId, String appId, String token, String userId) {
        savePlatIfNotExisted(platId,appId,token);
        savePlatUserIfNotExisted(platId,userId);
    }

    private void savePlatIfNotExisted(String platId,String appId,String token){
        Plat plat = new Plat();
        plat.setPlatId(platId);
        plat.setAppId(appId);

        Plat tmp = platMapper.selectOne(plat);
        if(platMapper.selectOne(plat)==null){
            plat.setId(GeneratorKit.getID());
            plat.setAccessToken(token);
            plat.setRefreshToken("");
            plat.setCreator("System");
            plat.setCreateTime(new Date());
            platMapper.insertSelective(plat);
        }else{
            plat.setId(tmp.getId());
            plat.setAccessToken(token);
            platMapper.updateByPrimaryKeySelective(plat);
        }

    }

    private void savePlatUserIfNotExisted(String platId,String userId){
        PlatUser user = platUserMapper.selectByPlatId(platId);
        if(user==null){
            user = new PlatUser();
            user.setId(GeneratorKit.getID());
            user.setUserId(userId);
            user.setPlatId(platId);
            user.setFirstLoginTime(new Date());
            user.setLastLoginTime(new Date());
            platUserMapper.insertSelective(user);
        }else{
            user.setUserId(userId);
            user.setLastLoginTime(new Date());
            platUserMapper.updateByPrimaryKeySelective(user);
        }
    }


}
