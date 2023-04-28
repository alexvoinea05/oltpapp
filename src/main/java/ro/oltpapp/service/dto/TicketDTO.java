package ro.oltpapp.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ro.oltpapp.domain.Ticket} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TicketDTO implements Serializable {

    private Long id;

    @NotNull
    private Double finalPrice;

    @NotNull
    private Integer quantity;

    @NotNull
    private ZonedDateTime time;

    private AppUserDTO idAppUser;

    private JourneyDTO idJourney;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public AppUserDTO getIdAppUser() {
        return idAppUser;
    }

    public void setIdAppUser(AppUserDTO idAppUser) {
        this.idAppUser = idAppUser;
    }

    public JourneyDTO getIdJourney() {
        return idJourney;
    }

    public void setIdJourney(JourneyDTO idJourney) {
        this.idJourney = idJourney;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TicketDTO)) {
            return false;
        }

        TicketDTO ticketDTO = (TicketDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ticketDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TicketDTO{" +
            "id=" + getId() +
            ", finalPrice=" + getFinalPrice() +
            ", quantity=" + getQuantity() +
            ", time='" + getTime() + "'" +
            ", idAppUser=" + getIdAppUser() +
            ", idJourney=" + getIdJourney() +
            "}";
    }
}
