package com.ecureuill.ada.avanade.orderapi.domain.user;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity(name = "User")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class UserEntity implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Auto-generated method stub
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));

    }

    @Override
    public boolean isAccountNonExpired() {
        //Auto-generated method stub
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //Auto-generated method stub
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //Auto-generated method stub
        return true;
    }

    @Override
    public boolean isEnabled() {
        //Auto-generated method stub
        return true;
    }

    
}
