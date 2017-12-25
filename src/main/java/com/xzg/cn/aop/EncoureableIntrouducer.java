package com.xzg.cn.aop;

import com.xzg.cn.aopextra.DefaultEncoreable;
import com.xzg.cn.aopextra.Encoreable;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Aspect
//@Component
public class EncoureableIntrouducer {
    //将BlankDisc类引入Encoreable接口的实现类DefaultEncoreable的功能
    @DeclareParents(value="com.xzg.cn.common.BlankDisc+",
    defaultImpl = DefaultEncoreable.class)
    public static Encoreable encoreable;

}
