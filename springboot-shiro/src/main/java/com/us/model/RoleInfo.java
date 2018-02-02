package com.us.model;

import java.util.List;

public class RoleInfo {

	private Integer id;
	private String Name;
	private List<WebPer> menus;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<WebPer> getMenus() {
		return menus;
	}
	public void setMenus(List<WebPer> menus) {
		this.menus = menus;
	}
	
}
