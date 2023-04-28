package ro.oltpapp.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.oltpapp.service.dto.RailwayStationDTO;

/**
 * Service Interface for managing {@link ro.oltpapp.domain.RailwayStation}.
 */
public interface RailwayStationService {
    /**
     * Save a railwayStation.
     *
     * @param railwayStationDTO the entity to save.
     * @return the persisted entity.
     */
    RailwayStationDTO save(RailwayStationDTO railwayStationDTO);

    /**
     * Updates a railwayStation.
     *
     * @param railwayStationDTO the entity to update.
     * @return the persisted entity.
     */
    RailwayStationDTO update(RailwayStationDTO railwayStationDTO);

    /**
     * Partially updates a railwayStation.
     *
     * @param railwayStationDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RailwayStationDTO> partialUpdate(RailwayStationDTO railwayStationDTO);

    /**
     * Get all the railwayStations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<RailwayStationDTO> findAll(Pageable pageable);

    /**
     * Get the "id" railwayStation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RailwayStationDTO> findOne(Long id);

    /**
     * Delete the "id" railwayStation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
