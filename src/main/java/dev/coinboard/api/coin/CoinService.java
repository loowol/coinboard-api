package dev.coinboard.api.coin;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CoinService {
  private final CoinSource coinSource;

  public CoinService(CoinSource coinSource) {
    this.coinSource = coinSource;
  }

  public List<String> topCoinIds(int limit) {
    if (limit <= 0) {
      return List.of();
    }

    List<String> response = coinSource.fetchCoinIds();

    return List.copyOf(response.subList(0, Math.min(response.size(), limit)));
  }
}