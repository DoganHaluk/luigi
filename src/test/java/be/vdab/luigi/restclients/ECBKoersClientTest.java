package be.vdab.luigi.restclients;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ECBKoersClientTest {
    private ECBKoersClient client;

    @BeforeEach
    void beforeEach() {
        client = new ECBKoersClient();
    }

    @Test
    void deKoersIsPositief() {
        assertThat(client.getDollarKoers()).isPositive();
    }
}
