package info.jab.examples;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A simple calculator class that provides basic arithmetic operations.
 * This class demonstrates basic Java programming concepts and best practices.
 */
public class Calculator {

    private static final int DEFAULT_SCALE = 10;

    /**
     * Adds two numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the sum of a and b
     */
    public double add(double a, double b) {
        return BigDecimal.valueOf(a)
                .add(BigDecimal.valueOf(b))
                .doubleValue();
    }

    /**
     * Subtracts the second number from the first number.
     *
     * @param a the first number (minuend)
     * @param b the second number (subtrahend)
     * @return the difference of a and b
     */
    public double subtract(double a, double b) {
        return BigDecimal.valueOf(a)
                .subtract(BigDecimal.valueOf(b))
                .doubleValue();
    }

    /**
     * Multiplies two numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the product of a and b
     */
    public double multiply(double a, double b) {
        return BigDecimal.valueOf(a)
                .multiply(BigDecimal.valueOf(b))
                .doubleValue();
    }

    /**
     * Divides the first number by the second number.
     *
     * @param a the dividend
     * @param b the divisor
     * @return the quotient of a divided by b
     * @throws IllegalArgumentException if the divisor is zero
     */
    public double divide(double a, double b) {
        if (b == 0.0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }

        return BigDecimal.valueOf(a)
                .divide(BigDecimal.valueOf(b), DEFAULT_SCALE, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Calculates the power of a number.
     *
     * @param base the base number
     * @param exponent the exponent
     * @return the result of base raised to the power of exponent
     */
    public double power(double base, int exponent) {
        return Math.pow(base, exponent);
    }

    /**
     * Calculates the square root of a number.
     *
     * @param number the number to find the square root of
     * @return the square root of the number
     * @throws IllegalArgumentException if the number is negative
     */
    public double sqrt(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot calculate square root of negative number");
        }
        return Math.sqrt(number);
    }

    /**
     * Calculates the absolute value of a number.
     *
     * @param number the number
     * @return the absolute value of the number
     */
    public double abs(double number) {
        return Math.abs(number);
    }

    /**
     * Calculates the percentage of a number.
     *
     * @param number the base number
     * @param percentage the percentage to calculate
     * @return the percentage of the number
     */
    public double percentage(double number, double percentage) {
        return BigDecimal.valueOf(number)
                .multiply(BigDecimal.valueOf(percentage))
                .divide(BigDecimal.valueOf(100), DEFAULT_SCALE, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * Finds the maximum of two numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the larger of the two numbers
     */
    public double max(double a, double b) {
        return Math.max(a, b);
    }

    /**
     * Finds the minimum of two numbers.
     *
     * @param a the first number
     * @param b the second number
     * @return the smaller of the two numbers
     */
    public double min(double a, double b) {
        return Math.min(a, b);
    }
}
