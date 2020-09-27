package com.zk.cloudalibaba.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class AuthUserDetails  implements UserDetails {
    private String password;
    private String username;
    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credntialNonExpired;
    private Boolean enabled;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return enabled;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = true;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = true;
    }

    public void setCredntialNonExpired(Boolean credntialNonExpired) {
        this.credntialNonExpired = true;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }


}
