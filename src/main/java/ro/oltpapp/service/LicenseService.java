package ro.oltpapp.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.oltpapp.service.dto.LicenseDTO;

/**
 * Service Interface for managing {@link ro.oltpapp.domain.License}.
 */
public interface LicenseService {
    /**
     * Save a license.
     *
     * @param licenseDTO the entity to save.
     * @return the persisted entity.
     */
    LicenseDTO save(LicenseDTO licenseDTO);

    /**
     * Updates a license.
     *
     * @param licenseDTO the entity to update.
     * @return the persisted entity.
     */
    LicenseDTO update(LicenseDTO licenseDTO);

    /**
     * Partially updates a license.
     *
     * @param licenseDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LicenseDTO> partialUpdate(LicenseDTO licenseDTO);

    /**
     * Get all the licenses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LicenseDTO> findAll(Pageable pageable);

    /**
     * Get the "id" license.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LicenseDTO> findOne(Long id);

    /**
     * Delete the "id" license.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
