package dev.coinboard.api.coin;

import java.util.List;

public interface CoinSource {
  List<String> fetchCoinIds();
}