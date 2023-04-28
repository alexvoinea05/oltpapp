package ro.oltpapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class IncidentStatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidentStatus.class);
        IncidentStatus incidentStatus1 = new IncidentStatus();
        incidentStatus1.setId(1L);
        IncidentStatus incidentStatus2 = new IncidentStatus();
        incidentStatus2.setId(incidentStatus1.getId());
        assertThat(incidentStatus1).isEqualTo(incidentStatus2);
        incidentStatus2.setId(2L);
        assertThat(incidentStatus1).isNotEqualTo(incidentStatus2);
        incidentStatus1.setId(null);
        assertThat(incidentStatus1).isNotEqualTo(incidentStatus2);
    }
}
