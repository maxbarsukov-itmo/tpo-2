package ru.itmo.qa.lab2.log.module;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.itmo.qa.lab2.log.NaturalLogarithm;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NaturalLogarithmTest {

  private NaturalLogarithm ln;
  private static final BigDecimal PRECISION = new BigDecimal("0.000001");

  @BeforeEach
  void init() {
    ln = new NaturalLogarithm();
  }

  @Test
  void shouldNotCalculateForZero() {
    assertThrows(ArithmeticException.class, () -> ln.calculate(ZERO, PRECISION));
  }

  @Test
  void shouldCalculateForOne() {
    assertEquals(ZERO, ln.calculate(ONE, PRECISION));
  }

  @ParameterizedTest(name = "ln({0})")
  @ValueSource(doubles = { 0.5, 0.707, 1.2, 2.0, 2.2 })
  void testLn(double d) {
    BigDecimal expected = BigDecimal.valueOf(Math.log(d)).setScale(6, HALF_EVEN);
    assertEquals(expected, ln.calculate(BigDecimal.valueOf(d), PRECISION));
  }
}
