package ro.oltpapp.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
import ro.oltpapp.repository.CompanyXLicenseRepository;
import ro.oltpapp.service.CompanyXLicenseService;
import ro.oltpapp.service.dto.CompanyXLicenseDTO;
import ro.oltpapp.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link ro.oltpapp.domain.CompanyXLicense}.
 */
@RestController
@RequestMapping("/api")
public class CompanyXLicenseResource {

    private final Logger log = LoggerFactory.getLogger(CompanyXLicenseResource.class);

    private static final String ENTITY_NAME = "companyXLicense";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyXLicenseService companyXLicenseService;

    private final CompanyXLicenseRepository companyXLicenseRepository;

    public CompanyXLicenseResource(CompanyXLicenseService companyXLicenseService, CompanyXLicenseRepository companyXLicenseRepository) {
        this.companyXLicenseService = companyXLicenseService;
        this.companyXLicenseRepository = companyXLicenseRepository;
    }

    /**
     * {@code POST  /company-x-licenses} : Create a new companyXLicense.
     *
     * @param companyXLicenseDTO the companyXLicenseDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new companyXLicenseDTO, or with status {@code 400 (Bad Request)} if the companyXLicense has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/company-x-licenses")
    public ResponseEntity<CompanyXLicenseDTO> createCompanyXLicense(@RequestBody CompanyXLicenseDTO companyXLicenseDTO)
        throws URISyntaxException {
        log.debug("REST request to save CompanyXLicense : {}", companyXLicenseDTO);
        if (companyXLicenseDTO.getId() != null) {
            throw new BadRequestAlertException("A new companyXLicense cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CompanyXLicenseDTO result = companyXLicenseService.save(companyXLicenseDTO);
        return ResponseEntity
            .created(new URI("/api/company-x-licenses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /company-x-licenses/:id} : Updates an existing companyXLicense.
     *
     * @param id the id of the companyXLicenseDTO to save.
     * @param companyXLicenseDTO the companyXLicenseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyXLicenseDTO,
     * or with status {@code 400 (Bad Request)} if the companyXLicenseDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the companyXLicenseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/company-x-licenses/{id}")
    public ResponseEntity<CompanyXLicenseDTO> updateCompanyXLicense(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyXLicenseDTO companyXLicenseDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CompanyXLicense : {}, {}", id, companyXLicenseDTO);
        if (companyXLicenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyXLicenseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyXLicenseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CompanyXLicenseDTO result = companyXLicenseService.update(companyXLicenseDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyXLicenseDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /company-x-licenses/:id} : Partial updates given fields of an existing companyXLicense, field will ignore if it is null
     *
     * @param id the id of the companyXLicenseDTO to save.
     * @param companyXLicenseDTO the companyXLicenseDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated companyXLicenseDTO,
     * or with status {@code 400 (Bad Request)} if the companyXLicenseDTO is not valid,
     * or with status {@code 404 (Not Found)} if the companyXLicenseDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the companyXLicenseDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/company-x-licenses/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyXLicenseDTO> partialUpdateCompanyXLicense(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyXLicenseDTO companyXLicenseDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CompanyXLicense partially : {}, {}", id, companyXLicenseDTO);
        if (companyXLicenseDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, companyXLicenseDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!companyXLicenseRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompanyXLicenseDTO> result = companyXLicenseService.partialUpdate(companyXLicenseDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, companyXLicenseDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /company-x-licenses} : get all the companyXLicenses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companyXLicenses in body.
     */
    @GetMapping("/company-x-licenses")
    public ResponseEntity<List<CompanyXLicenseDTO>> getAllCompanyXLicenses(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of CompanyXLicenses");
        Page<CompanyXLicenseDTO> page = companyXLicenseService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /company-x-licenses/:id} : get the "id" companyXLicense.
     *
     * @param id the id of the companyXLicenseDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the companyXLicenseDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/company-x-licenses/{id}")
    public ResponseEntity<CompanyXLicenseDTO> getCompanyXLicense(@PathVariable Long id) {
        log.debug("REST request to get CompanyXLicense : {}", id);
        Optional<CompanyXLicenseDTO> companyXLicenseDTO = companyXLicenseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(companyXLicenseDTO);
    }

    /**
     * {@code DELETE  /company-x-licenses/:id} : delete the "id" companyXLicense.
     *
     * @param id the id of the companyXLicenseDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/company-x-licenses/{id}")
    public ResponseEntity<Void> deleteCompanyXLicense(@PathVariable Long id) {
        log.debug("REST request to delete CompanyXLicense : {}", id);
        companyXLicenseService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
