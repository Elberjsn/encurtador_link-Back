package org.elberjsn.encurtador_link.controller;

import org.elberjsn.encurtador_link.dto.UserDTO;
import org.elberjsn.encurtador_link.model.User;
import org.elberjsn.encurtador_link.security.userDatails.UserDetailsImplementsServices;
import org.elberjsn.encurtador_link.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    UserDetailsImplementsServices securityServices;

    @PostMapping("/login")
    public ResponseEntity<String> loginPost(@RequestBody UserDTO user) {
        var login = securityServices.processLoginToken(user.email(), user.pwd());
        if (login.id() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Usuario ou Senha Não Aceito");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + login.token());
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).build();
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerPost(@RequestBody User user) {
        User us = service.save(user);
        if (us.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Erro ao Criar novo Usuario ");
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/#/{id}")
    public ResponseEntity<String> findAllId(@PathVariable("id") Long id) {
        User user = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user.toString());
    }
    
    

}
