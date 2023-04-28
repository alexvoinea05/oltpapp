package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Journey.
 */
@Entity
@Table(name = "journey")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Journey implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "distance", nullable = false)
    private Double distance;

    @NotNull
    @Column(name = "journey_duration", nullable = false)
    private Double journeyDuration;

    @Column(name = "actual_departure_time")
    private ZonedDateTime actualDepartureTime;

    @NotNull
    @Column(name = "planned_departure_time", nullable = false)
    private ZonedDateTime plannedDepartureTime;

    @Column(name = "actual_arrival_time")
    private ZonedDateTime actualArrivalTime;

    @NotNull
    @Column(name = "planned_arrival_time", nullable = false)
    private ZonedDateTime plannedArrivalTime;

    @NotNull
    @Column(name = "ticket_price", nullable = false)
    private Double ticketPrice;

    @Column(name = "number_of_stops")
    private Integer numberOfStops;

    @NotNull
    @Column(name = "time_of_stops", nullable = false)
    private Double timeOfStops;

    @NotNull
    @Column(name = "minutes_late", nullable = false)
    private Double minutesLate;

    @OneToMany(mappedBy = "idJourney")
    @JsonIgnoreProperties(value = { "idIncidentStatus", "idAppUser", "idJourney" }, allowSetters = true)
    private Set<Incident> incidents = new HashSet<>();

    @OneToMany(mappedBy = "idJourney")
    @JsonIgnoreProperties(value = { "idAppUser", "idJourney" }, allowSetters = true)
    private Set<Ticket> tickets = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "journey_status_id")
    @JsonIgnoreProperties(value = { "journeys" }, allowSetters = true)
    private JourneyStatus idJourneyStatus;

    @ManyToOne
    @JoinColumn(name = "train_id")
    @JsonIgnoreProperties(value = { "journeys", "idFuelType", "idTrainType" }, allowSetters = true)
    private Train idTrain;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties(value = { "journeys", "companyXLicenses" }, allowSetters = true)
    private Company idCompany;

    @ManyToOne
    @JoinColumn(name = "departure_railway_station_id")
    @JsonIgnoreProperties(value = { "idJourneyDepartures", "idJourneyArrivals", "idRailwayType", "idAddress" }, allowSetters = true)
    private RailwayStation idRailwayStationDeparture;

    @ManyToOne
    @JoinColumn(name = "arrival_railway_station_id")
    @JsonIgnoreProperties(value = { "idJourneyDepartures", "idJourneyArrivals", "idRailwayType", "idAddress" }, allowSetters = true)
    private RailwayStation idRailwayStationArrival;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Journey id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDistance() {
        return this.distance;
    }

    public Journey distance(Double distance) {
        this.setDistance(distance);
        return this;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getJourneyDuration() {
        return this.journeyDuration;
    }

    public Journey journeyDuration(Double journeyDuration) {
        this.setJourneyDuration(journeyDuration);
        return this;
    }

    public void setJourneyDuration(Double journeyDuration) {
        this.journeyDuration = journeyDuration;
    }

    public ZonedDateTime getActualDepartureTime() {
        return this.actualDepartureTime;
    }

    public Journey actualDepartureTime(ZonedDateTime actualDepartureTime) {
        this.setActualDepartureTime(actualDepartureTime);
        return this;
    }

    public void setActualDepartureTime(ZonedDateTime actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
    }

    public ZonedDateTime getPlannedDepartureTime() {
        return this.plannedDepartureTime;
    }

    public Journey plannedDepartureTime(ZonedDateTime plannedDepartureTime) {
        this.setPlannedDepartureTime(plannedDepartureTime);
        return this;
    }

    public void setPlannedDepartureTime(ZonedDateTime plannedDepartureTime) {
        this.plannedDepartureTime = plannedDepartureTime;
    }

    public ZonedDateTime getActualArrivalTime() {
        return this.actualArrivalTime;
    }

    public Journey actualArrivalTime(ZonedDateTime actualArrivalTime) {
        this.setActualArrivalTime(actualArrivalTime);
        return this;
    }

    public void setActualArrivalTime(ZonedDateTime actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public ZonedDateTime getPlannedArrivalTime() {
        return this.plannedArrivalTime;
    }

    public Journey plannedArrivalTime(ZonedDateTime plannedArrivalTime) {
        this.setPlannedArrivalTime(plannedArrivalTime);
        return this;
    }

    public void setPlannedArrivalTime(ZonedDateTime plannedArrivalTime) {
        this.plannedArrivalTime = plannedArrivalTime;
    }

    public Double getTicketPrice() {
        return this.ticketPrice;
    }

    public Journey ticketPrice(Double ticketPrice) {
        this.setTicketPrice(ticketPrice);
        return this;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getNumberOfStops() {
        return this.numberOfStops;
    }

    public Journey numberOfStops(Integer numberOfStops) {
        this.setNumberOfStops(numberOfStops);
        return this;
    }

    public void setNumberOfStops(Integer numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public Double getTimeOfStops() {
        return this.timeOfStops;
    }

    public Journey timeOfStops(Double timeOfStops) {
        this.setTimeOfStops(timeOfStops);
        return this;
    }

    public void setTimeOfStops(Double timeOfStops) {
        this.timeOfStops = timeOfStops;
    }

    public Double getMinutesLate() {
        return this.minutesLate;
    }

    public Journey minutesLate(Double minutesLate) {
        this.setMinutesLate(minutesLate);
        return this;
    }

    public void setMinutesLate(Double minutesLate) {
        this.minutesLate = minutesLate;
    }

    public Set<Incident> getIncidents() {
        return this.incidents;
    }

    public void setIncidents(Set<Incident> incidents) {
        if (this.incidents != null) {
            this.incidents.forEach(i -> i.setIdJourney(null));
        }
        if (incidents != null) {
            incidents.forEach(i -> i.setIdJourney(this));
        }
        this.incidents = incidents;
    }

    public Journey incidents(Set<Incident> incidents) {
        this.setIncidents(incidents);
        return this;
    }

    public Journey addIncident(Incident incident) {
        this.incidents.add(incident);
        incident.setIdJourney(this);
        return this;
    }

    public Journey removeIncident(Incident incident) {
        this.incidents.remove(incident);
        incident.setIdJourney(null);
        return this;
    }

    public Set<Ticket> getTickets() {
        return this.tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        if (this.tickets != null) {
            this.tickets.forEach(i -> i.setIdJourney(null));
        }
        if (tickets != null) {
            tickets.forEach(i -> i.setIdJourney(this));
        }
        this.tickets = tickets;
    }

    public Journey tickets(Set<Ticket> tickets) {
        this.setTickets(tickets);
        return this;
    }

    public Journey addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        ticket.setIdJourney(this);
        return this;
    }

    public Journey removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
        ticket.setIdJourney(null);
        return this;
    }

    public JourneyStatus getIdJourneyStatus() {
        return this.idJourneyStatus;
    }

    public void setIdJourneyStatus(JourneyStatus journeyStatus) {
        this.idJourneyStatus = journeyStatus;
    }

    public Journey idJourneyStatus(JourneyStatus journeyStatus) {
        this.setIdJourneyStatus(journeyStatus);
        return this;
    }

    public Train getIdTrain() {
        return this.idTrain;
    }

    public void setIdTrain(Train train) {
        this.idTrain = train;
    }

    public Journey idTrain(Train train) {
        this.setIdTrain(train);
        return this;
    }

    public Company getIdCompany() {
        return this.idCompany;
    }

    public void setIdCompany(Company company) {
        this.idCompany = company;
    }

    public Journey idCompany(Company company) {
        this.setIdCompany(company);
        return this;
    }

    public RailwayStation getIdRailwayStationDeparture() {
        return this.idRailwayStationDeparture;
    }

    public void setIdRailwayStationDeparture(RailwayStation railwayStation) {
        this.idRailwayStationDeparture = railwayStation;
    }

    public Journey idRailwayStationDeparture(RailwayStation railwayStation) {
        this.setIdRailwayStationDeparture(railwayStation);
        return this;
    }

    public RailwayStation getIdRailwayStationArrival() {
        return this.idRailwayStationArrival;
    }

    public void setIdRailwayStationArrival(RailwayStation railwayStation) {
        this.idRailwayStationArrival = railwayStation;
    }

    public Journey idRailwayStationArrival(RailwayStation railwayStation) {
        this.setIdRailwayStationArrival(railwayStation);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Journey)) {
            return false;
        }
        return id != null && id.equals(((Journey) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Journey{" +
            "id=" + getId() +
            ", distance=" + getDistance() +
            ", journeyDuration=" + getJourneyDuration() +
            ", actualDepartureTime='" + getActualDepartureTime() + "'" +
            ", plannedDepartureTime='" + getPlannedDepartureTime() + "'" +
            ", actualArrivalTime='" + getActualArrivalTime() + "'" +
            ", plannedArrivalTime='" + getPlannedArrivalTime() + "'" +
            ", ticketPrice=" + getTicketPrice() +
            ", numberOfStops=" + getNumberOfStops() +
            ", timeOfStops=" + getTimeOfStops() +
            ", minutesLate=" + getMinutesLate() +
            "}";
    }
}
