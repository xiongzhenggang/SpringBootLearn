package com.xzg.cn.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
//这个注解扫描制定的包下面所有继承JpaRepostory接口的类，并自动添加实现方法，无需手动添加
@EnableJpaRepositories(basePackages = "com.xzg.cn.repository.impJpaRepostory"
        ,entityManagerFactoryRef="emfb"
        ,repositoryImplementationPostfix = "Impl")
//entityManagerFactoryRef="emfb" 。Spring Data JPA默认查询为entityManagerFactory的bean，为了让jpa识别
public class JpaAutoConfig {
}
