package com.us.activiti;

import java.util.List;
import java.util.Map;


import org.activiti.engine.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.us.dao.ActivitiGroupDao;

import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
/**
 * Copyright: Copyright (c) 2018 LanRu-Caifu
 * @author xzg
 * 2018年2月2日
 * @ClassName: MyGroupManager.java
 * @Description: 实现GroupDataManager接口用于自定义用户角色处理
 * @version: v1.0.0
 */
@Component
public class MyGroupManager implements GroupDataManager {

	@Autowired
	private ActivitiGroupDao ago;
	
	@Override
	public GroupEntity create() {
		// TODO Auto-generated method stub
		 return new GroupEntityImpl();
	}

	@Override
	public GroupEntity findById(String entityId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void insert(GroupEntity entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public GroupEntity update(GroupEntity entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(GroupEntity entity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public long findGroupCountByQueryCriteria(GroupQueryImpl query) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
	@Override
	public List<Group> findGroupsByUser(String userId) {
		// TODO Auto-generated method stub
//		throw new UnsupportedOperationException();
		return ago.findGroupsByUser(userId);
	}

	@Override
	public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public long findGroupCountByNativeQuery(Map<String, Object> parameterMap) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

   
}