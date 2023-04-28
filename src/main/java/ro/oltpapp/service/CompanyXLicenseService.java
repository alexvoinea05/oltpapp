package ro.oltpapp.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.oltpapp.service.dto.CompanyXLicenseDTO;

/**
 * Service Interface for managing {@link ro.oltpapp.domain.CompanyXLicense}.
 */
public interface CompanyXLicenseService {
    /**
     * Save a companyXLicense.
     *
     * @param companyXLicenseDTO the entity to save.
     * @return the persisted entity.
     */
    CompanyXLicenseDTO save(CompanyXLicenseDTO companyXLicenseDTO);

    /**
     * Updates a companyXLicense.
     *
     * @param companyXLicenseDTO the entity to update.
     * @return the persisted entity.
     */
    CompanyXLicenseDTO update(CompanyXLicenseDTO companyXLicenseDTO);

    /**
     * Partially updates a companyXLicense.
     *
     * @param companyXLicenseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CompanyXLicenseDTO> partialUpdate(CompanyXLicenseDTO companyXLicenseDTO);

    /**
     * Get all the companyXLicenses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CompanyXLicenseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" companyXLicense.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CompanyXLicenseDTO> findOne(Long id);

    /**
     * Delete the "id" companyXLicense.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
