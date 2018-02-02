package com.us.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.us.bean.Menu;
import com.us.model.MenuTree;
import com.us.model.RoleInfo;
import com.us.model.WebPer;

public interface WebPerDao {

//	public RoleInfo getRoleInfo(@Param("roleId") Integer roleId);
	
	public List<MenuTree> getMenuTree();
	
}
