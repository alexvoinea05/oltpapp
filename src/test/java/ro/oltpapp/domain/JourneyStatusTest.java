package ro.oltpapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class JourneyStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JourneyStatus.class);
        JourneyStatus journeyStatus1 = new JourneyStatus();
        journeyStatus1.setId(1L);
        JourneyStatus journeyStatus2 = new JourneyStatus();
        journeyStatus2.setId(journeyStatus1.getId());
        assertThat(journeyStatus1).isEqualTo(journeyStatus2);
        journeyStatus2.setId(2L);
        assertThat(journeyStatus1).isNotEqualTo(journeyStatus2);
        journeyStatus1.setId(null);
        assertThat(journeyStatus1).isNotEqualTo(journeyStatus2);
    }
}
