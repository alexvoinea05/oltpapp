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
import ro.oltpapp.repository.LicenseRepository;
import ro.oltpapp.service.LicenseService;
import ro.oltpapp.service.dto.LicenseDTO;
import ro.oltpapp.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.oltpapp.domain.License}.
 */
@RestController
@RequestMapping("/api")
public class LicenseResource {

    private final Logger log = LoggerFactory.getLogger(LicenseResource.class);

    private static final String ENTITY_NAME = "license";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LicenseService licenseService;

    private final LicenseRepository licenseRepository;

    public LicenseResource(LicenseService licenseService, LicenseRepository licenseRepository) {
        this.licenseService = licenseService;
        this.licenseRepository = licenseRepository;
    }

    /**
     * {@code POST  /licenses} : Create a new license.
     *
     * @param licenseDTO the licenseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new licenseDTO, or with status {@code 400 (Bad Request)} if the license has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/licenses")
    public ResponseEntity<LicenseDTO> createLicense(@Valid @RequestBody LicenseDTO licenseDTO) throws URISyntaxException {
        log.debug("REST request to save License : {}", licenseDTO);
        if (licenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new license cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LicenseDTO result = licenseService.save(licenseDTO);
        return ResponseEntity
            .created(new URI("/api/licenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /licenses/:id} : Updates an existing license.
     *
     * @param id the id of the licenseDTO to save.
     * @param licenseDTO the licenseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licenseDTO,
     * or with status {@code 400 (Bad Request)} if the licenseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the licenseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/licenses/{id}")
    public ResponseEntity<LicenseDTO> updateLicense(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LicenseDTO licenseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update License : {}, {}", id, licenseDTO);
        if (licenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, licenseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!licenseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LicenseDTO result = licenseService.update(licenseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, licenseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /licenses/:id} : Partial updates given fields of an existing license, field will ignore if it is null
     *
     * @param id the id of the licenseDTO to save.
     * @param licenseDTO the licenseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated licenseDTO,
     * or with status {@code 400 (Bad Request)} if the licenseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the licenseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the licenseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/licenses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<LicenseDTO> partialUpdateLicense(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LicenseDTO licenseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update License partially : {}, {}", id, licenseDTO);
        if (licenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, licenseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!licenseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LicenseDTO> result = licenseService.partialUpdate(licenseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, licenseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /licenses} : get all the licenses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of licenses in body.
     */
    @GetMapping("/licenses")
    public ResponseEntity<List<LicenseDTO>> getAllLicenses(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Licenses");
        Page<LicenseDTO> page = licenseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /licenses/:id} : get the "id" license.
     *
     * @param id the id of the licenseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the licenseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/licenses/{id}")
    public ResponseEntity<LicenseDTO> getLicense(@PathVariable Long id) {
        log.debug("REST request to get License : {}", id);
        Optional<LicenseDTO> licenseDTO = licenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(licenseDTO);
    }

    /**
     * {@code DELETE  /licenses/:id} : delete the "id" license.
     *
     * @param id the id of the licenseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/licenses/{id}")
    public ResponseEntity<Void> deleteLicense(@PathVariable Long id) {
        log.debug("REST request to delete License : {}", id);
        licenseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
