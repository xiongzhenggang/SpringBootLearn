package com.us.model;

import java.util.List;
//web
public class WebPer {
	private Integer id;
	private String name;
	private String permissionUrl;
	private String method;
	private String webRoute;
	private Integer parentId;
	private int permissionLevel;
//	private List<WebPer> children;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPermissionUrl() {
		return permissionUrl;
	}
	public void setPermissionUrl(String permissionUrl) {
		this.permissionUrl = permissionUrl;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getWebRoute() {
		return webRoute;
	}
	public void setWebRoute(String webRoute) {
		this.webRoute = webRoute;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public int getPermissionLevel() {
		return permissionLevel;
	}
	public void setPermissionLevel(int permissionLevel) {
		this.permissionLevel = permissionLevel;
	}
	/*public List<WebPer> getChildren() {
		return children;
	}
	public void setChildren(List<WebPer> children) {
		this.children = children;
	}*/
	
}
