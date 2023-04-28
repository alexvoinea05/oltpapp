package ro.oltpapp.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.oltpapp.domain.Train;
import ro.oltpapp.repository.TrainRepository;
import ro.oltpapp.service.TrainService;
import ro.oltpapp.service.dto.TrainDTO;
import ro.oltpapp.service.mapper.TrainMapper;

/**
 * Service Implementation for managing {@link Train}.
 */
@Service
@Transactional
public class TrainServiceImpl implements TrainService {

    private final Logger log = LoggerFactory.getLogger(TrainServiceImpl.class);

    private final TrainRepository trainRepository;

    private final TrainMapper trainMapper;

    public TrainServiceImpl(TrainRepository trainRepository, TrainMapper trainMapper) {
        this.trainRepository = trainRepository;
        this.trainMapper = trainMapper;
    }

    @Override
    public TrainDTO save(TrainDTO trainDTO) {
        log.debug("Request to save Train : {}", trainDTO);
        Train train = trainMapper.toEntity(trainDTO);
        train = trainRepository.save(train);
        return trainMapper.toDto(train);
    }

    @Override
    public TrainDTO update(TrainDTO trainDTO) {
        log.debug("Request to update Train : {}", trainDTO);
        Train train = trainMapper.toEntity(trainDTO);
        train = trainRepository.save(train);
        return trainMapper.toDto(train);
    }

    @Override
    public Optional<TrainDTO> partialUpdate(TrainDTO trainDTO) {
        log.debug("Request to partially update Train : {}", trainDTO);

        return trainRepository
            .findById(trainDTO.getId())
            .map(existingTrain -> {
                trainMapper.partialUpdate(existingTrain, trainDTO);

                return existingTrain;
            })
            .map(trainRepository::save)
            .map(trainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Trains");
        return trainRepository.findAll(pageable).map(trainMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainDTO> findOne(Long id) {
        log.debug("Request to get Train : {}", id);
        return trainRepository.findById(id).map(trainMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Train : {}", id);
        trainRepository.deleteById(id);
    }
}
