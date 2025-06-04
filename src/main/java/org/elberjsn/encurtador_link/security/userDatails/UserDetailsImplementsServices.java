package org.elberjsn.encurtador_link.security.userDatails;

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
public class UserDetailsImplementsServices implements UserDetailsService {

    @Autowired
    private UserRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    // Find email and add credentials
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.findByEmail(email).orElseThrow(() -> new NullPointerException("Email não encontrado."));
        return user;
    }

    // Get credentials, check hashed password and create token
    public UserDTO processLoginToken(String email, String pwd) {

        UserDetails user = loadUserByUsername(email);
        if (!encoder.matches(pwd, user.getPassword())) {
            throw new BadCredentialsException("Senha Invalida");
        }
        String token = JWTConfig.generateToken((User) user);

        UserDTO userDto = UserMapper.toDTO((User) user, token);

        return userDto;
    }

}
