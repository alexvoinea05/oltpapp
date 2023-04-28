package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.City;
import ro.oltpapp.domain.District;
import ro.oltpapp.service.dto.CityDTO;
import ro.oltpapp.service.dto.DistrictDTO;

/**
 * Mapper for the entity {@link City} and its DTO {@link CityDTO}.
 */
@Mapper(componentModel = "spring")
public interface CityMapper extends EntityMapper<CityDTO, City> {
    @Mapping(target = "idDistrict", source = "idDistrict", qualifiedByName = "districtId")
    CityDTO toDto(City s);

    @Named("districtId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DistrictDTO toDtoDistrictId(District district);
}
