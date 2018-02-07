package com.xzg.javadesign;


public class VisitorTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vistor vistor = new MyVistory();
		Subject01 subject = new MySubject01();
		//作为被访问者，监听到访问者到来后，返回给自己的数据
		subject.accept(vistor);
	}
}
/**
 * @author xzg
 *	访问者接口
 */
interface Vistor{
	/**
	 * 访问的对象
	 */
	public void vist(Subject01 subject);
}
interface Subject01{
	/**
	 * @param vistor
	 * 监听来的访问者
	 */
	public void accept(Vistor vistor);
	/**
	 * @return
	 * 返回访问后的数据
	 */
	public String getSubject();
}
/**
 * @author xzg
 *	访问实现的类
 */
class MyVistory implements Vistor{

	public void vist(Subject01 subject) {
		// TODO Auto-generated method stub
		System.out.println("这里去访问："+subject.getSubject());
	}
}
/**
 * @author xzg
 * 被访问的实现类方法
 */
class MySubject01 implements Subject01{

	public void accept(Vistor vistor) {
		// 被访问的对象监听访问到来后，将自己本身返回给访问者，接受将要访问它的对象
		vistor.vist(this);
	}

	/**
	 *  获取将要被访问的属性，
	 */
	public String getSubject() {
		// TODO Auto-generated method stub
		return "五脏六腑";
	}
}