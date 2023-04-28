package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.IncidentStatus;
import ro.oltpapp.service.dto.IncidentStatusDTO;

/**
 * Mapper for the entity {@link IncidentStatus} and its DTO {@link IncidentStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface IncidentStatusMapper extends EntityMapper<IncidentStatusDTO, IncidentStatus> {}
