package org.elberjsn.encurtador_link.security.userDatails;

import org.elberjsn.encurtador_link.dto.UserDTO;
import org.elberjsn.encurtador_link.mapper.UserMapper;
import org.elberjsn.encurtador_link.model.User;
import org.elberjsn.encurtador_link.repository.UserRepository;
import org.elberjsn.encurtador_link.security.JWTConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsImplementsServices implements UserDetailsService{

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
        return new UserDetailsImplments(user);
    }

    public UserDTO processLoginToken(String email,String pwd){
        
        UserDetails ud = loadUserByUsername(email);
        String token = JWTConfig.generateToken((UserDetailsImplments) ud);
        UserDTO userDto = UserMapper.toDTO(repository.findByEmailAndPassword(email, pwd).get(),token);
        return userDto;
    }


}
