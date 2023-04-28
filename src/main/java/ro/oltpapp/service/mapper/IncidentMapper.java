package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.AppUser;
import ro.oltpapp.domain.Incident;
import ro.oltpapp.domain.IncidentStatus;
import ro.oltpapp.domain.Journey;
import ro.oltpapp.service.dto.AppUserDTO;
import ro.oltpapp.service.dto.IncidentDTO;
import ro.oltpapp.service.dto.IncidentStatusDTO;
import ro.oltpapp.service.dto.JourneyDTO;

/**
 * Mapper for the entity {@link Incident} and its DTO {@link IncidentDTO}.
 */
@Mapper(componentModel = "spring")
public interface IncidentMapper extends EntityMapper<IncidentDTO, Incident> {
    @Mapping(target = "idIncidentStatus", source = "idIncidentStatus", qualifiedByName = "incidentStatusId")
    @Mapping(target = "idAppUser", source = "idAppUser", qualifiedByName = "appUserIdUser")
    @Mapping(target = "idJourney", source = "idJourney", qualifiedByName = "journeyId")
    IncidentDTO toDto(Incident s);

    @Named("incidentStatusId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    IncidentStatusDTO toDtoIncidentStatusId(IncidentStatus incidentStatus);

    @Named("appUserIdUser")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "idUser", source = "idUser")
    AppUserDTO toDtoAppUserIdUser(AppUser appUser);

    @Named("journeyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    JourneyDTO toDtoJourneyId(Journey journey);
}
