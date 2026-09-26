package dev.coinboard.api.coin;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class CoinServiceWiringTest {

  @Autowired
  CoinService coinService;

  @Autowired
  ApplicationContext context;

  @Test
  void serviceIsCreatedAndWired() {
    assertThat(coinService).isNotNull();
  }

  @Test
  void thereIsExactlyOneInstance() {
    assertThat(context.getBean(CoinService.class)).isSameAs(coinService);
  }

  @Test
  void topCoinIdsWithLimitThreeReturnsThreeCoins() {
    assertThat(coinService.topCoinIds(3)).containsExactly("bitcoin", "ethereum", "tether");
  }
}