package ro.oltpapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class AppUserDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUserDTO.class);
        AppUserDTO appUserDTO1 = new AppUserDTO();
        appUserDTO1.setIdUser(1L);
        AppUserDTO appUserDTO2 = new AppUserDTO();
        assertThat(appUserDTO1).isNotEqualTo(appUserDTO2);
        appUserDTO2.setIdUser(appUserDTO1.getIdUser());
        assertThat(appUserDTO1).isEqualTo(appUserDTO2);
        appUserDTO2.setIdUser(2L);
        assertThat(appUserDTO1).isNotEqualTo(appUserDTO2);
        appUserDTO1.setIdUser(null);
        assertThat(appUserDTO1).isNotEqualTo(appUserDTO2);
    }
}
