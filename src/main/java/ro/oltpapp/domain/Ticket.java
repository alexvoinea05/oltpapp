package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "final_price", nullable = false)
    private Double finalPrice;

    @NotNull
    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @NotNull
    @Column(name = "time", nullable = false)
    private ZonedDateTime time;

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

    public Ticket id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFinalPrice() {
        return this.finalPrice;
    }

    public Ticket finalPrice(Double finalPrice) {
        this.setFinalPrice(finalPrice);
        return this;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public Ticket quantity(Integer quantity) {
        this.setQuantity(quantity);
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ZonedDateTime getTime() {
        return this.time;
    }

    public Ticket time(ZonedDateTime time) {
        this.setTime(time);
        return this;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public AppUser getIdAppUser() {
        return this.idAppUser;
    }

    public void setIdAppUser(AppUser appUser) {
        this.idAppUser = appUser;
    }

    public Ticket idAppUser(AppUser appUser) {
        this.setIdAppUser(appUser);
        return this;
    }

    public Journey getIdJourney() {
        return this.idJourney;
    }

    public void setIdJourney(Journey journey) {
        this.idJourney = journey;
    }

    public Ticket idJourney(Journey journey) {
        this.setIdJourney(journey);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ticket)) {
            return false;
        }
        return id != null && id.equals(((Ticket) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + getId() +
            ", finalPrice=" + getFinalPrice() +
            ", quantity=" + getQuantity() +
            ", time='" + getTime() + "'" +
            "}";
    }
}
