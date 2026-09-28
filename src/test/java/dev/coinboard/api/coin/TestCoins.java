package dev.coinboard.api.coin;

import java.util.List;

final class TestCoins {
    static final CoinSummary BITCOIN = new CoinSummary("bitcoin", "btc", "Bitcoin", 1);
    static final CoinSummary ETHEREUM = new CoinSummary("ethereum", "eth", "Ethereum", 2);
    static final CoinSummary TETHER = new CoinSummary("tether", "usdt", "Tether", 3);
    static final CoinSummary BNB = new CoinSummary("binancecoin", "bnb", "BNB", 4);
    static final CoinSummary XRP = new CoinSummary("ripple", "xrp", "XRP", 5);

    static final List<CoinSummary> TOP_FIVE = List.of(BITCOIN, ETHEREUM, TETHER, BNB, XRP);

    static final String TWO_COINS_JSON = """
                [{"id":"bitcoin", "symbol":"btc", "name":"Bitcoin", "marketCapRank":1},
                {"id":"ethereum", "symbol":"eth", "name":"Ethereum", "marketCapRank":2}]
            """;

    private TestCoins() {}
}
