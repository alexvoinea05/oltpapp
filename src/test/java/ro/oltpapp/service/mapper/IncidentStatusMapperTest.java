package ro.oltpapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IncidentStatusMapperTest {

    private IncidentStatusMapper incidentStatusMapper;

    @BeforeEach
    public void setUp() {
        incidentStatusMapper = new IncidentStatusMapperImpl();
    }
}
