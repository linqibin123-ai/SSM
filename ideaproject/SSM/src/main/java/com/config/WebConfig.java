package com.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebConfig implements WebApplicationInitializer, WebMvcConfigurer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext springcontext = new AnnotationConfigWebApplicationContext();
        springcontext.register(SpringConfig.class);
        servletContext.addListener(new ContextLoaderListener(springcontext));
        AnnotationConfigWebApplicationContext springmvccontext = new AnnotationConfigWebApplicationContext();
        springmvccontext.register(SpringMvcConfig.class);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("DispatcherServlet", new DispatcherServlet(springmvccontext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
    }
}
