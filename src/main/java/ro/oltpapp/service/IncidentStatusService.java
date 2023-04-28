package ro.oltpapp.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.oltpapp.service.dto.IncidentStatusDTO;

/**
 * Service Interface for managing {@link ro.oltpapp.domain.IncidentStatus}.
 */
public interface IncidentStatusService {
    /**
     * Save a incidentStatus.
     *
     * @param incidentStatusDTO the entity to save.
     * @return the persisted entity.
     */
    IncidentStatusDTO save(IncidentStatusDTO incidentStatusDTO);

    /**
     * Updates a incidentStatus.
     *
     * @param incidentStatusDTO the entity to update.
     * @return the persisted entity.
     */
    IncidentStatusDTO update(IncidentStatusDTO incidentStatusDTO);

    /**
     * Partially updates a incidentStatus.
     *
     * @param incidentStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IncidentStatusDTO> partialUpdate(IncidentStatusDTO incidentStatusDTO);

    /**
     * Get all the incidentStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IncidentStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" incidentStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IncidentStatusDTO> findOne(Long id);

    /**
     * Delete the "id" incidentStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
