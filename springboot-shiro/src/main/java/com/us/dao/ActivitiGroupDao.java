package com.us.dao;

import java.util.List;

import org.activiti.engine.identity.Group;

public interface ActivitiGroupDao {

	public List<Group> findGroupsByUser(String userId);
}
