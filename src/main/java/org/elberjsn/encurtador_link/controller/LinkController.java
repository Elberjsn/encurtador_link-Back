package org.elberjsn.encurtador_link.controller;

import java.time.LocalDate;

import org.elberjsn.encurtador_link.dto.LinkDTO;
import org.elberjsn.encurtador_link.model.Link;
import org.elberjsn.encurtador_link.security.JWTConfig;
import org.elberjsn.encurtador_link.services.LinkService;
import org.elberjsn.encurtador_link.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @Autowired
    UserService userService;

    @PostMapping("/generate")
    public ResponseEntity<String> shorLink(@RequestBody String url, Long idUser, HttpServletRequest request) {
        if (idUser == null) {
            String authHeader = request.getHeader("Authorization");
            idUser = findIdLong(authHeader.substring(7));

        }
        var shotner = linkService.shortener(url, idUser);
        LinkDTO linkDTO = new LinkDTO(url, shotner, LocalDate.now().plusDays(30), true);

        var link = linkService.save(Link.fromDTO(linkDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(link.toString());
    }

    private Long findIdLong(String token) {
        String email = JWTConfig.getSubjectFromToken(token);
        return userService.findByEmailUser(email).getId();
    }

}
