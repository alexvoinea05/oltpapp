package ro.oltpapp.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.oltpapp.domain.IncidentStatus;

/**
 * Spring Data JPA repository for the IncidentStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IncidentStatusRepository extends JpaRepository<IncidentStatus, Long> {}
