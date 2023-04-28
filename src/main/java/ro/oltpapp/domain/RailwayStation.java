package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RailwayStation.
 */
@Entity
@Table(name = "railway_station")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RailwayStation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "railway_station_name", nullable = false)
    private String railwayStationName;

    @OneToMany(mappedBy = "idRailwayStationDeparture")
    @JsonIgnoreProperties(
        value = {
            "incidents", "tickets", "idJourneyStatus", "idTrain", "idCompany", "idRailwayStationDeparture", "idRailwayStationArrival",
        },
        allowSetters = true
    )
    private Set<Journey> idJourneyDepartures = new HashSet<>();

    @OneToMany(mappedBy = "idRailwayStationArrival")
    @JsonIgnoreProperties(
        value = {
            "incidents", "tickets", "idJourneyStatus", "idTrain", "idCompany", "idRailwayStationDeparture", "idRailwayStationArrival",
        },
        allowSetters = true
    )
    private Set<Journey> idJourneyArrivals = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "railway_type_id")
    @JsonIgnoreProperties(value = { "railwayStations" }, allowSetters = true)
    private RailwayType idRailwayType;

    @ManyToOne
    @JoinColumn(name = "address_id")
    @JsonIgnoreProperties(value = { "railwayStations", "idCity" }, allowSetters = true)
    private Address idAddress;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RailwayStation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRailwayStationName() {
        return this.railwayStationName;
    }

    public RailwayStation railwayStationName(String railwayStationName) {
        this.setRailwayStationName(railwayStationName);
        return this;
    }

    public void setRailwayStationName(String railwayStationName) {
        this.railwayStationName = railwayStationName;
    }

    public Set<Journey> getIdJourneyDepartures() {
        return this.idJourneyDepartures;
    }

    public void setIdJourneyDepartures(Set<Journey> journeys) {
        if (this.idJourneyDepartures != null) {
            this.idJourneyDepartures.forEach(i -> i.setIdRailwayStationDeparture(null));
        }
        if (journeys != null) {
            journeys.forEach(i -> i.setIdRailwayStationDeparture(this));
        }
        this.idJourneyDepartures = journeys;
    }

    public RailwayStation idJourneyDepartures(Set<Journey> journeys) {
        this.setIdJourneyDepartures(journeys);
        return this;
    }

    public RailwayStation addIdJourneyDeparture(Journey journey) {
        this.idJourneyDepartures.add(journey);
        journey.setIdRailwayStationDeparture(this);
        return this;
    }

    public RailwayStation removeIdJourneyDeparture(Journey journey) {
        this.idJourneyDepartures.remove(journey);
        journey.setIdRailwayStationDeparture(null);
        return this;
    }

    public Set<Journey> getIdJourneyArrivals() {
        return this.idJourneyArrivals;
    }

    public void setIdJourneyArrivals(Set<Journey> journeys) {
        if (this.idJourneyArrivals != null) {
            this.idJourneyArrivals.forEach(i -> i.setIdRailwayStationArrival(null));
        }
        if (journeys != null) {
            journeys.forEach(i -> i.setIdRailwayStationArrival(this));
        }
        this.idJourneyArrivals = journeys;
    }

    public RailwayStation idJourneyArrivals(Set<Journey> journeys) {
        this.setIdJourneyArrivals(journeys);
        return this;
    }

    public RailwayStation addIdJourneyArrival(Journey journey) {
        this.idJourneyArrivals.add(journey);
        journey.setIdRailwayStationArrival(this);
        return this;
    }

    public RailwayStation removeIdJourneyArrival(Journey journey) {
        this.idJourneyArrivals.remove(journey);
        journey.setIdRailwayStationArrival(null);
        return this;
    }

    public RailwayType getIdRailwayType() {
        return this.idRailwayType;
    }

    public void setIdRailwayType(RailwayType railwayType) {
        this.idRailwayType = railwayType;
    }

    public RailwayStation idRailwayType(RailwayType railwayType) {
        this.setIdRailwayType(railwayType);
        return this;
    }

    public Address getIdAddress() {
        return this.idAddress;
    }

    public void setIdAddress(Address address) {
        this.idAddress = address;
    }

    public RailwayStation idAddress(Address address) {
        this.setIdAddress(address);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RailwayStation)) {
            return false;
        }
        return id != null && id.equals(((RailwayStation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RailwayStation{" +
            "id=" + getId() +
            ", railwayStationName='" + getRailwayStationName() + "'" +
            "}";
    }
}
