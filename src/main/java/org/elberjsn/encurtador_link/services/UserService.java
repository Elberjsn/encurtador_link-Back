package org.elberjsn.encurtador_link.services;

import org.elberjsn.encurtador_link.model.User;
import org.elberjsn.encurtador_link.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public User save(User user) {
        return repository.save(user);
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

}
