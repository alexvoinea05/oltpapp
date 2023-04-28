package ro.oltpapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class RailwayTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RailwayType.class);
        RailwayType railwayType1 = new RailwayType();
        railwayType1.setId(1L);
        RailwayType railwayType2 = new RailwayType();
        railwayType2.setId(railwayType1.getId());
        assertThat(railwayType1).isEqualTo(railwayType2);
        railwayType2.setId(2L);
        assertThat(railwayType1).isNotEqualTo(railwayType2);
        railwayType1.setId(null);
        assertThat(railwayType1).isNotEqualTo(railwayType2);
    }
}
