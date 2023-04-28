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
import ro.oltpapp.repository.TrainTypeRepository;
import ro.oltpapp.service.TrainTypeService;
import ro.oltpapp.service.dto.TrainTypeDTO;
import ro.oltpapp.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.oltpapp.domain.TrainType}.
 */
@RestController
@RequestMapping("/api")
public class TrainTypeResource {

    private final Logger log = LoggerFactory.getLogger(TrainTypeResource.class);

    private static final String ENTITY_NAME = "trainType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TrainTypeService trainTypeService;

    private final TrainTypeRepository trainTypeRepository;

    public TrainTypeResource(TrainTypeService trainTypeService, TrainTypeRepository trainTypeRepository) {
        this.trainTypeService = trainTypeService;
        this.trainTypeRepository = trainTypeRepository;
    }

    /**
     * {@code POST  /train-types} : Create a new trainType.
     *
     * @param trainTypeDTO the trainTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new trainTypeDTO, or with status {@code 400 (Bad Request)} if the trainType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/train-types")
    public ResponseEntity<TrainTypeDTO> createTrainType(@Valid @RequestBody TrainTypeDTO trainTypeDTO) throws URISyntaxException {
        log.debug("REST request to save TrainType : {}", trainTypeDTO);
        if (trainTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new trainType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TrainTypeDTO result = trainTypeService.save(trainTypeDTO);
        return ResponseEntity
            .created(new URI("/api/train-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /train-types/:id} : Updates an existing trainType.
     *
     * @param id the id of the trainTypeDTO to save.
     * @param trainTypeDTO the trainTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainTypeDTO,
     * or with status {@code 400 (Bad Request)} if the trainTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the trainTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/train-types/{id}")
    public ResponseEntity<TrainTypeDTO> updateTrainType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody TrainTypeDTO trainTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update TrainType : {}, {}", id, trainTypeDTO);
        if (trainTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        TrainTypeDTO result = trainTypeService.update(trainTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trainTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /train-types/:id} : Partial updates given fields of an existing trainType, field will ignore if it is null
     *
     * @param id the id of the trainTypeDTO to save.
     * @param trainTypeDTO the trainTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated trainTypeDTO,
     * or with status {@code 400 (Bad Request)} if the trainTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the trainTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the trainTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/train-types/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<TrainTypeDTO> partialUpdateTrainType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody TrainTypeDTO trainTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update TrainType partially : {}, {}", id, trainTypeDTO);
        if (trainTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, trainTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!trainTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<TrainTypeDTO> result = trainTypeService.partialUpdate(trainTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, trainTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /train-types} : get all the trainTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of trainTypes in body.
     */
    @GetMapping("/train-types")
    public ResponseEntity<List<TrainTypeDTO>> getAllTrainTypes(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of TrainTypes");
        Page<TrainTypeDTO> page = trainTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /train-types/:id} : get the "id" trainType.
     *
     * @param id the id of the trainTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the trainTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/train-types/{id}")
    public ResponseEntity<TrainTypeDTO> getTrainType(@PathVariable Long id) {
        log.debug("REST request to get TrainType : {}", id);
        Optional<TrainTypeDTO> trainTypeDTO = trainTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(trainTypeDTO);
    }

    /**
     * {@code DELETE  /train-types/:id} : delete the "id" trainType.
     *
     * @param id the id of the trainTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/train-types/{id}")
    public ResponseEntity<Void> deleteTrainType(@PathVariable Long id) {
        log.debug("REST request to delete TrainType : {}", id);
        trainTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
