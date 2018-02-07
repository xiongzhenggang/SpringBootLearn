package com.xzg.javadesign;

import java.util.Enumeration;
import java.util.Vector;


public class ObserverTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Subject mySubject = new MySubject();
		mySubject.add(new Observer01());
		mySubject.add(new Observer02());
		mySubject.operation();
	}
}

/**
 * @author xzg
 *被观察者的接口定义
 */
interface Observer{
	public void update();
}
//实现类
class Observer01 implements Observer{
	public void update() {
		// TODO Auto-generated method stub
		System.out.println(this.getClass()+"检测到更新了！！");
	}
}
class Observer02 implements Observer{
	public void update() {
		// TODO Auto-generated method stub
		System.out.println(this.getClass()+"检测到更新了！！");
	}
}
interface Subject{
	/** 增加观察者
	 * @param oberver
	 */
	public void add(Observer observer);
	/**
	 * 移除观察者
	 * @param oberver
	 */
	public void del(Observer observer);
	/**
	 * 通知所有的观察者
	 */
	public void notifyObserver();
	/**
	 * 自身的操作
	 */
	public void operation();
}

/**
 * @author xzg
 *抽象类的作用是对接口实现的灵活处理，省略某些不必要的处理
 */
abstract class AbstractSubject implements Subject{
	private Vector<Observer> vector = new Vector<Observer>();
	/* (non-Javadoc)
	 * @see com.xzg.design.Subject#add(com.xzg.design.Oberver)
	 */
	public void add(Observer observer){
		vector.add(observer);
	}
	/* (non-Javadoc)
	 * @see com.xzg.design.Subject#del(com.xzg.design.Oberver)
	 */
	public void del(Observer observer){
		vector.remove(observer);
	}
	/**(non-Javadoc)
	 * @see com.xzg.javadesign.Subject#notifyObserver()
	 * Enumeration（枚举）接口的作用和Iterator类似，
	 * 只提供了遍历Vector和HashTable类型集合元素的功能，不支持元素的移除操作。
	 */
	public void notifyObserver(){
		Enumeration<Observer> enumo = vector.elements();
		//通知所有注册了的观察者
		while(enumo.hasMoreElements()){
			enumo.nextElement().update();
		}
	}
}
/**
 * @author xzg
 *实现父类没有重写的方法
 */
class MySubject extends AbstractSubject{

	public void operation() {
		// TODO Auto-generated method stub
		System.out.println("更新了！！");
		//调用父类通知方法，通知所有注册的观察者
		notifyObserver();
	}
}