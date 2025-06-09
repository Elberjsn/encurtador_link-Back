package org.elberjsn.encurtador_link.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.elberjsn.encurtador_link.dto.LinkDTO;
import org.elberjsn.encurtador_link.mapper.LinkMapper;
import org.elberjsn.encurtador_link.model.Link;
import org.elberjsn.encurtador_link.model.User;
import org.elberjsn.encurtador_link.services.LinkService;
import org.elberjsn.encurtador_link.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    LinkService linkService;

    @Autowired
    UserService userService;

    @PostMapping("/generate/{id}")
    public ResponseEntity<LinkDTO> shorLink(@RequestBody LinkDTO links, @PathVariable("id") Long id) {
       
        var shotner = "localhost:8080/link/" + linkService.shortener(links.url(), id);

        User user = userService.findById(id);

        Link newLink = new Link(links.alias(), links.url(), shotner, LocalDate.now(), LocalDate.now().plusDays(30),
                user, true, 0);

        var link = linkService.save(newLink);

        return ResponseEntity.status(HttpStatus.CREATED).body(LinkMapper.toDTO(link));
    }

    @PostMapping("/{id}")
    public ResponseEntity<List<LinkDTO>> findId(@PathVariable("id") Long id) {

        // Verifica se o usuário existe
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Busca os links do usuário
        List<Link> links = linkService.findLinkUser(id);

        List<LinkDTO> linkDTOs = new ArrayList<>();
        for (Link l : links) {
            linkDTOs.add(LinkMapper.toDTO(l));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(linkDTOs);
    }

    /**
     * Process a Short Link and forward the correct link
     * 
     * @param shortLink
     * @return ResponseEntity with header indicating URL
     */
    @GetMapping("/{shortLink}")
    public ResponseEntity<String> accessShortLink(@PathVariable String shortLink) {
        String link = linkService.accessLink(shortLink);
        return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, link).build();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> putMethodName(@PathVariable("id") Long id, @RequestBody LinkDTO link) {
        var edit = linkService.findLink(id);
        if (edit == null || edit.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Link não encontrado");
        }
        edit.setAlias(link.alias());

        linkService.updateLink(edit);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLink(@PathVariable("id") Long id) {
        var link = linkService.findLink(id);
        
        if (link == null || link.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Link não encontrado");
        }

        linkService.deleteLink(link);

        return ResponseEntity.status(HttpStatus.OK).body("Link deletado com sucesso!");
    }

}
