package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.FuelType;
import ro.oltpapp.domain.Train;
import ro.oltpapp.domain.TrainType;
import ro.oltpapp.service.dto.FuelTypeDTO;
import ro.oltpapp.service.dto.TrainDTO;
import ro.oltpapp.service.dto.TrainTypeDTO;

/**
 * Mapper for the entity {@link Train} and its DTO {@link TrainDTO}.
 */
@Mapper(componentModel = "spring")
public interface TrainMapper extends EntityMapper<TrainDTO, Train> {
    @Mapping(target = "idFuelType", source = "idFuelType", qualifiedByName = "fuelTypeId")
    @Mapping(target = "idTrainType", source = "idTrainType", qualifiedByName = "trainTypeId")
    TrainDTO toDto(Train s);

    @Named("fuelTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FuelTypeDTO toDtoFuelTypeId(FuelType fuelType);

    @Named("trainTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TrainTypeDTO toDtoTrainTypeId(TrainType trainType);
}
