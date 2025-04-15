package ru.itmo.qa.lab2.trig.module;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.itmo.qa.lab2.trig.Cotangent;

import java.math.BigDecimal;

import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_EVEN;
import static org.junit.jupiter.api.Assertions.*;

class CotangentTest {
  private static final BigDecimal PRECISION = new BigDecimal("0.0000001");
  private Cotangent cot;

  @BeforeEach
  void init() {
    cot = new Cotangent();
  }

  @ParameterizedTest(name = "cot({0})")
  @ValueSource(doubles = { -1.5 * Math.PI, -Math.PI / 2, Math.PI / 2, 1.5 * Math.PI })
  void shouldCalculateForPiHalf(double x) {
    BigDecimal arg = BigDecimal.valueOf(x);
    assertEquals(ZERO.setScale(PRECISION.scale(), HALF_EVEN), cot.calculate(arg, PRECISION));
  }

  @ParameterizedTest
  @ValueSource(doubles = { -Math.PI, 0, Math.PI })
  void shouldNotCalculateForPi(double x) {
    BigDecimal arg = BigDecimal.valueOf(x).setScale(PRECISION.scale(), HALF_EVEN);
    assertAll(
        () -> {
          Throwable exception = assertThrows(ArithmeticException.class, () -> cot.calculate(arg, PRECISION));
          String msg = format("У котангенса нет значения при x = %s", arg);
          assertEquals(msg, exception.getMessage());
        });
  }

  @ParameterizedTest(name = "cot({0})")
  @CsvFileSource(resources = "/cot.csv", numLinesToSkip = 1, delimiter = ',')
  void testCot(BigDecimal x, BigDecimal y) {
    assertEquals(y, cot.calculate(x, PRECISION));
  }
}
