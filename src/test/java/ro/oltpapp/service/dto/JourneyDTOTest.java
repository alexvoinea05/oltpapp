package ro.oltpapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class JourneyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneyDTO.class);
        JourneyDTO journeyDTO1 = new JourneyDTO();
        journeyDTO1.setId(1L);
        JourneyDTO journeyDTO2 = new JourneyDTO();
        assertThat(journeyDTO1).isNotEqualTo(journeyDTO2);
        journeyDTO2.setId(journeyDTO1.getId());
        assertThat(journeyDTO1).isEqualTo(journeyDTO2);
        journeyDTO2.setId(2L);
        assertThat(journeyDTO1).isNotEqualTo(journeyDTO2);
        journeyDTO1.setId(null);
        assertThat(journeyDTO1).isNotEqualTo(journeyDTO2);
    }
}
