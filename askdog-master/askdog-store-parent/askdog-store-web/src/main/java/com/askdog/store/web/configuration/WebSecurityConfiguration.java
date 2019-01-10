package com.askdog.store.web.configuration;

import com.askdog.store.web.configuration.handler.*;
import com.askdog.store.web.configuration.oauth2.SecurityClientResources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static com.askdog.store.model.security.Authority.Role.BUYER;
import static org.springframework.http.HttpMethod.GET;

@Configuration
@EnableOAuth2Client
@Order(6)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private SecurityClientResources securityClientResources;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login", "/login/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/websocket/**").permitAll()
                .anyRequest().hasRole(BUYER.name())

                .and()
                /** API access denied handler**/
                .exceptionHandling()
                .accessDeniedHandler(redirectAccessDeniedHandler())
                .authenticationEntryPoint(
                        new AuthenticationResponseEntryPoint("/login")
                                .apiMatcher(new AntPathRequestMatcher("/api/**"))
                                .authenticationResponseHandler(authenticationResponseHandler())
                )

                .and()
                /** login **/
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler().targetUrlParam("request"))
                .failureHandler(authenticationFailureHandler().errorPage("/login?authentication_error=true"))

                .and()
                /** logout **/
                .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", GET.name()))
                .logoutSuccessHandler(webLogoutSuccessHandler())
                .deleteCookies("SESSION")
                .invalidateHttpSession(true)

                .and()
                .addFilterBefore(securityClientResources.filter("/login"), BasicAuthenticationFilter.class)

                /** csrf disable **/
                .csrf()
                .disable();
                /*csrf token settings
                .requireCsrfProtectionMatcher(new OrRequestMatcher(
                        new AntPathRequestMatcher("/login", GET.name()),
                        new AntPathRequestMatcher("/login", POST.name())
                ))
                .csrfTokenRepository(csrfTokenRepository()).and()
                .addFilterBefore(csrfCookieFilter(), CsrfFilter.class);*/

    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .parentAuthenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);
        //.passwordEncoder(passwordEncoder);
    }

    @Bean
    protected FilterRegistrationBean oAuth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(filter);
        registrationBean.setOrder(-100);
        return registrationBean;
    }

    @Bean
    public RedirectAccessDeniedHandler redirectAccessDeniedHandler() {
        return new RedirectAccessDeniedHandler();
    }

    @Bean
    public ApiAuthenticationResponseHandler authenticationResponseHandler() {
        return new ApiAuthenticationResponseHandler();
    }

    @Bean
    public WebAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new WebAuthenticationSuccessHandler();
    }

    @Bean
    public WebAuthenticationFailureHandler authenticationFailureHandler() {
        return new WebAuthenticationFailureHandler();
    }

    @Bean
    public LogoutSuccessHandler webLogoutSuccessHandler() {
        return new WebLogoutSuccessHandler();
    }

    /*
    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName(HEADER_X_XSRF_TOKEN);
        return repository;
    }

    @Bean
    public CsrfCookieFilter csrfCookieFilter() {
        return new CsrfCookieFilter();
    }*/

}
