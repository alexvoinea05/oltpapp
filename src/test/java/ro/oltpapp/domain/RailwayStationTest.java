package ro.oltpapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class RailwayStationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RailwayStation.class);
        RailwayStation railwayStation1 = new RailwayStation();
        railwayStation1.setId(1L);
        RailwayStation railwayStation2 = new RailwayStation();
        railwayStation2.setId(railwayStation1.getId());
        assertThat(railwayStation1).isEqualTo(railwayStation2);
        railwayStation2.setId(2L);
        assertThat(railwayStation1).isNotEqualTo(railwayStation2);
        railwayStation1.setId(null);
        assertThat(railwayStation1).isNotEqualTo(railwayStation2);
    }
}
