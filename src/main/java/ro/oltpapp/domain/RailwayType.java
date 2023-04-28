package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A RailwayType.
 */
@Entity
@Table(name = "railway_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RailwayType implements Serializable {

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

    @OneToMany(mappedBy = "idRailwayType")
    @JsonIgnoreProperties(value = { "idJourneyDepartures", "idJourneyArrivals", "idRailwayType", "idAddress" }, allowSetters = true)
    private Set<RailwayStation> railwayStations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RailwayType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public RailwayType code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public RailwayType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<RailwayStation> getRailwayStations() {
        return this.railwayStations;
    }

    public void setRailwayStations(Set<RailwayStation> railwayStations) {
        if (this.railwayStations != null) {
            this.railwayStations.forEach(i -> i.setIdRailwayType(null));
        }
        if (railwayStations != null) {
            railwayStations.forEach(i -> i.setIdRailwayType(this));
        }
        this.railwayStations = railwayStations;
    }

    public RailwayType railwayStations(Set<RailwayStation> railwayStations) {
        this.setRailwayStations(railwayStations);
        return this;
    }

    public RailwayType addRailwayStation(RailwayStation railwayStation) {
        this.railwayStations.add(railwayStation);
        railwayStation.setIdRailwayType(this);
        return this;
    }

    public RailwayType removeRailwayStation(RailwayStation railwayStation) {
        this.railwayStations.remove(railwayStation);
        railwayStation.setIdRailwayType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RailwayType)) {
            return false;
        }
        return id != null && id.equals(((RailwayType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RailwayType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
