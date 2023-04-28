package ro.oltpapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link ro.oltpapp.domain.CompanyXLicense} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyXLicenseDTO implements Serializable {

    private Long id;

    private CompanyDTO idCompany;

    private LicenseDTO idLicense;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompanyDTO getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(CompanyDTO idCompany) {
        this.idCompany = idCompany;
    }

    public LicenseDTO getIdLicense() {
        return idLicense;
    }

    public void setIdLicense(LicenseDTO idLicense) {
        this.idLicense = idLicense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyXLicenseDTO)) {
            return false;
        }

        CompanyXLicenseDTO companyXLicenseDTO = (CompanyXLicenseDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, companyXLicenseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyXLicenseDTO{" +
            "id=" + getId() +
            ", idCompany=" + getIdCompany() +
            ", idLicense=" + getIdLicense() +
            "}";
    }
}
