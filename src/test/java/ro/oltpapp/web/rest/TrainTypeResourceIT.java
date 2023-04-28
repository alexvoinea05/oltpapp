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
import ro.oltpapp.domain.TrainType;
import ro.oltpapp.repository.TrainTypeRepository;
import ro.oltpapp.service.dto.TrainTypeDTO;
import ro.oltpapp.service.mapper.TrainTypeMapper;

/**
 * Integration tests for the {@link TrainTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/train-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainTypeRepository trainTypeRepository;

    @Autowired
    private TrainTypeMapper trainTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainTypeMockMvc;

    private TrainType trainType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainType createEntity(EntityManager em) {
        TrainType trainType = new TrainType().code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return trainType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TrainType createUpdatedEntity(EntityManager em) {
        TrainType trainType = new TrainType().code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return trainType;
    }

    @BeforeEach
    public void initTest() {
        trainType = createEntity(em);
    }

    @Test
    @Transactional
    void createTrainType() throws Exception {
        int databaseSizeBeforeCreate = trainTypeRepository.findAll().size();
        // Create the TrainType
        TrainTypeDTO trainTypeDTO = trainTypeMapper.toDto(trainType);
        restTrainTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the TrainType in the database
        List<TrainType> trainTypeList = trainTypeRepository.findAll();
        assertThat(trainTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TrainType testTrainType = trainTypeList.get(trainTypeList.size() - 1);
        assertThat(testTrainType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTrainType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createTrainTypeWithExistingId() throws Exception {
        // Create the TrainType with an existing ID
        trainType.setId(1L);
        TrainTypeDTO trainTypeDTO = trainTypeMapper.toDto(trainType);

        int databaseSizeBeforeCreate = trainTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TrainType in the database
        List<TrainType> trainTypeList = trainTypeRepository.findAll();
        assertThat(trainTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainTypeRepository.findAll().size();
        // set the field null
        trainType.setCode(null);

        // Create the TrainType, which fails.
        TrainTypeDTO trainTypeDTO = trainTypeMapper.toDto(trainType);

        restTrainTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainTypeDTO)))
            .andExpect(status().isBadRequest());

        List<TrainType> trainTypeList = trainTypeRepository.findAll();
        assertThat(trainTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrainTypes() throws Exception {
        // Initialize the database
        trainTypeRepository.saveAndFlush(trainType);

        // Get all the trainTypeList
        restTrainTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trainType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getTrainType() throws Exception {
        // Initialize the database
        trainTypeRepository.saveAndFlush(trainType);

        // Get the trainType
        restTrainTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, trainType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(trainType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingTrainType() throws Exception {
        // Get the trainType
        restTrainTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrainType() throws Exception {
        // Initialize the database
        trainTypeRepository.saveAndFlush(trainType);

        int databaseSizeBeforeUpdate = trainTypeRepository.findAll().size();

        // Update the trainType
        TrainType updatedTrainType = trainTypeRepository.findById(trainType.getId()).get();
        // Disconnect from session so that the updates on updatedTrainType are not directly saved in db
        em.detach(updatedTrainType);
        updatedTrainType.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        TrainTypeDTO trainTypeDTO = trainTypeMapper.toDto(updatedTrainType);

        restTrainTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the TrainType in the database
        List<TrainType> trainTypeList = trainTypeRepository.findAll();
        assertThat(trainTypeList).hasSize(databaseSizeBeforeUpdate);
        TrainType testTrainType = trainTypeList.get(trainTypeList.size() - 1);
        assertThat(testTrainType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingTrainType() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeRepository.findAll().size();
        trainType.setId(count.incrementAndGet());

        // Create the TrainType
        TrainTypeDTO trainTypeDTO = trainTypeMapper.toDto(trainType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainType in the database
        List<TrainType> trainTypeList = trainTypeRepository.findAll();
        assertThat(trainTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrainType() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeRepository.findAll().size();
        trainType.setId(count.incrementAndGet());

        // Create the TrainType
        TrainTypeDTO trainTypeDTO = trainTypeMapper.toDto(trainType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainType in the database
        List<TrainType> trainTypeList = trainTypeRepository.findAll();
        assertThat(trainTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrainType() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeRepository.findAll().size();
        trainType.setId(count.incrementAndGet());

        // Create the TrainType
        TrainTypeDTO trainTypeDTO = trainTypeMapper.toDto(trainType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainType in the database
        List<TrainType> trainTypeList = trainTypeRepository.findAll();
        assertThat(trainTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainTypeWithPatch() throws Exception {
        // Initialize the database
        trainTypeRepository.saveAndFlush(trainType);

        int databaseSizeBeforeUpdate = trainTypeRepository.findAll().size();

        // Update the trainType using partial update
        TrainType partialUpdatedTrainType = new TrainType();
        partialUpdatedTrainType.setId(trainType.getId());

        partialUpdatedTrainType.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restTrainTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainType))
            )
            .andExpect(status().isOk());

        // Validate the TrainType in the database
        List<TrainType> trainTypeList = trainTypeRepository.findAll();
        assertThat(trainTypeList).hasSize(databaseSizeBeforeUpdate);
        TrainType testTrainType = trainTypeList.get(trainTypeList.size() - 1);
        assertThat(testTrainType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateTrainTypeWithPatch() throws Exception {
        // Initialize the database
        trainTypeRepository.saveAndFlush(trainType);

        int databaseSizeBeforeUpdate = trainTypeRepository.findAll().size();

        // Update the trainType using partial update
        TrainType partialUpdatedTrainType = new TrainType();
        partialUpdatedTrainType.setId(trainType.getId());

        partialUpdatedTrainType.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restTrainTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrainType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrainType))
            )
            .andExpect(status().isOk());

        // Validate the TrainType in the database
        List<TrainType> trainTypeList = trainTypeRepository.findAll();
        assertThat(trainTypeList).hasSize(databaseSizeBeforeUpdate);
        TrainType testTrainType = trainTypeList.get(trainTypeList.size() - 1);
        assertThat(testTrainType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrainType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingTrainType() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeRepository.findAll().size();
        trainType.setId(count.incrementAndGet());

        // Create the TrainType
        TrainTypeDTO trainTypeDTO = trainTypeMapper.toDto(trainType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainType in the database
        List<TrainType> trainTypeList = trainTypeRepository.findAll();
        assertThat(trainTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrainType() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeRepository.findAll().size();
        trainType.setId(count.incrementAndGet());

        // Create the TrainType
        TrainTypeDTO trainTypeDTO = trainTypeMapper.toDto(trainType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the TrainType in the database
        List<TrainType> trainTypeList = trainTypeRepository.findAll();
        assertThat(trainTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrainType() throws Exception {
        int databaseSizeBeforeUpdate = trainTypeRepository.findAll().size();
        trainType.setId(count.incrementAndGet());

        // Create the TrainType
        TrainTypeDTO trainTypeDTO = trainTypeMapper.toDto(trainType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(trainTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TrainType in the database
        List<TrainType> trainTypeList = trainTypeRepository.findAll();
        assertThat(trainTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrainType() throws Exception {
        // Initialize the database
        trainTypeRepository.saveAndFlush(trainType);

        int databaseSizeBeforeDelete = trainTypeRepository.findAll().size();

        // Delete the trainType
        restTrainTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, trainType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TrainType> trainTypeList = trainTypeRepository.findAll();
        assertThat(trainTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
