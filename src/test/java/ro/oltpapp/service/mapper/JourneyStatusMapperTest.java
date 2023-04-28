package ro.oltpapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JourneyStatusMapperTest {

    private JourneyStatusMapper journeyStatusMapper;

    @BeforeEach
    public void setUp() {
        journeyStatusMapper = new JourneyStatusMapperImpl();
    }
}
