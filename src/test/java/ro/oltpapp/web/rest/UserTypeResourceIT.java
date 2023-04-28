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
import ro.oltpapp.domain.UserType;
import ro.oltpapp.repository.UserTypeRepository;
import ro.oltpapp.service.dto.UserTypeDTO;
import ro.oltpapp.service.mapper.UserTypeMapper;

/**
 * Integration tests for the {@link UserTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_DISCOUNT = 1D;
    private static final Double UPDATED_DISCOUNT = 2D;

    private static final String ENTITY_API_URL = "/api/user-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private UserTypeMapper userTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserTypeMockMvc;

    private UserType userType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserType createEntity(EntityManager em) {
        UserType userType = new UserType().code(DEFAULT_CODE).discount(DEFAULT_DISCOUNT);
        return userType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserType createUpdatedEntity(EntityManager em) {
        UserType userType = new UserType().code(UPDATED_CODE).discount(UPDATED_DISCOUNT);
        return userType;
    }

    @BeforeEach
    public void initTest() {
        userType = createEntity(em);
    }

    @Test
    @Transactional
    void createUserType() throws Exception {
        int databaseSizeBeforeCreate = userTypeRepository.findAll().size();
        // Create the UserType
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(userType);
        restUserTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeCreate + 1);
        UserType testUserType = userTypeList.get(userTypeList.size() - 1);
        assertThat(testUserType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testUserType.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void createUserTypeWithExistingId() throws Exception {
        // Create the UserType with an existing ID
        userType.setId(1L);
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(userType);

        int databaseSizeBeforeCreate = userTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userTypeRepository.findAll().size();
        // set the field null
        userType.setCode(null);

        // Create the UserType, which fails.
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(userType);

        restUserTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTypeDTO)))
            .andExpect(status().isBadRequest());

        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDiscountIsRequired() throws Exception {
        int databaseSizeBeforeTest = userTypeRepository.findAll().size();
        // set the field null
        userType.setDiscount(null);

        // Create the UserType, which fails.
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(userType);

        restUserTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTypeDTO)))
            .andExpect(status().isBadRequest());

        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserTypes() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        // Get all the userTypeList
        restUserTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT.doubleValue())));
    }

    @Test
    @Transactional
    void getUserType() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        // Get the userType
        restUserTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, userType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingUserType() throws Exception {
        // Get the userType
        restUserTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingUserType() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();

        // Update the userType
        UserType updatedUserType = userTypeRepository.findById(userType.getId()).get();
        // Disconnect from session so that the updates on updatedUserType are not directly saved in db
        em.detach(updatedUserType);
        updatedUserType.code(UPDATED_CODE).discount(UPDATED_DISCOUNT);
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(updatedUserType);

        restUserTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate);
        UserType testUserType = userTypeList.get(userTypeList.size() - 1);
        assertThat(testUserType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUserType.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void putNonExistingUserType() throws Exception {
        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();
        userType.setId(count.incrementAndGet());

        // Create the UserType
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(userType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserType() throws Exception {
        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();
        userType.setId(count.incrementAndGet());

        // Create the UserType
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(userType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserType() throws Exception {
        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();
        userType.setId(count.incrementAndGet());

        // Create the UserType
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(userType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserTypeWithPatch() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();

        // Update the userType using partial update
        UserType partialUpdatedUserType = new UserType();
        partialUpdatedUserType.setId(userType.getId());

        partialUpdatedUserType.code(UPDATED_CODE);

        restUserTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserType))
            )
            .andExpect(status().isOk());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate);
        UserType testUserType = userTypeList.get(userTypeList.size() - 1);
        assertThat(testUserType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUserType.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
    }

    @Test
    @Transactional
    void fullUpdateUserTypeWithPatch() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();

        // Update the userType using partial update
        UserType partialUpdatedUserType = new UserType();
        partialUpdatedUserType.setId(userType.getId());

        partialUpdatedUserType.code(UPDATED_CODE).discount(UPDATED_DISCOUNT);

        restUserTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserType))
            )
            .andExpect(status().isOk());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate);
        UserType testUserType = userTypeList.get(userTypeList.size() - 1);
        assertThat(testUserType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testUserType.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingUserType() throws Exception {
        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();
        userType.setId(count.incrementAndGet());

        // Create the UserType
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(userType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserType() throws Exception {
        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();
        userType.setId(count.incrementAndGet());

        // Create the UserType
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(userType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserType() throws Exception {
        int databaseSizeBeforeUpdate = userTypeRepository.findAll().size();
        userType.setId(count.incrementAndGet());

        // Create the UserType
        UserTypeDTO userTypeDTO = userTypeMapper.toDto(userType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(userTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserType in the database
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserType() throws Exception {
        // Initialize the database
        userTypeRepository.saveAndFlush(userType);

        int databaseSizeBeforeDelete = userTypeRepository.findAll().size();

        // Delete the userType
        restUserTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, userType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserType> userTypeList = userTypeRepository.findAll();
        assertThat(userTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
