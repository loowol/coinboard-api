package dev.coinboard.api.coin;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class CoinServiceTest {

  static class FakeCoinSource implements CoinSource {
    @Override
    public List<String> fetchCoinIds() {
      return List.of("bitcoin", "ethereum", "tether", "binancecoin", "ripple");
    }
  }

  @Test
  void coinServiceRequestLimitLessThanAvailable() {
    var fakeCoinSource = new FakeCoinSource();
    var service = new CoinService(fakeCoinSource);

    var response = service.topCoinIds(3);

    assertThat(response).containsExactly("bitcoin", "ethereum", "tether");
  }

  @Test
  void coinServiceRequestLimitEqualsAvailable() {
    var fakeCoinSource = new FakeCoinSource();
    var coinService = new CoinService(fakeCoinSource);

    var response = coinService.topCoinIds(5);

    assertThat(response).containsExactly("bitcoin", "ethereum", "tether", "binancecoin", "ripple");
  }

  @Test
  void coinServiceRequestLimitMoreThanAvailable() {
    var fakeCoinSource = new FakeCoinSource();
    var coinService = new CoinService(fakeCoinSource);

    var response = coinService.topCoinIds(900);

    assertThat(response).containsExactly("bitcoin", "ethereum", "tether", "binancecoin", "ripple");
  }

  @Test
  void coinServiceRequestLimitIsZero() {
    var fakeCoinSource = new FakeCoinSource();
    var coinService = new CoinService(fakeCoinSource);

    var response = coinService.topCoinIds(0);
    assertThat(response).isEmpty();
  }

  @Test
  void coinServiceRequestLimitIsNegative() {
    var fakeCoinSource = new FakeCoinSource();
    var coinService = new CoinService(fakeCoinSource);

    var response = coinService.topCoinIds(-100);
    assertThat(response).isEmpty();
  }
}