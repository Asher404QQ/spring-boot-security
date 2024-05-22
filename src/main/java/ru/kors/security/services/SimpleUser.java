package ru.kors.security.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kors.models.MyUser;

import java.util.Collection;
import java.util.List;

public class SimpleUser implements UserDetails {
    private final MyUser myUser;

    public SimpleUser(MyUser myUser) {
        this.myUser = myUser;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(myUser::getAuthority);
    }

    @Override
    public String getPassword() {
        return this.myUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.myUser.getUsername();
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
