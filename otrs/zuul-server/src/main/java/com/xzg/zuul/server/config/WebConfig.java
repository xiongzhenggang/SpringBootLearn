package com.xzg.zuul.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc // 启用SpringMVC
//@ComponentScan("com.spring02") // 启动组件扫描
public class WebConfig extends WebMvcConfigurationSupport {
    @Bean
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(){
        return new MappingJackson2HttpMessageConverter();
    }
    // 配置视图解析器
    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setExposeContextBeansAsAttributes(true);
        return resolver;
    }
    //An even easier way to enable the request context listener is to add a bean annotation into your app.
    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
    // 配置处理静态资源
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    // 设置默认编码为UTF-8
    private Charset default_charset = Charset.forName("UTF-8");

    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(default_charset);
        List<MediaType> list = buildDefaultMediaTypes();
        converter.setSupportedMediaTypes(list);
        return converter;
    }
    // 设置MessageConverter
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(stringHttpMessageConverter());
        converters.add(mappingJackson2HttpMessageConverter());
        super.configureMessageConverters(converters);
    }
    // 设置响应头信息
    private static List<MediaType> buildDefaultMediaTypes() {
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.TEXT_HTML); // 这个必须设置在第一位
        list.add(MediaType.APPLICATION_JSON_UTF8);
        return list;
    }
}
