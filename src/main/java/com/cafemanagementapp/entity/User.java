package com.cafemanagementapp.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE email = :email")
@NamedQuery(name = "User.getUsers", query = "SELECT new com.cafemanagementapp.wrapper.UserWrapper(u.id,u.name,u.contactNumber,u.email,u.enable)  FROM User u WHERE u.role ='USER'")
@NamedQuery(name = "User.getAllAdmin", query = "SELECT u.email  FROM User u WHERE u.role ='ADMIN'")
@NamedQuery(name = "User.enableUser", query = "UPDATE User u SET u.enable = :enable WHERE u.id = :id")

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String contactNumber;

    private String email;
    private String password;
    private boolean enable;
    @Enumerated(EnumType.STRING)
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return enable;
    }
}
