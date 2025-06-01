package org.elberjsn.encurtador_link.services;

import org.elberjsn.encurtador_link.model.UserLink;
import org.elberjsn.encurtador_link.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public UserLink save(UserLink user){
        return repository.save(user);
    }
    public UserLink loginUser(String email,String pwd){
        return repository.findByEmailAndPassword(email, pwd).orElse(null);
    }
    public UserLink findById(Long id){
        return repository.findById(id).orElse(null);
    }
    public UserLink alterSenha(Long id,String pwd){
        var user = findById(id);
        user.setPassword(pwd);
        return save(user);
    }
    public UserLink alterEmail(Long id,String email){
        var user = findById(id);
        user.setEmail(email);
        return save(user);
    }
    public UserLink alter(UserLink newUser){
        var user = findById(newUser.getId());
        user.setEmail(newUser.getEmail());
        user.setName(newUser.getName());
        user.setPassword(newUser.getPassword());

        return save(user);
    }
    public void deleteUser(UserLink user){
        repository.delete(user);
    }

}
