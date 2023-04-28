package ro.oltpapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class CompanyXLicenseDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyXLicenseDTO.class);
        CompanyXLicenseDTO companyXLicenseDTO1 = new CompanyXLicenseDTO();
        companyXLicenseDTO1.setId(1L);
        CompanyXLicenseDTO companyXLicenseDTO2 = new CompanyXLicenseDTO();
        assertThat(companyXLicenseDTO1).isNotEqualTo(companyXLicenseDTO2);
        companyXLicenseDTO2.setId(companyXLicenseDTO1.getId());
        assertThat(companyXLicenseDTO1).isEqualTo(companyXLicenseDTO2);
        companyXLicenseDTO2.setId(2L);
        assertThat(companyXLicenseDTO1).isNotEqualTo(companyXLicenseDTO2);
        companyXLicenseDTO1.setId(null);
        assertThat(companyXLicenseDTO1).isNotEqualTo(companyXLicenseDTO2);
    }
}
