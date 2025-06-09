package org.elberjsn.encurtador_link.model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import org.elberjsn.encurtador_link.dto.LinkDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "table_links")
public class Link implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    
    private String alias;
    @Column(nullable = false)
    @NonNull
    private String urlOriginal;
    @Column(nullable = false)
    @NonNull
    private String urlShort;

    @Column(nullable = false)
    @NonNull
    private LocalDate dateStart;

    private LocalDate dateEnd;

    @ManyToOne
    @JoinColumn(name = "user_link_id", nullable = false)
    private User userLink;

    private Boolean status;

    private int counterAccess;

    

    public Link(String alias, @NonNull String urlOriginal, @NonNull String urlShort, @NonNull LocalDate dateStart,
            LocalDate dateEnd, User userLink, Boolean status, int counterAccess) {
        this.alias = alias;
        this.urlOriginal = urlOriginal;
        this.urlShort = urlShort;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.userLink = userLink;
        this.status = status;
        this.counterAccess = counterAccess;
    }
    public Link(){
    }
    public Link(Long id, String urlOriginal, String urlShort, Boolean status, String alias, int counterAccess) {
        this.id = id;
        this.urlOriginal = urlOriginal;
        this.urlShort = urlShort;
        this.status = status;
        this.alias = alias;
        this.counterAccess = counterAccess;
    }

    public static Link fromDTO(LinkDTO linkDTO) {
       
        return new Link(
            linkDTO.id(),
            linkDTO.url(),
            linkDTO.urlShort(),
            linkDTO.status(),
            linkDTO.alias(),
            linkDTO.clicks()
        );
    }
} 
