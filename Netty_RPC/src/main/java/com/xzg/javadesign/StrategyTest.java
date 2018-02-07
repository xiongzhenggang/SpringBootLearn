package com.xzg.javadesign;


public class StrategyTest {
	/**
	 * @param args
	 * 测试方法
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String exp = "6+6";
		AbstractCalculator abstractCalculator = new Plus01();
		int result = abstractCalculator.calculate(exp, "\\+");
		System.out.println(result);
		exp = "6-3";
		abstractCalculator = new Mnus01();
		result = abstractCalculator.calculate(exp, "\\-");
		System.out.println(result);
	}

}

/**
 * @author xzg
 *
 */
abstract class AbstractCalculator{
	/*主方法，实现对本类其它方法的调用*/
	public final int calculate(String exp,String opt){
		int arry[] = split(exp, opt);
		return calculate(arry[0], arry[1]);
	} 
	
	/**
	 * 需要子类重写的方法
	 * @param i1
	 * @param i2
	 * @return
	 */
	abstract public int calculate(int i1,int i2);
	//抽象类中需要的辅助
	public int[] split(String exp,String opt){
		String[] arrystr = exp.split(opt);
		int arryint[] = new int[2];
		arryint[0] = Integer.valueOf(arrystr[0]);
		arryint[1] = Integer.valueOf(arrystr[1]);
		return arryint;
	}
}

/**
 * @author xzg
 * 加法
 */
class Plus01 extends AbstractCalculator{
	@Override
	public int calculate(int num1,int num2){
		return num1+num2;
	}
}
/**
 * @author xzg
 *减法
 */
class Mnus01 extends AbstractCalculator{
	@Override
	public int calculate(int num1,int num2){
		return num1-num2;
	}
}