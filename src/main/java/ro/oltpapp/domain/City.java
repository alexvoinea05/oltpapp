package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A City.
 */
@Entity
@Table(name = "city")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "idCity")
    @JsonIgnoreProperties(value = { "railwayStations", "idCity" }, allowSetters = true)
    private Set<Address> addresses = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "district_id")
    @JsonIgnoreProperties(value = { "cities" }, allowSetters = true)
    private District idDistrict;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public City id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public City name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Address> getAddresses() {
        return this.addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        if (this.addresses != null) {
            this.addresses.forEach(i -> i.setIdCity(null));
        }
        if (addresses != null) {
            addresses.forEach(i -> i.setIdCity(this));
        }
        this.addresses = addresses;
    }

    public City addresses(Set<Address> addresses) {
        this.setAddresses(addresses);
        return this;
    }

    public City addAddress(Address address) {
        this.addresses.add(address);
        address.setIdCity(this);
        return this;
    }

    public City removeAddress(Address address) {
        this.addresses.remove(address);
        address.setIdCity(null);
        return this;
    }

    public District getIdDistrict() {
        return this.idDistrict;
    }

    public void setIdDistrict(District district) {
        this.idDistrict = district;
    }

    public City idDistrict(District district) {
        this.setIdDistrict(district);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof City)) {
            return false;
        }
        return id != null && id.equals(((City) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "City{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
