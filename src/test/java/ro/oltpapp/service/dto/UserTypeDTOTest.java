package ro.oltpapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class UserTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserTypeDTO.class);
        UserTypeDTO userTypeDTO1 = new UserTypeDTO();
        userTypeDTO1.setId(1L);
        UserTypeDTO userTypeDTO2 = new UserTypeDTO();
        assertThat(userTypeDTO1).isNotEqualTo(userTypeDTO2);
        userTypeDTO2.setId(userTypeDTO1.getId());
        assertThat(userTypeDTO1).isEqualTo(userTypeDTO2);
        userTypeDTO2.setId(2L);
        assertThat(userTypeDTO1).isNotEqualTo(userTypeDTO2);
        userTypeDTO1.setId(null);
        assertThat(userTypeDTO1).isNotEqualTo(userTypeDTO2);
    }
}
