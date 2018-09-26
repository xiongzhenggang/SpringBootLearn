package com.xzg.security.service.service;

import com.xzg.security.service.Utiils.BCryptUtil;
import com.xzg.security.service.Utiils.CreatHardRole;
import com.xzg.security.service.securityEtity.BaseUser;
import com.xzg.security.service.securityEtity.BaseUserDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author xzg
 */
@Service
public class BaseUserDetailService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("获取登陆信息：" + username);
        // 调用FeignClient查询用户
//        ResponseData<BaseUser> baseUserResponseData = baseUserService.getUserByUserName(username);
//        if(baseUserResponseData.getData() == null || !ResponseCode.SUCCESS.getCode().equals(baseUserResponseData.getCode())){
//            logger.error("找不到该用户，用户名：" + username);
//            throw new UsernameNotFoundException("找不到该用户，用户名：" + username);
//        }
//        BaseUser baseUser = baseUserResponseData.getData();

        //注意：实际生产应该从数据库中获取，用户名，密码（为BCryptPasswordEncoder hash后的密码）
        if (!"zhangsan".equals(username)) {
            logger.error("找不到该用户，用户名：" +username);
            throw new UsernameNotFoundException("找不到该用户，用户名：" + username);
        }
        //密码硬编码
        String password = BCryptUtil.encodePassword("12345");
        // 获取用户权限列表
        List<GrantedAuthority> authorities = CreatHardRole.createAuthorities().get();
        // 返回带有用户权限信息的User
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(username,
                password, true, true, true, true, authorities);
        return new BaseUserDetail(new BaseUser(username, password), user);
    }
}
