package com.us.bean;

import org.activiti.engine.identity.User;
public class ActivitiUser implements User{
// @Fields serialVersionUID : TODO
	private static final long serialVersionUID = 1L;
private String id;
private String firstName;
private String email;
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
	public String getFirstName() {
		// TODO Auto-generated method stub
		return firstName;
	}

	@Override
	public void setFirstName(String firstName) {
		// TODO Auto-generated method stub
		this.firstName = firstName;
	}

	@Override
	public void setLastName(String lastName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEmail(String email) {
		// TODO Auto-generated method stub
		this.email = email;
	}

	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPassword(String string) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPictureSet() {
		// TODO Auto-generated method stub
		return false;
	}

}
