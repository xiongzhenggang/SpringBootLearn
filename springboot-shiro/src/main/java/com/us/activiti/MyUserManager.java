package com.us.activiti;

import java.util.List;
import java.util.Map;


import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.UserDataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.us.dao.ActivitiUserDao;
import com.us.dao.UserDao;
/**
 * Copyright: Copyright (c) 2018 LanRu-Caifu
 * @author xzg
 * 2018年2月2日
 * @ClassName: MyUserManager.java
 * @Description: 实现GroupDataManager接口用于自定义用户角色处理
 * @version: v1.0.0
 */
@Component
public class MyUserManager implements UserDataManager {
	@Autowired
	private UserDao userDao;
	@Autowired
	private ActivitiUserDao ao;
	@Override
	public UserEntity create() {
		// TODO Auto-generated method stub
		 return new UserEntityImpl();
	}

	@Override
	public UserEntity findById(String entityId) {
		// TODO Auto-generated method stub
//		throw new UnsupportedOperationException();
		com.us.bean.User user =  userDao.getById(Integer.valueOf(entityId));
		UserEntityImpl ua = new UserEntityImpl();
		ua.setId(String.valueOf(user.getId()));
		ua.setEmail(user.getEmail());
		ua.setFirstName(user.getCnname());
		ua.setPassword(user.getPassword());
		return ua;
	}

	@Override
	public void insert(UserEntity entity) {
		// TODO Auto-generated method stub
//		com.us.bean.User user = new com.us.bean.User();
//		user.setId(Integer.valueOf(entity.getId()));
//		user.setCnname(entity.getLastName());
		throw new UnsupportedOperationException();
	}

	@Override
	public UserEntity update(UserEntity entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(UserEntity entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public List<User> findUserByQueryCriteria(UserQueryImpl query, Page page) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public long findUserCountByQueryCriteria(UserQueryImpl query) {
		// TODO Auto-generated method stub
		
//		return 0;
		throw new UnsupportedOperationException();
	}
////增加自己的处理
	@Override
	public List<Group> findGroupsByUser(String userId) {
		// TODO Auto-generated method stub
		
		
		throw new UnsupportedOperationException();
	}

	@Override
	public List<User> findUsersByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		return ao.findUsersByNativeQuery(parameterMap, firstResult, maxResults);
//		throw new UnsupportedOperationException();
	}

	@Override
	public long findUserCountByNativeQuery(Map<String, Object> parameterMap) {
		// TODO Auto-generated method stub
//		return 0;
		throw new UnsupportedOperationException();
	}

}
