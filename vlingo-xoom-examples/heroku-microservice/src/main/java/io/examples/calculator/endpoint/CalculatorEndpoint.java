package io.examples.calculator.endpoint;

import io.vlingo.common.Completes;
import io.vlingo.http.Response;
import io.vlingo.xoom.resource.Endpoint;

public interface CalculatorEndpoint extends Endpoint {

    String ENDPOINT_NAME = "Calculator";

    default String getName() {
        return ENDPOINT_NAME;
    }

    Completes<Response> calculate(String operation, Integer firstOperand, Integer secondOperand);

    Completes<Response> availableOperations();
}
