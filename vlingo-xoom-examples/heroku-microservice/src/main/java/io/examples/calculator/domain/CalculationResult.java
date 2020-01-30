package io.examples.calculator.domain;

import static java.lang.String.format;

/**
 * The {@code Calculation} concentrates all information about the result of
 * a mathematical operation.
 *
 * @author Danilo Ambrosio
 */
public class CalculationResult {

    private static final String RESULT_MESSAGE_PATTERN = "The %s of %d and %d is %d";

    private final Operation operation;
    private final Integer firstOperand;
    private final Integer secondOperand;
    private final Integer result;
    private final String description;

    public static CalculationResult from(final Operation operation,
                                  final Integer firstOperand,
                                  final Integer secondOperand,
                                  final Integer result) {
        return new CalculationResult(operation, firstOperand, secondOperand, result);
    }

    private CalculationResult(final Operation operation,
                             final Integer firstOperand,
                             final Integer secondOperand,
                             final Integer result) {
        this.operation = operation;
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.result = result;
        this.description = describe(operation, firstOperand, secondOperand, result);
    }

    private String describe(final Operation operation,
                            final Integer firstOperand,
                            final Integer secondOperand,
                            final Integer result) {
        return format(RESULT_MESSAGE_PATTERN, operation, firstOperand, secondOperand, result);
    }
}
