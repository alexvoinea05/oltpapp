package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.AppUser;
import ro.oltpapp.domain.User;
import ro.oltpapp.domain.UserType;
import ro.oltpapp.service.dto.AppUserDTO;
import ro.oltpapp.service.dto.UserDTO;
import ro.oltpapp.service.dto.UserTypeDTO;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "idUserType", source = "idUserType", qualifiedByName = "userTypeId")
    AppUserDTO toDto(AppUser s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("userTypeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserTypeDTO toDtoUserTypeId(UserType userType);
}
