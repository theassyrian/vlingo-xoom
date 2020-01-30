package io.examples.calculator.domain;

import io.examples.calculator.endpoint.CalculatorEndpoint;
import io.examples.calculator.endpoint.v1.CalculatorResource;
import io.vlingo.common.Completes;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/**
 * The {@code CalculatorService} exposes operations and business logic that pertains
 * to the {@link Calculator} domain model. This service forms an anti-corruption layer
 * that is exposed to consumers using the {@link CalculatorResource}.
 *
 * @author Danilo Ambrosio
 * @see CalculatorEndpoint
 */
@Singleton
public class CalculatorService {

    public Completes<CalculationResult> performOperation(final Operation operation,
                                                         final Integer firstOperand,
                                                         final Integer secondOperand) {
        final CalculationResult result =
                Calculator.input(firstOperand, secondOperand).calculate(operation);

        return Completes.withSuccess(result);
    }

    public Completes<List<Operation>> availableOperations() {
        return Completes.withSuccess(Arrays.asList(Operation.values()));
    }

}
