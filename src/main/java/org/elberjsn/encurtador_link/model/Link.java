package org.elberjsn.encurtador_link.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NonNull;

@Data
@Entity
public class Link {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
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
} 
