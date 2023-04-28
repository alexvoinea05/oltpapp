package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A IncidentStatus.
 */
@Entity
@Table(name = "incident_status")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IncidentStatus implements Serializable {

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

    @OneToMany(mappedBy = "idIncidentStatus")
    @JsonIgnoreProperties(value = { "idIncidentStatus", "idAppUser", "idJourney" }, allowSetters = true)
    private Set<Incident> incidents = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public IncidentStatus id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public IncidentStatus code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public IncidentStatus description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Incident> getIncidents() {
        return this.incidents;
    }

    public void setIncidents(Set<Incident> incidents) {
        if (this.incidents != null) {
            this.incidents.forEach(i -> i.setIdIncidentStatus(null));
        }
        if (incidents != null) {
            incidents.forEach(i -> i.setIdIncidentStatus(this));
        }
        this.incidents = incidents;
    }

    public IncidentStatus incidents(Set<Incident> incidents) {
        this.setIncidents(incidents);
        return this;
    }

    public IncidentStatus addIncident(Incident incident) {
        this.incidents.add(incident);
        incident.setIdIncidentStatus(this);
        return this;
    }

    public IncidentStatus removeIncident(Incident incident) {
        this.incidents.remove(incident);
        incident.setIdIncidentStatus(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IncidentStatus)) {
            return false;
        }
        return id != null && id.equals(((IncidentStatus) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IncidentStatus{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
