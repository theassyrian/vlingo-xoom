package io.examples.calculation.repository;

import io.examples.calculation.domain.Calculation;
import io.examples.calculation.domain.Operation;

public interface CalculationRepository {

    Calculation save(final Calculation calculation);

    Calculation applicableCalculationFor(final Operation operation, final Integer firstOperand, final Integer secondOperand);

}
