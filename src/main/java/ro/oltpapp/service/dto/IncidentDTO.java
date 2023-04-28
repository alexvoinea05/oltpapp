package ro.oltpapp.service.dto;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ro.oltpapp.domain.Incident} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class IncidentDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private ZonedDateTime time;

    private IncidentStatusDTO idIncidentStatus;

    private AppUserDTO idAppUser;

    private JourneyDTO idJourney;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public IncidentStatusDTO getIdIncidentStatus() {
        return idIncidentStatus;
    }

    public void setIdIncidentStatus(IncidentStatusDTO idIncidentStatus) {
        this.idIncidentStatus = idIncidentStatus;
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
        if (!(o instanceof IncidentDTO)) {
            return false;
        }

        IncidentDTO incidentDTO = (IncidentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, incidentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IncidentDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", time='" + getTime() + "'" +
            ", idIncidentStatus=" + getIdIncidentStatus() +
            ", idAppUser=" + getIdAppUser() +
            ", idJourney=" + getIdJourney() +
            "}";
    }
}
