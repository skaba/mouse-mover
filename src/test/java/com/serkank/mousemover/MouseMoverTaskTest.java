package com.serkank.mousemover;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.serkank.mousemover.MouseMoverTask.State;

class MouseMoverTaskTest {
	@ParameterizedTest
	@MethodSource("valuesForDetermineState")
	void testDetermineState(Duration idleDuration, State expectedState) {
		assertThat(MouseMoverTask.determineState(idleDuration)).isEqualTo(expectedState);
	}
	
	private static Stream<Arguments> valuesForDetermineState() {
	    return Stream.of(
	      Arguments.of(Duration.ofSeconds(10), State.ONLINE),
	      Arguments.of(Duration.ofSeconds(29), State.ONLINE),
	      Arguments.of(Duration.ofSeconds(30), State.IDLE),
	      Arguments.of(Duration.ofSeconds(40), State.IDLE),
	      Arguments.of(Duration.ofSeconds(59), State.IDLE),
	      Arguments.of(Duration.ofSeconds(60), State.AWAY),
	      Arguments.of(Duration.ofSeconds(61), State.AWAY),
	      Arguments.of(Duration.ofSeconds(90), State.AWAY)
	    );
	}
}
