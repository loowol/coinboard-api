package dev.coinboard.api.coin;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CoinService {
  private final CoinSource coinSource;

  public CoinService(CoinSource coinSource) {
    this.coinSource = coinSource;
  }

  public List<String> topCoinIds(int limit) {
    List<String> response = coinSource.fetchCoinIds();
    if (limit <= 0) {
      return List.of();
    } else if (response.size() <= limit) { // <= because size of response is 5 so if limit is 5 we get 5
      return response;
    }
    return new ArrayList<>(response.subList(0, limit));
  }
}