package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.TrainType;
import ro.oltpapp.service.dto.TrainTypeDTO;

/**
 * Mapper for the entity {@link TrainType} and its DTO {@link TrainTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainTypeMapper extends EntityMapper<TrainTypeDTO, TrainType> {}
