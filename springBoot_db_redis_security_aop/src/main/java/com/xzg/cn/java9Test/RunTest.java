package com.xzg.cn.java9Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RunTest {
    public static void main(String[] args) throws Exception{
    test02();
    }
    public static void test02() throws Exception{
        int tests = 0;
        int passed = 0;
        Class testClass = Class.forName(Simple2.class.getName());
        for(Method m : testClass.getDeclaredMethods()){
            //如果注解是自定义的执行如下
            if(m.isAnnotationPresent(ExceptionTest.class)){
                tests ++;
                try {
                    m.invoke(null);
                   System.out.printf("Test %s faild : now exception%n",m);
                }catch (InvocationTargetException e){
                    Throwable exc = e.getCause();
                    Class<? extends  Exception> excType =
                            m.getAnnotation(ExceptionTest.class).value();
                    if(excType.isInstance(exc)){
                        passed ++;
                    }else{
                        System.out.printf("Test %s faild:expend %s,got %s%n",m,excType.getName(),exc);
                    }
                }catch (Exception e){
                    System.out.println("Invalid @test" +m);
                }
            }
        }
    }
    public static void test01() throws Exception{
        int tests = 0;
        int passed = 0;
        Class testClass = Class.forName(Simple.class.getName());
        for(Method m : testClass.getDeclaredMethods()){
            tests ++;
            try {
                m.invoke(null);
                passed ++ ;
            }catch (InvocationTargetException e){
                Throwable exc = e.getCause();
                System.out.println(m + "faild" + exc);
            }catch (Exception e){
                System.out.println("Invalid @test" +m);
            }
            System.out.printf("passed:%d ,Faild: %d%n",passed,tests-passed);
        }
    }
}
