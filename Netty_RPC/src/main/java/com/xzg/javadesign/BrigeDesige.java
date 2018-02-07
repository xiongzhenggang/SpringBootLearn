package com.xzg.javadesign;

public class BrigeDesige {
	public static void  mian(String[] args){
		Brige brige = new Mybrige();
		Source source01 = new SourceAble01();
		Source Source02 =new SourceAble02();
		brige.setSource(source01);
		brige.method();
		brige.setSource(Source02);
		brige.method();
	}
}

interface Source{
	void method();
}
class SourceAble01 implements Source{

	public void method() {
		// TODO Auto-generated method stub
		System.out.println("first method");
	}
}
class SourceAble02 implements Source{
	public void method(){
		System.out.println("second method");
	}
}
//定义一个桥，持有Sourceable的一个实例：
abstract class Brige{
	private Source source;

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}
	          
	public void method(){
		source.method();
	}
}
//
class Mybrige extends Brige{
	public void method(){
		getSource().method();
	}
}