package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A JourneyStatus.
 */
@Entity
@Table(name = "journey_status")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class JourneyStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "idJourneyStatus")
    @JsonIgnoreProperties(
        value = {
            "incidents", "tickets", "idJourneyStatus", "idTrain", "idCompany", "idRailwayStationDeparture", "idRailwayStationArrival",
        },
        allowSetters = true
    )
    private Set<Journey> journeys = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public JourneyStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public JourneyStatus code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public JourneyStatus description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Journey> getJourneys() {
        return this.journeys;
    }

    public void setJourneys(Set<Journey> journeys) {
        if (this.journeys != null) {
            this.journeys.forEach(i -> i.setIdJourneyStatus(null));
        }
        if (journeys != null) {
            journeys.forEach(i -> i.setIdJourneyStatus(this));
        }
        this.journeys = journeys;
    }

    public JourneyStatus journeys(Set<Journey> journeys) {
        this.setJourneys(journeys);
        return this;
    }

    public JourneyStatus addJourney(Journey journey) {
        this.journeys.add(journey);
        journey.setIdJourneyStatus(this);
        return this;
    }

    public JourneyStatus removeJourney(Journey journey) {
        this.journeys.remove(journey);
        journey.setIdJourneyStatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JourneyStatus)) {
            return false;
        }
        return id != null && id.equals(((JourneyStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JourneyStatus{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
