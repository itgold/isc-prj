package com.iscweb.service.security;

import com.iscweb.common.model.entity.IUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Loop principal object.
 */
public class IscPrincipal extends User {

    private static final long serialVersionUID = -7218091552223711822L;

    @Getter
    @Setter
    private Long userId;

    public IscPrincipal(String username,
                        String password,
                        Long userId,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    /**
     * Builder method for Loop principal object.
     *
     * @param user principal user object.
     * @param authorities collection of user authorities.
     * @return new instance of Loop principal object.
     */
    public static IscPrincipal valueOf(IUser user, Collection<? extends GrantedAuthority> authorities) {
        return new IscPrincipal(user.getEmail(),
                                   user.getPassword(),
                                   user.getId(),
                                   authorities);
    }

    /**
     * @see User#toString()
     */
    @Override
    public String toString() {
        return "LoopPrincipal [" +
               "username=" + getUsername() +
               ", authorities=" + getAuthorities() +
               ", enabled=" + isEnabled() +
               ", accountNonExpired=" + isAccountNonExpired() +
               ", accountNonLocked=" + isAccountNonLocked() +
               ", credentialsNonExpired=" + isCredentialsNonExpired() +
               ", password=*****" +
               "]";
    }
}
