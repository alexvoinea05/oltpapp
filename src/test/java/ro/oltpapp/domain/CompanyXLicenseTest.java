package ro.oltpapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class CompanyXLicenseTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompanyXLicense.class);
        CompanyXLicense companyXLicense1 = new CompanyXLicense();
        companyXLicense1.setId(1L);
        CompanyXLicense companyXLicense2 = new CompanyXLicense();
        companyXLicense2.setId(companyXLicense1.getId());
        assertThat(companyXLicense1).isEqualTo(companyXLicense2);
        companyXLicense2.setId(2L);
        assertThat(companyXLicense1).isNotEqualTo(companyXLicense2);
        companyXLicense1.setId(null);
        assertThat(companyXLicense1).isNotEqualTo(companyXLicense2);
    }
}
