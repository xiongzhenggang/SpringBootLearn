package com.xzg.javadesign;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Prototype implements Cloneable,Serializable{
	/**
	 * 要实现深复制，需要采用流的形式读入当前对象的二进制输入，再写出二进制数据对应的对象。
	 */
	private static final long serialVersionUID = 1L;
	
	private String string;
	private SerializableObject object;
	
	/*浅复制*/
	public Object clone() throws CloneNotSupportedException{
		Prototype prototype = (Prototype) super.clone();
		return prototype;
	}
	/* 深复制 */
	public Object deepClone() throws IOException, ClassNotFoundException{
		//以二进制的形式写入输出管道
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(this);
		//使用输入管道去链接输出管道接收对象
		ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject(); //读取输入管道中的对象
	}
	//这里的get set方法是为了在进行深复制的将相应的对象引用也提供支持
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	public SerializableObject getObject() {
		return object;
	}
	public void setObject(SerializableObject object) {
		this.object = object;
	}

}
class SerializableObject implements Serializable{

	/**
	 * 作为类的引用属性深复制时也要继承Serializable
	 */
	private static final long serialVersionUID = 1L;
	
}
