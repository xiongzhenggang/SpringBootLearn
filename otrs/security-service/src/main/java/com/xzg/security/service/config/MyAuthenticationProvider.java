//package com.xzg.security.service.config;
//
//import com.xzg.security.service.Utiils.CreatHardRole;
//import com.xzg.security.service.securityEtity.BaseUser;
//import com.xzg.security.service.securityEtity.BaseUserDetail;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//
///**
// * @author xzg
// */
//@Component
//public class MyAuthenticationProvider implements AuthenticationProvider {
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = (String) authentication.getCredentials();
//        System.out.println("前端传过来的明文密码:" + password);
////        System.out.println("加密后的密码:" + MD5(password));
////        UserDetails user = userService.loadUserByUsername(username);
//        //加密过程在这里体现
//      /*  if (!user.getPassword().equals(MD5.MD5(password))) {
//            throw new DisabledException("Wrong password.");
//        }*/
//      //硬編碼寫
//        UserDetails user = new BaseUserDetail(new BaseUser(username,password),new User(username,password, CreatHardRole.createAuthorities().get()));
//        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
//        return new UsernamePasswordAuthenticationToken(user, password, authorities);
//    }
//
//    @Override
//    public boolean supports(Class<?> aClass) {
//        return false;
//    }
//}
