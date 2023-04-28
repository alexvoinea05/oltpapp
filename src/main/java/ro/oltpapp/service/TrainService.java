package ro.oltpapp.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.oltpapp.service.dto.TrainDTO;

/**
 * Service Interface for managing {@link ro.oltpapp.domain.Train}.
 */
public interface TrainService {
    /**
     * Save a train.
     *
     * @param trainDTO the entity to save.
     * @return the persisted entity.
     */
    TrainDTO save(TrainDTO trainDTO);

    /**
     * Updates a train.
     *
     * @param trainDTO the entity to update.
     * @return the persisted entity.
     */
    TrainDTO update(TrainDTO trainDTO);

    /**
     * Partially updates a train.
     *
     * @param trainDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrainDTO> partialUpdate(TrainDTO trainDTO);

    /**
     * Get all the trains.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainDTO> findAll(Pageable pageable);

    /**
     * Get the "id" train.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainDTO> findOne(Long id);

    /**
     * Delete the "id" train.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
