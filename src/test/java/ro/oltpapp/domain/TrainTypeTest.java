package ro.oltpapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import ro.oltpapp.web.rest.TestUtil;

class TrainTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrainType.class);
        TrainType trainType1 = new TrainType();
        trainType1.setId(1L);
        TrainType trainType2 = new TrainType();
        trainType2.setId(trainType1.getId());
        assertThat(trainType1).isEqualTo(trainType2);
        trainType2.setId(2L);
        assertThat(trainType1).isNotEqualTo(trainType2);
        trainType1.setId(null);
        assertThat(trainType1).isNotEqualTo(trainType2);
    }
}
