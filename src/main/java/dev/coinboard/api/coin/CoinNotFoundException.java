package dev.coinboard.api.coin;

public class CoinNotFoundException extends RuntimeException {
    private final String coinId;

    public CoinNotFoundException(String coinId) {
        super("No coin with id " + coinId);
        this.coinId = coinId;
    }

    public String coinId() {
        return coinId;
    }

}