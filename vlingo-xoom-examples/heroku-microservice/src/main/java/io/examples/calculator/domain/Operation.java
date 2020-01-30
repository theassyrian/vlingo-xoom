package io.examples.calculator.domain;

public enum Operation {

    ADDITION,
    SUBTRACTION,
    MULTIPLICATION;

    public static Operation withName(final String operationName) {
        final Operation operation = valueOf(operationName.toUpperCase());

        if(operation == null) {
            throw new IllegalArgumentException("Unknown operation");
        }

        return operation;
    }
}
