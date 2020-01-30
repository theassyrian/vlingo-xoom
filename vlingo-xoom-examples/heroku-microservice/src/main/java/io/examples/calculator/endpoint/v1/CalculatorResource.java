package io.vlingo.example.calculator.endpoint.v1;

import io.vlingo.common.Completes;
import io.vlingo.example.calculator.endpoint.CalculatorEndpoint;
import io.vlingo.example.calculator.service.CalculatorService;
import io.vlingo.http.Response;
import io.vlingo.http.resource.RequestHandler;
import io.vlingo.http.resource.ResourceBuilder;
import io.vlingo.xoom.resource.annotations.Resource;

import static io.vlingo.http.Response.Status.Ok;

@Resource
public class CalculatorResource implements CalculatorEndpoint {

    private static final String ENDPOINT_VERSION = "1.1";
    private final CalculatorService calculatorService;

    public CalculatorResource(final CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Override
    public Completes<Response> calculate(final String operation,
                                         final Integer firstOperand,
                                         final Integer secondOperand) {
        return response(Ok, calculatorService.performOperation(operation, firstOperand, secondOperand));
    }

    @Override
    public Completes<Response> availableOperations() {
        return response(Ok, calculatorService.availableOperations());
    }

    public RequestHandler[] getHandlers() {
        return new RequestHandler[]{
                ResourceBuilder.get("/v1/calculators")
                        .query("operation", String.class)
                        .query("firstOperand", Integer.class)
                        .query("secondOperand", Integer.class)
                        .handle(this::calculate)
                        .onError(this::getErrorResponse),
                ResourceBuilder.get("/v1/calculators/operations")
                        .handle(this::availableOperations)
        };
    }

    @Override
    public String getVersion() {
        return ENDPOINT_VERSION;
    }
}
