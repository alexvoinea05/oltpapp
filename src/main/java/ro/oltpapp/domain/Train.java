package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Train.
 */
@Entity
@Table(name = "train")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Train implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "number_of_seats", nullable = false)
    private Long numberOfSeats;

    @OneToMany(mappedBy = "idTrain")
    @JsonIgnoreProperties(
        value = {
            "incidents", "tickets", "idJourneyStatus", "idTrain", "idCompany", "idRailwayStationDeparture", "idRailwayStationArrival",
        },
        allowSetters = true
    )
    private Set<Journey> journeys = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "fuel_type_id")
    @JsonIgnoreProperties(value = { "trains" }, allowSetters = true)
    private FuelType idFuelType;

    @ManyToOne
    @JoinColumn(name = "train_type_id")
    @JsonIgnoreProperties(value = { "trains" }, allowSetters = true)
    private TrainType idTrainType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Train id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Train code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getNumberOfSeats() {
        return this.numberOfSeats;
    }

    public Train numberOfSeats(Long numberOfSeats) {
        this.setNumberOfSeats(numberOfSeats);
        return this;
    }

    public void setNumberOfSeats(Long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Set<Journey> getJourneys() {
        return this.journeys;
    }

    public void setJourneys(Set<Journey> journeys) {
        if (this.journeys != null) {
            this.journeys.forEach(i -> i.setIdTrain(null));
        }
        if (journeys != null) {
            journeys.forEach(i -> i.setIdTrain(this));
        }
        this.journeys = journeys;
    }

    public Train journeys(Set<Journey> journeys) {
        this.setJourneys(journeys);
        return this;
    }

    public Train addJourney(Journey journey) {
        this.journeys.add(journey);
        journey.setIdTrain(this);
        return this;
    }

    public Train removeJourney(Journey journey) {
        this.journeys.remove(journey);
        journey.setIdTrain(null);
        return this;
    }

    public FuelType getIdFuelType() {
        return this.idFuelType;
    }

    public void setIdFuelType(FuelType fuelType) {
        this.idFuelType = fuelType;
    }

    public Train idFuelType(FuelType fuelType) {
        this.setIdFuelType(fuelType);
        return this;
    }

    public TrainType getIdTrainType() {
        return this.idTrainType;
    }

    public void setIdTrainType(TrainType trainType) {
        this.idTrainType = trainType;
    }

    public Train idTrainType(TrainType trainType) {
        this.setIdTrainType(trainType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Train)) {
            return false;
        }
        return id != null && id.equals(((Train) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Train{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", numberOfSeats=" + getNumberOfSeats() +
            "}";
    }
}
