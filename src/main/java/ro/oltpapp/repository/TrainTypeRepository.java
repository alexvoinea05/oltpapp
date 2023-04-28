package ro.oltpapp.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ro.oltpapp.domain.TrainType;

/**
 * Spring Data JPA repository for the TrainType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrainTypeRepository extends JpaRepository<TrainType, Long> {}
