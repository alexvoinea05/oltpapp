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
import ro.oltpapp.repository.IncidentStatusRepository;
import ro.oltpapp.service.IncidentStatusService;
import ro.oltpapp.service.dto.IncidentStatusDTO;
import ro.oltpapp.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.oltpapp.domain.IncidentStatus}.
 */
@RestController
@RequestMapping("/api")
public class IncidentStatusResource {

    private final Logger log = LoggerFactory.getLogger(IncidentStatusResource.class);

    private static final String ENTITY_NAME = "incidentStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncidentStatusService incidentStatusService;

    private final IncidentStatusRepository incidentStatusRepository;

    public IncidentStatusResource(IncidentStatusService incidentStatusService, IncidentStatusRepository incidentStatusRepository) {
        this.incidentStatusService = incidentStatusService;
        this.incidentStatusRepository = incidentStatusRepository;
    }

    /**
     * {@code POST  /incident-statuses} : Create a new incidentStatus.
     *
     * @param incidentStatusDTO the incidentStatusDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incidentStatusDTO, or with status {@code 400 (Bad Request)} if the incidentStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incident-statuses")
    public ResponseEntity<IncidentStatusDTO> createIncidentStatus(@Valid @RequestBody IncidentStatusDTO incidentStatusDTO)
        throws URISyntaxException {
        log.debug("REST request to save IncidentStatus : {}", incidentStatusDTO);
        if (incidentStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new incidentStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncidentStatusDTO result = incidentStatusService.save(incidentStatusDTO);
        return ResponseEntity
            .created(new URI("/api/incident-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incident-statuses/:id} : Updates an existing incidentStatus.
     *
     * @param id the id of the incidentStatusDTO to save.
     * @param incidentStatusDTO the incidentStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incidentStatusDTO,
     * or with status {@code 400 (Bad Request)} if the incidentStatusDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incidentStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incident-statuses/{id}")
    public ResponseEntity<IncidentStatusDTO> updateIncidentStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IncidentStatusDTO incidentStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to update IncidentStatus : {}, {}", id, incidentStatusDTO);
        if (incidentStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, incidentStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!incidentStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IncidentStatusDTO result = incidentStatusService.update(incidentStatusDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, incidentStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /incident-statuses/:id} : Partial updates given fields of an existing incidentStatus, field will ignore if it is null
     *
     * @param id the id of the incidentStatusDTO to save.
     * @param incidentStatusDTO the incidentStatusDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incidentStatusDTO,
     * or with status {@code 400 (Bad Request)} if the incidentStatusDTO is not valid,
     * or with status {@code 404 (Not Found)} if the incidentStatusDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the incidentStatusDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/incident-statuses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IncidentStatusDTO> partialUpdateIncidentStatus(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IncidentStatusDTO incidentStatusDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update IncidentStatus partially : {}, {}", id, incidentStatusDTO);
        if (incidentStatusDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, incidentStatusDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!incidentStatusRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IncidentStatusDTO> result = incidentStatusService.partialUpdate(incidentStatusDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, incidentStatusDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /incident-statuses} : get all the incidentStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incidentStatuses in body.
     */
    @GetMapping("/incident-statuses")
    public ResponseEntity<List<IncidentStatusDTO>> getAllIncidentStatuses(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of IncidentStatuses");
        Page<IncidentStatusDTO> page = incidentStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /incident-statuses/:id} : get the "id" incidentStatus.
     *
     * @param id the id of the incidentStatusDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incidentStatusDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incident-statuses/{id}")
    public ResponseEntity<IncidentStatusDTO> getIncidentStatus(@PathVariable Long id) {
        log.debug("REST request to get IncidentStatus : {}", id);
        Optional<IncidentStatusDTO> incidentStatusDTO = incidentStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incidentStatusDTO);
    }

    /**
     * {@code DELETE  /incident-statuses/:id} : delete the "id" incidentStatus.
     *
     * @param id the id of the incidentStatusDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incident-statuses/{id}")
    public ResponseEntity<Void> deleteIncidentStatus(@PathVariable Long id) {
        log.debug("REST request to delete IncidentStatus : {}", id);
        incidentStatusService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
