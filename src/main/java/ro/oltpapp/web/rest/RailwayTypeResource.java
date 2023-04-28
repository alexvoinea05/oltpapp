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
import ro.oltpapp.repository.RailwayTypeRepository;
import ro.oltpapp.service.RailwayTypeService;
import ro.oltpapp.service.dto.RailwayTypeDTO;
import ro.oltpapp.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.oltpapp.domain.RailwayType}.
 */
@RestController
@RequestMapping("/api")
public class RailwayTypeResource {

    private final Logger log = LoggerFactory.getLogger(RailwayTypeResource.class);

    private static final String ENTITY_NAME = "railwayType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RailwayTypeService railwayTypeService;

    private final RailwayTypeRepository railwayTypeRepository;

    public RailwayTypeResource(RailwayTypeService railwayTypeService, RailwayTypeRepository railwayTypeRepository) {
        this.railwayTypeService = railwayTypeService;
        this.railwayTypeRepository = railwayTypeRepository;
    }

    /**
     * {@code POST  /railway-types} : Create a new railwayType.
     *
     * @param railwayTypeDTO the railwayTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new railwayTypeDTO, or with status {@code 400 (Bad Request)} if the railwayType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/railway-types")
    public ResponseEntity<RailwayTypeDTO> createRailwayType(@Valid @RequestBody RailwayTypeDTO railwayTypeDTO) throws URISyntaxException {
        log.debug("REST request to save RailwayType : {}", railwayTypeDTO);
        if (railwayTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new railwayType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RailwayTypeDTO result = railwayTypeService.save(railwayTypeDTO);
        return ResponseEntity
            .created(new URI("/api/railway-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /railway-types/:id} : Updates an existing railwayType.
     *
     * @param id the id of the railwayTypeDTO to save.
     * @param railwayTypeDTO the railwayTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated railwayTypeDTO,
     * or with status {@code 400 (Bad Request)} if the railwayTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the railwayTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/railway-types/{id}")
    public ResponseEntity<RailwayTypeDTO> updateRailwayType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RailwayTypeDTO railwayTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RailwayType : {}, {}", id, railwayTypeDTO);
        if (railwayTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, railwayTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!railwayTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RailwayTypeDTO result = railwayTypeService.update(railwayTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, railwayTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /railway-types/:id} : Partial updates given fields of an existing railwayType, field will ignore if it is null
     *
     * @param id the id of the railwayTypeDTO to save.
     * @param railwayTypeDTO the railwayTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated railwayTypeDTO,
     * or with status {@code 400 (Bad Request)} if the railwayTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the railwayTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the railwayTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/railway-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RailwayTypeDTO> partialUpdateRailwayType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RailwayTypeDTO railwayTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RailwayType partially : {}, {}", id, railwayTypeDTO);
        if (railwayTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, railwayTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!railwayTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RailwayTypeDTO> result = railwayTypeService.partialUpdate(railwayTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, railwayTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /railway-types} : get all the railwayTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of railwayTypes in body.
     */
    @GetMapping("/railway-types")
    public ResponseEntity<List<RailwayTypeDTO>> getAllRailwayTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RailwayTypes");
        Page<RailwayTypeDTO> page = railwayTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /railway-types/:id} : get the "id" railwayType.
     *
     * @param id the id of the railwayTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the railwayTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/railway-types/{id}")
    public ResponseEntity<RailwayTypeDTO> getRailwayType(@PathVariable Long id) {
        log.debug("REST request to get RailwayType : {}", id);
        Optional<RailwayTypeDTO> railwayTypeDTO = railwayTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(railwayTypeDTO);
    }

    /**
     * {@code DELETE  /railway-types/:id} : delete the "id" railwayType.
     *
     * @param id the id of the railwayTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/railway-types/{id}")
    public ResponseEntity<Void> deleteRailwayType(@PathVariable Long id) {
        log.debug("REST request to delete RailwayType : {}", id);
        railwayTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
