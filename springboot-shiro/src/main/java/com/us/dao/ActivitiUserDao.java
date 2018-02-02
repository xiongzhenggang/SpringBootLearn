package com.us.dao;

import java.util.List;
import java.util.Map;

import org.activiti.engine.identity.User;

//import com.us.bean.ActivitiUser;

public interface ActivitiUserDao {

	public List<User> findUsersByNativeQuery(Map<String,Object> map,int firstResult,int maxResults);
}
