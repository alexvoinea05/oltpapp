package ro.oltpapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class RailwayStationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RailwayStationDTO.class);
        RailwayStationDTO railwayStationDTO1 = new RailwayStationDTO();
        railwayStationDTO1.setId(1L);
        RailwayStationDTO railwayStationDTO2 = new RailwayStationDTO();
        assertThat(railwayStationDTO1).isNotEqualTo(railwayStationDTO2);
        railwayStationDTO2.setId(railwayStationDTO1.getId());
        assertThat(railwayStationDTO1).isEqualTo(railwayStationDTO2);
        railwayStationDTO2.setId(2L);
        assertThat(railwayStationDTO1).isNotEqualTo(railwayStationDTO2);
        railwayStationDTO1.setId(null);
        assertThat(railwayStationDTO1).isNotEqualTo(railwayStationDTO2);
    }
}
