package dev.coinboard.api.coin;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
class StubCoinSource implements CoinSource {
  @Override
  public List<String> fetchCoinIds() {
    return List.of("bitcoin", "etherium", "tether", "bnb", "xrp");
  }
}