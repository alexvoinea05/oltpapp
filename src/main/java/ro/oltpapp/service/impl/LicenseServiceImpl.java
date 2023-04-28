package ro.oltpapp.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.oltpapp.domain.License;
import ro.oltpapp.repository.LicenseRepository;
import ro.oltpapp.service.LicenseService;
import ro.oltpapp.service.dto.LicenseDTO;
import ro.oltpapp.service.mapper.LicenseMapper;

/**
 * Service Implementation for managing {@link License}.
 */
@Service
@Transactional
public class LicenseServiceImpl implements LicenseService {

    private final Logger log = LoggerFactory.getLogger(LicenseServiceImpl.class);

    private final LicenseRepository licenseRepository;

    private final LicenseMapper licenseMapper;

    public LicenseServiceImpl(LicenseRepository licenseRepository, LicenseMapper licenseMapper) {
        this.licenseRepository = licenseRepository;
        this.licenseMapper = licenseMapper;
    }

    @Override
    public LicenseDTO save(LicenseDTO licenseDTO) {
        log.debug("Request to save License : {}", licenseDTO);
        License license = licenseMapper.toEntity(licenseDTO);
        license = licenseRepository.save(license);
        return licenseMapper.toDto(license);
    }

    @Override
    public LicenseDTO update(LicenseDTO licenseDTO) {
        log.debug("Request to update License : {}", licenseDTO);
        License license = licenseMapper.toEntity(licenseDTO);
        license = licenseRepository.save(license);
        return licenseMapper.toDto(license);
    }

    @Override
    public Optional<LicenseDTO> partialUpdate(LicenseDTO licenseDTO) {
        log.debug("Request to partially update License : {}", licenseDTO);

        return licenseRepository
            .findById(licenseDTO.getId())
            .map(existingLicense -> {
                licenseMapper.partialUpdate(existingLicense, licenseDTO);

                return existingLicense;
            })
            .map(licenseRepository::save)
            .map(licenseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LicenseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Licenses");
        return licenseRepository.findAll(pageable).map(licenseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LicenseDTO> findOne(Long id) {
        log.debug("Request to get License : {}", id);
        return licenseRepository.findById(id).map(licenseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete License : {}", id);
        licenseRepository.deleteById(id);
    }
}
