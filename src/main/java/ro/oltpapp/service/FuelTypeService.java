package ro.oltpapp.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.oltpapp.service.dto.FuelTypeDTO;

/**
 * Service Interface for managing {@link ro.oltpapp.domain.FuelType}.
 */
public interface FuelTypeService {
    /**
     * Save a fuelType.
     *
     * @param fuelTypeDTO the entity to save.
     * @return the persisted entity.
     */
    FuelTypeDTO save(FuelTypeDTO fuelTypeDTO);

    /**
     * Updates a fuelType.
     *
     * @param fuelTypeDTO the entity to update.
     * @return the persisted entity.
     */
    FuelTypeDTO update(FuelTypeDTO fuelTypeDTO);

    /**
     * Partially updates a fuelType.
     *
     * @param fuelTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FuelTypeDTO> partialUpdate(FuelTypeDTO fuelTypeDTO);

    /**
     * Get all the fuelTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FuelTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fuelType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FuelTypeDTO> findOne(Long id);

    /**
     * Delete the "id" fuelType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
