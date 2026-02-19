package info.jab.examples;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

/**
 * Comprehensive test suite for the Calculator class.
 * Demonstrates various JUnit 5 features and testing best practices.
 */
@DisplayName("Calculator Tests")
class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Nested
    @DisplayName("Basic Arithmetic Operations")
    class BasicArithmeticTests {

        @Test
        @DisplayName("Should add two positive numbers correctly")
        void shouldAddTwoPositiveNumbers() {
            // Given
            double a = 5.0;
            double b = 3.0;

            // When
            double result = calculator.add(a, b);

            // Then
            assertThat(result).isEqualTo(8.0);
        }

        @ParameterizedTest
        @DisplayName("Should add various number combinations correctly")
        @CsvSource({
            "0.0, 0.0, 0.0",
            "1.0, 1.0, 2.0",
            "-1.0, 1.0, 0.0",
            "-5.0, -3.0, -8.0",
            "10.5, 2.3, 12.8"
        })
        void shouldAddVariousNumbers(double a, double b, double expected) {
            double result = calculator.add(a, b);
            assertThat(result).isCloseTo(expected, within(0.0001));
        }

        @Test
        @DisplayName("Should subtract two numbers correctly")
        void shouldSubtractTwoNumbers() {
            // Given
            double a = 10.0;
            double b = 3.0;

            // When
            double result = calculator.subtract(a, b);

            // Then
            assertThat(result).isEqualTo(7.0);
        }

        @ParameterizedTest
        @DisplayName("Should subtract various number combinations correctly")
        @CsvSource({
            "10.0, 5.0, 5.0",
            "0.0, 0.0, 0.0",
            "-5.0, -3.0, -2.0",
            "3.0, 5.0, -2.0"
        })
        void shouldSubtractVariousNumbers(double a, double b, double expected) {
            double result = calculator.subtract(a, b);
            assertThat(result).isCloseTo(expected, within(0.0001));
        }

        @Test
        @DisplayName("Should multiply two numbers correctly")
        void shouldMultiplyTwoNumbers() {
            // Given
            double a = 4.0;
            double b = 3.0;

            // When
            double result = calculator.multiply(a, b);

            // Then
            assertThat(result).isEqualTo(12.0);
        }

        @ParameterizedTest
        @DisplayName("Should multiply various number combinations correctly")
        @CsvSource({
            "0.0, 5.0, 0.0",
            "1.0, 1.0, 1.0",
            "-2.0, 3.0, -6.0",
            "-2.0, -3.0, 6.0",
            "2.5, 4.0, 10.0"
        })
        void shouldMultiplyVariousNumbers(double a, double b, double expected) {
            double result = calculator.multiply(a, b);
            assertThat(result).isCloseTo(expected, within(0.0001));
        }
    }

    @Nested
    @DisplayName("Division Operations")
    class DivisionTests {

        @Test
        @DisplayName("Should divide two numbers correctly")
        void shouldDivideTwoNumbers() {
            // Given
            double a = 10.0;
            double b = 2.0;

            // When
            double result = calculator.divide(a, b);

            // Then
            assertThat(result).isEqualTo(5.0);
        }

        @Test
        @DisplayName("Should throw exception when dividing by zero")
        void shouldThrowExceptionWhenDividingByZero() {
            // Given
            double a = 10.0;
            double b = 0.0;

            // When & Then
            assertThatThrownBy(() -> calculator.divide(a, b))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot divide by zero");
        }

        @ParameterizedTest
        @DisplayName("Should divide various number combinations correctly")
        @CsvSource({
            "10.0, 2.0, 5.0",
            "9.0, 3.0, 3.0",
            "-10.0, 2.0, -5.0",
            "10.0, -2.0, -5.0",
            "1.0, 3.0, 0.3333333333"
        })
        void shouldDivideVariousNumbers(double a, double b, double expected) {
            double result = calculator.divide(a, b);
            assertThat(result).isCloseTo(expected, within(0.0001));
        }
    }

    @Nested
    @DisplayName("Advanced Operations")
    class AdvancedOperationTests {

        @Test
        @DisplayName("Should calculate power correctly")
        void shouldCalculatePower() {
            // Given
            double base = 2.0;
            int exponent = 3;

            // When
            double result = calculator.power(base, exponent);

            // Then
            assertThat(result).isEqualTo(8.0);
        }

        @ParameterizedTest
        @DisplayName("Should calculate various powers correctly")
        @CsvSource({
            "2.0, 0, 1.0",
            "2.0, 1, 2.0",
            "2.0, 3, 8.0",
            "5.0, 2, 25.0",
            "10.0, -1, 0.1"
        })
        void shouldCalculateVariousPowers(double base, int exponent, double expected) {
            double result = calculator.power(base, exponent);
            assertThat(result).isCloseTo(expected, within(0.0001));
        }

        @Test
        @DisplayName("Should calculate square root correctly")
        void shouldCalculateSquareRoot() {
            // Given
            double number = 9.0;

            // When
            double result = calculator.sqrt(number);

            // Then
            assertThat(result).isEqualTo(3.0);
        }

        @Test
        @DisplayName("Should throw exception for negative square root")
        void shouldThrowExceptionForNegativeSquareRoot() {
            // Given
            double number = -4.0;

            // When & Then
            assertThatThrownBy(() -> calculator.sqrt(number))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Cannot calculate square root of negative number");
        }

        @ParameterizedTest
        @DisplayName("Should calculate square roots correctly")
        @ValueSource(doubles = {0.0, 1.0, 4.0, 16.0, 25.0})
        void shouldCalculateVariousSquareRoots(double number) {
            double result = calculator.sqrt(number);
            assertThat(result * result).isCloseTo(number, within(0.0001));
        }
    }

    @Nested
    @DisplayName("Utility Operations")
    class UtilityOperationTests {

        @Test
        @DisplayName("Should calculate absolute value correctly")
        void shouldCalculateAbsoluteValue() {
            assertThat(calculator.abs(-5.0)).isEqualTo(5.0);
            assertThat(calculator.abs(5.0)).isEqualTo(5.0);
            assertThat(calculator.abs(0.0)).isEqualTo(0.0);
        }

        @Test
        @DisplayName("Should calculate percentage correctly")
        void shouldCalculatePercentage() {
            // Given
            double number = 100.0;
            double percentage = 25.0;

            // When
            double result = calculator.percentage(number, percentage);

            // Then
            assertThat(result).isEqualTo(25.0);
        }

        @ParameterizedTest
        @DisplayName("Should calculate various percentages correctly")
        @CsvSource({
            "100.0, 10.0, 10.0",
            "200.0, 50.0, 100.0",
            "75.0, 20.0, 15.0",
            "1000.0, 2.5, 25.0"
        })
        void shouldCalculateVariousPercentages(double number, double percentage, double expected) {
            double result = calculator.percentage(number, percentage);
            assertThat(result).isCloseTo(expected, within(0.0001));
        }

        @Test
        @DisplayName("Should find maximum of two numbers")
        void shouldFindMaximum() {
            assertThat(calculator.max(5.0, 3.0)).isEqualTo(5.0);
            assertThat(calculator.max(-2.0, -5.0)).isEqualTo(-2.0);
            assertThat(calculator.max(0.0, 0.0)).isEqualTo(0.0);
        }

        @Test
        @DisplayName("Should find minimum of two numbers")
        void shouldFindMinimum() {
            assertThat(calculator.min(5.0, 3.0)).isEqualTo(3.0);
            assertThat(calculator.min(-2.0, -5.0)).isEqualTo(-5.0);
            assertThat(calculator.min(0.0, 0.0)).isEqualTo(0.0);
        }
    }
}
