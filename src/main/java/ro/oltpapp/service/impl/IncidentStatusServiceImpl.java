package ro.oltpapp.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.oltpapp.domain.IncidentStatus;
import ro.oltpapp.repository.IncidentStatusRepository;
import ro.oltpapp.service.IncidentStatusService;
import ro.oltpapp.service.dto.IncidentStatusDTO;
import ro.oltpapp.service.mapper.IncidentStatusMapper;

/**
 * Service Implementation for managing {@link IncidentStatus}.
 */
@Service
@Transactional
public class IncidentStatusServiceImpl implements IncidentStatusService {

    private final Logger log = LoggerFactory.getLogger(IncidentStatusServiceImpl.class);

    private final IncidentStatusRepository incidentStatusRepository;

    private final IncidentStatusMapper incidentStatusMapper;

    public IncidentStatusServiceImpl(IncidentStatusRepository incidentStatusRepository, IncidentStatusMapper incidentStatusMapper) {
        this.incidentStatusRepository = incidentStatusRepository;
        this.incidentStatusMapper = incidentStatusMapper;
    }

    @Override
    public IncidentStatusDTO save(IncidentStatusDTO incidentStatusDTO) {
        log.debug("Request to save IncidentStatus : {}", incidentStatusDTO);
        IncidentStatus incidentStatus = incidentStatusMapper.toEntity(incidentStatusDTO);
        incidentStatus = incidentStatusRepository.save(incidentStatus);
        return incidentStatusMapper.toDto(incidentStatus);
    }

    @Override
    public IncidentStatusDTO update(IncidentStatusDTO incidentStatusDTO) {
        log.debug("Request to update IncidentStatus : {}", incidentStatusDTO);
        IncidentStatus incidentStatus = incidentStatusMapper.toEntity(incidentStatusDTO);
        incidentStatus = incidentStatusRepository.save(incidentStatus);
        return incidentStatusMapper.toDto(incidentStatus);
    }

    @Override
    public Optional<IncidentStatusDTO> partialUpdate(IncidentStatusDTO incidentStatusDTO) {
        log.debug("Request to partially update IncidentStatus : {}", incidentStatusDTO);

        return incidentStatusRepository
            .findById(incidentStatusDTO.getId())
            .map(existingIncidentStatus -> {
                incidentStatusMapper.partialUpdate(existingIncidentStatus, incidentStatusDTO);

                return existingIncidentStatus;
            })
            .map(incidentStatusRepository::save)
            .map(incidentStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IncidentStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all IncidentStatuses");
        return incidentStatusRepository.findAll(pageable).map(incidentStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IncidentStatusDTO> findOne(Long id) {
        log.debug("Request to get IncidentStatus : {}", id);
        return incidentStatusRepository.findById(id).map(incidentStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete IncidentStatus : {}", id);
        incidentStatusRepository.deleteById(id);
    }
}
