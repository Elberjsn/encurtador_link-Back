package org.elberjsn.encurtador_link.services;

import java.sql.SQLIntegrityConstraintViolationException;

import org.elberjsn.encurtador_link.dto.UserDTO;
import org.elberjsn.encurtador_link.exception.GlobalException;
import org.elberjsn.encurtador_link.model.User;
import org.elberjsn.encurtador_link.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    public User save(UserDTO user) {
        var t = User.fromDTO(user);
        System.out.println("Service - "+t.toString());

        if (emailIsPresent(user.email()) == true) {
           GlobalException.jaExiste(new SQLIntegrityConstraintViolationException("Email já cadastrado"));
        }

        User saves= repository.save(t);
        if (saves.getId() == null) {
            throw new NullPointerException("Erro ao Cadastrar");
        }else{
            return saves;
        }
        
    }
    public User save(User user){
        return repository.save(user);
    }
    public boolean emailIsPresent(String email){
        return repository.findByEmail(email).isPresent();
    }

    public User findByEmailUser(String email) {
        return repository.findByEmail(email).orElse(null);
        
    }

    public User findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public User alterSenha(Long id, String pwd) {
        var user = findById(id);
        user.setPassword(pwd);
        return save(user);
    }

    public User alterEmail(Long id, String email) {
        var user = findById(id);
        user.setEmail(email);
        return save(user);
    }

    public User alter(User newUser) {
        var user = findById(newUser.getId());
        user.setEmail(newUser.getEmail());
        user.setName(newUser.getName());
        user.setPassword(newUser.getPassword());

        return save(user);
    }

    public void deleteUser(User user) {
        repository.delete(user);
    }

    @Override
    // Find email and add credentials
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Load User By Username: " + email);
        User user = repository.findByEmail(email).orElseThrow(() -> new NullPointerException("Email não encontrado."));
        return user;
    }

}
