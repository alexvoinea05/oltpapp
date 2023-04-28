package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.JourneyStatus;
import ro.oltpapp.service.dto.JourneyStatusDTO;

/**
 * Mapper for the entity {@link JourneyStatus} and its DTO {@link JourneyStatusDTO}.
 */
@Mapper(componentModel = "spring")
public interface JourneyStatusMapper extends EntityMapper<JourneyStatusDTO, JourneyStatus> {}
