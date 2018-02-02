package com.us.bean;

import org.activiti.engine.identity.Group;

public class ActivitiGroup implements Group{

	// @Fields serialVersionUID : TODO
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String type;
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name= name;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public void setType(String type) {
		// TODO Auto-generated method stub
		this.type = type;
	}

}
