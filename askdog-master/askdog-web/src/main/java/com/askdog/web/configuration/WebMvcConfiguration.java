package com.askdog.web.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ShallowEtagHeaderFilter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static java.util.Collections.singletonList;

// import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    private WebSettings webSettings;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/oauth/confirm_access").setViewName("authorize");
    }

//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		String[] apacheUrls = webSettings.getApacheUrls();
//		if (apacheUrls != null) {
//			registry.addMapping("/**").allowedOrigins(apacheUrls);
//		}
//	}

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new ShallowEtagHeaderFilter());
        // add ETag to header but only oss callback
        filterBean.setUrlPatterns(singletonList("/api/storage/callback"));
        return filterBean;
    }

    @Configuration
    @ConfigurationProperties("askdog.web")
    protected static class WebSettings {

        private String[] apacheUrls;

        public String[] getApacheUrls() {
            return apacheUrls;
        }

        public void setApacheUrls(String[] apacheUrls) {
            this.apacheUrls = apacheUrls;
        }
    }

}