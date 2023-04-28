package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Company.
 */
@Entity
@Table(name = "company")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Company implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "identification_number", nullable = false)
    private String identificationNumber;

    @OneToMany(mappedBy = "idCompany")
    @JsonIgnoreProperties(
        value = {
            "incidents", "tickets", "idJourneyStatus", "idTrain", "idCompany", "idRailwayStationDeparture", "idRailwayStationArrival",
        },
        allowSetters = true
    )
    private Set<Journey> journeys = new HashSet<>();

    @OneToMany(mappedBy = "idCompany")
    @JsonIgnoreProperties(value = { "idCompany", "idLicense" }, allowSetters = true)
    private Set<CompanyXLicense> companyXLicenses = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Company id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Company name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentificationNumber() {
        return this.identificationNumber;
    }

    public Company identificationNumber(String identificationNumber) {
        this.setIdentificationNumber(identificationNumber);
        return this;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Set<Journey> getJourneys() {
        return this.journeys;
    }

    public void setJourneys(Set<Journey> journeys) {
        if (this.journeys != null) {
            this.journeys.forEach(i -> i.setIdCompany(null));
        }
        if (journeys != null) {
            journeys.forEach(i -> i.setIdCompany(this));
        }
        this.journeys = journeys;
    }

    public Company journeys(Set<Journey> journeys) {
        this.setJourneys(journeys);
        return this;
    }

    public Company addJourney(Journey journey) {
        this.journeys.add(journey);
        journey.setIdCompany(this);
        return this;
    }

    public Company removeJourney(Journey journey) {
        this.journeys.remove(journey);
        journey.setIdCompany(null);
        return this;
    }

    public Set<CompanyXLicense> getCompanyXLicenses() {
        return this.companyXLicenses;
    }

    public void setCompanyXLicenses(Set<CompanyXLicense> companyXLicenses) {
        if (this.companyXLicenses != null) {
            this.companyXLicenses.forEach(i -> i.setIdCompany(null));
        }
        if (companyXLicenses != null) {
            companyXLicenses.forEach(i -> i.setIdCompany(this));
        }
        this.companyXLicenses = companyXLicenses;
    }

    public Company companyXLicenses(Set<CompanyXLicense> companyXLicenses) {
        this.setCompanyXLicenses(companyXLicenses);
        return this;
    }

    public Company addCompanyXLicense(CompanyXLicense companyXLicense) {
        this.companyXLicenses.add(companyXLicense);
        companyXLicense.setIdCompany(this);
        return this;
    }

    public Company removeCompanyXLicense(CompanyXLicense companyXLicense) {
        this.companyXLicenses.remove(companyXLicense);
        companyXLicense.setIdCompany(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Company)) {
            return false;
        }
        return id != null && id.equals(((Company) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Company{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", identificationNumber='" + getIdentificationNumber() + "'" +
            "}";
    }
}
