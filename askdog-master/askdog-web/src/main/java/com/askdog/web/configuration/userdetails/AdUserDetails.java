package com.askdog.web.configuration.userdetails;

import com.askdog.model.entity.User;
import com.askdog.model.security.Authority;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static com.askdog.model.entity.inner.user.UserStatus.MAIL_CONFIRMED;

public class AdUserDetails extends User implements UserDetails {

    private static final long serialVersionUID = -7966053625918135698L;

    private User user;

    public AdUserDetails(User user) {
        this.user = user;
        BeanUtils.copyProperties(user, this);
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList(Authority.Role.USER.authority());
    }

    @Override
    public String getUsername() {
        return user.getName();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.hasStatus(MAIL_CONFIRMED);
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