package ro.oltpapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RailwayTypeMapperTest {

    private RailwayTypeMapper railwayTypeMapper;

    @BeforeEach
    public void setUp() {
        railwayTypeMapper = new RailwayTypeMapperImpl();
    }
}
