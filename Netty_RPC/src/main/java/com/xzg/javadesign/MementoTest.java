package com.xzg.javadesign;


public class MementoTest {
	public static void main(String[] args){
		//创建初始类
		Original original = new Original("我是初始值！！");
		//创建存储类实例保存初始类的需要保存的状态即备忘录
		Storage storage = new Storage(original.createMemory());
		System.out.println("初始类初始值为："+original.getValue());
		original.setValue("我是修改后的值");
		System.out.println("修改后的值为："+original.getValue());
		//恢复初始值
		original.restoreMemory(storage.getMemory());
		System.out.println("恢复后的值为："+original.getValue());
	}
}
/**
 * @author xzg
 *作为一个保存类，用于保存原始类的状态，以便恢复
 */
class Memory{
	private String value;
	
	public Memory(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
/**
 * @author xzg
 * 初始类
 */
class Original{
	public Original(String value){
		this.value = value;
	}
	//原始类的数值
	private String value;
	/**
	 * @return 持有一个保存类，保存初始值
	 */
	public Memory createMemory(){
		return new Memory(value);
	}
	/**
	 * @param memory
	 * 恢复修改前的状态
	 */
	public void restoreMemory(Memory memory){
		this.value = memory.getValue();
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
/**
 * @author xzg
 *	保存memory，这里为原始类初始化的值
 */
class Storage{
	private Memory memory;

	public Storage(Memory memory){
		this.memory = memory;
	}
	public Memory getMemory() {
		return memory;
	}

	public void setMemory(Memory memory) {
		this.memory = memory;
	}
}
