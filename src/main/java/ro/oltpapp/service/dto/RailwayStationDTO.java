package ro.oltpapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ro.oltpapp.domain.RailwayStation} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RailwayStationDTO implements Serializable {

    private Long id;

    @NotNull
    private String railwayStationName;

    private RailwayTypeDTO idRailwayType;

    private AddressDTO idAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRailwayStationName() {
        return railwayStationName;
    }

    public void setRailwayStationName(String railwayStationName) {
        this.railwayStationName = railwayStationName;
    }

    public RailwayTypeDTO getIdRailwayType() {
        return idRailwayType;
    }

    public void setIdRailwayType(RailwayTypeDTO idRailwayType) {
        this.idRailwayType = idRailwayType;
    }

    public AddressDTO getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(AddressDTO idAddress) {
        this.idAddress = idAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RailwayStationDTO)) {
            return false;
        }

        RailwayStationDTO railwayStationDTO = (RailwayStationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, railwayStationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RailwayStationDTO{" +
            "id=" + getId() +
            ", railwayStationName='" + getRailwayStationName() + "'" +
            ", idRailwayType=" + getIdRailwayType() +
            ", idAddress=" + getIdAddress() +
            "}";
    }
}
