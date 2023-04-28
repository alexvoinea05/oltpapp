package ro.oltpapp.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.oltpapp.service.dto.JourneyStatusDTO;

/**
 * Service Interface for managing {@link ro.oltpapp.domain.JourneyStatus}.
 */
public interface JourneyStatusService {
    /**
     * Save a journeyStatus.
     *
     * @param journeyStatusDTO the entity to save.
     * @return the persisted entity.
     */
    JourneyStatusDTO save(JourneyStatusDTO journeyStatusDTO);

    /**
     * Updates a journeyStatus.
     *
     * @param journeyStatusDTO the entity to update.
     * @return the persisted entity.
     */
    JourneyStatusDTO update(JourneyStatusDTO journeyStatusDTO);

    /**
     * Partially updates a journeyStatus.
     *
     * @param journeyStatusDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JourneyStatusDTO> partialUpdate(JourneyStatusDTO journeyStatusDTO);

    /**
     * Get all the journeyStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JourneyStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" journeyStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JourneyStatusDTO> findOne(Long id);

    /**
     * Delete the "id" journeyStatus.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
