package com.shirish.reactivewebdemo.controller;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.shirish.reactivewebdemo.dto.Foo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@RestController
public class FooController {
	Logger logger = LoggerFactory.getLogger(FooController.class);


	@GetMapping(value = "/foo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Foo> streamFooObject() {
		Random r = new Random();		 
		return Flux.fromStream(
				Stream.generate(() -> r.nextInt())
				.map(s -> new Foo(s+"", "name"+s))
				).delayElements(Duration.ofSeconds(5));
	}


	@GetMapping(path = "/stringfoo", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> streamStringMsg() {
		return Flux.interval(
				Duration.ofSeconds(1))
				.map(sequence -> "alive:" + (new Random()).nextInt());
	}


}
