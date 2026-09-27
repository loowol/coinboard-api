package dev.coinboard.api.coin;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CoinServiceTest {
    static class FakeCoinSource implements CoinSource {
        @Override
        public List<CoinSummary> fetchCoins() {
            return TestCoins.TOP_FIVE;
        }
    }

    @Test
    void coinServiceRequestLimitLessThanAvailable() {
        var fakeCoinSource = new FakeCoinSource();
        var service = new CoinService(fakeCoinSource);

        var response = service.topCoins(3);

        assertThat(response).containsExactly(TestCoins.BITCOIN,
                TestCoins.ETHEREUM, TestCoins.TETHER);
    }

    @Test
    void coinServiceRequestLimitEqualsAvailable() {
        var fakeCoinSource = new FakeCoinSource();
        var coinService = new CoinService(fakeCoinSource);

        var response = coinService.topCoins(5);

        assertThat(response).isEqualTo(TestCoins.TOP_FIVE);
    }

    @Test
    void coinServiceRequestLimitMoreThanAvailable() {
        var fakeCoinSource = new FakeCoinSource();
        var coinService = new CoinService(fakeCoinSource);

        var response = coinService.topCoins(900);

        assertThat(response).isEqualTo(TestCoins.TOP_FIVE);
    }

    @Test
    void coinServiceRequestLimitIsZero() {
        var fakeCoinSource = new FakeCoinSource();
        var coinService = new CoinService(fakeCoinSource);

        var response = coinService.topCoins(0);
        assertThat(response).isEmpty();
    }

    @Test
    void coinServiceRequestLimitIsNegative() {
        var fakeCoinSource = new FakeCoinSource();
        var coinService = new CoinService(fakeCoinSource);

        var response = coinService.topCoins(-100);
        assertThat(response).isEmpty();
    }

    @Test
    void foundCoinReturnsCorrectRecord() {
        var fakeCoinSource = new FakeCoinSource();
        var coinService = new CoinService(fakeCoinSource);

        var response = coinService.getCoin("ethereum");
        assertThat(response).isEqualTo(TestCoins.ETHEREUM);
    }

    @Test
    void unknownIdThrows() {
        var fakeCoinSource = new FakeCoinSource();
        var coinService = new CoinService(fakeCoinSource);

        assertThatThrownBy(() -> coinService.getCoin("madeup"))
                .isInstanceOf(CoinNotFoundException.class);
    }
}