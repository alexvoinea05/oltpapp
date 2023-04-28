package ro.oltpapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ro.oltpapp.repository.UserTypeRepository;
import ro.oltpapp.service.UserTypeService;
import ro.oltpapp.service.dto.UserTypeDTO;
import ro.oltpapp.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.oltpapp.domain.UserType}.
 */
@RestController
@RequestMapping("/api")
public class UserTypeResource {

    private final Logger log = LoggerFactory.getLogger(UserTypeResource.class);

    private static final String ENTITY_NAME = "userType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserTypeService userTypeService;

    private final UserTypeRepository userTypeRepository;

    public UserTypeResource(UserTypeService userTypeService, UserTypeRepository userTypeRepository) {
        this.userTypeService = userTypeService;
        this.userTypeRepository = userTypeRepository;
    }

    /**
     * {@code POST  /user-types} : Create a new userType.
     *
     * @param userTypeDTO the userTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userTypeDTO, or with status {@code 400 (Bad Request)} if the userType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-types")
    public ResponseEntity<UserTypeDTO> createUserType(@Valid @RequestBody UserTypeDTO userTypeDTO) throws URISyntaxException {
        log.debug("REST request to save UserType : {}", userTypeDTO);
        if (userTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new userType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserTypeDTO result = userTypeService.save(userTypeDTO);
        return ResponseEntity
            .created(new URI("/api/user-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-types/:id} : Updates an existing userType.
     *
     * @param id the id of the userTypeDTO to save.
     * @param userTypeDTO the userTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTypeDTO,
     * or with status {@code 400 (Bad Request)} if the userTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-types/{id}")
    public ResponseEntity<UserTypeDTO> updateUserType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody UserTypeDTO userTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update UserType : {}, {}", id, userTypeDTO);
        if (userTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        UserTypeDTO result = userTypeService.update(userTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /user-types/:id} : Partial updates given fields of an existing userType, field will ignore if it is null
     *
     * @param id the id of the userTypeDTO to save.
     * @param userTypeDTO the userTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userTypeDTO,
     * or with status {@code 400 (Bad Request)} if the userTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the userTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the userTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/user-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<UserTypeDTO> partialUpdateUserType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody UserTypeDTO userTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update UserType partially : {}, {}", id, userTypeDTO);
        if (userTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, userTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!userTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<UserTypeDTO> result = userTypeService.partialUpdate(userTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, userTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /user-types} : get all the userTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userTypes in body.
     */
    @GetMapping("/user-types")
    public ResponseEntity<List<UserTypeDTO>> getAllUserTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of UserTypes");
        Page<UserTypeDTO> page = userTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /user-types/:id} : get the "id" userType.
     *
     * @param id the id of the userTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-types/{id}")
    public ResponseEntity<UserTypeDTO> getUserType(@PathVariable Long id) {
        log.debug("REST request to get UserType : {}", id);
        Optional<UserTypeDTO> userTypeDTO = userTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userTypeDTO);
    }

    /**
     * {@code DELETE  /user-types/:id} : delete the "id" userType.
     *
     * @param id the id of the userTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-types/{id}")
    public ResponseEntity<Void> deleteUserType(@PathVariable Long id) {
        log.debug("REST request to delete UserType : {}", id);
        userTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
