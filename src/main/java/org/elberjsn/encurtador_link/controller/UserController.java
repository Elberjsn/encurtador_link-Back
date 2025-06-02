package org.elberjsn.encurtador_link.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/")
public class UserController {

    @PostMapping("/register")
    public ResponseEntity<String> registerPost(@RequestBody String entity) {
        return ResponseEntity.ok().build();
    }
    
}
