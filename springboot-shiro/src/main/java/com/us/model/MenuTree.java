package com.us.model;

import java.util.List;

public class MenuTree {

		private Integer id;
	     private String name;
	     private String method;
	     private String description;
	     private String permissionUrl;
		 private List<MenuTree> children;
		
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
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
		public List<MenuTree> getChildren() {
			return children;
		}
		public void setChildren(List<MenuTree> children) {
			this.children = children;
		}
	
}
