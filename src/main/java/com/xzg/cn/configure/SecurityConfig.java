package com.xzg.cn.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//启用web的security
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

    //拦截请求
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()//
                .antMatchers("/", "/home").permitAll()//这俩请求不做登陆拦截
                .anyRequest().authenticated()//做登陆拦截
                .and()
                .formLogin()
                .loginPage("/login")//拦截需要登陆的请求到login页面
                .permitAll()
                .and()
                .logout()
                .permitAll();//退出不拦截
    }
//在内存创建用户信息
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("user").password("123").roles("USER");
    }

}
