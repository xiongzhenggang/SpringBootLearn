package com.us.shiro;

import com.us.bean.Permission;
import com.us.bean.Role;
import com.us.bean.User;
import com.us.dao.PermissionDao;
import com.us.dao.UserDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by cdyoue on 2016/10/21.
 */
//Realms：用于进行权限信息的验证
public class ShiroRealm extends AuthorizingRealm {
    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserDao userService;
    @Autowired
    private PermissionDao permissionService;
    
  //Authorization(授权)：是授权访问控制，用于对用户进行的操作进行人证授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("doGetAuthorizationInfo+"+principalCollection.toString());
        User user = userService.getByUserName((String) principalCollection.getPrimaryPrincipal());
        //把principals放session中 key=userId value=principals
        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()),SecurityUtils.getSubject().getPrincipals());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //赋予角色
        for(Role userRole:user.getRoles()){
            info.addRole(userRole.getName());
        }
        //赋予权限
        for(Permission permission:permissionService.getByUserId(user.getId())){
//            if(StringUtils.isNotBlank(permission.getPermCode()))
                info.addStringPermission(permission.getMethod());
        }

        //设置登录次数、时间
//        userService.updateUserLogin(user);
        return info;
    }
    //Authentication(认证)：是验证用户身份的过程。
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("doGetAuthenticationInfo +"  + authenticationToken.toString());

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName=token.getUsername();
        logger.info(userName+token.getPassword());

        User user = userService.getByUserName(token.getUsername());
        if (user != null) {
//            byte[] salt = Encodes.decodeHex(user.getSalt());
//            ShiroUser shiroUser=new ShiroUser(user.getId(), user.getLoginName(), user.getName());
            //设置用户session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user", user);
            return new SimpleAuthenticationInfo(userName,user.getPassword(),getName());
        } else {
        	 // 用户名不存在抛出异常
            throw new UnknownAccountException();
//            return null;
        }
//        return null;
    }

}
