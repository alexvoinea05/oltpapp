package ro.oltpapp.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.oltpapp.domain.RailwayType;
import ro.oltpapp.repository.RailwayTypeRepository;
import ro.oltpapp.service.RailwayTypeService;
import ro.oltpapp.service.dto.RailwayTypeDTO;
import ro.oltpapp.service.mapper.RailwayTypeMapper;

/**
 * Service Implementation for managing {@link RailwayType}.
 */
@Service
@Transactional
public class RailwayTypeServiceImpl implements RailwayTypeService {

    private final Logger log = LoggerFactory.getLogger(RailwayTypeServiceImpl.class);

    private final RailwayTypeRepository railwayTypeRepository;

    private final RailwayTypeMapper railwayTypeMapper;

    public RailwayTypeServiceImpl(RailwayTypeRepository railwayTypeRepository, RailwayTypeMapper railwayTypeMapper) {
        this.railwayTypeRepository = railwayTypeRepository;
        this.railwayTypeMapper = railwayTypeMapper;
    }

    @Override
    public RailwayTypeDTO save(RailwayTypeDTO railwayTypeDTO) {
        log.debug("Request to save RailwayType : {}", railwayTypeDTO);
        RailwayType railwayType = railwayTypeMapper.toEntity(railwayTypeDTO);
        railwayType = railwayTypeRepository.save(railwayType);
        return railwayTypeMapper.toDto(railwayType);
    }

    @Override
    public RailwayTypeDTO update(RailwayTypeDTO railwayTypeDTO) {
        log.debug("Request to update RailwayType : {}", railwayTypeDTO);
        RailwayType railwayType = railwayTypeMapper.toEntity(railwayTypeDTO);
        railwayType = railwayTypeRepository.save(railwayType);
        return railwayTypeMapper.toDto(railwayType);
    }

    @Override
    public Optional<RailwayTypeDTO> partialUpdate(RailwayTypeDTO railwayTypeDTO) {
        log.debug("Request to partially update RailwayType : {}", railwayTypeDTO);

        return railwayTypeRepository
            .findById(railwayTypeDTO.getId())
            .map(existingRailwayType -> {
                railwayTypeMapper.partialUpdate(existingRailwayType, railwayTypeDTO);

                return existingRailwayType;
            })
            .map(railwayTypeRepository::save)
            .map(railwayTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RailwayTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RailwayTypes");
        return railwayTypeRepository.findAll(pageable).map(railwayTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RailwayTypeDTO> findOne(Long id) {
        log.debug("Request to get RailwayType : {}", id);
        return railwayTypeRepository.findById(id).map(railwayTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RailwayType : {}", id);
        railwayTypeRepository.deleteById(id);
    }
}
