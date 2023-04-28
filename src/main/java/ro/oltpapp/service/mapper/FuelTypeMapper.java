package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.FuelType;
import ro.oltpapp.service.dto.FuelTypeDTO;

/**
 * Mapper for the entity {@link FuelType} and its DTO {@link FuelTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface FuelTypeMapper extends EntityMapper<FuelTypeDTO, FuelType> {}
