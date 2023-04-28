package ro.oltpapp.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.oltpapp.domain.TrainType;
import ro.oltpapp.repository.TrainTypeRepository;
import ro.oltpapp.service.TrainTypeService;
import ro.oltpapp.service.dto.TrainTypeDTO;
import ro.oltpapp.service.mapper.TrainTypeMapper;

/**
 * Service Implementation for managing {@link TrainType}.
 */
@Service
@Transactional
public class TrainTypeServiceImpl implements TrainTypeService {

    private final Logger log = LoggerFactory.getLogger(TrainTypeServiceImpl.class);

    private final TrainTypeRepository trainTypeRepository;

    private final TrainTypeMapper trainTypeMapper;

    public TrainTypeServiceImpl(TrainTypeRepository trainTypeRepository, TrainTypeMapper trainTypeMapper) {
        this.trainTypeRepository = trainTypeRepository;
        this.trainTypeMapper = trainTypeMapper;
    }

    @Override
    public TrainTypeDTO save(TrainTypeDTO trainTypeDTO) {
        log.debug("Request to save TrainType : {}", trainTypeDTO);
        TrainType trainType = trainTypeMapper.toEntity(trainTypeDTO);
        trainType = trainTypeRepository.save(trainType);
        return trainTypeMapper.toDto(trainType);
    }

    @Override
    public TrainTypeDTO update(TrainTypeDTO trainTypeDTO) {
        log.debug("Request to update TrainType : {}", trainTypeDTO);
        TrainType trainType = trainTypeMapper.toEntity(trainTypeDTO);
        trainType = trainTypeRepository.save(trainType);
        return trainTypeMapper.toDto(trainType);
    }

    @Override
    public Optional<TrainTypeDTO> partialUpdate(TrainTypeDTO trainTypeDTO) {
        log.debug("Request to partially update TrainType : {}", trainTypeDTO);

        return trainTypeRepository
            .findById(trainTypeDTO.getId())
            .map(existingTrainType -> {
                trainTypeMapper.partialUpdate(existingTrainType, trainTypeDTO);

                return existingTrainType;
            })
            .map(trainTypeRepository::save)
            .map(trainTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TrainTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TrainTypes");
        return trainTypeRepository.findAll(pageable).map(trainTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TrainTypeDTO> findOne(Long id) {
        log.debug("Request to get TrainType : {}", id);
        return trainTypeRepository.findById(id).map(trainTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TrainType : {}", id);
        trainTypeRepository.deleteById(id);
    }
}
