package ro.oltpapp.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.oltpapp.domain.RailwayStation;
import ro.oltpapp.repository.RailwayStationRepository;
import ro.oltpapp.service.RailwayStationService;
import ro.oltpapp.service.dto.RailwayStationDTO;
import ro.oltpapp.service.mapper.RailwayStationMapper;

/**
 * Service Implementation for managing {@link RailwayStation}.
 */
@Service
@Transactional
public class RailwayStationServiceImpl implements RailwayStationService {

    private final Logger log = LoggerFactory.getLogger(RailwayStationServiceImpl.class);

    private final RailwayStationRepository railwayStationRepository;

    private final RailwayStationMapper railwayStationMapper;

    public RailwayStationServiceImpl(RailwayStationRepository railwayStationRepository, RailwayStationMapper railwayStationMapper) {
        this.railwayStationRepository = railwayStationRepository;
        this.railwayStationMapper = railwayStationMapper;
    }

    @Override
    public RailwayStationDTO save(RailwayStationDTO railwayStationDTO) {
        log.debug("Request to save RailwayStation : {}", railwayStationDTO);
        RailwayStation railwayStation = railwayStationMapper.toEntity(railwayStationDTO);
        railwayStation = railwayStationRepository.save(railwayStation);
        return railwayStationMapper.toDto(railwayStation);
    }

    @Override
    public RailwayStationDTO update(RailwayStationDTO railwayStationDTO) {
        log.debug("Request to update RailwayStation : {}", railwayStationDTO);
        RailwayStation railwayStation = railwayStationMapper.toEntity(railwayStationDTO);
        railwayStation = railwayStationRepository.save(railwayStation);
        return railwayStationMapper.toDto(railwayStation);
    }

    @Override
    public Optional<RailwayStationDTO> partialUpdate(RailwayStationDTO railwayStationDTO) {
        log.debug("Request to partially update RailwayStation : {}", railwayStationDTO);

        return railwayStationRepository
            .findById(railwayStationDTO.getId())
            .map(existingRailwayStation -> {
                railwayStationMapper.partialUpdate(existingRailwayStation, railwayStationDTO);

                return existingRailwayStation;
            })
            .map(railwayStationRepository::save)
            .map(railwayStationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RailwayStationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RailwayStations");
        return railwayStationRepository.findAll(pageable).map(railwayStationMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RailwayStationDTO> findOne(Long id) {
        log.debug("Request to get RailwayStation : {}", id);
        return railwayStationRepository.findById(id).map(railwayStationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RailwayStation : {}", id);
        railwayStationRepository.deleteById(id);
    }
}
