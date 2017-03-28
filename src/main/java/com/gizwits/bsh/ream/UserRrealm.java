package com.gizwits.bsh.ream;

import com.gizwits.bsh.enums.ErrType;
import com.gizwits.bsh.mapper.SystemUserMapper;
import com.gizwits.bsh.model.entity.SystemUser;
import com.gizwits.bsh.util.MD5Kit;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 权限加载
 * Created by zhl on 2016/12/23.
 */
public class UserRrealm extends AuthorizingRealm {
    private final static Logger logger = LoggerFactory.getLogger(UserRrealm.class);

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * 授权操作
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
        /*SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        Integer roleId = (Integer)session.getAttribute("roleId");
        //   List<String> authorityList = roleAuthorityMapper.getAuthorityListByRoleId(roleId);
        final List<RoleAuthority> roleAuthoritys = roleAuthorityMapper.getRoleAuthoritysByRoleId(roleId);

        for(RoleAuthority authority:roleAuthoritys){
            if(authority.getAuthorityId().length()!=2){//二级权限不用给
                info.addStringPermission(authority.getAuthority());
            }
        }*/
        //// TODO: 2016/12/23 系统只存在一个系统管理员角色,不需要授权操作
        return new SimpleAuthorizationInfo();
    }

    /**
     * 身份验证操作
     * @param authToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authToken) throws AuthenticationException {
        UserPwdTypeToken token = (UserPwdTypeToken) authToken;
        String loginName = token.getUsername();
        SystemUser systemUser = systemUserMapper.selectUserByUsername(loginName);

        if(systemUser==null){
            throw new UnknownAccountException();
        }else{
            Object tokenCredentials = encrypt(loginName,String.valueOf(token.getPassword()));
            if(!tokenCredentials.equals(systemUser.getPassword())){
                throw new AuthenticationException(ErrType.WEB_ILLEGAL_ACCOUNT_PASSWORD.getErrmsg());
            }else{
                Subject currentUser = SecurityUtils.getSubject();
                Session session = currentUser.getSession();
                session.setAttribute("username",loginName);
                session.setAttribute("userid",systemUser.getId());
            }
        }

        return new SimpleAuthenticationInfo(loginName,token.getPassword(),getName());
    }

    private String encrypt(String username,String password) {
        return MD5Kit.encodeSalt(password, username);
    }
}
