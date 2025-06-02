package org.elberjsn.encurtador_link.dto;

import jakarta.validation.constraints.Email;

public record UserDTO(

        Long id,

        @Email String email,

        String pwd,
        String name) {
}
