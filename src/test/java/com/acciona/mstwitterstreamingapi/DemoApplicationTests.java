package com.acciona.mstwitterstreamingapi;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@Log4j2
//@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
		final var array = Arrays.asList(1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17);

		array.stream()
				.skip(0)
				.limit(500)
				.forEach(log::info);
	}

}
