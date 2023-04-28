package ro.oltpapp.service.mapper;

import org.mapstruct.*;
import ro.oltpapp.domain.Company;
import ro.oltpapp.service.dto.CompanyDTO;

/**
 * Mapper for the entity {@link Company} and its DTO {@link CompanyDTO}.
 */
@Mapper(componentModel = "spring")
public interface CompanyMapper extends EntityMapper<CompanyDTO, Company> {}
