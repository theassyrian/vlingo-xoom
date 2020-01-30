public class Calculator {

    public CalculationResult sum(final Integer firstOperand, final Integer secondOperand) {
        final Integer result = firstOperand + secondOperand;
        return CalculationResult.with(Operation.ADDITION);
    }

    public CalculationResult subtract(final Integer firstOperand, final Integer secondOperand) {

    }

    public CalculationResult multiply(final Integer firstOperand, final Integer secondOperand) {

    }

}