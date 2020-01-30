package io.examples;

import io.examples.calculator.domain.Calculator;
import io.micronaut.runtime.Micronaut;

/**
 * The {@code CalculatorApplication} is a microservice that implements features in the {@link Calculator} context.
 */
public class CalculatorApplication {

    public static void main(String[] args) {
        Micronaut.run(CalculatorApplication.class);
    }
}