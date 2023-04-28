package ro.oltpapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class FuelTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FuelType.class);
        FuelType fuelType1 = new FuelType();
        fuelType1.setId(1L);
        FuelType fuelType2 = new FuelType();
        fuelType2.setId(fuelType1.getId());
        assertThat(fuelType1).isEqualTo(fuelType2);
        fuelType2.setId(2L);
        assertThat(fuelType1).isNotEqualTo(fuelType2);
        fuelType1.setId(null);
        assertThat(fuelType1).isNotEqualTo(fuelType2);
    }
}
