package com.xzg.cn.java9Test;

public class Simple2 {
    @ExceptionTest(ArithmeticException.class)
    public  static void m1(){
        int i = 0;
        i = i/i;
    }
    @ExceptionTest(ArithmeticException.class)
    public static void m2(){
        int[] a =new int[2];
        int i = a[1];
    }
    @ExceptionTest(ArithmeticException.class)
    public static void m3(){

    }

}
