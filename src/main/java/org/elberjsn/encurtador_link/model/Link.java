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

    public static Link fromDTO(LinkDTO linkDTO) {
        return new Link(
            linkDTO.url(),
            linkDTO.urlShort(),
            linkDTO.expira()
        );
    }
} 
