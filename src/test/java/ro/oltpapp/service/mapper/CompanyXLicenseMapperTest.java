package ro.oltpapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyXLicenseMapperTest {

    private CompanyXLicenseMapper companyXLicenseMapper;

    @BeforeEach
    public void setUp() {
        companyXLicenseMapper = new CompanyXLicenseMapperImpl();
    }
}
