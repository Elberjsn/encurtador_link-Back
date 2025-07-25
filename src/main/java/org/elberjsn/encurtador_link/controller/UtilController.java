package org.elberjsn.encurtador_link.controller;

import org.elberjsn.encurtador_link.dto.UserDTO;
import org.elberjsn.encurtador_link.exception.CustomError;
import org.elberjsn.encurtador_link.model.User;
import org.elberjsn.encurtador_link.security.AuthenticationFilter;
import org.elberjsn.encurtador_link.security.JWTConfig;
import org.elberjsn.encurtador_link.services.LinkService;
import org.elberjsn.encurtador_link.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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

    @GetMapping("")
    public ResponseEntity<Boolean> validToken(@RequestParam String token) throws CustomError {
        var tokenV =JWTConfig.getSubjectFromToken(token.substring(7));
        if(tokenV != null){
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }else{
            return ResponseEntity.status(HttpStatus.RESET_CONTENT).body(false);
        }
    }
    

    /**
     * Process login request with email and password.
     *
     * @param user the user credentials
     * @return ResponseEntity with status and token if successful
     */
    @PostMapping("login")
    @CrossOrigin(origins = "http://localhost:4200") // Adjust the origin as needed
    public ResponseEntity<String> loginPost(@RequestBody UserDTO user) {
        
        var login = filter.processLoginToken(user.email(), user.password());

        if (login.id() == null) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Usuario ou Senha Não Aceito");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + login.token() +"|"+login.id().toString() );;
        return ResponseEntity.status(HttpStatus.ACCEPTED).headers(headers).build();
    }

    /**
     * Register a new user with the provided user information.
     *
     * @param user the user to be registered
     * @return ResponseEntity with status indicating success or failure
     */
    @PostMapping("register")
    @CrossOrigin(origins = "http://localhost:4200") // Adjust the origin as needed
    public ResponseEntity<User> registerPost(@RequestBody UserDTO user) {
       
        if (user != null) {
            User us = service.save(user);
            System.out.println(us);
            if (us.getId() != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(us);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
    }

    

}
