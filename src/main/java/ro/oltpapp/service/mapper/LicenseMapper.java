package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.License;
import ro.oltpapp.service.dto.LicenseDTO;

/**
 * Mapper for the entity {@link License} and its DTO {@link LicenseDTO}.
 */
@Mapper(componentModel = "spring")
public interface LicenseMapper extends EntityMapper<LicenseDTO, License> {}
