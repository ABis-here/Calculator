package com.example.calculator;

import org.junit.Test;
import static org.junit.Assert.*;

public class CalculatorUnitTest {

    @Test
    public void addition_isCorrect() {
        // Testuoju paprasta sudeti
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testDecimalAddition() {
        // Testuoju skaiciu su kableliu sudeti
        double result = 2.5 + 1.5;
        assertEquals(4.0, result, 0.001); // delta leidzia nedidele paklaida
    }

    @Test
    public void testDivisionByZero() {
        // Testuoju dalyba is nulio
        try {
            double result = 10 / 0;
            fail("Dalyba iš nulio turėtų mesti išimtį");
        } catch (ArithmeticException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testMultiplication() {
        // Testuoju daugyba
        assertEquals(6, 2 * 3);
    }

    @Test
    public void testSubtraction() {
        // Testuoju atimti
        assertEquals(3, 5 - 2);
    }
}