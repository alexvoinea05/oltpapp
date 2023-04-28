package ro.oltpapp.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.oltpapp.service.dto.RailwayTypeDTO;

/**
 * Service Interface for managing {@link ro.oltpapp.domain.RailwayType}.
 */
public interface RailwayTypeService {
    /**
     * Save a railwayType.
     *
     * @param railwayTypeDTO the entity to save.
     * @return the persisted entity.
     */
    RailwayTypeDTO save(RailwayTypeDTO railwayTypeDTO);

    /**
     * Updates a railwayType.
     *
     * @param railwayTypeDTO the entity to update.
     * @return the persisted entity.
     */
    RailwayTypeDTO update(RailwayTypeDTO railwayTypeDTO);

    /**
     * Partially updates a railwayType.
     *
     * @param railwayTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RailwayTypeDTO> partialUpdate(RailwayTypeDTO railwayTypeDTO);

    /**
     * Get all the railwayTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RailwayTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" railwayType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RailwayTypeDTO> findOne(Long id);

    /**
     * Delete the "id" railwayType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
