package ro.oltpapp.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.oltpapp.domain.JourneyStatus;
import ro.oltpapp.repository.JourneyStatusRepository;
import ro.oltpapp.service.JourneyStatusService;
import ro.oltpapp.service.dto.JourneyStatusDTO;
import ro.oltpapp.service.mapper.JourneyStatusMapper;

/**
 * Service Implementation for managing {@link JourneyStatus}.
 */
@Service
@Transactional
public class JourneyStatusServiceImpl implements JourneyStatusService {

    private final Logger log = LoggerFactory.getLogger(JourneyStatusServiceImpl.class);

    private final JourneyStatusRepository journeyStatusRepository;

    private final JourneyStatusMapper journeyStatusMapper;

    public JourneyStatusServiceImpl(JourneyStatusRepository journeyStatusRepository, JourneyStatusMapper journeyStatusMapper) {
        this.journeyStatusRepository = journeyStatusRepository;
        this.journeyStatusMapper = journeyStatusMapper;
    }

    @Override
    public JourneyStatusDTO save(JourneyStatusDTO journeyStatusDTO) {
        log.debug("Request to save JourneyStatus : {}", journeyStatusDTO);
        JourneyStatus journeyStatus = journeyStatusMapper.toEntity(journeyStatusDTO);
        journeyStatus = journeyStatusRepository.save(journeyStatus);
        return journeyStatusMapper.toDto(journeyStatus);
    }

    @Override
    public JourneyStatusDTO update(JourneyStatusDTO journeyStatusDTO) {
        log.debug("Request to update JourneyStatus : {}", journeyStatusDTO);
        JourneyStatus journeyStatus = journeyStatusMapper.toEntity(journeyStatusDTO);
        journeyStatus = journeyStatusRepository.save(journeyStatus);
        return journeyStatusMapper.toDto(journeyStatus);
    }

    @Override
    public Optional<JourneyStatusDTO> partialUpdate(JourneyStatusDTO journeyStatusDTO) {
        log.debug("Request to partially update JourneyStatus : {}", journeyStatusDTO);

        return journeyStatusRepository
            .findById(journeyStatusDTO.getId())
            .map(existingJourneyStatus -> {
                journeyStatusMapper.partialUpdate(existingJourneyStatus, journeyStatusDTO);

                return existingJourneyStatus;
            })
            .map(journeyStatusRepository::save)
            .map(journeyStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JourneyStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JourneyStatuses");
        return journeyStatusRepository.findAll(pageable).map(journeyStatusMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JourneyStatusDTO> findOne(Long id) {
        log.debug("Request to get JourneyStatus : {}", id);
        return journeyStatusRepository.findById(id).map(journeyStatusMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JourneyStatus : {}", id);
        journeyStatusRepository.deleteById(id);
    }
}
