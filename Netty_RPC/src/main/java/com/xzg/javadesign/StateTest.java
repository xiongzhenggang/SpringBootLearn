package com.xzg.javadesign;

public class StateTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		State state = new State();
		Context context = new Context(state);
		context.turnState();
		//设置第一种状态
		state.setValue("state01");
		context.turnState();
		//切换第二种状态
		state.setValue("state02");
		context.turnState();
	}
}
/**
 * @author xzg
 * 状态类，包含不同情况的执行方法
 */
class State{
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public void method01(){
		setValue("第一种状态对应的方法1");
		System.out.println(getValue());
	}
	public void method02(){
		setValue("第二种方法对应的方法2");
		System.out.println(getValue());
	}
}
/**
 * @author xzg
 *	用于切换状态的类，通过它来达到不同的状态执行不同的方法
 */
class Context{
	
	/**
	 * @param state
	 *  初始状态
	 */
	public Context(State state){
		this.state = state;
	}
	/**
	 * 保存状态
	 */
	private State state;
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	/**
	 * 根据不同的状态执行不同的操作，当然这里可以定义通知方法
	 */
	public void turnState(){
		if("state01".equals(state.getValue())){
			state.method01();
		}else if("state02".equals(state.getValue())){
			state.method02();
		}else{
			System.out.println("请先设置相应的状态");
		}
	}
}