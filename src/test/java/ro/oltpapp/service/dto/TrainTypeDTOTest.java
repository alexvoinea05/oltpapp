package ro.oltpapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class TrainTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainTypeDTO.class);
        TrainTypeDTO trainTypeDTO1 = new TrainTypeDTO();
        trainTypeDTO1.setId(1L);
        TrainTypeDTO trainTypeDTO2 = new TrainTypeDTO();
        assertThat(trainTypeDTO1).isNotEqualTo(trainTypeDTO2);
        trainTypeDTO2.setId(trainTypeDTO1.getId());
        assertThat(trainTypeDTO1).isEqualTo(trainTypeDTO2);
        trainTypeDTO2.setId(2L);
        assertThat(trainTypeDTO1).isNotEqualTo(trainTypeDTO2);
        trainTypeDTO1.setId(null);
        assertThat(trainTypeDTO1).isNotEqualTo(trainTypeDTO2);
    }
}
