package ro.oltpapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ro.oltpapp.IntegrationTest;
import ro.oltpapp.domain.CompanyXLicense;
import ro.oltpapp.repository.CompanyXLicenseRepository;
import ro.oltpapp.service.dto.CompanyXLicenseDTO;
import ro.oltpapp.service.mapper.CompanyXLicenseMapper;

/**
 * Integration tests for the {@link CompanyXLicenseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CompanyXLicenseResourceIT {

    private static final String ENTITY_API_URL = "/api/company-x-licenses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CompanyXLicenseRepository companyXLicenseRepository;

    @Autowired
    private CompanyXLicenseMapper companyXLicenseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCompanyXLicenseMockMvc;

    private CompanyXLicense companyXLicense;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyXLicense createEntity(EntityManager em) {
        CompanyXLicense companyXLicense = new CompanyXLicense();
        return companyXLicense;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompanyXLicense createUpdatedEntity(EntityManager em) {
        CompanyXLicense companyXLicense = new CompanyXLicense();
        return companyXLicense;
    }

    @BeforeEach
    public void initTest() {
        companyXLicense = createEntity(em);
    }

    @Test
    @Transactional
    void createCompanyXLicense() throws Exception {
        int databaseSizeBeforeCreate = companyXLicenseRepository.findAll().size();
        // Create the CompanyXLicense
        CompanyXLicenseDTO companyXLicenseDTO = companyXLicenseMapper.toDto(companyXLicense);
        restCompanyXLicenseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyXLicenseDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CompanyXLicense in the database
        List<CompanyXLicense> companyXLicenseList = companyXLicenseRepository.findAll();
        assertThat(companyXLicenseList).hasSize(databaseSizeBeforeCreate + 1);
        CompanyXLicense testCompanyXLicense = companyXLicenseList.get(companyXLicenseList.size() - 1);
    }

    @Test
    @Transactional
    void createCompanyXLicenseWithExistingId() throws Exception {
        // Create the CompanyXLicense with an existing ID
        companyXLicense.setId(1L);
        CompanyXLicenseDTO companyXLicenseDTO = companyXLicenseMapper.toDto(companyXLicense);

        int databaseSizeBeforeCreate = companyXLicenseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompanyXLicenseMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyXLicenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyXLicense in the database
        List<CompanyXLicense> companyXLicenseList = companyXLicenseRepository.findAll();
        assertThat(companyXLicenseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCompanyXLicenses() throws Exception {
        // Initialize the database
        companyXLicenseRepository.saveAndFlush(companyXLicense);

        // Get all the companyXLicenseList
        restCompanyXLicenseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(companyXLicense.getId().intValue())));
    }

    @Test
    @Transactional
    void getCompanyXLicense() throws Exception {
        // Initialize the database
        companyXLicenseRepository.saveAndFlush(companyXLicense);

        // Get the companyXLicense
        restCompanyXLicenseMockMvc
            .perform(get(ENTITY_API_URL_ID, companyXLicense.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(companyXLicense.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingCompanyXLicense() throws Exception {
        // Get the companyXLicense
        restCompanyXLicenseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCompanyXLicense() throws Exception {
        // Initialize the database
        companyXLicenseRepository.saveAndFlush(companyXLicense);

        int databaseSizeBeforeUpdate = companyXLicenseRepository.findAll().size();

        // Update the companyXLicense
        CompanyXLicense updatedCompanyXLicense = companyXLicenseRepository.findById(companyXLicense.getId()).get();
        // Disconnect from session so that the updates on updatedCompanyXLicense are not directly saved in db
        em.detach(updatedCompanyXLicense);
        CompanyXLicenseDTO companyXLicenseDTO = companyXLicenseMapper.toDto(updatedCompanyXLicense);

        restCompanyXLicenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyXLicenseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyXLicenseDTO))
            )
            .andExpect(status().isOk());

        // Validate the CompanyXLicense in the database
        List<CompanyXLicense> companyXLicenseList = companyXLicenseRepository.findAll();
        assertThat(companyXLicenseList).hasSize(databaseSizeBeforeUpdate);
        CompanyXLicense testCompanyXLicense = companyXLicenseList.get(companyXLicenseList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingCompanyXLicense() throws Exception {
        int databaseSizeBeforeUpdate = companyXLicenseRepository.findAll().size();
        companyXLicense.setId(count.incrementAndGet());

        // Create the CompanyXLicense
        CompanyXLicenseDTO companyXLicenseDTO = companyXLicenseMapper.toDto(companyXLicense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyXLicenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, companyXLicenseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyXLicenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyXLicense in the database
        List<CompanyXLicense> companyXLicenseList = companyXLicenseRepository.findAll();
        assertThat(companyXLicenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCompanyXLicense() throws Exception {
        int databaseSizeBeforeUpdate = companyXLicenseRepository.findAll().size();
        companyXLicense.setId(count.incrementAndGet());

        // Create the CompanyXLicense
        CompanyXLicenseDTO companyXLicenseDTO = companyXLicenseMapper.toDto(companyXLicense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyXLicenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(companyXLicenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyXLicense in the database
        List<CompanyXLicense> companyXLicenseList = companyXLicenseRepository.findAll();
        assertThat(companyXLicenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCompanyXLicense() throws Exception {
        int databaseSizeBeforeUpdate = companyXLicenseRepository.findAll().size();
        companyXLicense.setId(count.incrementAndGet());

        // Create the CompanyXLicense
        CompanyXLicenseDTO companyXLicenseDTO = companyXLicenseMapper.toDto(companyXLicense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyXLicenseMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(companyXLicenseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyXLicense in the database
        List<CompanyXLicense> companyXLicenseList = companyXLicenseRepository.findAll();
        assertThat(companyXLicenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCompanyXLicenseWithPatch() throws Exception {
        // Initialize the database
        companyXLicenseRepository.saveAndFlush(companyXLicense);

        int databaseSizeBeforeUpdate = companyXLicenseRepository.findAll().size();

        // Update the companyXLicense using partial update
        CompanyXLicense partialUpdatedCompanyXLicense = new CompanyXLicense();
        partialUpdatedCompanyXLicense.setId(companyXLicense.getId());

        restCompanyXLicenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyXLicense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyXLicense))
            )
            .andExpect(status().isOk());

        // Validate the CompanyXLicense in the database
        List<CompanyXLicense> companyXLicenseList = companyXLicenseRepository.findAll();
        assertThat(companyXLicenseList).hasSize(databaseSizeBeforeUpdate);
        CompanyXLicense testCompanyXLicense = companyXLicenseList.get(companyXLicenseList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateCompanyXLicenseWithPatch() throws Exception {
        // Initialize the database
        companyXLicenseRepository.saveAndFlush(companyXLicense);

        int databaseSizeBeforeUpdate = companyXLicenseRepository.findAll().size();

        // Update the companyXLicense using partial update
        CompanyXLicense partialUpdatedCompanyXLicense = new CompanyXLicense();
        partialUpdatedCompanyXLicense.setId(companyXLicense.getId());

        restCompanyXLicenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCompanyXLicense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCompanyXLicense))
            )
            .andExpect(status().isOk());

        // Validate the CompanyXLicense in the database
        List<CompanyXLicense> companyXLicenseList = companyXLicenseRepository.findAll();
        assertThat(companyXLicenseList).hasSize(databaseSizeBeforeUpdate);
        CompanyXLicense testCompanyXLicense = companyXLicenseList.get(companyXLicenseList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingCompanyXLicense() throws Exception {
        int databaseSizeBeforeUpdate = companyXLicenseRepository.findAll().size();
        companyXLicense.setId(count.incrementAndGet());

        // Create the CompanyXLicense
        CompanyXLicenseDTO companyXLicenseDTO = companyXLicenseMapper.toDto(companyXLicense);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompanyXLicenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, companyXLicenseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyXLicenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyXLicense in the database
        List<CompanyXLicense> companyXLicenseList = companyXLicenseRepository.findAll();
        assertThat(companyXLicenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCompanyXLicense() throws Exception {
        int databaseSizeBeforeUpdate = companyXLicenseRepository.findAll().size();
        companyXLicense.setId(count.incrementAndGet());

        // Create the CompanyXLicense
        CompanyXLicenseDTO companyXLicenseDTO = companyXLicenseMapper.toDto(companyXLicense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyXLicenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyXLicenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CompanyXLicense in the database
        List<CompanyXLicense> companyXLicenseList = companyXLicenseRepository.findAll();
        assertThat(companyXLicenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCompanyXLicense() throws Exception {
        int databaseSizeBeforeUpdate = companyXLicenseRepository.findAll().size();
        companyXLicense.setId(count.incrementAndGet());

        // Create the CompanyXLicense
        CompanyXLicenseDTO companyXLicenseDTO = companyXLicenseMapper.toDto(companyXLicense);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCompanyXLicenseMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(companyXLicenseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CompanyXLicense in the database
        List<CompanyXLicense> companyXLicenseList = companyXLicenseRepository.findAll();
        assertThat(companyXLicenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCompanyXLicense() throws Exception {
        // Initialize the database
        companyXLicenseRepository.saveAndFlush(companyXLicense);

        int databaseSizeBeforeDelete = companyXLicenseRepository.findAll().size();

        // Delete the companyXLicense
        restCompanyXLicenseMockMvc
            .perform(delete(ENTITY_API_URL_ID, companyXLicense.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompanyXLicense> companyXLicenseList = companyXLicenseRepository.findAll();
        assertThat(companyXLicenseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
