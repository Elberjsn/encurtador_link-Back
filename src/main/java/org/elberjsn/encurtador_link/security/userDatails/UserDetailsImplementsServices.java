package org.elberjsn.encurtador_link.security.userDatails;

import org.elberjsn.encurtador_link.model.User;
import org.elberjsn.encurtador_link.repository.UserRepository;
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


}
