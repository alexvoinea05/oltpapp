package ro.oltpapp.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.oltpapp.service.dto.TrainTypeDTO;

/**
 * Service Interface for managing {@link ro.oltpapp.domain.TrainType}.
 */
public interface TrainTypeService {
    /**
     * Save a trainType.
     *
     * @param trainTypeDTO the entity to save.
     * @return the persisted entity.
     */
    TrainTypeDTO save(TrainTypeDTO trainTypeDTO);

    /**
     * Updates a trainType.
     *
     * @param trainTypeDTO the entity to update.
     * @return the persisted entity.
     */
    TrainTypeDTO update(TrainTypeDTO trainTypeDTO);

    /**
     * Partially updates a trainType.
     *
     * @param trainTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TrainTypeDTO> partialUpdate(TrainTypeDTO trainTypeDTO);

    /**
     * Get all the trainTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TrainTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" trainType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TrainTypeDTO> findOne(Long id);

    /**
     * Delete the "id" trainType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
