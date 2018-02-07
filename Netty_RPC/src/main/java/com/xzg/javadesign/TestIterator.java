package com.xzg.javadesign;

public class TestIterator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Collection collection = new MyCollection();
		Iterator iterator = collection.iterator();
		//初始前移
		System.out.println(iterator.previous());
		//迭代循环
		while(iterator.hasNext()){
			System.out.println(iterator.next());
		}
		//第一个值
		System.out.println(iterator.first());
		//前一个
		System.out.println(iterator.previous());
	}
}

interface Collection{
	//集合中包含的迭代器
	public Iterator iterator();
	/*取得集合中的元素*/
	public Object get(int i);
	/* 取得集合的大小*/
	public int size();
}
interface Iterator{
	//前移
	public Object previous();
	//后移
	public Object next();
	//判断集合是否为空
	public boolean hasNext();
	//取得第一个元素
	public Object first();
}
class MyIterator implements Iterator{
	//迭代器持有相应集合的对象
	private Collection collection;
	//作为迭代器的指针
	private int pos = -1;
	//绑定集合对象
	public MyIterator(Collection collection){
		this.collection = collection;
	}
	/* 前移
	 * @see com.xzg.design.Iterator#prebious()
	 */
	public Object previous() {
		// TODO Auto-generated method stub
		if(pos <= 0)
			return null;
		return collection.get(--pos);
	}

	public Object next() {
		// TODO Auto-generated method stub
		if(pos>=collection.size())
			return null;
		return collection.get(++pos);
	}

	public boolean hasNext() {
		// TODO Auto-generated method stub
		if(pos<collection.size()-1)
			return true;
		return false;
	}

	public Object first() {
		// TODO Auto-generated method stub
		return collection.get(0);
	}
}
class MyCollection implements Collection{

	//作为操作的实际集合
	String string[] = {"abc","bcd","def","fgh"};
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return new MyIterator(this);
	}

	public Object get(int i) {
		// TODO Auto-generated method stub
		return string[i];
	}

	public int size() {
		// TODO Auto-generated method stub
		return string.length;
	}
}
