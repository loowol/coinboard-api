package dev.coinboard.api.coin;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
class StubCoinSource implements CoinSource {
    @Override
                                                        public List<CoinSummary> fetchCoins() {
        CoinSummary BITCOIN = new CoinSummary("bitcoin", "btc", "Bitcoin", 1);
        CoinSummary ETHEREUM = new CoinSummary("ethereum", "eth", "Ethereum", 2);
        CoinSummary TETHER = new CoinSummary("tether", "usdt", "Tether", 3);
        CoinSummary BNB = new CoinSummary("binancecoin", "bnb", "BNB", 4);
        CoinSummary XRP = new CoinSummary("ripple", "xrp", "XRP", 5);

        return List.of(BITCOIN, ETHEREUM, TETHER, BNB, XRP);
    }
}
