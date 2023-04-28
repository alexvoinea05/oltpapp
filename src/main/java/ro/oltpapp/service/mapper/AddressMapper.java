package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.Address;
import ro.oltpapp.domain.City;
import ro.oltpapp.service.dto.AddressDTO;
import ro.oltpapp.service.dto.CityDTO;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {
    @Mapping(target = "idCity", source = "idCity", qualifiedByName = "cityId")
    AddressDTO toDto(Address s);

    @Named("cityId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CityDTO toDtoCityId(City city);
}
