package ro.oltpapp.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ro.oltpapp.domain.Journey} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JourneyDTO implements Serializable {

    private Long id;

    @NotNull
    private Double distance;

    @NotNull
    private Double journeyDuration;

    private ZonedDateTime actualDepartureTime;

    @NotNull
    private ZonedDateTime plannedDepartureTime;

    private ZonedDateTime actualArrivalTime;

    @NotNull
    private ZonedDateTime plannedArrivalTime;

    @NotNull
    private Double ticketPrice;

    private Integer numberOfStops;

    @NotNull
    private Double timeOfStops;

    @NotNull
    private Double minutesLate;

    private JourneyStatusDTO idJourneyStatus;

    private TrainDTO idTrain;

    private CompanyDTO idCompany;

    private RailwayStationDTO idRailwayStationDeparture;

    private RailwayStationDTO idRailwayStationArrival;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getJourneyDuration() {
        return journeyDuration;
    }

    public void setJourneyDuration(Double journeyDuration) {
        this.journeyDuration = journeyDuration;
    }

    public ZonedDateTime getActualDepartureTime() {
        return actualDepartureTime;
    }

    public void setActualDepartureTime(ZonedDateTime actualDepartureTime) {
        this.actualDepartureTime = actualDepartureTime;
    }

    public ZonedDateTime getPlannedDepartureTime() {
        return plannedDepartureTime;
    }

    public void setPlannedDepartureTime(ZonedDateTime plannedDepartureTime) {
        this.plannedDepartureTime = plannedDepartureTime;
    }

    public ZonedDateTime getActualArrivalTime() {
        return actualArrivalTime;
    }

    public void setActualArrivalTime(ZonedDateTime actualArrivalTime) {
        this.actualArrivalTime = actualArrivalTime;
    }

    public ZonedDateTime getPlannedArrivalTime() {
        return plannedArrivalTime;
    }

    public void setPlannedArrivalTime(ZonedDateTime plannedArrivalTime) {
        this.plannedArrivalTime = plannedArrivalTime;
    }

    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getNumberOfStops() {
        return numberOfStops;
    }

    public void setNumberOfStops(Integer numberOfStops) {
        this.numberOfStops = numberOfStops;
    }

    public Double getTimeOfStops() {
        return timeOfStops;
    }

    public void setTimeOfStops(Double timeOfStops) {
        this.timeOfStops = timeOfStops;
    }

    public Double getMinutesLate() {
        return minutesLate;
    }

    public void setMinutesLate(Double minutesLate) {
        this.minutesLate = minutesLate;
    }

    public JourneyStatusDTO getIdJourneyStatus() {
        return idJourneyStatus;
    }

    public void setIdJourneyStatus(JourneyStatusDTO idJourneyStatus) {
        this.idJourneyStatus = idJourneyStatus;
    }

    public TrainDTO getIdTrain() {
        return idTrain;
    }

    public void setIdTrain(TrainDTO idTrain) {
        this.idTrain = idTrain;
    }

    public CompanyDTO getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(CompanyDTO idCompany) {
        this.idCompany = idCompany;
    }

    public RailwayStationDTO getIdRailwayStationDeparture() {
        return idRailwayStationDeparture;
    }

    public void setIdRailwayStationDeparture(RailwayStationDTO idRailwayStationDeparture) {
        this.idRailwayStationDeparture = idRailwayStationDeparture;
    }

    public RailwayStationDTO getIdRailwayStationArrival() {
        return idRailwayStationArrival;
    }

    public void setIdRailwayStationArrival(RailwayStationDTO idRailwayStationArrival) {
        this.idRailwayStationArrival = idRailwayStationArrival;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JourneyDTO)) {
            return false;
        }

        JourneyDTO journeyDTO = (JourneyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, journeyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JourneyDTO{" +
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
            ", idJourneyStatus=" + getIdJourneyStatus() +
            ", idTrain=" + getIdTrain() +
            ", idCompany=" + getIdCompany() +
            ", idRailwayStationDeparture=" + getIdRailwayStationDeparture() +
            ", idRailwayStationArrival=" + getIdRailwayStationArrival() +
            "}";
    }
}
