package dev.coinboard.api.coin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@WebMvcTest(CoinController.class)
class CoinControllerTest {
    @Autowired
    MockMvcTester mvc;

    @MockitoBean
    CoinService coinService;

    @Test
    void noLimitUsesDefault100() {
        when(coinService.topCoins(100))
                .thenReturn(List.of(TestCoins.BITCOIN, TestCoins.ETHEREUM));

        assertThat(mvc.get().uri("/api/coins")).hasStatusOk().bodyJson()
                .isStrictlyEqualTo(TestCoins.TWO_COINS_JSON);

        verify(coinService).topCoins(100);
    }

    @Test
    void limitEquals250IsAccepted() {
        when(coinService.topCoins(250))
                .thenReturn(List.of(TestCoins.BITCOIN, TestCoins.ETHEREUM));

        assertThat(mvc.get().uri("/api/coins?limit=250")).hasStatusOk()
                .bodyJson()
                .isStrictlyEqualTo(TestCoins.TWO_COINS_JSON);

        verify(coinService).topCoins(250);
    }

    @Test
    void limitEquals0IsRejected() {
        assertThat(mvc.get().uri("/api/coins?limit=0")).hasStatus(400)
                .bodyJson().extractingPath("$.errors").asArray()
                .containsExactlyInAnyOrder(
                        "limit: must be greater than or equal to 1");
        verifyNoInteractions(coinService);
    }

    @Test
    void limitEquals251IsRejected() {
        assertThat(mvc.get().uri("/api/coins?limit=251")).hasStatus(400)
                .bodyJson().extractingPath("$.errors").asArray()
                .containsExactlyInAnyOrder(
                        "limit: must be less than or equal to 250");
        verifyNoInteractions(coinService);
    }

    @Test
    void limitEqualsAbcIsRejected() {
        assertThat(mvc.get().uri("/api/coins?limit=abc")).hasStatus(400)
                .hasContentType(MediaType.APPLICATION_PROBLEM_JSON);
        verifyNoInteractions(coinService);
    }

    @Test
    void validCoinIdWorks() {
        when(coinService.getCoin("bitcoin")).thenReturn(TestCoins.BITCOIN);

        assertThat(mvc.get().uri("/api/coins/bitcoin")).hasStatus(200)
                .bodyJson().isStrictlyEqualTo(
                        "{'id':'bitcoin', 'symbol':'btc', 'name':'Bitcoin', 'marketCapRank':1}");

        verify(coinService).getCoin("bitcoin");
    }

    @Test
    void unknownIdThrows() {
        when(coinService.getCoin("madeup"))
                .thenThrow(new CoinNotFoundException("madeup"));

        assertThat(mvc.get().uri("/api/coins/madeup")).hasStatus(404)
                .hasContentType(MediaType.APPLICATION_PROBLEM_JSON).bodyJson()
                .isStrictlyEqualTo(
                        """
                        {"title": "Coin not found", "status": 404, "detail":"No coin with id madeup", "instance": "/api/coins/madeup", "coinId":"madeup"}
                            """);

        verify(coinService).getCoin("madeup");
    }

    @Test
    void unexpectedErrorReturnsGeneric500() {
        when(coinService.getCoin("catchall"))
                .thenThrow(new IllegalStateException("db password is hunter2"));

        assertThat(mvc.get().uri("/api/coins/catchall")).hasStatus(500)
                .bodyJson().extractingPath("$.detail")
                .isEqualTo("Something went wrong.");

        verify(coinService).getCoin("catchall");
    }
}