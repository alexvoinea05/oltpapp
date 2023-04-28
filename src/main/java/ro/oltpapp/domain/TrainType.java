package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A TrainType.
 */
@Entity
@Table(name = "train_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrainType implements Serializable {

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

    @OneToMany(mappedBy = "idTrainType")
    @JsonIgnoreProperties(value = { "journeys", "idFuelType", "idTrainType" }, allowSetters = true)
    private Set<Train> trains = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TrainType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public TrainType code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return this.description;
    }

    public TrainType description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Train> getTrains() {
        return this.trains;
    }

    public void setTrains(Set<Train> trains) {
        if (this.trains != null) {
            this.trains.forEach(i -> i.setIdTrainType(null));
        }
        if (trains != null) {
            trains.forEach(i -> i.setIdTrainType(this));
        }
        this.trains = trains;
    }

    public TrainType trains(Set<Train> trains) {
        this.setTrains(trains);
        return this;
    }

    public TrainType addTrain(Train train) {
        this.trains.add(train);
        train.setIdTrainType(this);
        return this;
    }

    public TrainType removeTrain(Train train) {
        this.trains.remove(train);
        train.setIdTrainType(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainType)) {
            return false;
        }
        return id != null && id.equals(((TrainType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainType{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
