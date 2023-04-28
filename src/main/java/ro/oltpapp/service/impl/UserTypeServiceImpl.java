package ro.oltpapp.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.oltpapp.domain.UserType;
import ro.oltpapp.repository.UserTypeRepository;
import ro.oltpapp.service.UserTypeService;
import ro.oltpapp.service.dto.UserTypeDTO;
import ro.oltpapp.service.mapper.UserTypeMapper;

/**
 * Service Implementation for managing {@link UserType}.
 */
@Service
@Transactional
public class UserTypeServiceImpl implements UserTypeService {

    private final Logger log = LoggerFactory.getLogger(UserTypeServiceImpl.class);

    private final UserTypeRepository userTypeRepository;

    private final UserTypeMapper userTypeMapper;

    public UserTypeServiceImpl(UserTypeRepository userTypeRepository, UserTypeMapper userTypeMapper) {
        this.userTypeRepository = userTypeRepository;
        this.userTypeMapper = userTypeMapper;
    }

    @Override
    public UserTypeDTO save(UserTypeDTO userTypeDTO) {
        log.debug("Request to save UserType : {}", userTypeDTO);
        UserType userType = userTypeMapper.toEntity(userTypeDTO);
        userType = userTypeRepository.save(userType);
        return userTypeMapper.toDto(userType);
    }

    @Override
    public UserTypeDTO update(UserTypeDTO userTypeDTO) {
        log.debug("Request to update UserType : {}", userTypeDTO);
        UserType userType = userTypeMapper.toEntity(userTypeDTO);
        userType = userTypeRepository.save(userType);
        return userTypeMapper.toDto(userType);
    }

    @Override
    public Optional<UserTypeDTO> partialUpdate(UserTypeDTO userTypeDTO) {
        log.debug("Request to partially update UserType : {}", userTypeDTO);

        return userTypeRepository
            .findById(userTypeDTO.getId())
            .map(existingUserType -> {
                userTypeMapper.partialUpdate(existingUserType, userTypeDTO);

                return existingUserType;
            })
            .map(userTypeRepository::save)
            .map(userTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserTypes");
        return userTypeRepository.findAll(pageable).map(userTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserTypeDTO> findOne(Long id) {
        log.debug("Request to get UserType : {}", id);
        return userTypeRepository.findById(id).map(userTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserType : {}", id);
        userTypeRepository.deleteById(id);
    }
}
