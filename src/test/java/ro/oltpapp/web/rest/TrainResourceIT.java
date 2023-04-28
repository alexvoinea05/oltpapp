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
import ro.oltpapp.domain.Train;
import ro.oltpapp.repository.TrainRepository;
import ro.oltpapp.service.dto.TrainDTO;
import ro.oltpapp.service.mapper.TrainMapper;

/**
 * Integration tests for the {@link TrainResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TrainResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMBER_OF_SEATS = 1L;
    private static final Long UPDATED_NUMBER_OF_SEATS = 2L;

    private static final String ENTITY_API_URL = "/api/trains";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private TrainMapper trainMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTrainMockMvc;

    private Train train;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Train createEntity(EntityManager em) {
        Train train = new Train().code(DEFAULT_CODE).numberOfSeats(DEFAULT_NUMBER_OF_SEATS);
        return train;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Train createUpdatedEntity(EntityManager em) {
        Train train = new Train().code(UPDATED_CODE).numberOfSeats(UPDATED_NUMBER_OF_SEATS);
        return train;
    }

    @BeforeEach
    public void initTest() {
        train = createEntity(em);
    }

    @Test
    @Transactional
    void createTrain() throws Exception {
        int databaseSizeBeforeCreate = trainRepository.findAll().size();
        // Create the Train
        TrainDTO trainDTO = trainMapper.toDto(train);
        restTrainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainDTO)))
            .andExpect(status().isCreated());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeCreate + 1);
        Train testTrain = trainList.get(trainList.size() - 1);
        assertThat(testTrain.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testTrain.getNumberOfSeats()).isEqualTo(DEFAULT_NUMBER_OF_SEATS);
    }

    @Test
    @Transactional
    void createTrainWithExistingId() throws Exception {
        // Create the Train with an existing ID
        train.setId(1L);
        TrainDTO trainDTO = trainMapper.toDto(train);

        int databaseSizeBeforeCreate = trainRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainRepository.findAll().size();
        // set the field null
        train.setCode(null);

        // Create the Train, which fails.
        TrainDTO trainDTO = trainMapper.toDto(train);

        restTrainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainDTO)))
            .andExpect(status().isBadRequest());

        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumberOfSeatsIsRequired() throws Exception {
        int databaseSizeBeforeTest = trainRepository.findAll().size();
        // set the field null
        train.setNumberOfSeats(null);

        // Create the Train, which fails.
        TrainDTO trainDTO = trainMapper.toDto(train);

        restTrainMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainDTO)))
            .andExpect(status().isBadRequest());

        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTrains() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        // Get all the trainList
        restTrainMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(train.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].numberOfSeats").value(hasItem(DEFAULT_NUMBER_OF_SEATS.intValue())));
    }

    @Test
    @Transactional
    void getTrain() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        // Get the train
        restTrainMockMvc
            .perform(get(ENTITY_API_URL_ID, train.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(train.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.numberOfSeats").value(DEFAULT_NUMBER_OF_SEATS.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingTrain() throws Exception {
        // Get the train
        restTrainMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTrain() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        int databaseSizeBeforeUpdate = trainRepository.findAll().size();

        // Update the train
        Train updatedTrain = trainRepository.findById(train.getId()).get();
        // Disconnect from session so that the updates on updatedTrain are not directly saved in db
        em.detach(updatedTrain);
        updatedTrain.code(UPDATED_CODE).numberOfSeats(UPDATED_NUMBER_OF_SEATS);
        TrainDTO trainDTO = trainMapper.toDto(updatedTrain);

        restTrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainDTO))
            )
            .andExpect(status().isOk());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
        Train testTrain = trainList.get(trainList.size() - 1);
        assertThat(testTrain.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrain.getNumberOfSeats()).isEqualTo(UPDATED_NUMBER_OF_SEATS);
    }

    @Test
    @Transactional
    void putNonExistingTrain() throws Exception {
        int databaseSizeBeforeUpdate = trainRepository.findAll().size();
        train.setId(count.incrementAndGet());

        // Create the Train
        TrainDTO trainDTO = trainMapper.toDto(train);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, trainDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTrain() throws Exception {
        int databaseSizeBeforeUpdate = trainRepository.findAll().size();
        train.setId(count.incrementAndGet());

        // Create the Train
        TrainDTO trainDTO = trainMapper.toDto(train);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(trainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTrain() throws Exception {
        int databaseSizeBeforeUpdate = trainRepository.findAll().size();
        train.setId(count.incrementAndGet());

        // Create the Train
        TrainDTO trainDTO = trainMapper.toDto(train);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(trainDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTrainWithPatch() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        int databaseSizeBeforeUpdate = trainRepository.findAll().size();

        // Update the train using partial update
        Train partialUpdatedTrain = new Train();
        partialUpdatedTrain.setId(train.getId());

        partialUpdatedTrain.code(UPDATED_CODE).numberOfSeats(UPDATED_NUMBER_OF_SEATS);

        restTrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrain))
            )
            .andExpect(status().isOk());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
        Train testTrain = trainList.get(trainList.size() - 1);
        assertThat(testTrain.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrain.getNumberOfSeats()).isEqualTo(UPDATED_NUMBER_OF_SEATS);
    }

    @Test
    @Transactional
    void fullUpdateTrainWithPatch() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        int databaseSizeBeforeUpdate = trainRepository.findAll().size();

        // Update the train using partial update
        Train partialUpdatedTrain = new Train();
        partialUpdatedTrain.setId(train.getId());

        partialUpdatedTrain.code(UPDATED_CODE).numberOfSeats(UPDATED_NUMBER_OF_SEATS);

        restTrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTrain.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTrain))
            )
            .andExpect(status().isOk());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
        Train testTrain = trainList.get(trainList.size() - 1);
        assertThat(testTrain.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testTrain.getNumberOfSeats()).isEqualTo(UPDATED_NUMBER_OF_SEATS);
    }

    @Test
    @Transactional
    void patchNonExistingTrain() throws Exception {
        int databaseSizeBeforeUpdate = trainRepository.findAll().size();
        train.setId(count.incrementAndGet());

        // Create the Train
        TrainDTO trainDTO = trainMapper.toDto(train);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, trainDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTrain() throws Exception {
        int databaseSizeBeforeUpdate = trainRepository.findAll().size();
        train.setId(count.incrementAndGet());

        // Create the Train
        TrainDTO trainDTO = trainMapper.toDto(train);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(trainDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTrain() throws Exception {
        int databaseSizeBeforeUpdate = trainRepository.findAll().size();
        train.setId(count.incrementAndGet());

        // Create the Train
        TrainDTO trainDTO = trainMapper.toDto(train);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTrainMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(trainDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Train in the database
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTrain() throws Exception {
        // Initialize the database
        trainRepository.saveAndFlush(train);

        int databaseSizeBeforeDelete = trainRepository.findAll().size();

        // Delete the train
        restTrainMockMvc
            .perform(delete(ENTITY_API_URL_ID, train.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Train> trainList = trainRepository.findAll();
        assertThat(trainList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
