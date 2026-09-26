package dev.coinboard.api;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CoinboardApiApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void failureTest() {
		fail("Yo");
	}

}
