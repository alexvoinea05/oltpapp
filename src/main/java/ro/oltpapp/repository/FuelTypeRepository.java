package ro.oltpapp.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.oltpapp.domain.FuelType;

/**
 * Spring Data JPA repository for the FuelType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelTypeRepository extends JpaRepository<FuelType, Long> {}
