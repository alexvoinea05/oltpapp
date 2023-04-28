package ro.oltpapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ro.oltpapp.domain.UserType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private Double discount;

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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserTypeDTO)) {
            return false;
        }

        UserTypeDTO userTypeDTO = (UserTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserTypeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", discount=" + getDiscount() +
            "}";
    }
}
