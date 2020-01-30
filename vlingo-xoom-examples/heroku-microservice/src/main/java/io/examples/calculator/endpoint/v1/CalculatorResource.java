package io.examples.calculator.endpoint.v1;

import io.examples.calculator.domain.CalculatorService;
import io.examples.calculator.domain.Operation;
import io.examples.calculator.endpoint.CalculatorEndpoint;
import io.vlingo.common.Completes;
import io.vlingo.http.Response;
import io.vlingo.http.resource.RequestHandler;
import io.vlingo.http.resource.ResourceBuilder;
import io.vlingo.xoom.resource.Endpoint;
import io.vlingo.xoom.resource.annotations.Resource;

import static io.vlingo.http.Response.Status.Ok;

/**
 * This {@code CalculatorResource} exposes a REST API that maps resource HTTP request-response handlers to operations
 * contained in the {@link CalculatorService}. This {@link Endpoint} implementation forms an anti-corruption layer between
 * consuming services and this microservice's {@link CalculatorService} API.
 * <p>
 * This resource is a versioned API definition that implements the {@link CalculatorEndpoint}. To fork versions, create a
 * separate implementation of the {@link CalculatorEndpoint} in a separate package and change the getRequestHandlers
 * method by incrementing the versioned URI root.
 *
 * @author Danilo Ambrosio
 * @see CalculatorEndpoint
 */
@Resource
public class CalculatorResource implements CalculatorEndpoint {

    private static final String ENDPOINT_VERSION = "1.1";
    private final CalculatorService calculatorService;

    public CalculatorResource(final CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @Override
    public Completes<Response> calculate(final String operationName,
                                         final Integer firstOperand,
                                         final Integer secondOperand) {
        final Operation operation = Operation.withName(operationName);
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
