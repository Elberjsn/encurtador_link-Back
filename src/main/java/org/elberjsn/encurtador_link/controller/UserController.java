package org.elberjsn.encurtador_link.controller;

import org.elberjsn.encurtador_link.model.User;
import org.elberjsn.encurtador_link.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * UserController handles requests related to user information retrieval.
 * It provides endpoints to fetch user details with sensitive data.
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    /**
     * Find user by id and return a simple representation without sensitive data.
     *
     * @param id the user id
     * @return ResponseEntity with user information
     */
    @GetMapping("/simple/{id}")
    public ResponseEntity<String> findSimpleId(@PathVariable Long id) {
        User user = service.findById(id);
        user.setPassword("-");
        return ResponseEntity.status(HttpStatus.OK).body(user.toString());
    }

    /**
     * Find user by id and return all information including sensitive data.
     *
     * @param id the user id
     * @return ResponseEntity with full user information
     */
    @GetMapping("/all/{id}")
    public ResponseEntity<String> findAllId(@PathVariable Long id) {
        User user = service.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user.toString());
    }
   
    

}
