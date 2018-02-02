package com.us.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.us.dao.WebPerDao;
import com.us.service.MenuService;


/**
 * Created by cdyoue on 2016/10/21.
 * 登陆控制器
 */
@RestController
public class LoginController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    private WebPerDao webPerDao;
    
    @Resource
    private MenuService ms;
    @RequestMapping(value = "/login", method = {RequestMethod.POST,RequestMethod.GET})
    public String login(
            @RequestParam(value = "username", required = true) String userName,
            @RequestParam(value = "password", required = true) String password,
            @RequestParam(value = "rememberMe", required = true, defaultValue = "false") boolean rememberMe
    ) {
        logger.info("==========" + userName + password + rememberMe);
        //Subject 当前用户的操作
//        System.out.println(JSONObject.toJSON(webPerDao.getRoleInfo(1)));
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(rememberMe);

        try {
            subject.login(token);
        }catch (UnknownAccountException e) {
        	 return "{\"Msg\":\"该用户不存在\",\"state\":\"failed\"}";
		}catch (IncorrectCredentialsException e) {
			// TODO: handle exception
        	  return "{\"Msg\":\"您的账号或密码输入错误\",\"state\":\"failed\"}";
		}catch (AuthenticationException e) {
            e.printStackTrace();
//            rediect.addFlashAttribute("errorText", "您的账号或密码输入错误!");
            return "{\"Msg\":\"您无权登录\",\"state\":\"failed\"}";
        }
        //返回前端相应的数据
        
        return ms.getMenue(1).toJSONString();
//        return "{\"Msg\":\"登陆成功\",\"state\":\"success\"}";
    }

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "no permission";
    }
}
