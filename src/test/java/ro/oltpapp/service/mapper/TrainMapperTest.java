package ro.oltpapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainMapperTest {

    private TrainMapper trainMapper;

    @BeforeEach
    public void setUp() {
        trainMapper = new TrainMapperImpl();
    }
}
