// com.back.multitenantback.security.UserPrincipal
package com.back.projectmanagermultitenantback.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {
    private final Long id;
    private final String email;
    private final Collection<? extends GrantedAuthority> authorities;
    private final List<String> roles;     // ex: ["ROLE_USER","ROLE_ADMIN"]
    private final List<Long> orgIds;      // ex: [1,2,3]

    public UserPrincipal(Long id,
                         String email,
                         Collection<? extends GrantedAuthority> authorities,
                         List<String> roles,
                         List<Long> orgIds) {
        this.id = id;
        this.email = email;
        this.authorities = authorities == null ? List.of() : authorities;
        this.roles = roles == null ? List.of() : roles;
        this.orgIds = orgIds == null ? List.of() : orgIds;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public List<String> getRoles() { return roles; }
    public List<Long> getOrgIds() { return orgIds; }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }
    @Override public String getPassword() { return null; }
    @Override public String getUsername() { return email; }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
