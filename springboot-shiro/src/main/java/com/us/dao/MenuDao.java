package com.us.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface MenuDao {

	public List<Integer> getMenuByRoleId(@Param("roleId") Integer roleId);
}
