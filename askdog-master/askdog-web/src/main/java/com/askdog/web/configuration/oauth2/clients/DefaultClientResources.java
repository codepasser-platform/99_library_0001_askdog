package com.askdog.web.configuration.oauth2.clients;

import com.askdog.model.entity.ExternalUser;
import com.askdog.model.entity.User;
import com.askdog.model.entity.inner.user.UserProvider;
import com.askdog.model.entity.inner.user.UserType;
import com.askdog.service.ExternalUserService;
import com.askdog.web.configuration.handler.WebAuthenticationSuccessHandler;
import com.askdog.web.configuration.oauth2.AdOAuth2RestTemplate;
import com.askdog.web.configuration.oauth2.AdUserInfoTokenServices;
import com.askdog.web.configuration.oauth2.ClientResources;
import com.askdog.web.configuration.userdetails.AdUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import static org.springframework.security.core.authority.AuthorityUtils.commaSeparatedStringToAuthorityList;

public class DefaultClientResources extends ClientResources {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private OAuth2ClientContext oAuth2ClientContext;

    @Autowired
    private WebAuthenticationSuccessHandler webAuthenticationSuccessHandler;

    @Autowired
    private ExternalUserService externalUserService;

    @Override
    public OAuth2ClientAuthenticationProcessingFilter filter(String path) {
        OAuth2ClientAuthenticationProcessingFilter filter = new AdOAuth2ClientAuthenticationProcessingFilter(path);
        filter.setRestTemplate(new AdOAuth2RestTemplate(getClient(), oAuth2ClientContext));
        filter.setTokenServices(new AdUserInfoTokenServices(getResource(), getClient(), getDefinition(), filter.restTemplate));
        filter.setAuthenticationSuccessHandler(webAuthenticationSuccessHandler);
        return filter;
    }

    private class AdOAuth2ClientAuthenticationProcessingFilter extends OAuth2ClientAuthenticationProcessingFilter {

        AdOAuth2ClientAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
            super(defaultFilterProcessesUrl);
        }

        @Override
        public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
                throws AuthenticationException, IOException, ServletException {

            Map<String, Object> oAuthUserDetails = getOAuthUserDetails(request, response);

            String openId = getDefinition().getProperty(oAuthUserDetails, getDefinition().getId());

            Optional<ExternalUser> externalUserOptional = externalUserService.findByExternalUserId(openId);

            if (!externalUserOptional.isPresent()) {
                // TODO append properties
                User openIdUser = new User();
                openIdUser.setType(UserType.EXTERNAL);

                ExternalUser externalUser = new ExternalUser();
                externalUser.setUser(openIdUser);
                externalUser.setExternalUserId(openId);
                externalUser.setProvider(UserProvider.valueOf(getClient().getId()));
                externalUserOptional = Optional.of(externalUserService.save(externalUser));
            }

            User bindUser = externalUserOptional.get().getUser();
            // update transient fields
            bindUser.setNickName(getDefinition().getProperty(oAuthUserDetails, getDefinition().getNickname()));
            bindUser.setAvatarUrl(getDefinition().getProperty(oAuthUserDetails, getDefinition().getAvatar()));
            return new UsernamePasswordAuthenticationToken(new AdUserDetails(bindUser),
                    "N/A", commaSeparatedStringToAuthorityList("ROLE_USER"));
        }

        @SuppressWarnings("unchecked")
        private Map<String, Object> getOAuthUserDetails(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            OAuth2Authentication oAuthAuthentication = (OAuth2Authentication) super.attemptAuthentication(request, response);
            Authentication userAuthentication = oAuthAuthentication.getUserAuthentication();

            return (Map<String, Object>) userAuthentication.getDetails();
        }

    }

}
