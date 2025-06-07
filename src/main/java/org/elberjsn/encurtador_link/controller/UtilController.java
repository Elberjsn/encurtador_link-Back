package org.elberjsn.encurtador_link.controller;

import org.elberjsn.encurtador_link.dto.UserDTO;
import org.elberjsn.encurtador_link.model.User;
import org.elberjsn.encurtador_link.security.AuthenticationFilter;
import org.elberjsn.encurtador_link.services.LinkService;
import org.elberjsn.encurtador_link.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * UtilController handles user login and registration requests.
 * It provides endpoints for user authentication and account creation.
 */
@RestController
@RequestMapping("/")
public class UtilController {

    @Autowired
    UserService service;

    @Autowired
    LinkService linkService;

    @Autowired
    AuthenticationFilter filter;

    /**
     * Process login request with email and password.
     *
     * @param user the user credentials
     * @return ResponseEntity with status and token if successful
     */
    @PostMapping("login")
    public ResponseEntity<String> loginPost(@RequestBody UserDTO user) {
        System.out.println(user.toString());
        var login = filter.processLoginToken(user.email(), user.password());

        if (login.id() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Usuario ou Senha Não Aceito");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + login.token());
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).build();
    }

    /**
     * Register a new user with the provided user information.
     *
     * @param user the user to be registered
     * @return ResponseEntity with status indicating success or failure
     */
    @PostMapping("register")
    public ResponseEntity<String> registerPost(@RequestBody User user) {
        User us = service.save(user);
        if (us.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Erro ao Criar novo Usuario ");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(us.toString());
    }


    /**
     * Process a Short Link and forward the correct link
     * 
     * @param shortLink
     * @return ResponseEntity with header indicating URL 
     */
    @GetMapping("{shortLink}")
    public ResponseEntity<String> accessShortLink(@PathVariable String shortLink) {
        String link = linkService.accessLink(shortLink);
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION,link).build();
    }
    
}
