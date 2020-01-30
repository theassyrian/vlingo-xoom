package io.examples.calculator.domain;

import java.util.function.Function;

/**
 * The {@code Operation} specifies the supported mathematical
 * operations on {@link Calculator}.
 *
 * @author Danilo Ambrosio
 */
public enum Operation {

    ADDITION(Calculator::sum),
    SUBTRACTION(Calculator::subtract),
    MULTIPLICATION(Calculator::multiply);

    private final Function<Calculator, CalculationResult> function;

    Operation(final Function<Calculator, CalculationResult> function) {
        this.function = function;
    }

    public static Operation withName(final String operationName) {
        return valueOf(operationName.toUpperCase());
    }

    public CalculationResult perform(final Calculator calculator) {
        return this.function.apply(calculator);
    }
}
