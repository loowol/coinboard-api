package dev.coinboard.api.coin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
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
        when(coinService.topCoins(100)).thenReturn(List.of(TestCoins.BITCOIN, TestCoins.ETHEREUM));

        assertThat(mvc.get().uri("/api/coins")).hasStatusOk().bodyJson().isStrictlyEqualTo(TestCoins.TWO_COINS_JSON);

        verify(coinService).topCoins(100);
    }

    @Test
    void limitEquals250IsAccepted() {
        when(coinService.topCoins(250)).thenReturn(List.of(TestCoins.BITCOIN, TestCoins.ETHEREUM));

        assertThat(mvc.get().uri("/api/coins?limit=250")).hasStatusOk().bodyJson()
                .isStrictlyEqualTo(TestCoins.TWO_COINS_JSON);

        verify(coinService).topCoins(250);
    }

    @Test
    void limitEquals0IsRejected() {
        assertThat(mvc.get().uri("/api/coins?limit=0")).hasStatus(400);
        verifyNoInteractions(coinService);
    }

    @Test
    void limitEquals251IsRejected() {
        assertThat(mvc.get().uri("/api/coins?limit=251")).hasStatus(400);
        verifyNoInteractions(coinService);
    }

    @Test
    void limitEqualsAbcIsRejected() {
        assertThat(mvc.get().uri("/api/coins?limit=abc")).hasStatus(400);
        verifyNoInteractions(coinService);
    }
}