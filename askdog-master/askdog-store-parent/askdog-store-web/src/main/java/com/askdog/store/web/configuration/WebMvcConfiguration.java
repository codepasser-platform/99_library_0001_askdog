package com.askdog.store.web.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // TODO: SSO login
        //registry.addViewController("/login").setViewName("login");
        //registry.addViewController("/oauth/confirm_access").setViewName("authorize");
    }

   /* @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        // TODO add ETag to header but only oss callback
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new ShallowEtagHeaderFilter());
        filterBean.setUrlPatterns(singletonList("/api/storage/callback"));
        return filterBean;
    }*/


}