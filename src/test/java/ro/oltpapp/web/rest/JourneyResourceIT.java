package ro.oltpapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ro.oltpapp.web.rest.TestUtil.sameInstant;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import ro.oltpapp.domain.Journey;
import ro.oltpapp.repository.JourneyRepository;
import ro.oltpapp.service.dto.JourneyDTO;
import ro.oltpapp.service.mapper.JourneyMapper;

/**
 * Integration tests for the {@link JourneyResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JourneyResourceIT {

    private static final Double DEFAULT_DISTANCE = 1D;
    private static final Double UPDATED_DISTANCE = 2D;

    private static final Double DEFAULT_JOURNEY_DURATION = 1D;
    private static final Double UPDATED_JOURNEY_DURATION = 2D;

    private static final ZonedDateTime DEFAULT_ACTUAL_DEPARTURE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTUAL_DEPARTURE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PLANNED_DEPARTURE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PLANNED_DEPARTURE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_ACTUAL_ARRIVAL_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTUAL_ARRIVAL_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_PLANNED_ARRIVAL_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PLANNED_ARRIVAL_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Double DEFAULT_TICKET_PRICE = 1D;
    private static final Double UPDATED_TICKET_PRICE = 2D;

    private static final Integer DEFAULT_NUMBER_OF_STOPS = 1;
    private static final Integer UPDATED_NUMBER_OF_STOPS = 2;

    private static final Double DEFAULT_TIME_OF_STOPS = 1D;
    private static final Double UPDATED_TIME_OF_STOPS = 2D;

    private static final Double DEFAULT_MINUTES_LATE = 1D;
    private static final Double UPDATED_MINUTES_LATE = 2D;

    private static final String ENTITY_API_URL = "/api/journeys";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JourneyRepository journeyRepository;

    @Autowired
    private JourneyMapper journeyMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJourneyMockMvc;

    private Journey journey;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Journey createEntity(EntityManager em) {
        Journey journey = new Journey()
            .distance(DEFAULT_DISTANCE)
            .journeyDuration(DEFAULT_JOURNEY_DURATION)
            .actualDepartureTime(DEFAULT_ACTUAL_DEPARTURE_TIME)
            .plannedDepartureTime(DEFAULT_PLANNED_DEPARTURE_TIME)
            .actualArrivalTime(DEFAULT_ACTUAL_ARRIVAL_TIME)
            .plannedArrivalTime(DEFAULT_PLANNED_ARRIVAL_TIME)
            .ticketPrice(DEFAULT_TICKET_PRICE)
            .numberOfStops(DEFAULT_NUMBER_OF_STOPS)
            .timeOfStops(DEFAULT_TIME_OF_STOPS)
            .minutesLate(DEFAULT_MINUTES_LATE);
        return journey;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Journey createUpdatedEntity(EntityManager em) {
        Journey journey = new Journey()
            .distance(UPDATED_DISTANCE)
            .journeyDuration(UPDATED_JOURNEY_DURATION)
            .actualDepartureTime(UPDATED_ACTUAL_DEPARTURE_TIME)
            .plannedDepartureTime(UPDATED_PLANNED_DEPARTURE_TIME)
            .actualArrivalTime(UPDATED_ACTUAL_ARRIVAL_TIME)
            .plannedArrivalTime(UPDATED_PLANNED_ARRIVAL_TIME)
            .ticketPrice(UPDATED_TICKET_PRICE)
            .numberOfStops(UPDATED_NUMBER_OF_STOPS)
            .timeOfStops(UPDATED_TIME_OF_STOPS)
            .minutesLate(UPDATED_MINUTES_LATE);
        return journey;
    }

    @BeforeEach
    public void initTest() {
        journey = createEntity(em);
    }

    @Test
    @Transactional
    void createJourney() throws Exception {
        int databaseSizeBeforeCreate = journeyRepository.findAll().size();
        // Create the Journey
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);
        restJourneyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isCreated());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeCreate + 1);
        Journey testJourney = journeyList.get(journeyList.size() - 1);
        assertThat(testJourney.getDistance()).isEqualTo(DEFAULT_DISTANCE);
        assertThat(testJourney.getJourneyDuration()).isEqualTo(DEFAULT_JOURNEY_DURATION);
        assertThat(testJourney.getActualDepartureTime()).isEqualTo(DEFAULT_ACTUAL_DEPARTURE_TIME);
        assertThat(testJourney.getPlannedDepartureTime()).isEqualTo(DEFAULT_PLANNED_DEPARTURE_TIME);
        assertThat(testJourney.getActualArrivalTime()).isEqualTo(DEFAULT_ACTUAL_ARRIVAL_TIME);
        assertThat(testJourney.getPlannedArrivalTime()).isEqualTo(DEFAULT_PLANNED_ARRIVAL_TIME);
        assertThat(testJourney.getTicketPrice()).isEqualTo(DEFAULT_TICKET_PRICE);
        assertThat(testJourney.getNumberOfStops()).isEqualTo(DEFAULT_NUMBER_OF_STOPS);
        assertThat(testJourney.getTimeOfStops()).isEqualTo(DEFAULT_TIME_OF_STOPS);
        assertThat(testJourney.getMinutesLate()).isEqualTo(DEFAULT_MINUTES_LATE);
    }

    @Test
    @Transactional
    void createJourneyWithExistingId() throws Exception {
        // Create the Journey with an existing ID
        journey.setId(1L);
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        int databaseSizeBeforeCreate = journeyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJourneyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkDistanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = journeyRepository.findAll().size();
        // set the field null
        journey.setDistance(null);

        // Create the Journey, which fails.
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        restJourneyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isBadRequest());

        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkJourneyDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = journeyRepository.findAll().size();
        // set the field null
        journey.setJourneyDuration(null);

        // Create the Journey, which fails.
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        restJourneyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isBadRequest());

        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPlannedDepartureTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = journeyRepository.findAll().size();
        // set the field null
        journey.setPlannedDepartureTime(null);

        // Create the Journey, which fails.
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        restJourneyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isBadRequest());

        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPlannedArrivalTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = journeyRepository.findAll().size();
        // set the field null
        journey.setPlannedArrivalTime(null);

        // Create the Journey, which fails.
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        restJourneyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isBadRequest());

        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTicketPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = journeyRepository.findAll().size();
        // set the field null
        journey.setTicketPrice(null);

        // Create the Journey, which fails.
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        restJourneyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isBadRequest());

        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTimeOfStopsIsRequired() throws Exception {
        int databaseSizeBeforeTest = journeyRepository.findAll().size();
        // set the field null
        journey.setTimeOfStops(null);

        // Create the Journey, which fails.
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        restJourneyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isBadRequest());

        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMinutesLateIsRequired() throws Exception {
        int databaseSizeBeforeTest = journeyRepository.findAll().size();
        // set the field null
        journey.setMinutesLate(null);

        // Create the Journey, which fails.
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        restJourneyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isBadRequest());

        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllJourneys() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);

        // Get all the journeyList
        restJourneyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(journey.getId().intValue())))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].journeyDuration").value(hasItem(DEFAULT_JOURNEY_DURATION.doubleValue())))
            .andExpect(jsonPath("$.[*].actualDepartureTime").value(hasItem(sameInstant(DEFAULT_ACTUAL_DEPARTURE_TIME))))
            .andExpect(jsonPath("$.[*].plannedDepartureTime").value(hasItem(sameInstant(DEFAULT_PLANNED_DEPARTURE_TIME))))
            .andExpect(jsonPath("$.[*].actualArrivalTime").value(hasItem(sameInstant(DEFAULT_ACTUAL_ARRIVAL_TIME))))
            .andExpect(jsonPath("$.[*].plannedArrivalTime").value(hasItem(sameInstant(DEFAULT_PLANNED_ARRIVAL_TIME))))
            .andExpect(jsonPath("$.[*].ticketPrice").value(hasItem(DEFAULT_TICKET_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].numberOfStops").value(hasItem(DEFAULT_NUMBER_OF_STOPS)))
            .andExpect(jsonPath("$.[*].timeOfStops").value(hasItem(DEFAULT_TIME_OF_STOPS.doubleValue())))
            .andExpect(jsonPath("$.[*].minutesLate").value(hasItem(DEFAULT_MINUTES_LATE.doubleValue())));
    }

    @Test
    @Transactional
    void getJourney() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);

        // Get the journey
        restJourneyMockMvc
            .perform(get(ENTITY_API_URL_ID, journey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(journey.getId().intValue()))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE.doubleValue()))
            .andExpect(jsonPath("$.journeyDuration").value(DEFAULT_JOURNEY_DURATION.doubleValue()))
            .andExpect(jsonPath("$.actualDepartureTime").value(sameInstant(DEFAULT_ACTUAL_DEPARTURE_TIME)))
            .andExpect(jsonPath("$.plannedDepartureTime").value(sameInstant(DEFAULT_PLANNED_DEPARTURE_TIME)))
            .andExpect(jsonPath("$.actualArrivalTime").value(sameInstant(DEFAULT_ACTUAL_ARRIVAL_TIME)))
            .andExpect(jsonPath("$.plannedArrivalTime").value(sameInstant(DEFAULT_PLANNED_ARRIVAL_TIME)))
            .andExpect(jsonPath("$.ticketPrice").value(DEFAULT_TICKET_PRICE.doubleValue()))
            .andExpect(jsonPath("$.numberOfStops").value(DEFAULT_NUMBER_OF_STOPS))
            .andExpect(jsonPath("$.timeOfStops").value(DEFAULT_TIME_OF_STOPS.doubleValue()))
            .andExpect(jsonPath("$.minutesLate").value(DEFAULT_MINUTES_LATE.doubleValue()));
    }

    @Test
    @Transactional
    void getNonExistingJourney() throws Exception {
        // Get the journey
        restJourneyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingJourney() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);

        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();

        // Update the journey
        Journey updatedJourney = journeyRepository.findById(journey.getId()).get();
        // Disconnect from session so that the updates on updatedJourney are not directly saved in db
        em.detach(updatedJourney);
        updatedJourney
            .distance(UPDATED_DISTANCE)
            .journeyDuration(UPDATED_JOURNEY_DURATION)
            .actualDepartureTime(UPDATED_ACTUAL_DEPARTURE_TIME)
            .plannedDepartureTime(UPDATED_PLANNED_DEPARTURE_TIME)
            .actualArrivalTime(UPDATED_ACTUAL_ARRIVAL_TIME)
            .plannedArrivalTime(UPDATED_PLANNED_ARRIVAL_TIME)
            .ticketPrice(UPDATED_TICKET_PRICE)
            .numberOfStops(UPDATED_NUMBER_OF_STOPS)
            .timeOfStops(UPDATED_TIME_OF_STOPS)
            .minutesLate(UPDATED_MINUTES_LATE);
        JourneyDTO journeyDTO = journeyMapper.toDto(updatedJourney);

        restJourneyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, journeyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeUpdate);
        Journey testJourney = journeyList.get(journeyList.size() - 1);
        assertThat(testJourney.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testJourney.getJourneyDuration()).isEqualTo(UPDATED_JOURNEY_DURATION);
        assertThat(testJourney.getActualDepartureTime()).isEqualTo(UPDATED_ACTUAL_DEPARTURE_TIME);
        assertThat(testJourney.getPlannedDepartureTime()).isEqualTo(UPDATED_PLANNED_DEPARTURE_TIME);
        assertThat(testJourney.getActualArrivalTime()).isEqualTo(UPDATED_ACTUAL_ARRIVAL_TIME);
        assertThat(testJourney.getPlannedArrivalTime()).isEqualTo(UPDATED_PLANNED_ARRIVAL_TIME);
        assertThat(testJourney.getTicketPrice()).isEqualTo(UPDATED_TICKET_PRICE);
        assertThat(testJourney.getNumberOfStops()).isEqualTo(UPDATED_NUMBER_OF_STOPS);
        assertThat(testJourney.getTimeOfStops()).isEqualTo(UPDATED_TIME_OF_STOPS);
        assertThat(testJourney.getMinutesLate()).isEqualTo(UPDATED_MINUTES_LATE);
    }

    @Test
    @Transactional
    void putNonExistingJourney() throws Exception {
        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();
        journey.setId(count.incrementAndGet());

        // Create the Journey
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, journeyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJourney() throws Exception {
        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();
        journey.setId(count.incrementAndGet());

        // Create the Journey
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(journeyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJourney() throws Exception {
        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();
        journey.setId(count.incrementAndGet());

        // Create the Journey
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(journeyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJourneyWithPatch() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);

        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();

        // Update the journey using partial update
        Journey partialUpdatedJourney = new Journey();
        partialUpdatedJourney.setId(journey.getId());

        partialUpdatedJourney
            .distance(UPDATED_DISTANCE)
            .journeyDuration(UPDATED_JOURNEY_DURATION)
            .actualDepartureTime(UPDATED_ACTUAL_DEPARTURE_TIME)
            .plannedDepartureTime(UPDATED_PLANNED_DEPARTURE_TIME)
            .numberOfStops(UPDATED_NUMBER_OF_STOPS)
            .timeOfStops(UPDATED_TIME_OF_STOPS);

        restJourneyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourney.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJourney))
            )
            .andExpect(status().isOk());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeUpdate);
        Journey testJourney = journeyList.get(journeyList.size() - 1);
        assertThat(testJourney.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testJourney.getJourneyDuration()).isEqualTo(UPDATED_JOURNEY_DURATION);
        assertThat(testJourney.getActualDepartureTime()).isEqualTo(UPDATED_ACTUAL_DEPARTURE_TIME);
        assertThat(testJourney.getPlannedDepartureTime()).isEqualTo(UPDATED_PLANNED_DEPARTURE_TIME);
        assertThat(testJourney.getActualArrivalTime()).isEqualTo(DEFAULT_ACTUAL_ARRIVAL_TIME);
        assertThat(testJourney.getPlannedArrivalTime()).isEqualTo(DEFAULT_PLANNED_ARRIVAL_TIME);
        assertThat(testJourney.getTicketPrice()).isEqualTo(DEFAULT_TICKET_PRICE);
        assertThat(testJourney.getNumberOfStops()).isEqualTo(UPDATED_NUMBER_OF_STOPS);
        assertThat(testJourney.getTimeOfStops()).isEqualTo(UPDATED_TIME_OF_STOPS);
        assertThat(testJourney.getMinutesLate()).isEqualTo(DEFAULT_MINUTES_LATE);
    }

    @Test
    @Transactional
    void fullUpdateJourneyWithPatch() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);

        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();

        // Update the journey using partial update
        Journey partialUpdatedJourney = new Journey();
        partialUpdatedJourney.setId(journey.getId());

        partialUpdatedJourney
            .distance(UPDATED_DISTANCE)
            .journeyDuration(UPDATED_JOURNEY_DURATION)
            .actualDepartureTime(UPDATED_ACTUAL_DEPARTURE_TIME)
            .plannedDepartureTime(UPDATED_PLANNED_DEPARTURE_TIME)
            .actualArrivalTime(UPDATED_ACTUAL_ARRIVAL_TIME)
            .plannedArrivalTime(UPDATED_PLANNED_ARRIVAL_TIME)
            .ticketPrice(UPDATED_TICKET_PRICE)
            .numberOfStops(UPDATED_NUMBER_OF_STOPS)
            .timeOfStops(UPDATED_TIME_OF_STOPS)
            .minutesLate(UPDATED_MINUTES_LATE);

        restJourneyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJourney.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJourney))
            )
            .andExpect(status().isOk());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeUpdate);
        Journey testJourney = journeyList.get(journeyList.size() - 1);
        assertThat(testJourney.getDistance()).isEqualTo(UPDATED_DISTANCE);
        assertThat(testJourney.getJourneyDuration()).isEqualTo(UPDATED_JOURNEY_DURATION);
        assertThat(testJourney.getActualDepartureTime()).isEqualTo(UPDATED_ACTUAL_DEPARTURE_TIME);
        assertThat(testJourney.getPlannedDepartureTime()).isEqualTo(UPDATED_PLANNED_DEPARTURE_TIME);
        assertThat(testJourney.getActualArrivalTime()).isEqualTo(UPDATED_ACTUAL_ARRIVAL_TIME);
        assertThat(testJourney.getPlannedArrivalTime()).isEqualTo(UPDATED_PLANNED_ARRIVAL_TIME);
        assertThat(testJourney.getTicketPrice()).isEqualTo(UPDATED_TICKET_PRICE);
        assertThat(testJourney.getNumberOfStops()).isEqualTo(UPDATED_NUMBER_OF_STOPS);
        assertThat(testJourney.getTimeOfStops()).isEqualTo(UPDATED_TIME_OF_STOPS);
        assertThat(testJourney.getMinutesLate()).isEqualTo(UPDATED_MINUTES_LATE);
    }

    @Test
    @Transactional
    void patchNonExistingJourney() throws Exception {
        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();
        journey.setId(count.incrementAndGet());

        // Create the Journey
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJourneyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, journeyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJourney() throws Exception {
        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();
        journey.setId(count.incrementAndGet());

        // Create the Journey
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(journeyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJourney() throws Exception {
        int databaseSizeBeforeUpdate = journeyRepository.findAll().size();
        journey.setId(count.incrementAndGet());

        // Create the Journey
        JourneyDTO journeyDTO = journeyMapper.toDto(journey);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJourneyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(journeyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Journey in the database
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJourney() throws Exception {
        // Initialize the database
        journeyRepository.saveAndFlush(journey);

        int databaseSizeBeforeDelete = journeyRepository.findAll().size();

        // Delete the journey
        restJourneyMockMvc
            .perform(delete(ENTITY_API_URL_ID, journey.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Journey> journeyList = journeyRepository.findAll();
        assertThat(journeyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
