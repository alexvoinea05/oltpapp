package ro.oltpapp.service;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.oltpapp.service.dto.UserTypeDTO;

/**
 * Service Interface for managing {@link ro.oltpapp.domain.UserType}.
 */
public interface UserTypeService {
    /**
     * Save a userType.
     *
     * @param userTypeDTO the entity to save.
     * @return the persisted entity.
     */
    UserTypeDTO save(UserTypeDTO userTypeDTO);

    /**
     * Updates a userType.
     *
     * @param userTypeDTO the entity to update.
     * @return the persisted entity.
     */
    UserTypeDTO update(UserTypeDTO userTypeDTO);

    /**
     * Partially updates a userType.
     *
     * @param userTypeDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserTypeDTO> partialUpdate(UserTypeDTO userTypeDTO);

    /**
     * Get all the userTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserTypeDTO> findAll(Pageable pageable);

    /**
     * Get the "id" userType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserTypeDTO> findOne(Long id);

    /**
     * Delete the "id" userType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
