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
import ro.oltpapp.domain.License;
import ro.oltpapp.repository.LicenseRepository;
import ro.oltpapp.service.dto.LicenseDTO;
import ro.oltpapp.service.mapper.LicenseMapper;

/**
 * Integration tests for the {@link LicenseResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LicenseResourceIT {

    private static final Long DEFAULT_LICENSE_NUMBER = 1L;
    private static final Long UPDATED_LICENSE_NUMBER = 2L;

    private static final String DEFAULT_LICENSE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LICENSE_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/licenses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private LicenseMapper licenseMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLicenseMockMvc;

    private License license;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static License createEntity(EntityManager em) {
        License license = new License().licenseNumber(DEFAULT_LICENSE_NUMBER).licenseDescription(DEFAULT_LICENSE_DESCRIPTION);
        return license;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static License createUpdatedEntity(EntityManager em) {
        License license = new License().licenseNumber(UPDATED_LICENSE_NUMBER).licenseDescription(UPDATED_LICENSE_DESCRIPTION);
        return license;
    }

    @BeforeEach
    public void initTest() {
        license = createEntity(em);
    }

    @Test
    @Transactional
    void createLicense() throws Exception {
        int databaseSizeBeforeCreate = licenseRepository.findAll().size();
        // Create the License
        LicenseDTO licenseDTO = licenseMapper.toDto(license);
        restLicenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licenseDTO)))
            .andExpect(status().isCreated());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeCreate + 1);
        License testLicense = licenseList.get(licenseList.size() - 1);
        assertThat(testLicense.getLicenseNumber()).isEqualTo(DEFAULT_LICENSE_NUMBER);
        assertThat(testLicense.getLicenseDescription()).isEqualTo(DEFAULT_LICENSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void createLicenseWithExistingId() throws Exception {
        // Create the License with an existing ID
        license.setId(1L);
        LicenseDTO licenseDTO = licenseMapper.toDto(license);

        int databaseSizeBeforeCreate = licenseRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licenseDTO)))
            .andExpect(status().isBadRequest());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkLicenseNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = licenseRepository.findAll().size();
        // set the field null
        license.setLicenseNumber(null);

        // Create the License, which fails.
        LicenseDTO licenseDTO = licenseMapper.toDto(license);

        restLicenseMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licenseDTO)))
            .andExpect(status().isBadRequest());

        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLicenses() throws Exception {
        // Initialize the database
        licenseRepository.saveAndFlush(license);

        // Get all the licenseList
        restLicenseMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(license.getId().intValue())))
            .andExpect(jsonPath("$.[*].licenseNumber").value(hasItem(DEFAULT_LICENSE_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].licenseDescription").value(hasItem(DEFAULT_LICENSE_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getLicense() throws Exception {
        // Initialize the database
        licenseRepository.saveAndFlush(license);

        // Get the license
        restLicenseMockMvc
            .perform(get(ENTITY_API_URL_ID, license.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(license.getId().intValue()))
            .andExpect(jsonPath("$.licenseNumber").value(DEFAULT_LICENSE_NUMBER.intValue()))
            .andExpect(jsonPath("$.licenseDescription").value(DEFAULT_LICENSE_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingLicense() throws Exception {
        // Get the license
        restLicenseMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingLicense() throws Exception {
        // Initialize the database
        licenseRepository.saveAndFlush(license);

        int databaseSizeBeforeUpdate = licenseRepository.findAll().size();

        // Update the license
        License updatedLicense = licenseRepository.findById(license.getId()).get();
        // Disconnect from session so that the updates on updatedLicense are not directly saved in db
        em.detach(updatedLicense);
        updatedLicense.licenseNumber(UPDATED_LICENSE_NUMBER).licenseDescription(UPDATED_LICENSE_DESCRIPTION);
        LicenseDTO licenseDTO = licenseMapper.toDto(updatedLicense);

        restLicenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, licenseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licenseDTO))
            )
            .andExpect(status().isOk());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeUpdate);
        License testLicense = licenseList.get(licenseList.size() - 1);
        assertThat(testLicense.getLicenseNumber()).isEqualTo(UPDATED_LICENSE_NUMBER);
        assertThat(testLicense.getLicenseDescription()).isEqualTo(UPDATED_LICENSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingLicense() throws Exception {
        int databaseSizeBeforeUpdate = licenseRepository.findAll().size();
        license.setId(count.incrementAndGet());

        // Create the License
        LicenseDTO licenseDTO = licenseMapper.toDto(license);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, licenseDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLicense() throws Exception {
        int databaseSizeBeforeUpdate = licenseRepository.findAll().size();
        license.setId(count.incrementAndGet());

        // Create the License
        LicenseDTO licenseDTO = licenseMapper.toDto(license);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenseMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(licenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLicense() throws Exception {
        int databaseSizeBeforeUpdate = licenseRepository.findAll().size();
        license.setId(count.incrementAndGet());

        // Create the License
        LicenseDTO licenseDTO = licenseMapper.toDto(license);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenseMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(licenseDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLicenseWithPatch() throws Exception {
        // Initialize the database
        licenseRepository.saveAndFlush(license);

        int databaseSizeBeforeUpdate = licenseRepository.findAll().size();

        // Update the license using partial update
        License partialUpdatedLicense = new License();
        partialUpdatedLicense.setId(license.getId());

        partialUpdatedLicense.licenseNumber(UPDATED_LICENSE_NUMBER);

        restLicenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLicense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLicense))
            )
            .andExpect(status().isOk());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeUpdate);
        License testLicense = licenseList.get(licenseList.size() - 1);
        assertThat(testLicense.getLicenseNumber()).isEqualTo(UPDATED_LICENSE_NUMBER);
        assertThat(testLicense.getLicenseDescription()).isEqualTo(DEFAULT_LICENSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateLicenseWithPatch() throws Exception {
        // Initialize the database
        licenseRepository.saveAndFlush(license);

        int databaseSizeBeforeUpdate = licenseRepository.findAll().size();

        // Update the license using partial update
        License partialUpdatedLicense = new License();
        partialUpdatedLicense.setId(license.getId());

        partialUpdatedLicense.licenseNumber(UPDATED_LICENSE_NUMBER).licenseDescription(UPDATED_LICENSE_DESCRIPTION);

        restLicenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLicense.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLicense))
            )
            .andExpect(status().isOk());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeUpdate);
        License testLicense = licenseList.get(licenseList.size() - 1);
        assertThat(testLicense.getLicenseNumber()).isEqualTo(UPDATED_LICENSE_NUMBER);
        assertThat(testLicense.getLicenseDescription()).isEqualTo(UPDATED_LICENSE_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingLicense() throws Exception {
        int databaseSizeBeforeUpdate = licenseRepository.findAll().size();
        license.setId(count.incrementAndGet());

        // Create the License
        LicenseDTO licenseDTO = licenseMapper.toDto(license);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLicenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, licenseDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(licenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLicense() throws Exception {
        int databaseSizeBeforeUpdate = licenseRepository.findAll().size();
        license.setId(count.incrementAndGet());

        // Create the License
        LicenseDTO licenseDTO = licenseMapper.toDto(license);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenseMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(licenseDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLicense() throws Exception {
        int databaseSizeBeforeUpdate = licenseRepository.findAll().size();
        license.setId(count.incrementAndGet());

        // Create the License
        LicenseDTO licenseDTO = licenseMapper.toDto(license);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLicenseMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(licenseDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the License in the database
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLicense() throws Exception {
        // Initialize the database
        licenseRepository.saveAndFlush(license);

        int databaseSizeBeforeDelete = licenseRepository.findAll().size();

        // Delete the license
        restLicenseMockMvc
            .perform(delete(ENTITY_API_URL_ID, license.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<License> licenseList = licenseRepository.findAll();
        assertThat(licenseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
