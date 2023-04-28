package ro.oltpapp.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.oltpapp.domain.FuelType;
import ro.oltpapp.repository.FuelTypeRepository;
import ro.oltpapp.service.FuelTypeService;
import ro.oltpapp.service.dto.FuelTypeDTO;
import ro.oltpapp.service.mapper.FuelTypeMapper;

/**
 * Service Implementation for managing {@link FuelType}.
 */
@Service
@Transactional
public class FuelTypeServiceImpl implements FuelTypeService {

    private final Logger log = LoggerFactory.getLogger(FuelTypeServiceImpl.class);

    private final FuelTypeRepository fuelTypeRepository;

    private final FuelTypeMapper fuelTypeMapper;

    public FuelTypeServiceImpl(FuelTypeRepository fuelTypeRepository, FuelTypeMapper fuelTypeMapper) {
        this.fuelTypeRepository = fuelTypeRepository;
        this.fuelTypeMapper = fuelTypeMapper;
    }

    @Override
    public FuelTypeDTO save(FuelTypeDTO fuelTypeDTO) {
        log.debug("Request to save FuelType : {}", fuelTypeDTO);
        FuelType fuelType = fuelTypeMapper.toEntity(fuelTypeDTO);
        fuelType = fuelTypeRepository.save(fuelType);
        return fuelTypeMapper.toDto(fuelType);
    }

    @Override
    public FuelTypeDTO update(FuelTypeDTO fuelTypeDTO) {
        log.debug("Request to update FuelType : {}", fuelTypeDTO);
        FuelType fuelType = fuelTypeMapper.toEntity(fuelTypeDTO);
        fuelType = fuelTypeRepository.save(fuelType);
        return fuelTypeMapper.toDto(fuelType);
    }

    @Override
    public Optional<FuelTypeDTO> partialUpdate(FuelTypeDTO fuelTypeDTO) {
        log.debug("Request to partially update FuelType : {}", fuelTypeDTO);

        return fuelTypeRepository
            .findById(fuelTypeDTO.getId())
            .map(existingFuelType -> {
                fuelTypeMapper.partialUpdate(existingFuelType, fuelTypeDTO);

                return existingFuelType;
            })
            .map(fuelTypeRepository::save)
            .map(fuelTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FuelTypeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FuelTypes");
        return fuelTypeRepository.findAll(pageable).map(fuelTypeMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FuelTypeDTO> findOne(Long id) {
        log.debug("Request to get FuelType : {}", id);
        return fuelTypeRepository.findById(id).map(fuelTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FuelType : {}", id);
        fuelTypeRepository.deleteById(id);
    }
}
