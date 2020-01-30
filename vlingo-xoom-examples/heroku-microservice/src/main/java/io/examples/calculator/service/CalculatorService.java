package io.vlingo.example.calculator.service;

import io.examples.calculator.domain.Operation;
import io.vlingo.common.Completes;

import javax.inject.Singleton;

import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

@Singleton
public class CalculatorService {

    private static final String RESULT_MESSAGE_PATTERN = "The %s of %d and %d is %d";

    public Completes<String> performOperation(final String operation,
                                              final Integer firstOperand,
                                              final Integer secondOperand) {
        final Integer result = calculate(operation, firstOperand, secondOperand);
        return Completes.withSuccess(format(RESULT_MESSAGE_PATTERN, operation, firstOperand, secondOperand, result));
    }

    public Completes<List<Operation>> availableOperations() {
        return Completes.withSuccess(Arrays.asList(Operation.values()));
    }

    private Integer calculate(final String operationName,
                              final Integer firstOperand,
                              final Integer secondOperand) {

        final Operation operation = Operation.withName(operationName);

        switch(operation) {
            case ADDITION:
                return firstOperand + secondOperand;
            case SUBTRACTION:
                return firstOperand - secondOperand;
            case MULTIPLICATION:
                return firstOperand * secondOperand;
            default:
                throw new IllegalArgumentException("Unknown operation");
        }
    }

}
