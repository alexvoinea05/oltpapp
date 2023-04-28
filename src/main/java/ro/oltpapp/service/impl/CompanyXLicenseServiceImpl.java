package ro.oltpapp.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.oltpapp.domain.CompanyXLicense;
import ro.oltpapp.repository.CompanyXLicenseRepository;
import ro.oltpapp.service.CompanyXLicenseService;
import ro.oltpapp.service.dto.CompanyXLicenseDTO;
import ro.oltpapp.service.mapper.CompanyXLicenseMapper;

/**
 * Service Implementation for managing {@link CompanyXLicense}.
 */
@Service
@Transactional
public class CompanyXLicenseServiceImpl implements CompanyXLicenseService {

    private final Logger log = LoggerFactory.getLogger(CompanyXLicenseServiceImpl.class);

    private final CompanyXLicenseRepository companyXLicenseRepository;

    private final CompanyXLicenseMapper companyXLicenseMapper;

    public CompanyXLicenseServiceImpl(CompanyXLicenseRepository companyXLicenseRepository, CompanyXLicenseMapper companyXLicenseMapper) {
        this.companyXLicenseRepository = companyXLicenseRepository;
        this.companyXLicenseMapper = companyXLicenseMapper;
    }

    @Override
    public CompanyXLicenseDTO save(CompanyXLicenseDTO companyXLicenseDTO) {
        log.debug("Request to save CompanyXLicense : {}", companyXLicenseDTO);
        CompanyXLicense companyXLicense = companyXLicenseMapper.toEntity(companyXLicenseDTO);
        companyXLicense = companyXLicenseRepository.save(companyXLicense);
        return companyXLicenseMapper.toDto(companyXLicense);
    }

    @Override
    public CompanyXLicenseDTO update(CompanyXLicenseDTO companyXLicenseDTO) {
        log.debug("Request to update CompanyXLicense : {}", companyXLicenseDTO);
        CompanyXLicense companyXLicense = companyXLicenseMapper.toEntity(companyXLicenseDTO);
        companyXLicense = companyXLicenseRepository.save(companyXLicense);
        return companyXLicenseMapper.toDto(companyXLicense);
    }

    @Override
    public Optional<CompanyXLicenseDTO> partialUpdate(CompanyXLicenseDTO companyXLicenseDTO) {
        log.debug("Request to partially update CompanyXLicense : {}", companyXLicenseDTO);

        return companyXLicenseRepository
            .findById(companyXLicenseDTO.getId())
            .map(existingCompanyXLicense -> {
                companyXLicenseMapper.partialUpdate(existingCompanyXLicense, companyXLicenseDTO);

                return existingCompanyXLicense;
            })
            .map(companyXLicenseRepository::save)
            .map(companyXLicenseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompanyXLicenseDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CompanyXLicenses");
        return companyXLicenseRepository.findAll(pageable).map(companyXLicenseMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CompanyXLicenseDTO> findOne(Long id) {
        log.debug("Request to get CompanyXLicense : {}", id);
        return companyXLicenseRepository.findById(id).map(companyXLicenseMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CompanyXLicense : {}", id);
        companyXLicenseRepository.deleteById(id);
    }
}
