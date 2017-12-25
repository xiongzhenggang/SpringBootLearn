package com.xzg.cn.filter;

import org.springframework.web.WebApplicationInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
public class MyServletInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        FilterRegistration.Dynamic  filter =
                servletContext.addFilter("myFilter",MyFilter.class);
        filter.addMappingForUrlPatterns(null,false,"/*");
    }
}
