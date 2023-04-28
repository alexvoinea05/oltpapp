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
import ro.oltpapp.repository.JourneyStatusRepository;
import ro.oltpapp.service.JourneyStatusService;
import ro.oltpapp.service.dto.JourneyStatusDTO;
import ro.oltpapp.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.oltpapp.domain.JourneyStatus}.
 */
@RestController
@RequestMapping("/api")
public class JourneyStatusResource {

    private final Logger log = LoggerFactory.getLogger(JourneyStatusResource.class);

    private static final String ENTITY_NAME = "journeyStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JourneyStatusService journeyStatusService;

    private final JourneyStatusRepository journeyStatusRepository;

    public JourneyStatusResource(JourneyStatusService journeyStatusService, JourneyStatusRepository journeyStatusRepository) {
        this.journeyStatusService = journeyStatusService;
        this.journeyStatusRepository = journeyStatusRepository;
    }

    /**
     * {@code POST  /journey-statuses} : Create a new journeyStatus.
     *
     * @param journeyStatusDTO the journeyStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new journeyStatusDTO, or with status {@code 400 (Bad Request)} if the journeyStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/journey-statuses")
    public ResponseEntity<JourneyStatusDTO> createJourneyStatus(@Valid @RequestBody JourneyStatusDTO journeyStatusDTO)
        throws URISyntaxException {
        log.debug("REST request to save JourneyStatus : {}", journeyStatusDTO);
        if (journeyStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new journeyStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JourneyStatusDTO result = journeyStatusService.save(journeyStatusDTO);
        return ResponseEntity
            .created(new URI("/api/journey-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /journey-statuses/:id} : Updates an existing journeyStatus.
     *
     * @param id the id of the journeyStatusDTO to save.
     * @param journeyStatusDTO the journeyStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journeyStatusDTO,
     * or with status {@code 400 (Bad Request)} if the journeyStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the journeyStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/journey-statuses/{id}")
    public ResponseEntity<JourneyStatusDTO> updateJourneyStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody JourneyStatusDTO journeyStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update JourneyStatus : {}, {}", id, journeyStatusDTO);
        if (journeyStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, journeyStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!journeyStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JourneyStatusDTO result = journeyStatusService.update(journeyStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, journeyStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /journey-statuses/:id} : Partial updates given fields of an existing journeyStatus, field will ignore if it is null
     *
     * @param id the id of the journeyStatusDTO to save.
     * @param journeyStatusDTO the journeyStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated journeyStatusDTO,
     * or with status {@code 400 (Bad Request)} if the journeyStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the journeyStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the journeyStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/journey-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JourneyStatusDTO> partialUpdateJourneyStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody JourneyStatusDTO journeyStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update JourneyStatus partially : {}, {}", id, journeyStatusDTO);
        if (journeyStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, journeyStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!journeyStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JourneyStatusDTO> result = journeyStatusService.partialUpdate(journeyStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, journeyStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /journey-statuses} : get all the journeyStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of journeyStatuses in body.
     */
    @GetMapping("/journey-statuses")
    public ResponseEntity<List<JourneyStatusDTO>> getAllJourneyStatuses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of JourneyStatuses");
        Page<JourneyStatusDTO> page = journeyStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /journey-statuses/:id} : get the "id" journeyStatus.
     *
     * @param id the id of the journeyStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the journeyStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/journey-statuses/{id}")
    public ResponseEntity<JourneyStatusDTO> getJourneyStatus(@PathVariable Long id) {
        log.debug("REST request to get JourneyStatus : {}", id);
        Optional<JourneyStatusDTO> journeyStatusDTO = journeyStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(journeyStatusDTO);
    }

    /**
     * {@code DELETE  /journey-statuses/:id} : delete the "id" journeyStatus.
     *
     * @param id the id of the journeyStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/journey-statuses/{id}")
    public ResponseEntity<Void> deleteJourneyStatus(@PathVariable Long id) {
        log.debug("REST request to delete JourneyStatus : {}", id);
        journeyStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
