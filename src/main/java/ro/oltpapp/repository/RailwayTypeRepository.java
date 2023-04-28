package ro.oltpapp.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.oltpapp.domain.RailwayType;

/**
 * Spring Data JPA repository for the RailwayType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RailwayTypeRepository extends JpaRepository<RailwayType, Long> {}
