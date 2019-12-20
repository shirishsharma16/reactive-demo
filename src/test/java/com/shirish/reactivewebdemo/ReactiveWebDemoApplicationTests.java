package com.shirish.reactivewebdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "100000")
class ReactiveWebDemoApplicationTests {

	@Autowired
	private WebTestClient webClient;

	@Test
	public void testCommentStream() {
		int testIterations = 4;
		List<String> fooResponseList = webClient
				.get().uri("/stringfoo")
				.accept(MediaType.valueOf(MediaType.TEXT_EVENT_STREAM_VALUE))
				.exchange()
				.expectStatus().isOk()
				.returnResult(String.class)
				.getResponseBody()
				.take(testIterations) 
				.collectList()
				.block();

		fooResponseList.forEach(response -> System.out.println(response));

		assertEquals(testIterations,  fooResponseList.size());

	}

}
