package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A License.
 */
@Entity
@Table(name = "license")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class License implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "license_number", nullable = false)
    private Long licenseNumber;

    @Column(name = "license_description")
    private String licenseDescription;

    @OneToMany(mappedBy = "idLicense")
    @JsonIgnoreProperties(value = { "idCompany", "idLicense" }, allowSetters = true)
    private Set<CompanyXLicense> companyXLicenses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public License id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLicenseNumber() {
        return this.licenseNumber;
    }

    public License licenseNumber(Long licenseNumber) {
        this.setLicenseNumber(licenseNumber);
        return this;
    }

    public void setLicenseNumber(Long licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseDescription() {
        return this.licenseDescription;
    }

    public License licenseDescription(String licenseDescription) {
        this.setLicenseDescription(licenseDescription);
        return this;
    }

    public void setLicenseDescription(String licenseDescription) {
        this.licenseDescription = licenseDescription;
    }

    public Set<CompanyXLicense> getCompanyXLicenses() {
        return this.companyXLicenses;
    }

    public void setCompanyXLicenses(Set<CompanyXLicense> companyXLicenses) {
        if (this.companyXLicenses != null) {
            this.companyXLicenses.forEach(i -> i.setIdLicense(null));
        }
        if (companyXLicenses != null) {
            companyXLicenses.forEach(i -> i.setIdLicense(this));
        }
        this.companyXLicenses = companyXLicenses;
    }

    public License companyXLicenses(Set<CompanyXLicense> companyXLicenses) {
        this.setCompanyXLicenses(companyXLicenses);
        return this;
    }

    public License addCompanyXLicense(CompanyXLicense companyXLicense) {
        this.companyXLicenses.add(companyXLicense);
        companyXLicense.setIdLicense(this);
        return this;
    }

    public License removeCompanyXLicense(CompanyXLicense companyXLicense) {
        this.companyXLicenses.remove(companyXLicense);
        companyXLicense.setIdLicense(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof License)) {
            return false;
        }
        return id != null && id.equals(((License) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "License{" +
            "id=" + getId() +
            ", licenseNumber=" + getLicenseNumber() +
            ", licenseDescription='" + getLicenseDescription() + "'" +
            "}";
    }
}
