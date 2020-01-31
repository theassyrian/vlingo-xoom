package io.examples.calculation.endpoint;

import io.examples.calculation.domain.Operation;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class CalculationEndPointTests {

    @Inject
    @Client("/v1")
    HttpClient client;

    @Test
    public void testOperationsRetrieval() {
        final HttpRequest request = HttpRequest.GET("/calculations/operations");
        final HttpResponse<List<Operation>> response = client.toBlocking().exchange(request, Argument.listOf(Operation.class));
        final List<Operation> supportedOperations = response.getBody().get();
        assertEquals(HttpStatus.CREATED, response.getStatus());
        assertEquals(3, supportedOperations.size());
    }
}
