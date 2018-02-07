package com.xzg.javadesign;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Vector;
public class ConnectionPool {
	private Vector<Connection> pool;
	/*公有属性*/
	private String url="jdbc:mysql://localhost:3306:xzg";
	private String username="xzg";
	private String password = "xzg";
	private int poolSize=0;//默认为10
	private static ConnectionPool instance = null;//
	private String driveClassName = "com.mysql.jdbc.Driver";
	Connection connection =null;
	private ConnectionPool(){
		//this.poolSize = poolSize;
		pool = new Vector<Connection>(poolSize);
		for(int i=0;i<poolSize;i++){
			try {
				Class.forName(driveClassName);
				connection = DriverManager.getConnection(url, username, password);
				pool.add(connection);			
			} catch (SQLException  e) {
				// TODO: handle exception
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//初始化完成后的相应的处理
	}
	/* 返回连接到连接池 */
	public synchronized void releace(){
		pool.add(connection);
	}
	/* 返回连接池中的一个数据库连接 */
	public synchronized Connection getConnection(){
		if(poolSize>0){
			Connection connection = pool.get(0);
			pool.remove(0);
			return connection;
		}else{
			return null;
		}
	}
	// 单例模式获取连接池实例
	public static ConnectionPool getConnectionPool(){
		if(null == instance){
			synchronized (instance) {//锁定对象
				instance = new ConnectionPool();
			}
		}
		return instance;
	}
}
