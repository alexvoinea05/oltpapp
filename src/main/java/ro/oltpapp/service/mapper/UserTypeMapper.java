package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.UserType;
import ro.oltpapp.service.dto.UserTypeDTO;

/**
 * Mapper for the entity {@link UserType} and its DTO {@link UserTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserTypeMapper extends EntityMapper<UserTypeDTO, UserType> {}
