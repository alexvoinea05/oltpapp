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
import ro.oltpapp.repository.RailwayStationRepository;
import ro.oltpapp.service.RailwayStationService;
import ro.oltpapp.service.dto.RailwayStationDTO;
import ro.oltpapp.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.oltpapp.domain.RailwayStation}.
 */
@RestController
@RequestMapping("/api")
public class RailwayStationResource {

    private final Logger log = LoggerFactory.getLogger(RailwayStationResource.class);

    private static final String ENTITY_NAME = "railwayStation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RailwayStationService railwayStationService;

    private final RailwayStationRepository railwayStationRepository;

    public RailwayStationResource(RailwayStationService railwayStationService, RailwayStationRepository railwayStationRepository) {
        this.railwayStationService = railwayStationService;
        this.railwayStationRepository = railwayStationRepository;
    }

    /**
     * {@code POST  /railway-stations} : Create a new railwayStation.
     *
     * @param railwayStationDTO the railwayStationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new railwayStationDTO, or with status {@code 400 (Bad Request)} if the railwayStation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/railway-stations")
    public ResponseEntity<RailwayStationDTO> createRailwayStation(@Valid @RequestBody RailwayStationDTO railwayStationDTO)
        throws URISyntaxException {
        log.debug("REST request to save RailwayStation : {}", railwayStationDTO);
        if (railwayStationDTO.getId() != null) {
            throw new BadRequestAlertException("A new railwayStation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RailwayStationDTO result = railwayStationService.save(railwayStationDTO);
        return ResponseEntity
            .created(new URI("/api/railway-stations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /railway-stations/:id} : Updates an existing railwayStation.
     *
     * @param id the id of the railwayStationDTO to save.
     * @param railwayStationDTO the railwayStationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated railwayStationDTO,
     * or with status {@code 400 (Bad Request)} if the railwayStationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the railwayStationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/railway-stations/{id}")
    public ResponseEntity<RailwayStationDTO> updateRailwayStation(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody RailwayStationDTO railwayStationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update RailwayStation : {}, {}", id, railwayStationDTO);
        if (railwayStationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, railwayStationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!railwayStationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RailwayStationDTO result = railwayStationService.update(railwayStationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, railwayStationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /railway-stations/:id} : Partial updates given fields of an existing railwayStation, field will ignore if it is null
     *
     * @param id the id of the railwayStationDTO to save.
     * @param railwayStationDTO the railwayStationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated railwayStationDTO,
     * or with status {@code 400 (Bad Request)} if the railwayStationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the railwayStationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the railwayStationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/railway-stations/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<RailwayStationDTO> partialUpdateRailwayStation(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody RailwayStationDTO railwayStationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update RailwayStation partially : {}, {}", id, railwayStationDTO);
        if (railwayStationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, railwayStationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!railwayStationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RailwayStationDTO> result = railwayStationService.partialUpdate(railwayStationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, railwayStationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /railway-stations} : get all the railwayStations.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of railwayStations in body.
     */
    @GetMapping("/railway-stations")
    public ResponseEntity<List<RailwayStationDTO>> getAllRailwayStations(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of RailwayStations");
        Page<RailwayStationDTO> page = railwayStationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /railway-stations/:id} : get the "id" railwayStation.
     *
     * @param id the id of the railwayStationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the railwayStationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/railway-stations/{id}")
    public ResponseEntity<RailwayStationDTO> getRailwayStation(@PathVariable Long id) {
        log.debug("REST request to get RailwayStation : {}", id);
        Optional<RailwayStationDTO> railwayStationDTO = railwayStationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(railwayStationDTO);
    }

    /**
     * {@code DELETE  /railway-stations/:id} : delete the "id" railwayStation.
     *
     * @param id the id of the railwayStationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/railway-stations/{id}")
    public ResponseEntity<Void> deleteRailwayStation(@PathVariable Long id) {
        log.debug("REST request to delete RailwayStation : {}", id);
        railwayStationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
