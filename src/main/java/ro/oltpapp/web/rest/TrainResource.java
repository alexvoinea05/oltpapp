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
import ro.oltpapp.repository.TrainRepository;
import ro.oltpapp.service.TrainService;
import ro.oltpapp.service.dto.TrainDTO;
import ro.oltpapp.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.oltpapp.domain.Train}.
 */
@RestController
@RequestMapping("/api")
public class TrainResource {

    private final Logger log = LoggerFactory.getLogger(TrainResource.class);

    private static final String ENTITY_NAME = "train";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainService trainService;

    private final TrainRepository trainRepository;

    public TrainResource(TrainService trainService, TrainRepository trainRepository) {
        this.trainService = trainService;
        this.trainRepository = trainRepository;
    }

    /**
     * {@code POST  /trains} : Create a new train.
     *
     * @param trainDTO the trainDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainDTO, or with status {@code 400 (Bad Request)} if the train has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/trains")
    public ResponseEntity<TrainDTO> createTrain(@Valid @RequestBody TrainDTO trainDTO) throws URISyntaxException {
        log.debug("REST request to save Train : {}", trainDTO);
        if (trainDTO.getId() != null) {
            throw new BadRequestAlertException("A new train cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainDTO result = trainService.save(trainDTO);
        return ResponseEntity
            .created(new URI("/api/trains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /trains/:id} : Updates an existing train.
     *
     * @param id the id of the trainDTO to save.
     * @param trainDTO the trainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainDTO,
     * or with status {@code 400 (Bad Request)} if the trainDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/trains/{id}")
    public ResponseEntity<TrainDTO> updateTrain(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrainDTO trainDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Train : {}, {}", id, trainDTO);
        if (trainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrainDTO result = trainService.update(trainDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trainDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /trains/:id} : Partial updates given fields of an existing train, field will ignore if it is null
     *
     * @param id the id of the trainDTO to save.
     * @param trainDTO the trainDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainDTO,
     * or with status {@code 400 (Bad Request)} if the trainDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trainDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trainDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/trains/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrainDTO> partialUpdateTrain(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrainDTO trainDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Train partially : {}, {}", id, trainDTO);
        if (trainDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrainDTO> result = trainService.partialUpdate(trainDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trainDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /trains} : get all the trains.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trains in body.
     */
    @GetMapping("/trains")
    public ResponseEntity<List<TrainDTO>> getAllTrains(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Trains");
        Page<TrainDTO> page = trainService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /trains/:id} : get the "id" train.
     *
     * @param id the id of the trainDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/trains/{id}")
    public ResponseEntity<TrainDTO> getTrain(@PathVariable Long id) {
        log.debug("REST request to get Train : {}", id);
        Optional<TrainDTO> trainDTO = trainService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainDTO);
    }

    /**
     * {@code DELETE  /trains/:id} : delete the "id" train.
     *
     * @param id the id of the trainDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/trains/{id}")
    public ResponseEntity<Void> deleteTrain(@PathVariable Long id) {
        log.debug("REST request to delete Train : {}", id);
        trainService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
