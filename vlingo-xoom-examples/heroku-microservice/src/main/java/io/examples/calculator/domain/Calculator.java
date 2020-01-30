package io.examples.calculator.domain;

import static io.examples.calculator.domain.Operation.*;

/**
 * The {@code Calculator} has been modeled to serve a basic mathematical calculation domain,
 * being able to calculate the following operations:
 *
 * <li>{@link Operation#ADDITION}</li>
 * <li>{@link Operation#SUBTRACTION}</li>
 * <li>{@link Operation#MULTIPLICATION}</li>
 *
 * @author Danilo Ambrosio
 */
public class Calculator {

    private final Integer firstOperand;
    private final Integer secondOperand;

    public static Calculator input(final Integer firstOperand, final Integer secondOperand) {
        return new Calculator(firstOperand, secondOperand);
    }

    private Calculator(final Integer firstOperand, final Integer secondOperand) {
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    public CalculationResult calculate(final Operation operation) {
        return operation.perform(this);
    }

    protected CalculationResult sum() {
        return CalculationResult.from(ADDITION, firstOperand,
                secondOperand, firstOperand + secondOperand);
    }

    protected CalculationResult subtract() {
        return CalculationResult.from(SUBTRACTION, firstOperand,
                secondOperand, firstOperand - secondOperand);
    }

    protected CalculationResult multiply() {
        return CalculationResult.from(MULTIPLICATION, firstOperand,
                secondOperand, firstOperand * secondOperand);
    }

}