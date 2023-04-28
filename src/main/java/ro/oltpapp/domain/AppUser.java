package ro.oltpapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A AppUser.
 */
@Entity
@Table(name = "app_user")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id_user")
    private Long idUser;

    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "balance")
    private Double balance;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "idAppUser")
    @JsonIgnoreProperties(value = { "idIncidentStatus", "idAppUser", "idJourney" }, allowSetters = true)
    private Set<Incident> incidents = new HashSet<>();

    @OneToMany(mappedBy = "idAppUser")
    @JsonIgnoreProperties(value = { "idAppUser", "idJourney" }, allowSetters = true)
    private Set<Ticket> tickets = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_type_id")
    @JsonIgnoreProperties(value = { "appUsers" }, allowSetters = true)
    private UserType idUserType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getIdUser() {
        return this.idUser;
    }

    public AppUser idUser(Long idUser) {
        this.setIdUser(idUser);
        return this;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return this.email;
    }

    public AppUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public AppUser password(String password) {
        this.setPassword(password);
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return this.balance;
    }

    public AppUser balance(Double balance) {
        this.setBalance(balance);
        return this;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getLastName() {
        return this.lastName;
    }

    public AppUser lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public AppUser firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AppUser user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Incident> getIncidents() {
        return this.incidents;
    }

    public void setIncidents(Set<Incident> incidents) {
        if (this.incidents != null) {
            this.incidents.forEach(i -> i.setIdAppUser(null));
        }
        if (incidents != null) {
            incidents.forEach(i -> i.setIdAppUser(this));
        }
        this.incidents = incidents;
    }

    public AppUser incidents(Set<Incident> incidents) {
        this.setIncidents(incidents);
        return this;
    }

    public AppUser addIncident(Incident incident) {
        this.incidents.add(incident);
        incident.setIdAppUser(this);
        return this;
    }

    public AppUser removeIncident(Incident incident) {
        this.incidents.remove(incident);
        incident.setIdAppUser(null);
        return this;
    }

    public Set<Ticket> getTickets() {
        return this.tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        if (this.tickets != null) {
            this.tickets.forEach(i -> i.setIdAppUser(null));
        }
        if (tickets != null) {
            tickets.forEach(i -> i.setIdAppUser(this));
        }
        this.tickets = tickets;
    }

    public AppUser tickets(Set<Ticket> tickets) {
        this.setTickets(tickets);
        return this;
    }

    public AppUser addTicket(Ticket ticket) {
        this.tickets.add(ticket);
        ticket.setIdAppUser(this);
        return this;
    }

    public AppUser removeTicket(Ticket ticket) {
        this.tickets.remove(ticket);
        ticket.setIdAppUser(null);
        return this;
    }

    public UserType getIdUserType() {
        return this.idUserType;
    }

    public void setIdUserType(UserType userType) {
        this.idUserType = userType;
    }

    public AppUser idUserType(UserType userType) {
        this.setIdUserType(userType);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUser)) {
            return false;
        }
        return idUser != null && idUser.equals(((AppUser) o).idUser);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUser{" +
            "idUser=" + getIdUser() +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", balance=" + getBalance() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            "}";
    }
}
