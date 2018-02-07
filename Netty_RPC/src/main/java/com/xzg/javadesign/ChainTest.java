package com.xzg.javadesign;


public class ChainTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyHandler my0 = new MyHandler("zero");
		MyHandler my1 = new MyHandler("first");
		MyHandler my2 = new MyHandler("second");
		//责任链模式流程核心，将对象通过类似链表的方式进行链接
		my0.setHandler(my1);
		my1.setHandler(my2);
		my0.opertator();
	}

}
/**
 * @author xzg
 *	定义责任链持有的对象的接口
 */
interface Handler{
	public void opertator();
}

/**
 * @author xzg
 * 为责任链模式提供共通处理方法
 */
abstract class Abstracthandler{
	private Handler handler;

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}

class MyHandler extends Abstracthandler implements Handler{
	//用于标识该节点的名称
	private String name;
	public MyHandler(String name){
		this.name = name;
	}
	/**
	 * 节点操作
	 */
	public void opertator() {
		// TODO Auto-generated method stub
		System.out.println("本节点："+name);
		if(getHandler()!= null)
			getHandler().opertator();
	}
}