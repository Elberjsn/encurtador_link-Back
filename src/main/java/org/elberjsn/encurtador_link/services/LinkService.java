package org.elberjsn.encurtador_link.services;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.elberjsn.encurtador_link.model.Link;
import org.elberjsn.encurtador_link.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LinkService {

    @Autowired
    LinkRepository repository;

    public Link save(Link link) {

        try {
            return repository.save(link);
        } catch (Exception e) {
            throw new NullPointerException("Esse link não existe");
        }
    }

    public List<Link> findLinkUser(Long user) {
        return repository.findByUserLinkId(user);
    }

    public Link findLinkId(Long user, Long idLink) {
        var links = findLinkUser(user);
        for (Link link : links) {
            if (link.getId() == idLink) {
                return link;
            }
        }
        return null;
    }

    public Link findLinkUrl(Long user, String url) {
        var links = findLinkUser(user);
        for (Link link : links) {
            if (link.getUrlOriginal().equals(url)) {
                return link;
            }
        }
        return null;
    }

    public Link findLinkShortUrl(Long user, String shortUrl) {
        var links = findLinkUser(user);
        for (Link link : links) {
            if (link.getUrlShort().equals(shortUrl)) {
                return link;
            }
        }
        throw new NullPointerException("Esse link não existe");
    }

    public void deleteLink(Link link) {
        repository.delete(link);
    }

    public void disableLink(Link link) {
        link.setStatus(false);
        save(link);
    }

    public void reactivateLink(Link link) {
        link.setStatus(true);
        save(link);
    }

    public void addAcess(Link link) {
        link.setCounterAccess(link.getCounterAccess() + 1);
        save(link);
    }

    public String accessLink(String shortLink) {
        Long user = Long.parseLong(shortLink.substring(10, shortLink.length()));
        Link link = findLinkShortUrl(user, shortLink.substring(0, 10));

        addAcess(link);

        return link.getUrlOriginal();
    }

    public String shortener(String url, Long id) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] digest = md.digest(url.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append("%02x".formatted(b));
            }
            return sb.toString().substring(0, 10) + id.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
