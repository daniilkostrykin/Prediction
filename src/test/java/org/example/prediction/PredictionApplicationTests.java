package org.example.prediction;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.example.prediction.config.RedisConfig;

@SpringBootTest
@ActiveProfiles("test")
@ImportAutoConfiguration(exclude = {RedisConfig.class})
class PredictionApplicationTests {

	@Test
	void contextLoads() {
	}

}
