package com.xzg.security.service.config;

//import com.xzg.security.service.service.BaseUserDetailService;
import com.xzg.security.service.service.BaseUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@Order(-1)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private BaseUserDetailService baseUserDetailService;
    //Spring Security 4.x -> 5.x  會無法直接注入AuthenticationManager，下面解決
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    /**
     * 用户验证
     * @param auth
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http    // 配置登陆页/login并允许访问
                .formLogin().permitAll()
                // 登出页
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
                // 其余所有请求全部需要鉴权认证
                .and().authorizeRequests().anyRequest().authenticated()
                // 由于使用的是JWT，我们这里不需要csrf
                .and().csrf().disable();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 设置userDetailsService
        provider.setUserDetailsService(baseUserDetailService);
        // 禁止隐藏用户未找到异常
        provider.setHideUserNotFoundExceptions(false);
        // 使用BCrypt（BCryptPasswordEncoder方法采用SHA-256 +随机盐+密钥）进行密码的hash
        provider.setPasswordEncoder(new BCryptPasswordEncoder(6));
        return provider;
    }
    /**
     * 自定义登陆过滤器
     * @return
     */
//    @Bean
//    public MyLoginAuthenticationFilter getMyLoginAuthenticationFilter() {
//        MyLoginAuthenticationFilter filter = new MyLoginAuthenticationFilter();
//        try {
//            filter.setAuthenticationManager(this.authenticationManagerBean());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        filter.setAuthenticationSuccessHandler(new MyLoginAuthSuccessHandler());
//        filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error"));
//        return filter;
//    }
}
