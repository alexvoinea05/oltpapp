package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.AppUser;
import ro.oltpapp.domain.Journey;
import ro.oltpapp.domain.Ticket;
import ro.oltpapp.service.dto.AppUserDTO;
import ro.oltpapp.service.dto.JourneyDTO;
import ro.oltpapp.service.dto.TicketDTO;

/**
 * Mapper for the entity {@link Ticket} and its DTO {@link TicketDTO}.
 */
@Mapper(componentModel = "spring")
public interface TicketMapper extends EntityMapper<TicketDTO, Ticket> {
    @Mapping(target = "idAppUser", source = "idAppUser", qualifiedByName = "appUserIdUser")
    @Mapping(target = "idJourney", source = "idJourney", qualifiedByName = "journeyId")
    TicketDTO toDto(Ticket s);

    @Named("appUserIdUser")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idUser", source = "idUser")
    AppUserDTO toDtoAppUserIdUser(AppUser appUser);

    @Named("journeyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JourneyDTO toDtoJourneyId(Journey journey);
}
