package org.elberjsn.encurtador_link.model;

import java.util.List;

import org.elberjsn.encurtador_link.dto.UserDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

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
    private List<Link> links;

    public User(Long id, String name, String email, String pwd) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = pwd;
    }

    public static User fromDTO(UserDTO userDTO) {
        return new User(
            userDTO.id(),
            userDTO.name(),
            userDTO.email(),
            userDTO.pwd()
        );
    }

}
