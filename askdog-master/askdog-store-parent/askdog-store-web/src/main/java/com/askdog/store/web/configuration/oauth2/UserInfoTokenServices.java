package com.askdog.store.web.configuration.oauth2;

import com.askdog.store.model.security.Authority;
import com.askdog.store.service.bo.BuyerDetail;
import com.askdog.store.web.configuration.oauth2.SecurityClientResources.ResourceServerProperties;
import com.askdog.store.web.configuration.userdetails.AdBuyerDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.util.List;

import static org.springframework.security.oauth2.common.OAuth2AccessToken.BEARER_TYPE;

/**
 * {@link ResourceServerTokenServices} that uses a user info REST service.
 *
 * @author Dave Syer
 * @since 1.3.0
 */
public class UserInfoTokenServices implements ResourceServerTokenServices {

    private OAuth2ProtectedResourceDetails client;

    private ResourceServerProperties resource;

    private OAuth2RestOperations restTemplate;

    public UserInfoTokenServices(ResourceServerProperties resource, OAuth2ProtectedResourceDetails client, OAuth2RestOperations restTemplate) {
        this.resource = resource;
        this.client = client;
        this.restTemplate = restTemplate;
    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        BuyerDetail buyerDetail = getBuyerDetail(this.resource.getUserInfoUri(), accessToken);
        return extractAuthentication(buyerDetail);
    }


    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    private OAuth2Authentication extractAuthentication(BuyerDetail buyerDetail) {
        Object principal = getPrincipal(buyerDetail);
        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(Authority.Role.BUYER.authority());
        OAuth2Request request = new OAuth2Request(null, this.client.getClientId(), null, true, null, null, null, null, null);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        token.setDetails(buyerDetail);
        return new OAuth2Authentication(request, token);
    }

    protected Object getPrincipal(BuyerDetail buyerDetail) {
        return ((buyerDetail == null) ? "unknown" : new AdBuyerDetails(buyerDetail));
    }

    @SuppressWarnings({"unchecked"})
    private BuyerDetail getBuyerDetail(String path, String accessToken) {
        OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext().getAccessToken();
        if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
            DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
            token.setTokenType(BEARER_TYPE);
            restTemplate.getOAuth2ClientContext().setAccessToken(token);
        }
        return restTemplate.getForEntity(path, BuyerDetail.class).getBody();
    }


    public OAuth2ProtectedResourceDetails getClient() {
        return client;
    }

    public ResourceServerProperties getResource() {
        return resource;
    }
}