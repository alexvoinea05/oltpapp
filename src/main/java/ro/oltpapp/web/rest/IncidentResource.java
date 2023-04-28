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
import ro.oltpapp.repository.IncidentRepository;
import ro.oltpapp.service.IncidentService;
import ro.oltpapp.service.dto.IncidentDTO;
import ro.oltpapp.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.oltpapp.domain.Incident}.
 */
@RestController
@RequestMapping("/api")
public class IncidentResource {

    private final Logger log = LoggerFactory.getLogger(IncidentResource.class);

    private static final String ENTITY_NAME = "incident";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final IncidentService incidentService;

    private final IncidentRepository incidentRepository;

    public IncidentResource(IncidentService incidentService, IncidentRepository incidentRepository) {
        this.incidentService = incidentService;
        this.incidentRepository = incidentRepository;
    }

    /**
     * {@code POST  /incidents} : Create a new incident.
     *
     * @param incidentDTO the incidentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new incidentDTO, or with status {@code 400 (Bad Request)} if the incident has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/incidents")
    public ResponseEntity<IncidentDTO> createIncident(@Valid @RequestBody IncidentDTO incidentDTO) throws URISyntaxException {
        log.debug("REST request to save Incident : {}", incidentDTO);
        if (incidentDTO.getId() != null) {
            throw new BadRequestAlertException("A new incident cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IncidentDTO result = incidentService.save(incidentDTO);
        return ResponseEntity
            .created(new URI("/api/incidents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /incidents/:id} : Updates an existing incident.
     *
     * @param id the id of the incidentDTO to save.
     * @param incidentDTO the incidentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incidentDTO,
     * or with status {@code 400 (Bad Request)} if the incidentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the incidentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/incidents/{id}")
    public ResponseEntity<IncidentDTO> updateIncident(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody IncidentDTO incidentDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Incident : {}, {}", id, incidentDTO);
        if (incidentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, incidentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!incidentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        IncidentDTO result = incidentService.update(incidentDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, incidentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /incidents/:id} : Partial updates given fields of an existing incident, field will ignore if it is null
     *
     * @param id the id of the incidentDTO to save.
     * @param incidentDTO the incidentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated incidentDTO,
     * or with status {@code 400 (Bad Request)} if the incidentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the incidentDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the incidentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/incidents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<IncidentDTO> partialUpdateIncident(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody IncidentDTO incidentDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Incident partially : {}, {}", id, incidentDTO);
        if (incidentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, incidentDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!incidentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<IncidentDTO> result = incidentService.partialUpdate(incidentDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, incidentDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /incidents} : get all the incidents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of incidents in body.
     */
    @GetMapping("/incidents")
    public ResponseEntity<List<IncidentDTO>> getAllIncidents(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Incidents");
        Page<IncidentDTO> page = incidentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /incidents/:id} : get the "id" incident.
     *
     * @param id the id of the incidentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the incidentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/incidents/{id}")
    public ResponseEntity<IncidentDTO> getIncident(@PathVariable Long id) {
        log.debug("REST request to get Incident : {}", id);
        Optional<IncidentDTO> incidentDTO = incidentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(incidentDTO);
    }

    /**
     * {@code DELETE  /incidents/:id} : delete the "id" incident.
     *
     * @param id the id of the incidentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/incidents/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        log.debug("REST request to delete Incident : {}", id);
        incidentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
