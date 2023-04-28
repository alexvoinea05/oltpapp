package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Address.
 */
@Entity
@Table(name = "address")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "street_number")
    private String streetNumber;

    @Column(name = "street")
    private String street;

    @Column(name = "zipcode")
    private String zipcode;

    @OneToMany(mappedBy = "idAddress")
    @JsonIgnoreProperties(value = { "idJourneyDepartures", "idJourneyArrivals", "idRailwayType", "idAddress" }, allowSetters = true)
    private Set<RailwayStation> railwayStations = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "city_id")
    @JsonIgnoreProperties(value = { "addresses", "idDistrict" }, allowSetters = true)
    private City idCity;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Address id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetNumber() {
        return this.streetNumber;
    }

    public Address streetNumber(String streetNumber) {
        this.setStreetNumber(streetNumber);
        return this;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getStreet() {
        return this.street;
    }

    public Address street(String street) {
        this.setStreet(street);
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return this.zipcode;
    }

    public Address zipcode(String zipcode) {
        this.setZipcode(zipcode);
        return this;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public Set<RailwayStation> getRailwayStations() {
        return this.railwayStations;
    }

    public void setRailwayStations(Set<RailwayStation> railwayStations) {
        if (this.railwayStations != null) {
            this.railwayStations.forEach(i -> i.setIdAddress(null));
        }
        if (railwayStations != null) {
            railwayStations.forEach(i -> i.setIdAddress(this));
        }
        this.railwayStations = railwayStations;
    }

    public Address railwayStations(Set<RailwayStation> railwayStations) {
        this.setRailwayStations(railwayStations);
        return this;
    }

    public Address addRailwayStation(RailwayStation railwayStation) {
        this.railwayStations.add(railwayStation);
        railwayStation.setIdAddress(this);
        return this;
    }

    public Address removeRailwayStation(RailwayStation railwayStation) {
        this.railwayStations.remove(railwayStation);
        railwayStation.setIdAddress(null);
        return this;
    }

    public City getIdCity() {
        return this.idCity;
    }

    public void setIdCity(City city) {
        this.idCity = city;
    }

    public Address idCity(City city) {
        this.setIdCity(city);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address)) {
            return false;
        }
        return id != null && id.equals(((Address) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Address{" +
            "id=" + getId() +
            ", streetNumber='" + getStreetNumber() + "'" +
            ", street='" + getStreet() + "'" +
            ", zipcode='" + getZipcode() + "'" +
            "}";
    }
}
