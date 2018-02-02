package com.us.bean;


public class Permission {
    private Integer id;
    private String name;
    private String permissionUrl;
    private String method;
    private String description;
    private String webRoute;
    private String parentId;
    private String permissionLevel;
    
    public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(String permissionLevel) {
		this.permissionLevel = permissionLevel;
	}

	public String getWebRoute() {
		return webRoute;
	}

	public void setWebRoute(String webRoute) {
		this.webRoute = webRoute;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", name=" + name +
                ", permissionUrl=" + permissionUrl +
                ", method=" + method +
                ", description=" + description +
                '}';
    }
}