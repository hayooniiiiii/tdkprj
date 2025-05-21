package com.example.tdkprj1.auth;

import com.example.tdkprj1.model.UserDto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Data
public class PrincipalDetails implements UserDetails {

    private UserDto userDto;
    private Map<String, Object> attributes;
    //일반 로그인
    public PrincipalDetails(UserDto userDto) {
        this.userDto=userDto;
    }

    @Override
    public String getPassword() {
        return userDto.getUserPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(userDto.getUserRole()));
    }

    @Override
    public String getUsername() {
        return userDto.getUserLoginid();
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
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
