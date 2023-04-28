package ro.oltpapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrainTypeMapperTest {

    private TrainTypeMapper trainTypeMapper;

    @BeforeEach
    public void setUp() {
        trainTypeMapper = new TrainTypeMapperImpl();
    }
}
