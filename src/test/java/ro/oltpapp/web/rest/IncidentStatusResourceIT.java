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
import ro.oltpapp.domain.IncidentStatus;
import ro.oltpapp.repository.IncidentStatusRepository;
import ro.oltpapp.service.dto.IncidentStatusDTO;
import ro.oltpapp.service.mapper.IncidentStatusMapper;

/**
 * Integration tests for the {@link IncidentStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class IncidentStatusResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/incident-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private IncidentStatusRepository incidentStatusRepository;

    @Autowired
    private IncidentStatusMapper incidentStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restIncidentStatusMockMvc;

    private IncidentStatus incidentStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncidentStatus createEntity(EntityManager em) {
        IncidentStatus incidentStatus = new IncidentStatus().code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return incidentStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static IncidentStatus createUpdatedEntity(EntityManager em) {
        IncidentStatus incidentStatus = new IncidentStatus().code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return incidentStatus;
    }

    @BeforeEach
    public void initTest() {
        incidentStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createIncidentStatus() throws Exception {
        int databaseSizeBeforeCreate = incidentStatusRepository.findAll().size();
        // Create the IncidentStatus
        IncidentStatusDTO incidentStatusDTO = incidentStatusMapper.toDto(incidentStatus);
        restIncidentStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(incidentStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the IncidentStatus in the database
        List<IncidentStatus> incidentStatusList = incidentStatusRepository.findAll();
        assertThat(incidentStatusList).hasSize(databaseSizeBeforeCreate + 1);
        IncidentStatus testIncidentStatus = incidentStatusList.get(incidentStatusList.size() - 1);
        assertThat(testIncidentStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testIncidentStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createIncidentStatusWithExistingId() throws Exception {
        // Create the IncidentStatus with an existing ID
        incidentStatus.setId(1L);
        IncidentStatusDTO incidentStatusDTO = incidentStatusMapper.toDto(incidentStatus);

        int databaseSizeBeforeCreate = incidentStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restIncidentStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(incidentStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncidentStatus in the database
        List<IncidentStatus> incidentStatusList = incidentStatusRepository.findAll();
        assertThat(incidentStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = incidentStatusRepository.findAll().size();
        // set the field null
        incidentStatus.setCode(null);

        // Create the IncidentStatus, which fails.
        IncidentStatusDTO incidentStatusDTO = incidentStatusMapper.toDto(incidentStatus);

        restIncidentStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(incidentStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<IncidentStatus> incidentStatusList = incidentStatusRepository.findAll();
        assertThat(incidentStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllIncidentStatuses() throws Exception {
        // Initialize the database
        incidentStatusRepository.saveAndFlush(incidentStatus);

        // Get all the incidentStatusList
        restIncidentStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(incidentStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getIncidentStatus() throws Exception {
        // Initialize the database
        incidentStatusRepository.saveAndFlush(incidentStatus);

        // Get the incidentStatus
        restIncidentStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, incidentStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(incidentStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingIncidentStatus() throws Exception {
        // Get the incidentStatus
        restIncidentStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingIncidentStatus() throws Exception {
        // Initialize the database
        incidentStatusRepository.saveAndFlush(incidentStatus);

        int databaseSizeBeforeUpdate = incidentStatusRepository.findAll().size();

        // Update the incidentStatus
        IncidentStatus updatedIncidentStatus = incidentStatusRepository.findById(incidentStatus.getId()).get();
        // Disconnect from session so that the updates on updatedIncidentStatus are not directly saved in db
        em.detach(updatedIncidentStatus);
        updatedIncidentStatus.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        IncidentStatusDTO incidentStatusDTO = incidentStatusMapper.toDto(updatedIncidentStatus);

        restIncidentStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, incidentStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(incidentStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the IncidentStatus in the database
        List<IncidentStatus> incidentStatusList = incidentStatusRepository.findAll();
        assertThat(incidentStatusList).hasSize(databaseSizeBeforeUpdate);
        IncidentStatus testIncidentStatus = incidentStatusList.get(incidentStatusList.size() - 1);
        assertThat(testIncidentStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIncidentStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingIncidentStatus() throws Exception {
        int databaseSizeBeforeUpdate = incidentStatusRepository.findAll().size();
        incidentStatus.setId(count.incrementAndGet());

        // Create the IncidentStatus
        IncidentStatusDTO incidentStatusDTO = incidentStatusMapper.toDto(incidentStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidentStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, incidentStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(incidentStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncidentStatus in the database
        List<IncidentStatus> incidentStatusList = incidentStatusRepository.findAll();
        assertThat(incidentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchIncidentStatus() throws Exception {
        int databaseSizeBeforeUpdate = incidentStatusRepository.findAll().size();
        incidentStatus.setId(count.incrementAndGet());

        // Create the IncidentStatus
        IncidentStatusDTO incidentStatusDTO = incidentStatusMapper.toDto(incidentStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncidentStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(incidentStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncidentStatus in the database
        List<IncidentStatus> incidentStatusList = incidentStatusRepository.findAll();
        assertThat(incidentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamIncidentStatus() throws Exception {
        int databaseSizeBeforeUpdate = incidentStatusRepository.findAll().size();
        incidentStatus.setId(count.incrementAndGet());

        // Create the IncidentStatus
        IncidentStatusDTO incidentStatusDTO = incidentStatusMapper.toDto(incidentStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncidentStatusMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(incidentStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IncidentStatus in the database
        List<IncidentStatus> incidentStatusList = incidentStatusRepository.findAll();
        assertThat(incidentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateIncidentStatusWithPatch() throws Exception {
        // Initialize the database
        incidentStatusRepository.saveAndFlush(incidentStatus);

        int databaseSizeBeforeUpdate = incidentStatusRepository.findAll().size();

        // Update the incidentStatus using partial update
        IncidentStatus partialUpdatedIncidentStatus = new IncidentStatus();
        partialUpdatedIncidentStatus.setId(incidentStatus.getId());

        partialUpdatedIncidentStatus.code(UPDATED_CODE);

        restIncidentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIncidentStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIncidentStatus))
            )
            .andExpect(status().isOk());

        // Validate the IncidentStatus in the database
        List<IncidentStatus> incidentStatusList = incidentStatusRepository.findAll();
        assertThat(incidentStatusList).hasSize(databaseSizeBeforeUpdate);
        IncidentStatus testIncidentStatus = incidentStatusList.get(incidentStatusList.size() - 1);
        assertThat(testIncidentStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIncidentStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateIncidentStatusWithPatch() throws Exception {
        // Initialize the database
        incidentStatusRepository.saveAndFlush(incidentStatus);

        int databaseSizeBeforeUpdate = incidentStatusRepository.findAll().size();

        // Update the incidentStatus using partial update
        IncidentStatus partialUpdatedIncidentStatus = new IncidentStatus();
        partialUpdatedIncidentStatus.setId(incidentStatus.getId());

        partialUpdatedIncidentStatus.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restIncidentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedIncidentStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedIncidentStatus))
            )
            .andExpect(status().isOk());

        // Validate the IncidentStatus in the database
        List<IncidentStatus> incidentStatusList = incidentStatusRepository.findAll();
        assertThat(incidentStatusList).hasSize(databaseSizeBeforeUpdate);
        IncidentStatus testIncidentStatus = incidentStatusList.get(incidentStatusList.size() - 1);
        assertThat(testIncidentStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testIncidentStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingIncidentStatus() throws Exception {
        int databaseSizeBeforeUpdate = incidentStatusRepository.findAll().size();
        incidentStatus.setId(count.incrementAndGet());

        // Create the IncidentStatus
        IncidentStatusDTO incidentStatusDTO = incidentStatusMapper.toDto(incidentStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restIncidentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, incidentStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(incidentStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncidentStatus in the database
        List<IncidentStatus> incidentStatusList = incidentStatusRepository.findAll();
        assertThat(incidentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchIncidentStatus() throws Exception {
        int databaseSizeBeforeUpdate = incidentStatusRepository.findAll().size();
        incidentStatus.setId(count.incrementAndGet());

        // Create the IncidentStatus
        IncidentStatusDTO incidentStatusDTO = incidentStatusMapper.toDto(incidentStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncidentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(incidentStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the IncidentStatus in the database
        List<IncidentStatus> incidentStatusList = incidentStatusRepository.findAll();
        assertThat(incidentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamIncidentStatus() throws Exception {
        int databaseSizeBeforeUpdate = incidentStatusRepository.findAll().size();
        incidentStatus.setId(count.incrementAndGet());

        // Create the IncidentStatus
        IncidentStatusDTO incidentStatusDTO = incidentStatusMapper.toDto(incidentStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restIncidentStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(incidentStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the IncidentStatus in the database
        List<IncidentStatus> incidentStatusList = incidentStatusRepository.findAll();
        assertThat(incidentStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteIncidentStatus() throws Exception {
        // Initialize the database
        incidentStatusRepository.saveAndFlush(incidentStatus);

        int databaseSizeBeforeDelete = incidentStatusRepository.findAll().size();

        // Delete the incidentStatus
        restIncidentStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, incidentStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<IncidentStatus> incidentStatusList = incidentStatusRepository.findAll();
        assertThat(incidentStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
