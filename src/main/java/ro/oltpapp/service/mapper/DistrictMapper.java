package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.District;
import ro.oltpapp.service.dto.DistrictDTO;

/**
 * Mapper for the entity {@link District} and its DTO {@link DistrictDTO}.
 */
@Mapper(componentModel = "spring")
public interface DistrictMapper extends EntityMapper<DistrictDTO, District> {}
