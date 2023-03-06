package com.example.AgilTestautomatisering;

import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CalculatorTests {

    Calculator calculator = new Calculator();

    @Test
    @DisplayName("Add two positive numbers and assert equal")
    void givenDualPositiveNumber_ShouldAddUp() {
        int firstNumber = 1;
        int secondNumber = 2;
        int result = calculator.add(firstNumber, secondNumber);

        assertEquals(3, result);
    }

    @Test
    @DisplayName("Add two negative numbers and assert equal")
    void givenDualNegativeNumber_ShouldAddUp() {
        int firstNumber = -1;
        int secondNumber = -2;
        int result = calculator.add(firstNumber, secondNumber);

        assertEquals(-3, result);
    }

    @Test
    @DisplayName("Subtract two positive numbers and assert equal")
    void givenDualPositiveNumber_ShouldSubtract() {
        int firstNumber = 2;
        int secondNumber = 1;
        int result = calculator.subtract(firstNumber, secondNumber);

        assertEquals(1, result);
    }

    @Test
    @DisplayName("Subtract two negative numbers and assert equal")
    void givenDualNegativeNumber_ShouldSubtract() {
        int firstNumber = -1;
        int secondNumber = -2;
        int result = calculator.subtract(firstNumber, secondNumber);

        assertEquals(1, result);
    }

    @Test
    @DisplayName("Multiply two numbers and assert equal")
    void givenDualPositiveNumbers_ShouldMultiply() {
        int firstNumber = 2;
        int secondNumber = 2;
        int result = calculator.multiply(firstNumber, secondNumber);

        assertEquals(4, result);
    }

    @Test
    @DisplayName("Divide two numbers and assert equal")
    void givenDualPositiveNumbers_ShouldDivide() {
        int firstNumber = 8;
        int secondNumber = 2;
        double result = calculator.divide(firstNumber, secondNumber);

        assertEquals(4, result);
    }

    @Test
    @DisplayName("Divide with zero and assert true that result is Infinity")
    public void givenZero_ShouldBeInfinite_WhenDivide() {
        int firstNumber = 8;
        int secondNumber = 0;
        double result = calculator.divide(firstNumber, secondNumber);

        assertTrue(Double.isInfinite(result));
    }

    @Test
    @DisplayName("Calculate Square Root Of number and assert equal")
    public void givenNumber_ShouldCalculateSquareRootOf() {
        int number = 4;
        double result = calculator.squareRootOf(number);

        assertEquals(2, result);
    }

    @Test
    @DisplayName("Calculate Square Root Of negative number and assert result is NaN")
    public void givenNegativeNumber_ShouldBeNan_WhenCalculateSquareRootOf() {
        int number = -4;
        double result = calculator.squareRootOf(number);

        assertTrue(Double.isNaN(result));
    }

    @Test
    @DisplayName("Calculate CircumReference of number and assert equal")
    public void givenNumber_ShouldCalculateCircumReference() {
        int number = 4;
        double result = calculator.getCircumference(number);

        assertEquals(25.13, result);
    }
    @Test
    @DisplayName("Calculate area of number and assert equal")
    public void givenNumber_ShouldCalculateArea() {
        int number = 4;
        double result = calculator.getArea(number);

        assertEquals(50.27, result);
    }
}
