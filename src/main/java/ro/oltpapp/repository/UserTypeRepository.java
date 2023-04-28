package ro.oltpapp.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.oltpapp.domain.UserType;

/**
 * Spring Data JPA repository for the UserType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserTypeRepository extends JpaRepository<UserType, Long> {}
