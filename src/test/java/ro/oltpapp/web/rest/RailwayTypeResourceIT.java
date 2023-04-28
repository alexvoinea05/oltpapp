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
import ro.oltpapp.domain.RailwayType;
import ro.oltpapp.repository.RailwayTypeRepository;
import ro.oltpapp.service.dto.RailwayTypeDTO;
import ro.oltpapp.service.mapper.RailwayTypeMapper;

/**
 * Integration tests for the {@link RailwayTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class RailwayTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/railway-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private RailwayTypeRepository railwayTypeRepository;

    @Autowired
    private RailwayTypeMapper railwayTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRailwayTypeMockMvc;

    private RailwayType railwayType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RailwayType createEntity(EntityManager em) {
        RailwayType railwayType = new RailwayType().code(DEFAULT_CODE).description(DEFAULT_DESCRIPTION);
        return railwayType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RailwayType createUpdatedEntity(EntityManager em) {
        RailwayType railwayType = new RailwayType().code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        return railwayType;
    }

    @BeforeEach
    public void initTest() {
        railwayType = createEntity(em);
    }

    @Test
    @Transactional
    void createRailwayType() throws Exception {
        int databaseSizeBeforeCreate = railwayTypeRepository.findAll().size();
        // Create the RailwayType
        RailwayTypeDTO railwayTypeDTO = railwayTypeMapper.toDto(railwayType);
        restRailwayTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the RailwayType in the database
        List<RailwayType> railwayTypeList = railwayTypeRepository.findAll();
        assertThat(railwayTypeList).hasSize(databaseSizeBeforeCreate + 1);
        RailwayType testRailwayType = railwayTypeList.get(railwayTypeList.size() - 1);
        assertThat(testRailwayType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRailwayType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    void createRailwayTypeWithExistingId() throws Exception {
        // Create the RailwayType with an existing ID
        railwayType.setId(1L);
        RailwayTypeDTO railwayTypeDTO = railwayTypeMapper.toDto(railwayType);

        int databaseSizeBeforeCreate = railwayTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restRailwayTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayType in the database
        List<RailwayType> railwayTypeList = railwayTypeRepository.findAll();
        assertThat(railwayTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = railwayTypeRepository.findAll().size();
        // set the field null
        railwayType.setCode(null);

        // Create the RailwayType, which fails.
        RailwayTypeDTO railwayTypeDTO = railwayTypeMapper.toDto(railwayType);

        restRailwayTypeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<RailwayType> railwayTypeList = railwayTypeRepository.findAll();
        assertThat(railwayTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllRailwayTypes() throws Exception {
        // Initialize the database
        railwayTypeRepository.saveAndFlush(railwayType);

        // Get all the railwayTypeList
        restRailwayTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(railwayType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    void getRailwayType() throws Exception {
        // Initialize the database
        railwayTypeRepository.saveAndFlush(railwayType);

        // Get the railwayType
        restRailwayTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, railwayType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(railwayType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    void getNonExistingRailwayType() throws Exception {
        // Get the railwayType
        restRailwayTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingRailwayType() throws Exception {
        // Initialize the database
        railwayTypeRepository.saveAndFlush(railwayType);

        int databaseSizeBeforeUpdate = railwayTypeRepository.findAll().size();

        // Update the railwayType
        RailwayType updatedRailwayType = railwayTypeRepository.findById(railwayType.getId()).get();
        // Disconnect from session so that the updates on updatedRailwayType are not directly saved in db
        em.detach(updatedRailwayType);
        updatedRailwayType.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);
        RailwayTypeDTO railwayTypeDTO = railwayTypeMapper.toDto(updatedRailwayType);

        restRailwayTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, railwayTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the RailwayType in the database
        List<RailwayType> railwayTypeList = railwayTypeRepository.findAll();
        assertThat(railwayTypeList).hasSize(databaseSizeBeforeUpdate);
        RailwayType testRailwayType = railwayTypeList.get(railwayTypeList.size() - 1);
        assertThat(testRailwayType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRailwayType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void putNonExistingRailwayType() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeRepository.findAll().size();
        railwayType.setId(count.incrementAndGet());

        // Create the RailwayType
        RailwayTypeDTO railwayTypeDTO = railwayTypeMapper.toDto(railwayType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRailwayTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, railwayTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayType in the database
        List<RailwayType> railwayTypeList = railwayTypeRepository.findAll();
        assertThat(railwayTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchRailwayType() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeRepository.findAll().size();
        railwayType.setId(count.incrementAndGet());

        // Create the RailwayType
        RailwayTypeDTO railwayTypeDTO = railwayTypeMapper.toDto(railwayType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayType in the database
        List<RailwayType> railwayTypeList = railwayTypeRepository.findAll();
        assertThat(railwayTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamRailwayType() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeRepository.findAll().size();
        railwayType.setId(count.incrementAndGet());

        // Create the RailwayType
        RailwayTypeDTO railwayTypeDTO = railwayTypeMapper.toDto(railwayType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(railwayTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the RailwayType in the database
        List<RailwayType> railwayTypeList = railwayTypeRepository.findAll();
        assertThat(railwayTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateRailwayTypeWithPatch() throws Exception {
        // Initialize the database
        railwayTypeRepository.saveAndFlush(railwayType);

        int databaseSizeBeforeUpdate = railwayTypeRepository.findAll().size();

        // Update the railwayType using partial update
        RailwayType partialUpdatedRailwayType = new RailwayType();
        partialUpdatedRailwayType.setId(railwayType.getId());

        partialUpdatedRailwayType.description(UPDATED_DESCRIPTION);

        restRailwayTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRailwayType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRailwayType))
            )
            .andExpect(status().isOk());

        // Validate the RailwayType in the database
        List<RailwayType> railwayTypeList = railwayTypeRepository.findAll();
        assertThat(railwayTypeList).hasSize(databaseSizeBeforeUpdate);
        RailwayType testRailwayType = railwayTypeList.get(railwayTypeList.size() - 1);
        assertThat(testRailwayType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRailwayType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void fullUpdateRailwayTypeWithPatch() throws Exception {
        // Initialize the database
        railwayTypeRepository.saveAndFlush(railwayType);

        int databaseSizeBeforeUpdate = railwayTypeRepository.findAll().size();

        // Update the railwayType using partial update
        RailwayType partialUpdatedRailwayType = new RailwayType();
        partialUpdatedRailwayType.setId(railwayType.getId());

        partialUpdatedRailwayType.code(UPDATED_CODE).description(UPDATED_DESCRIPTION);

        restRailwayTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedRailwayType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedRailwayType))
            )
            .andExpect(status().isOk());

        // Validate the RailwayType in the database
        List<RailwayType> railwayTypeList = railwayTypeRepository.findAll();
        assertThat(railwayTypeList).hasSize(databaseSizeBeforeUpdate);
        RailwayType testRailwayType = railwayTypeList.get(railwayTypeList.size() - 1);
        assertThat(testRailwayType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRailwayType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void patchNonExistingRailwayType() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeRepository.findAll().size();
        railwayType.setId(count.incrementAndGet());

        // Create the RailwayType
        RailwayTypeDTO railwayTypeDTO = railwayTypeMapper.toDto(railwayType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRailwayTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, railwayTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayType in the database
        List<RailwayType> railwayTypeList = railwayTypeRepository.findAll();
        assertThat(railwayTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchRailwayType() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeRepository.findAll().size();
        railwayType.setId(count.incrementAndGet());

        // Create the RailwayType
        RailwayTypeDTO railwayTypeDTO = railwayTypeMapper.toDto(railwayType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(railwayTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the RailwayType in the database
        List<RailwayType> railwayTypeList = railwayTypeRepository.findAll();
        assertThat(railwayTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamRailwayType() throws Exception {
        int databaseSizeBeforeUpdate = railwayTypeRepository.findAll().size();
        railwayType.setId(count.incrementAndGet());

        // Create the RailwayType
        RailwayTypeDTO railwayTypeDTO = railwayTypeMapper.toDto(railwayType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restRailwayTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(railwayTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the RailwayType in the database
        List<RailwayType> railwayTypeList = railwayTypeRepository.findAll();
        assertThat(railwayTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteRailwayType() throws Exception {
        // Initialize the database
        railwayTypeRepository.saveAndFlush(railwayType);

        int databaseSizeBeforeDelete = railwayTypeRepository.findAll().size();

        // Delete the railwayType
        restRailwayTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, railwayType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RailwayType> railwayTypeList = railwayTypeRepository.findAll();
        assertThat(railwayTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
