package ro.oltpapp.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.oltpapp.service.dto.IncidentDTO;

/**
 * Service Interface for managing {@link ro.oltpapp.domain.Incident}.
 */
public interface IncidentService {
    /**
     * Save a incident.
     *
     * @param incidentDTO the entity to save.
     * @return the persisted entity.
     */
    IncidentDTO save(IncidentDTO incidentDTO);

    /**
     * Updates a incident.
     *
     * @param incidentDTO the entity to update.
     * @return the persisted entity.
     */
    IncidentDTO update(IncidentDTO incidentDTO);

    /**
     * Partially updates a incident.
     *
     * @param incidentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<IncidentDTO> partialUpdate(IncidentDTO incidentDTO);

    /**
     * Get all the incidents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<IncidentDTO> findAll(Pageable pageable);

    /**
     * Get the "id" incident.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<IncidentDTO> findOne(Long id);

    /**
     * Delete the "id" incident.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
