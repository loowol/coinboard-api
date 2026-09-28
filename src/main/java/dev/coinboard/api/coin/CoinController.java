package dev.coinboard.api.coin;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coins")
public class CoinController {
    private final CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    @GetMapping
    public List<CoinSummary> topCoins(@RequestParam(defaultValue = "100") @Min(1) @Max(250) int limit) {
        return coinService.topCoins(limit);
    }

    @GetMapping("/{id}")
    public CoinSummary getCoin(@PathVariable String id) {
        return coinService.getCoin(id);
    }
}
