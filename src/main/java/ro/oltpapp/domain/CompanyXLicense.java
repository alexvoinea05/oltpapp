package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A CompanyXLicense.
 */
@Entity
@Table(name = "company_x_license")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CompanyXLicense implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties(value = { "journeys", "companyXLicenses" }, allowSetters = true)
    private Company idCompany;

    @ManyToOne
    @JoinColumn(name = "license_id")
    @JsonIgnoreProperties(value = { "companyXLicenses" }, allowSetters = true)
    private License idLicense;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CompanyXLicense id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getIdCompany() {
        return this.idCompany;
    }

    public void setIdCompany(Company company) {
        this.idCompany = company;
    }

    public CompanyXLicense idCompany(Company company) {
        this.setIdCompany(company);
        return this;
    }

    public License getIdLicense() {
        return this.idLicense;
    }

    public void setIdLicense(License license) {
        this.idLicense = license;
    }

    public CompanyXLicense idLicense(License license) {
        this.setIdLicense(license);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompanyXLicense)) {
            return false;
        }
        return id != null && id.equals(((CompanyXLicense) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CompanyXLicense{" +
            "id=" + getId() +
            "}";
    }
}
