package com.us.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.us.bean.User;

@RestController
public class TestController {

	 @RequestMapping(value = "/test", method = {RequestMethod.POST,RequestMethod.GET})
	    @ResponseBody
	    public User index() {
	        return new User();
	    }
}
