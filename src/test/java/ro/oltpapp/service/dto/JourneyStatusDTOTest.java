package ro.oltpapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class JourneyStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneyStatusDTO.class);
        JourneyStatusDTO journeyStatusDTO1 = new JourneyStatusDTO();
        journeyStatusDTO1.setId(1L);
        JourneyStatusDTO journeyStatusDTO2 = new JourneyStatusDTO();
        assertThat(journeyStatusDTO1).isNotEqualTo(journeyStatusDTO2);
        journeyStatusDTO2.setId(journeyStatusDTO1.getId());
        assertThat(journeyStatusDTO1).isEqualTo(journeyStatusDTO2);
        journeyStatusDTO2.setId(2L);
        assertThat(journeyStatusDTO1).isNotEqualTo(journeyStatusDTO2);
        journeyStatusDTO1.setId(null);
        assertThat(journeyStatusDTO1).isNotEqualTo(journeyStatusDTO2);
    }
}
