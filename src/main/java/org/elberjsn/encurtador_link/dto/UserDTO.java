package org.elberjsn.encurtador_link.dto;

import jakarta.validation.constraints.Email;
import lombok.NonNull;

public record UserDTO(

        Long id,

        @NonNull
        @Email String email,

        @NonNull
        String password,
        String name,
        String token) {
}
