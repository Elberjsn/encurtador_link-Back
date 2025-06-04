package org.elberjsn.encurtador_link.dto;

import java.time.LocalDate;

public record LinkDTO(String url, String urlShort, LocalDate expira, Boolean status) {
}