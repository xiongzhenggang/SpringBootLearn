package com.xzg.javadesign;


public class MediatorTest {
	public static void main(String[] args){
		Mediator mediator = new MyMediator();
		mediator.createMediator();
		mediator.workAll();
	}
}

/**
 * @author xzg
 *	中介的接口规范
 */	
interface Mediator{
	/**
	 * 创建中介要执行的内容
	 */
	public void createMediator();
	/**
	 * 替用户执行
	 */
	public void workAll();
}
	/**
	 * @author xzg
	 *	因为多个实例用户的中介相同，这里使用抽象类，抽象出共同的模式
	 */
	abstract class User{
		private Mediator mediator;
	
		public void setMediator(Mediator mediator) {
			this.mediator = mediator;
		}
	
		public Mediator getMediator() {
			return mediator;
		}
		public User(Mediator mediator){
			this.mediator = mediator;
		}
		/**
		 * 需要不同子类自定义
		 */
		public abstract void work();
	}
class User01 extends User{

	/**
	 * @param mediator
	 * 指定中介
	 */
	public User01(Mediator mediator) {
		super(mediator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void work() {
		// TODO Auto-generated method stub
		System.out.println("用户01工作！！");
	}
}
class User02 extends User{

	public User02(Mediator mediator) {
		super(mediator);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void work() {
		// TODO Auto-generated method stub
		System.out.println("用户02工作！！");
	}
	
}
/**
 * @author xzg
 *	中介接口的实现，持有来注册的用户
 */
class MyMediator implements Mediator{
	private User user01;
	private User user02;
	public User getUser01() {
		return user01;
	}

	public void setUser01(User user01) {
		this.user01 = user01;
	}

	public User getUser02() {
		return user02;
	}

	public void setUser02(User user02) {
		this.user02 = user02;
	}

	/**
	 * @see com.xzg.javadesign.Mediator#createMediator()
	 * 这里相当于ioc，由中介替我们创建对象。并且将中介本身注册到每个用户中
	 */
	public void createMediator() {
		// TODO Auto-generated method stub
		user01 = new User01(this);
		user02 = new User02(this);
	}
	/**
	 * @see com.xzg.javadesign.Mediator#workAll()
	 * 由中介替用户调用执行
	 */
	public void workAll() {
		// TODO Auto-generated method stub
		user01.work();
		user02.work();
	}
}