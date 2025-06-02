package org.elberjsn.encurtador_link.mapper;

import org.elberjsn.encurtador_link.dto.UserDTO;
import org.elberjsn.encurtador_link.model.User;

public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }
        return new UserDTO(user.getId(), user.getEmail(), user.getPassword(), user.getPassword());
    }
}
