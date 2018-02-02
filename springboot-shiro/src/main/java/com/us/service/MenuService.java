package com.us.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.us.dao.MenuDao;
import com.us.dao.WebPerDao;
import com.us.model.MenuTree;

@Service
public class MenuService {

	@Resource
	private WebPerDao wd;
	@Resource
	private MenuDao md;
	
	@Cacheable(value = "menueCache" , key = "#roleId")
	public JSONArray getMenue(Integer roleId){
		List<Integer> menus = md.getMenuByRoleId(1);
		List<MenuTree> mts = wd.getMenuTree();
		List<MenuTree> afts = enclosure(menus,mts);
		return  (JSONArray) JSONArray.toJSON(afts);
	}
	
	public List<MenuTree> enclosure(List<Integer> menus,List<MenuTree> mts){
		//
		for(int i=0;i<mts.size();i++){
			if(!menus.contains(mts.get(i).getId())){
				mts.remove(i);
				continue;
			}
			//有子节点递归
			if(0 < mts.get(i).getChildren().size()){
				enclosure(menus,mts.get(i).getChildren());
			}
		}
		return mts;
	}
	
}
