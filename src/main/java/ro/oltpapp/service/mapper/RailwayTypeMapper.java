package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.RailwayType;
import ro.oltpapp.service.dto.RailwayTypeDTO;

/**
 * Mapper for the entity {@link RailwayType} and its DTO {@link RailwayTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface RailwayTypeMapper extends EntityMapper<RailwayTypeDTO, RailwayType> {}
