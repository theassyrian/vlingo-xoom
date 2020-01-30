package io.examples;

import io.examples.calculator.domain.CalculationResult;
import io.examples.calculator.domain.Operation;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.RxStreamingHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class CalculatorServiceTests {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    @Ignore
    @SuppressWarnings("unchecked")
    public void testCalculatorOperations() {
//        HttpRequest request = HttpRequest.GET("/v1/calculators/operations");
//        HttpResponse<List<Operation>> response = client.toBlocking().exchange(request, Argument.listOf(Operation.class));
//        List<Operation> supportedOperations = response.getBody().get();
//        assertEquals(HttpStatus.CREATED, response.getStatus());
//        assertEquals(3, supportedOperations.size());
    }
}
