package ro.oltpapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ro.oltpapp.domain.License} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LicenseDTO implements Serializable {

    private Long id;

    @NotNull
    private Long licenseNumber;

    private String licenseDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(Long licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseDescription() {
        return licenseDescription;
    }

    public void setLicenseDescription(String licenseDescription) {
        this.licenseDescription = licenseDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LicenseDTO)) {
            return false;
        }

        LicenseDTO licenseDTO = (LicenseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, licenseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LicenseDTO{" +
            "id=" + getId() +
            ", licenseNumber=" + getLicenseNumber() +
            ", licenseDescription='" + getLicenseDescription() + "'" +
            "}";
    }
}
