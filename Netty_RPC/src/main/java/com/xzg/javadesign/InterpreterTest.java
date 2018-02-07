package com.xzg.javadesign;

public class InterpreterTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IContext context = new IContext(6, 3);
		IPlus plus = new IPlus();
		IMinus minus = new IMinus();
		System.out.println(plus.interpret(context));
		System.out.println(minus.interpret(context));
	}
}
/**
 * @author xzg
 *	持有上下文数据的容器类
 */
class IContext{
	private int num1;
	private int num2;
	public IContext(int num1,int num2){
		this.num1 = num1;
		this.num2 = num2;
	}
	public int getNum1() {
		return num1;
	}
	public void setNum1(int num1) {
		this.num1 = num1;
	}
	public int getNum2() {
		return num2;
	}
	public void setNum2(int num2) {
		this.num2 = num2;
	}
}

/**
 * @author xzg
 *	提供一个公有的计算接口
 */
interface Expression{
	public int interpret(IContext context);
}

/**
 * @author xzg
 *	加法实现
 */
class IPlus implements Expression{

	public int interpret(IContext context) {
		// TODO Auto-generated method stub
		return context.getNum1()+context.getNum2();
	}
}
/**
 * @author xzg
 *	减法实现
 */
class IMinus implements Expression{

	public int interpret(IContext context) {
		// TODO Auto-generated method stub
		return context.getNum1() - context.getNum2();
	}
}