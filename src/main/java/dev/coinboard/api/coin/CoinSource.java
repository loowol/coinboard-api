package dev.coinboard.api.coin;

import java.util.List;

public interface CoinSource {
  public List<String> fetchCoinIds();
}