package com.stock.capital.common.web.configuration;

import static com.google.common.base.Strings.isNullOrEmpty;
import static java.util.Collections.enumeration;
import static java.util.Collections.list;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.of;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * [WebMvcConfiguration].
 *
 * @author Joker.Cheng.
 * @version 0.0.1
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

  @Autowired private WebSettings webSettings;

  /**
   * Resource Handlers
   *
   * @param registry
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
  }

  /**
   * Default View controllers.
   *
   * @param registry registry
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/login").setViewName("login");
    registry.addViewController("/error/404").setViewName("error/404");
  }

  /**
   * Interceptors.
   *
   * @param registry registry
   */
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    //    registry.addInterceptor(baseInterceptor).excludePathPatterns("/static/**");
  }

  /**
   * Cors.
   *
   * @param registry registry
   */
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    String[] apacheUrls = webSettings.getCorsMappings();
    if (apacheUrls != null) {
      registry
          .addMapping("/**")
          .allowedOrigins(apacheUrls)
          .allowedMethods(of(GET, POST, PUT, DELETE, HEAD).map(Enum::name).toArray(String[]::new));
    }
  }

  /**
   * Cors.
   *
   * @return filter
   */
  @Bean
  public FilterRegistrationBean corsPreflightFilterRegistrationBean() {
    FilterRegistrationBean filterBean = new FilterRegistrationBean();
    filterBean.setFilter(
        new OncePerRequestFilter() {
          @Override
          protected void doFilterInternal(
              HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
              throws ServletException, IOException {
            if (CorsUtils.isPreFlightRequest(request)) {
              HttpServletRequest wrappedServletRequest =
                  new HttpServletRequestWrapper(request) {

                    @Override
                    public Enumeration<String> getHeaders(String name) {
                      Enumeration<String> headers = super.getHeaders(name);
                      return enumeration(
                          list(headers)
                              .stream()
                              .filter(item -> !isNullOrEmpty(item))
                              .collect(toList()));
                    }

                    @Override
                    public String getHeader(String name) {
                      String header = super.getHeader(name);
                      return isNullOrEmpty(header) ? null : header;
                    }
                  };
              filterChain.doFilter(wrappedServletRequest, response);
            } else {
              filterChain.doFilter(request, response);
            }
          }
        });
    filterBean.setUrlPatterns(singletonList("/*"));
    filterBean.setOrder(1);
    return filterBean;
  }

  //  /**
  //   * E-TAG.
  //   *
  //   * @return filter
  //   */
  //  @Bean
  //  public FilterRegistrationBean filterRegistrationBean() {
  //
  //    // TODO Shallow ETAG with header
  //    FilterRegistrationBean filterBean = new FilterRegistrationBean();
  //    filterBean.setFilter(new ShallowEtagHeaderFilter());
  //    // add ETag to header but only oss callback
  //    filterBean.setUrlPatterns(singletonList("/api/storage/callback"));
  //    return filterBean;
  //  }

  @Configuration
  @ConfigurationProperties("capital.web")
  public static class WebSettings {

    private String[] corsMappings;
    private String cookieDomainNamePattern;
    private String rememberMeCookieDomain;

    public String[] getCorsMappings() {
      return corsMappings;
    }

    public void setCorsMappings(String[] corsMappings) {
      this.corsMappings = corsMappings;
    }

    public String getCookieDomainNamePattern() {
      return cookieDomainNamePattern;
    }

    public void setCookieDomainNamePattern(String cookieDomainNamePattern) {
      this.cookieDomainNamePattern = cookieDomainNamePattern;
    }

    public String getRememberMeCookieDomain() {
      return rememberMeCookieDomain;
    }

    public void setRememberMeCookieDomain(String rememberMeCookieDomain) {
      this.rememberMeCookieDomain = rememberMeCookieDomain;
    }
  }
}
