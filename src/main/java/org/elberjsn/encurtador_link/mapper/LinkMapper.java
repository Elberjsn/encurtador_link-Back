package org.elberjsn.encurtador_link.mapper;

import org.elberjsn.encurtador_link.dto.LinkDTO;
import org.elberjsn.encurtador_link.model.Link;

public class LinkMapper {
    public static LinkDTO toDTO(Link link) {
        if (link == null) {
            return null;
        }
        return new LinkDTO(link.getUrlOriginal(), link.getUrlShort(), link.getDateEnd(), link.getStatus());
    }
}
