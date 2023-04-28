package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Incident.
 */
@Entity
@Table(name = "incident")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Incident implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "time", nullable = false)
    private ZonedDateTime time;

    @ManyToOne
    @JoinColumn(name = "incident_status_id")
    @JsonIgnoreProperties(value = { "incidents" }, allowSetters = true)
    private IncidentStatus idIncidentStatus;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    @JsonIgnoreProperties(value = { "user", "incidents", "tickets", "idUserType" }, allowSetters = true)
    private AppUser idAppUser;

    @ManyToOne
    @JoinColumn(name = "journey_id")
    @JsonIgnoreProperties(
        value = {
            "incidents", "tickets", "idJourneyStatus", "idTrain", "idCompany", "idRailwayStationDeparture", "idRailwayStationArrival",
        },
        allowSetters = true
    )
    private Journey idJourney;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Incident id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Incident description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getTime() {
        return this.time;
    }

    public Incident time(ZonedDateTime time) {
        this.setTime(time);
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public IncidentStatus getIdIncidentStatus() {
        return this.idIncidentStatus;
    }

    public void setIdIncidentStatus(IncidentStatus incidentStatus) {
        this.idIncidentStatus = incidentStatus;
    }

    public Incident idIncidentStatus(IncidentStatus incidentStatus) {
        this.setIdIncidentStatus(incidentStatus);
        return this;
    }

    public AppUser getIdAppUser() {
        return this.idAppUser;
    }

    public void setIdAppUser(AppUser appUser) {
        this.idAppUser = appUser;
    }

    public Incident idAppUser(AppUser appUser) {
        this.setIdAppUser(appUser);
        return this;
    }

    public Journey getIdJourney() {
        return this.idJourney;
    }

    public void setIdJourney(Journey journey) {
        this.idJourney = journey;
    }

    public Incident idJourney(Journey journey) {
        this.setIdJourney(journey);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Incident)) {
            return false;
        }
        return id != null && id.equals(((Incident) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Incident{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", time='" + getTime() + "'" +
            "}";
    }
}
