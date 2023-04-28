package ro.oltpapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class RailwayTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RailwayTypeDTO.class);
        RailwayTypeDTO railwayTypeDTO1 = new RailwayTypeDTO();
        railwayTypeDTO1.setId(1L);
        RailwayTypeDTO railwayTypeDTO2 = new RailwayTypeDTO();
        assertThat(railwayTypeDTO1).isNotEqualTo(railwayTypeDTO2);
        railwayTypeDTO2.setId(railwayTypeDTO1.getId());
        assertThat(railwayTypeDTO1).isEqualTo(railwayTypeDTO2);
        railwayTypeDTO2.setId(2L);
        assertThat(railwayTypeDTO1).isNotEqualTo(railwayTypeDTO2);
        railwayTypeDTO1.setId(null);
        assertThat(railwayTypeDTO1).isNotEqualTo(railwayTypeDTO2);
    }
}
