package ro.oltpapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RailwayStationMapperTest {

    private RailwayStationMapper railwayStationMapper;

    @BeforeEach
    public void setUp() {
        railwayStationMapper = new RailwayStationMapperImpl();
    }
}
