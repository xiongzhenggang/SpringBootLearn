package com.xzg.javadesign;

public class Strategy {
	 public static void main(String[] args){
		Plus plus = new Plus();
		int result = plus.calcultor("1+1");
		System.out.println(result);
		Minus minus = new Minus();
		result = minus.calcultor("3-0");
		System.out.println(result);
	}

}

interface ICalcultor{
	public int 	calcultor(String exp);
}
abstract class CalcultorUtil{
	public int[] split(String exp,String opt){
		String[] arrystr = exp.split(opt);
		int[] arryint = new int[2];
		arryint[0] = Integer.valueOf(arrystr[0]);
		arryint[1] = Integer.valueOf(arrystr[1]);
		return arryint;
	}

}
class Plus extends CalcultorUtil implements ICalcultor{
	
	public int calcultor(String exp) {
		// TODO Auto-generated method stub
		int[] arryint = split(exp, "\\+");
		return arryint[0]+arryint[1];
	}
	
}

class Minus extends CalcultorUtil implements ICalcultor{
	public int calcultor(String exp){
		int[] arryint = split(exp, "\\-");
		return arryint[0]- arryint[1];
	}
}
