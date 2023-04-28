package ro.oltpapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ro.oltpapp.domain.Train} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TrainDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Long numberOfSeats;

    private FuelTypeDTO idFuelType;

    private TrainTypeDTO idTrainType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(Long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public FuelTypeDTO getIdFuelType() {
        return idFuelType;
    }

    public void setIdFuelType(FuelTypeDTO idFuelType) {
        this.idFuelType = idFuelType;
    }

    public TrainTypeDTO getIdTrainType() {
        return idTrainType;
    }

    public void setIdTrainType(TrainTypeDTO idTrainType) {
        this.idTrainType = idTrainType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TrainDTO)) {
            return false;
        }

        TrainDTO trainDTO = (TrainDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, trainDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TrainDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", numberOfSeats=" + getNumberOfSeats() +
            ", idFuelType=" + getIdFuelType() +
            ", idTrainType=" + getIdTrainType() +
            "}";
    }
}
