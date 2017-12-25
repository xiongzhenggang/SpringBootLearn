package com.xzg.cn.configure;

import com.xzg.cn.aop.TrackCounter;
import com.xzg.cn.common.CompactDisc;
import com.xzg.cn.filter.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.servlet.MultipartConfigElement;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@ImportResource(locations={"classpath:application-bean.xml"})
@EnableAspectJAutoProxy //开启aspect aop代理
public class TrackCounterConfig {

    @Bean
    public CompactDisc sgtCompactDisc(){
        CompactDisc cd = new CompactDisc("compectDisc","切面处理");
        List<String > tranks = new ArrayList<>();
        tranks.add("sgt people heart lonly ");
        tranks.add("with little help");
        tranks.add("good happy to you");
        tranks.add("are you ok");
        tranks.add("you are my heart");
        cd.setTrancks(tranks);
        return cd;
    }

    @Bean
    public TrackCounter trackCounter(){
        return new TrackCounter();
    }

    @Bean
    public MultipartResolver multipartResolver() throws IOException{
        return new StandardServletMultipartResolver();
    }
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 设置文件大小限制 ,超出设置页面会抛出异常信息，
        // 这样在文件上传的地方就需要进行异常信息的处理了;
        factory.setMaxFileSize("256KB"); // KB,MB
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("512KB");
        // Sets the directory location where files will be stored.
        factory.setLocation("E:\\tmp");
        return factory.createMultipartConfig();
    }
//将配置的filter注入spring
/*@Bean
public FilterRegistrationBean registration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new MyFilter());
    registration.addUrlPatterns("/*");
    registration.addInitParameter("paramName", "paramValue");
    registration.setName("myFilter");
    registration.setOrder(1);
    return registration;
}*/

}
