package io.examples;

import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class CalculationApplicationServiceTests {

    @Inject
    @Client("/v1")
    HttpClient client;

    @Test
    @Ignore
    @SuppressWarnings("unchecked")
    public void testOperationsRetrieval() {
//        HttpRequest request = HttpRequest.GET("/calculators/operations");
//        HttpResponse<List<Operation>> response = client.toBlocking().exchange(request, Argument.listOf(Operation.class));
//        List<Operation> supportedOperations = response.getBody().get();
//        assertEquals(HttpStatus.CREATED, response.getStatus());
//        assertEquals(3, supportedOperations.size());
    }
}
