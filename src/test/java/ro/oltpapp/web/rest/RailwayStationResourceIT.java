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
import ro.oltpapp.domain.RailwayStation;
import ro.oltpapp.repository.RailwayStationRepository;
import ro.oltpapp.service.dto.RailwayStationDTO;
import ro.oltpapp.service.mapper.RailwayStationMapper;

/**
 * Integration tests for the {@link RailwayStationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RailwayStationResourceIT {

    private static final String DEFAULT_RAILWAY_STATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RAILWAY_STATION_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/railway-stations";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RailwayStationRepository railwayStationRepository;

    @Autowired
    private RailwayStationMapper railwayStationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRailwayStationMockMvc;

    private RailwayStation railwayStation;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RailwayStation createEntity(EntityManager em) {
        RailwayStation railwayStation = new RailwayStation().railwayStationName(DEFAULT_RAILWAY_STATION_NAME);
        return railwayStation;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RailwayStation createUpdatedEntity(EntityManager em) {
        RailwayStation railwayStation = new RailwayStation().railwayStationName(UPDATED_RAILWAY_STATION_NAME);
        return railwayStation;
    }

    @BeforeEach
    public void initTest() {
        railwayStation = createEntity(em);
    }

    @Test
    @Transactional
    void createRailwayStation() throws Exception {
        int databaseSizeBeforeCreate = railwayStationRepository.findAll().size();
        // Create the RailwayStation
        RailwayStationDTO railwayStationDTO = railwayStationMapper.toDto(railwayStation);
        restRailwayStationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayStationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RailwayStation in the database
        List<RailwayStation> railwayStationList = railwayStationRepository.findAll();
        assertThat(railwayStationList).hasSize(databaseSizeBeforeCreate + 1);
        RailwayStation testRailwayStation = railwayStationList.get(railwayStationList.size() - 1);
        assertThat(testRailwayStation.getRailwayStationName()).isEqualTo(DEFAULT_RAILWAY_STATION_NAME);
    }

    @Test
    @Transactional
    void createRailwayStationWithExistingId() throws Exception {
        // Create the RailwayStation with an existing ID
        railwayStation.setId(1L);
        RailwayStationDTO railwayStationDTO = railwayStationMapper.toDto(railwayStation);

        int databaseSizeBeforeCreate = railwayStationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRailwayStationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayStationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStation in the database
        List<RailwayStation> railwayStationList = railwayStationRepository.findAll();
        assertThat(railwayStationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRailwayStationNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = railwayStationRepository.findAll().size();
        // set the field null
        railwayStation.setRailwayStationName(null);

        // Create the RailwayStation, which fails.
        RailwayStationDTO railwayStationDTO = railwayStationMapper.toDto(railwayStation);

        restRailwayStationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayStationDTO))
            )
            .andExpect(status().isBadRequest());

        List<RailwayStation> railwayStationList = railwayStationRepository.findAll();
        assertThat(railwayStationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRailwayStations() throws Exception {
        // Initialize the database
        railwayStationRepository.saveAndFlush(railwayStation);

        // Get all the railwayStationList
        restRailwayStationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(railwayStation.getId().intValue())))
            .andExpect(jsonPath("$.[*].railwayStationName").value(hasItem(DEFAULT_RAILWAY_STATION_NAME)));
    }

    @Test
    @Transactional
    void getRailwayStation() throws Exception {
        // Initialize the database
        railwayStationRepository.saveAndFlush(railwayStation);

        // Get the railwayStation
        restRailwayStationMockMvc
            .perform(get(ENTITY_API_URL_ID, railwayStation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(railwayStation.getId().intValue()))
            .andExpect(jsonPath("$.railwayStationName").value(DEFAULT_RAILWAY_STATION_NAME));
    }

    @Test
    @Transactional
    void getNonExistingRailwayStation() throws Exception {
        // Get the railwayStation
        restRailwayStationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRailwayStation() throws Exception {
        // Initialize the database
        railwayStationRepository.saveAndFlush(railwayStation);

        int databaseSizeBeforeUpdate = railwayStationRepository.findAll().size();

        // Update the railwayStation
        RailwayStation updatedRailwayStation = railwayStationRepository.findById(railwayStation.getId()).get();
        // Disconnect from session so that the updates on updatedRailwayStation are not directly saved in db
        em.detach(updatedRailwayStation);
        updatedRailwayStation.railwayStationName(UPDATED_RAILWAY_STATION_NAME);
        RailwayStationDTO railwayStationDTO = railwayStationMapper.toDto(updatedRailwayStation);

        restRailwayStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, railwayStationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationDTO))
            )
            .andExpect(status().isOk());

        // Validate the RailwayStation in the database
        List<RailwayStation> railwayStationList = railwayStationRepository.findAll();
        assertThat(railwayStationList).hasSize(databaseSizeBeforeUpdate);
        RailwayStation testRailwayStation = railwayStationList.get(railwayStationList.size() - 1);
        assertThat(testRailwayStation.getRailwayStationName()).isEqualTo(UPDATED_RAILWAY_STATION_NAME);
    }

    @Test
    @Transactional
    void putNonExistingRailwayStation() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationRepository.findAll().size();
        railwayStation.setId(count.incrementAndGet());

        // Create the RailwayStation
        RailwayStationDTO railwayStationDTO = railwayStationMapper.toDto(railwayStation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRailwayStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, railwayStationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStation in the database
        List<RailwayStation> railwayStationList = railwayStationRepository.findAll();
        assertThat(railwayStationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRailwayStation() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationRepository.findAll().size();
        railwayStation.setId(count.incrementAndGet());

        // Create the RailwayStation
        RailwayStationDTO railwayStationDTO = railwayStationMapper.toDto(railwayStation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayStationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStation in the database
        List<RailwayStation> railwayStationList = railwayStationRepository.findAll();
        assertThat(railwayStationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRailwayStation() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationRepository.findAll().size();
        railwayStation.setId(count.incrementAndGet());

        // Create the RailwayStation
        RailwayStationDTO railwayStationDTO = railwayStationMapper.toDto(railwayStation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayStationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayStationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RailwayStation in the database
        List<RailwayStation> railwayStationList = railwayStationRepository.findAll();
        assertThat(railwayStationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRailwayStationWithPatch() throws Exception {
        // Initialize the database
        railwayStationRepository.saveAndFlush(railwayStation);

        int databaseSizeBeforeUpdate = railwayStationRepository.findAll().size();

        // Update the railwayStation using partial update
        RailwayStation partialUpdatedRailwayStation = new RailwayStation();
        partialUpdatedRailwayStation.setId(railwayStation.getId());

        partialUpdatedRailwayStation.railwayStationName(UPDATED_RAILWAY_STATION_NAME);

        restRailwayStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRailwayStation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRailwayStation))
            )
            .andExpect(status().isOk());

        // Validate the RailwayStation in the database
        List<RailwayStation> railwayStationList = railwayStationRepository.findAll();
        assertThat(railwayStationList).hasSize(databaseSizeBeforeUpdate);
        RailwayStation testRailwayStation = railwayStationList.get(railwayStationList.size() - 1);
        assertThat(testRailwayStation.getRailwayStationName()).isEqualTo(UPDATED_RAILWAY_STATION_NAME);
    }

    @Test
    @Transactional
    void fullUpdateRailwayStationWithPatch() throws Exception {
        // Initialize the database
        railwayStationRepository.saveAndFlush(railwayStation);

        int databaseSizeBeforeUpdate = railwayStationRepository.findAll().size();

        // Update the railwayStation using partial update
        RailwayStation partialUpdatedRailwayStation = new RailwayStation();
        partialUpdatedRailwayStation.setId(railwayStation.getId());

        partialUpdatedRailwayStation.railwayStationName(UPDATED_RAILWAY_STATION_NAME);

        restRailwayStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRailwayStation.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRailwayStation))
            )
            .andExpect(status().isOk());

        // Validate the RailwayStation in the database
        List<RailwayStation> railwayStationList = railwayStationRepository.findAll();
        assertThat(railwayStationList).hasSize(databaseSizeBeforeUpdate);
        RailwayStation testRailwayStation = railwayStationList.get(railwayStationList.size() - 1);
        assertThat(testRailwayStation.getRailwayStationName()).isEqualTo(UPDATED_RAILWAY_STATION_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingRailwayStation() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationRepository.findAll().size();
        railwayStation.setId(count.incrementAndGet());

        // Create the RailwayStation
        RailwayStationDTO railwayStationDTO = railwayStationMapper.toDto(railwayStation);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRailwayStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, railwayStationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStation in the database
        List<RailwayStation> railwayStationList = railwayStationRepository.findAll();
        assertThat(railwayStationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRailwayStation() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationRepository.findAll().size();
        railwayStation.setId(count.incrementAndGet());

        // Create the RailwayStation
        RailwayStationDTO railwayStationDTO = railwayStationMapper.toDto(railwayStation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayStationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayStation in the database
        List<RailwayStation> railwayStationList = railwayStationRepository.findAll();
        assertThat(railwayStationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRailwayStation() throws Exception {
        int databaseSizeBeforeUpdate = railwayStationRepository.findAll().size();
        railwayStation.setId(count.incrementAndGet());

        // Create the RailwayStation
        RailwayStationDTO railwayStationDTO = railwayStationMapper.toDto(railwayStation);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayStationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayStationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RailwayStation in the database
        List<RailwayStation> railwayStationList = railwayStationRepository.findAll();
        assertThat(railwayStationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRailwayStation() throws Exception {
        // Initialize the database
        railwayStationRepository.saveAndFlush(railwayStation);

        int databaseSizeBeforeDelete = railwayStationRepository.findAll().size();

        // Delete the railwayStation
        restRailwayStationMockMvc
            .perform(delete(ENTITY_API_URL_ID, railwayStation.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RailwayStation> railwayStationList = railwayStationRepository.findAll();
        assertThat(railwayStationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
