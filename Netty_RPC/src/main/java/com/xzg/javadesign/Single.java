package com.xzg.javadesign;

import java.util.Vector;


public class Single {
	//实现延迟加载
	private Single(){}
	/*//使用内部类来维护
	private static class SingleFactory{
		//
		private static Single single = null;
		
		private static Single newInstance(){
			if(single == null){
				return new Single();
			}
			return single;
		}
	}
	public Single getInsetace(){
		return SingleFactory.newInstance();
	}*/
	
	private Vector<Object> provate = null;
	private static Single single = null;
//单独使用锁维护	
public static synchronized Single sysInstancce(){
	if(single == null){
		return new Single();
	}
return single;
	}
//
public static Single newIns(){
	if(single == null){
		sysInstancce();
	}
	return single;
}
public Vector<Object> getProperts(){
	return provate;
}
public void updateProvate(){
	Single single = new Single();
	provate = single.getProperts();
}

}
