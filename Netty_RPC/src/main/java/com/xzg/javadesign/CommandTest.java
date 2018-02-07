package com.xzg.javadesign;

public class CommandTest {
	public static void main(String[] args){
		Receiver receiver = new Receiver();
		Command command = new MyCommand(receiver);
		Invoker invoker = new Invoker(command);
		invoker.action("明天去打猎！！");
	}
}
/**
 * @author xzg
 * 命令接口
 */
interface Command{
	public void exec(String str);
}
/**
 * @author xzg
 *	定义命令实体，包含执行该命令的下属。
 */
class MyCommand implements Command{
	private Receiver receiver;
	/**@param receiver
	 */
	public MyCommand(Receiver receiver){
		this.receiver = receiver;
	}
	/**
	 * @see com.xzg.javadesign.Command#exec()
	 * 上级发出的命令的实际执者
	 */
	public void exec(String str) {
		// TODO Auto-generated method stub
		receiver.action(str);
	}
}
/**
 * @author xzg
 *作为命令的接受执行者，定义执行方法
 */
class Receiver{
	/**
	 * 执行者执行的方法，可传递命令
	 */
	public void action(String str){
		System.out.println(this.toString()+"收到命令--执行："+str);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "小斥候";
	}
	
}
/**
 * @author xzg
 * 命令的发出者并不知道谁是命令的执行者是谁。它只需要明确自己的命令即可。
 * 命令的发出者和执行者之间解耦，实现请求和执行分开
 */
class Invoker{
	private Command command;
	public Invoker(Command command){
		this.command = command;
	}
	/**
	 * @param str
	 * 调用命令去执行想要执行的内容
	 */
	public void action(String str) {
		command.exec(str);
	}
}
