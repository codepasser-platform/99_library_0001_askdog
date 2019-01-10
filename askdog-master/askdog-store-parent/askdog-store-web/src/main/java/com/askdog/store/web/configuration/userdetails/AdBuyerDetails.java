package com.askdog.store.web.configuration.userdetails;

import com.askdog.store.service.bo.BuyerDetail;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static com.askdog.store.model.security.Authority.Role.BUYER;


public class AdBuyerDetails extends BuyerDetail implements UserDetails {

    private static final long serialVersionUID = -6070328713262003889L;

    private BuyerDetail buyer;

    public AdBuyerDetails(BuyerDetail buyer) {
        this.buyer = buyer;
        BeanUtils.copyProperties(buyer, this);
    }

    public BuyerDetail getBuyer() {
        return buyer;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(BUYER.authority());
    }

    @Override
    public String getUsername() {
        return buyer.getName();
    }

    @Override
    public String getPassword() {
        return buyer.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}