package org.elberjsn.encurtador_link.services;

import java.sql.SQLIntegrityConstraintViolationException;

import org.elberjsn.encurtador_link.dto.UserDTO;
import org.elberjsn.encurtador_link.mapper.UserMapper;
import org.elberjsn.encurtador_link.model.User;
import org.elberjsn.encurtador_link.repository.UserRepository;
import org.elberjsn.encurtador_link.security.JWTConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{

    @Autowired
    UserRepository repository;
    
   

    public User save(User user) {
        try{
            if (findByEmailUser(user.getEmail()) != null) {
                throw new SQLIntegrityConstraintViolationException("Email já Cadastrado");
            }else{
                return repository.save(user);
            }
        }catch(Exception e){
            throw new NullPointerException("Erro ao Cadastrar");
        }
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
        User user = repository.findByEmail(email).orElseThrow(() -> new NullPointerException("Email não encontrado."));
        return user;
    }

   

}
