package ro.oltpapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ro.oltpapp.domain.AppUser} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AppUserDTO implements Serializable {

    private Long idUser;

    @NotNull
    private String email;

    @NotNull
    private String password;

    private Double balance;

    @NotNull
    private String lastName;

    @NotNull
    private String firstName;

    private UserDTO user;

    private UserTypeDTO idUserType;

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UserTypeDTO getIdUserType() {
        return idUserType;
    }

    public void setIdUserType(UserTypeDTO idUserType) {
        this.idUserType = idUserType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AppUserDTO)) {
            return false;
        }

        AppUserDTO appUserDTO = (AppUserDTO) o;
        if (this.idUser == null) {
            return false;
        }
        return Objects.equals(this.idUser, appUserDTO.idUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.idUser);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AppUserDTO{" +
            "idUser=" + getIdUser() +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", balance=" + getBalance() +
            ", lastName='" + getLastName() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", user=" + getUser() +
            ", idUserType=" + getIdUserType() +
            "}";
    }
}
