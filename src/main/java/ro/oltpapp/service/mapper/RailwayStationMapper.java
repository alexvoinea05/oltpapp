package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.Address;
import ro.oltpapp.domain.RailwayStation;
import ro.oltpapp.domain.RailwayType;
import ro.oltpapp.service.dto.AddressDTO;
import ro.oltpapp.service.dto.RailwayStationDTO;
import ro.oltpapp.service.dto.RailwayTypeDTO;

/**
 * Mapper for the entity {@link RailwayStation} and its DTO {@link RailwayStationDTO}.
 */
@Mapper(componentModel = "spring")
public interface RailwayStationMapper extends EntityMapper<RailwayStationDTO, RailwayStation> {
    @Mapping(target = "idRailwayType", source = "idRailwayType", qualifiedByName = "railwayTypeId")
    @Mapping(target = "idAddress", source = "idAddress", qualifiedByName = "addressId")
    RailwayStationDTO toDto(RailwayStation s);

    @Named("railwayTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    RailwayTypeDTO toDtoRailwayTypeId(RailwayType railwayType);

    @Named("addressId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AddressDTO toDtoAddressId(Address address);
}
