package ro.oltpapp.repository.up;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ro.oltpapp.domain.Address;
import ro.oltpapp.domain.Company;

@Repository
public interface UpdateCompanyRepository extends JpaRepository<Company, Long> {

    /*@Query(value = "CALL \"C##ALEXVOINEA\".\"TRAIN_OLTP_TO_TRAIN_DW_COMPANY\";", nativeQuery = true)
    void updateCompanyDw();*/
}
