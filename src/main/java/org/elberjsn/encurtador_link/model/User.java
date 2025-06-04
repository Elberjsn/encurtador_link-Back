package org.elberjsn.encurtador_link.model;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.elberjsn.encurtador_link.dto.UserDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "table_users")
public class User implements UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    final Long id;

    @Column(nullable = false)
    @NonNull
    private String name;

    @Column(unique = true, nullable = false)
    @NonNull
    private String email;

    @Column(nullable = false)
    @NonNull
    private String password;
    
    @OneToMany(mappedBy = "userLink")
    @JsonIgnore
    private List<Link> links;


    public static User fromDTO(UserDTO userDTO) {
        return new User(
            userDTO.id(),
            userDTO.name(),
            userDTO.email(),
            userDTO.password()
        );
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
       List<String> list = new ArrayList<>();
       list.add("ROLE_ADMINISTRAOR");
       return list.stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return this.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
       return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
       return true;
    }

}
