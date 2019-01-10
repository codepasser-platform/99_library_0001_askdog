package com.askdog.store.web.configuration.oauth2;

import com.askdog.store.web.configuration.handler.WebAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

@Configuration
@ConfigurationProperties("askdog.security.oauth2")
public class SecurityClientResources {


    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private OAuth2ClientContext oauth2ClientContext;

    private OAuth2ProtectedResourceDetails client = new AuthorizationCodeResourceDetails();

    private ResourceServerProperties resource = new ResourceServerProperties();

    @Autowired
    private WebAuthenticationSuccessHandler webAuthenticationSuccessHandler;

    public OAuth2ClientAuthenticationProcessingFilter filter(String path) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
        filter.setRestTemplate(new OAuth2RestTemplate(getClient(), oauth2ClientContext));
        filter.setTokenServices(new UserInfoTokenServices(getResource(), getClient(), filter.restTemplate));
        filter.setAuthenticationSuccessHandler(webAuthenticationSuccessHandler);

        return filter;
    }

    public static class ResourceServerProperties {

        private String userInfoUri;

        public String getUserInfoUri() {
            return userInfoUri;
        }

        public void setUserInfoUri(String userInfoUri) {
            this.userInfoUri = userInfoUri;
        }
    }

    public OAuth2ProtectedResourceDetails getClient() {
        return client;
    }

    public ResourceServerProperties getResource() {
        return resource;
    }


}
