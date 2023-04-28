package ro.oltpapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncidentMapperTest {

    private IncidentMapper incidentMapper;

    @BeforeEach
    public void setUp() {
        incidentMapper = new IncidentMapperImpl();
    }
}
