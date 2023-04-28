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
import ro.oltpapp.domain.JourneyStatus;
import ro.oltpapp.repository.JourneyStatusRepository;
import ro.oltpapp.service.dto.JourneyStatusDTO;
import ro.oltpapp.service.mapper.JourneyStatusMapper;

/**
 * Integration tests for the {@link JourneyStatusResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JourneyStatusResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/journey-statuses";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JourneyStatusRepository journeyStatusRepository;

    @Autowired
    private JourneyStatusMapper journeyStatusMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJourneyStatusMockMvc;

    private JourneyStatus journeyStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourneyStatus createEntity(EntityManager em) {
        JourneyStatus journeyStatus = new JourneyStatus().code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return journeyStatus;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JourneyStatus createUpdatedEntity(EntityManager em) {
        JourneyStatus journeyStatus = new JourneyStatus().code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return journeyStatus;
    }

    @BeforeEach
    public void initTest() {
        journeyStatus = createEntity(em);
    }

    @Test
    @Transactional
    void createJourneyStatus() throws Exception {
        int databaseSizeBeforeCreate = journeyStatusRepository.findAll().size();
        // Create the JourneyStatus
        JourneyStatusDTO journeyStatusDTO = journeyStatusMapper.toDto(journeyStatus);
        restJourneyStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyStatusDTO))
            )
            .andExpect(status().isCreated());

        // Validate the JourneyStatus in the database
        List<JourneyStatus> journeyStatusList = journeyStatusRepository.findAll();
        assertThat(journeyStatusList).hasSize(databaseSizeBeforeCreate + 1);
        JourneyStatus testJourneyStatus = journeyStatusList.get(journeyStatusList.size() - 1);
        assertThat(testJourneyStatus.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testJourneyStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createJourneyStatusWithExistingId() throws Exception {
        // Create the JourneyStatus with an existing ID
        journeyStatus.setId(1L);
        JourneyStatusDTO journeyStatusDTO = journeyStatusMapper.toDto(journeyStatus);

        int databaseSizeBeforeCreate = journeyStatusRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourneyStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatus in the database
        List<JourneyStatus> journeyStatusList = journeyStatusRepository.findAll();
        assertThat(journeyStatusList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = journeyStatusRepository.findAll().size();
        // set the field null
        journeyStatus.setCode(null);

        // Create the JourneyStatus, which fails.
        JourneyStatusDTO journeyStatusDTO = journeyStatusMapper.toDto(journeyStatus);

        restJourneyStatusMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        List<JourneyStatus> journeyStatusList = journeyStatusRepository.findAll();
        assertThat(journeyStatusList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllJourneyStatuses() throws Exception {
        // Initialize the database
        journeyStatusRepository.saveAndFlush(journeyStatus);

        // Get all the journeyStatusList
        restJourneyStatusMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journeyStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getJourneyStatus() throws Exception {
        // Initialize the database
        journeyStatusRepository.saveAndFlush(journeyStatus);

        // Get the journeyStatus
        restJourneyStatusMockMvc
            .perform(get(ENTITY_API_URL_ID, journeyStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(journeyStatus.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingJourneyStatus() throws Exception {
        // Get the journeyStatus
        restJourneyStatusMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJourneyStatus() throws Exception {
        // Initialize the database
        journeyStatusRepository.saveAndFlush(journeyStatus);

        int databaseSizeBeforeUpdate = journeyStatusRepository.findAll().size();

        // Update the journeyStatus
        JourneyStatus updatedJourneyStatus = journeyStatusRepository.findById(journeyStatus.getId()).get();
        // Disconnect from session so that the updates on updatedJourneyStatus are not directly saved in db
        em.detach(updatedJourneyStatus);
        updatedJourneyStatus.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        JourneyStatusDTO journeyStatusDTO = journeyStatusMapper.toDto(updatedJourneyStatus);

        restJourneyStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, journeyStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusDTO))
            )
            .andExpect(status().isOk());

        // Validate the JourneyStatus in the database
        List<JourneyStatus> journeyStatusList = journeyStatusRepository.findAll();
        assertThat(journeyStatusList).hasSize(databaseSizeBeforeUpdate);
        JourneyStatus testJourneyStatus = journeyStatusList.get(journeyStatusList.size() - 1);
        assertThat(testJourneyStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJourneyStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingJourneyStatus() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusRepository.findAll().size();
        journeyStatus.setId(count.incrementAndGet());

        // Create the JourneyStatus
        JourneyStatusDTO journeyStatusDTO = journeyStatusMapper.toDto(journeyStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, journeyStatusDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatus in the database
        List<JourneyStatus> journeyStatusList = journeyStatusRepository.findAll();
        assertThat(journeyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJourneyStatus() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusRepository.findAll().size();
        journeyStatus.setId(count.incrementAndGet());

        // Create the JourneyStatus
        JourneyStatusDTO journeyStatusDTO = journeyStatusMapper.toDto(journeyStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyStatusMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatus in the database
        List<JourneyStatus> journeyStatusList = journeyStatusRepository.findAll();
        assertThat(journeyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJourneyStatus() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusRepository.findAll().size();
        journeyStatus.setId(count.incrementAndGet());

        // Create the JourneyStatus
        JourneyStatusDTO journeyStatusDTO = journeyStatusMapper.toDto(journeyStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyStatusMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JourneyStatus in the database
        List<JourneyStatus> journeyStatusList = journeyStatusRepository.findAll();
        assertThat(journeyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJourneyStatusWithPatch() throws Exception {
        // Initialize the database
        journeyStatusRepository.saveAndFlush(journeyStatus);

        int databaseSizeBeforeUpdate = journeyStatusRepository.findAll().size();

        // Update the journeyStatus using partial update
        JourneyStatus partialUpdatedJourneyStatus = new JourneyStatus();
        partialUpdatedJourneyStatus.setId(journeyStatus.getId());

        partialUpdatedJourneyStatus.code(UPDATED_CODE);

        restJourneyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourneyStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJourneyStatus))
            )
            .andExpect(status().isOk());

        // Validate the JourneyStatus in the database
        List<JourneyStatus> journeyStatusList = journeyStatusRepository.findAll();
        assertThat(journeyStatusList).hasSize(databaseSizeBeforeUpdate);
        JourneyStatus testJourneyStatus = journeyStatusList.get(journeyStatusList.size() - 1);
        assertThat(testJourneyStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJourneyStatus.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateJourneyStatusWithPatch() throws Exception {
        // Initialize the database
        journeyStatusRepository.saveAndFlush(journeyStatus);

        int databaseSizeBeforeUpdate = journeyStatusRepository.findAll().size();

        // Update the journeyStatus using partial update
        JourneyStatus partialUpdatedJourneyStatus = new JourneyStatus();
        partialUpdatedJourneyStatus.setId(journeyStatus.getId());

        partialUpdatedJourneyStatus.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restJourneyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourneyStatus.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJourneyStatus))
            )
            .andExpect(status().isOk());

        // Validate the JourneyStatus in the database
        List<JourneyStatus> journeyStatusList = journeyStatusRepository.findAll();
        assertThat(journeyStatusList).hasSize(databaseSizeBeforeUpdate);
        JourneyStatus testJourneyStatus = journeyStatusList.get(journeyStatusList.size() - 1);
        assertThat(testJourneyStatus.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testJourneyStatus.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingJourneyStatus() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusRepository.findAll().size();
        journeyStatus.setId(count.incrementAndGet());

        // Create the JourneyStatus
        JourneyStatusDTO journeyStatusDTO = journeyStatusMapper.toDto(journeyStatus);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, journeyStatusDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatus in the database
        List<JourneyStatus> journeyStatusList = journeyStatusRepository.findAll();
        assertThat(journeyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJourneyStatus() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusRepository.findAll().size();
        journeyStatus.setId(count.incrementAndGet());

        // Create the JourneyStatus
        JourneyStatusDTO journeyStatusDTO = journeyStatusMapper.toDto(journeyStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JourneyStatus in the database
        List<JourneyStatus> journeyStatusList = journeyStatusRepository.findAll();
        assertThat(journeyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJourneyStatus() throws Exception {
        int databaseSizeBeforeUpdate = journeyStatusRepository.findAll().size();
        journeyStatus.setId(count.incrementAndGet());

        // Create the JourneyStatus
        JourneyStatusDTO journeyStatusDTO = journeyStatusMapper.toDto(journeyStatus);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyStatusMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyStatusDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JourneyStatus in the database
        List<JourneyStatus> journeyStatusList = journeyStatusRepository.findAll();
        assertThat(journeyStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJourneyStatus() throws Exception {
        // Initialize the database
        journeyStatusRepository.saveAndFlush(journeyStatus);

        int databaseSizeBeforeDelete = journeyStatusRepository.findAll().size();

        // Delete the journeyStatus
        restJourneyStatusMockMvc
            .perform(delete(ENTITY_API_URL_ID, journeyStatus.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JourneyStatus> journeyStatusList = journeyStatusRepository.findAll();
        assertThat(journeyStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
