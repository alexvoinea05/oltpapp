package ro.oltpapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class IncidentStatusDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IncidentStatusDTO.class);
        IncidentStatusDTO incidentStatusDTO1 = new IncidentStatusDTO();
        incidentStatusDTO1.setId(1L);
        IncidentStatusDTO incidentStatusDTO2 = new IncidentStatusDTO();
        assertThat(incidentStatusDTO1).isNotEqualTo(incidentStatusDTO2);
        incidentStatusDTO2.setId(incidentStatusDTO1.getId());
        assertThat(incidentStatusDTO1).isEqualTo(incidentStatusDTO2);
        incidentStatusDTO2.setId(2L);
        assertThat(incidentStatusDTO1).isNotEqualTo(incidentStatusDTO2);
        incidentStatusDTO1.setId(null);
        assertThat(incidentStatusDTO1).isNotEqualTo(incidentStatusDTO2);
    }
}
