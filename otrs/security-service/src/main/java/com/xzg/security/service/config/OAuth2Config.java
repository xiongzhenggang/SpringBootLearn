package com.xzg.security.service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

/**
 * @author xzg
 * 授权认证服务：AuthenticationServer
 */
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * @param endpointsConfigurer
     * 用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpointsConfigurer) throws Exception {
        endpointsConfigurer.authenticationManager(authenticationManager);
    }

    /**
     * @param clientDetailsServiceConfigurer
     * ClientDetailsServiceConfigurer：用来配置客户端详情服务（ClientDetailsService）
     * 客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer) throws Exception {
        // Using hardcoded inmemory mechanism because it is just an example
        clientDetailsServiceConfigurer.inMemory()//使用方法代替in-memory、JdbcClientDetailsService、jwt
                //用来标识客户的Id。
//        client_id: 第三方用户的id（可理解为账号）
//        client_secret:第三方应用和授权服务器之间的安全凭证(可理解为密码)
                .withClient("client")
                //（需要值得信任的客户端）客户端安全码
                .secret("password")
                .accessTokenValiditySeconds(7200)
                .authorizedGrantTypes("authorization_code", "refresh_token", "implicit", "password", "client_credentials")
                .scopes("apiAccess");
    }

    /**
     * @param security
     *  AuthorizationServerSecurityConfigurer：用来配置令牌端点(Token Endpoint)的安全约束
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // 开启/oauth/token_key验证端口无权限访问
                .tokenKeyAccess("permitAll()")
                // 开启/oauth/check_token验证端口认证权限访问
                .checkTokenAccess("isAuthenticated()");
    }

}