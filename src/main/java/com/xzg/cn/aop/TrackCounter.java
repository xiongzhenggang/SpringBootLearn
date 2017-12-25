package com.xzg.cn.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class TrackCounter {
    private Map<Integer,Integer> trankCount
            = new HashMap<>();

    //切点定义
    @Pointcut("execution(* com.xzg.cn.common.CompactDisc.playTranks(int))" +
    "&& args(trank)")
    public void trackPlayed(int trank){}
    //执行切面
    @Before("trackPlayed(trank)")
    public void countTrack(int trank){
        System.out.println("========>>在方法前执行");
        int currentCount = getPlayCount(trank);
        trankCount.put(trank,currentCount+1);
    }
    @After("trackPlayed(trank)")
    public void testAfter(int trank){
        System.out.println("=========》》在方法后执行");
    }

    @Around("trackPlayed(trank)")
    //这里的参数会传递给目标方法，如果不加上会抛出非法参数异常
    public void  watchAround(ProceedingJoinPoint pj , int trank){
        try {
            System.out.println("环绕通知前");
            pj.proceed();
            System.out.println("环绕通知后");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
    public int getPlayCount(int trank){
        return  trankCount.containsKey(trank)
                ? trankCount.get(trank):0;
    }
}
