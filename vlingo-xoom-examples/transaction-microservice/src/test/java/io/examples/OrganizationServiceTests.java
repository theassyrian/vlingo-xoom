package io.examples;

import io.examples.domain.Organization;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;


@MicronautTest(application = DemoApplication.class)
public class OrganizationServiceTests {
    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/v1")
    HttpClient client;

    @SuppressWarnings("unchecked")
    @Test
    public void testCreateOrganization() {
        Organization organization = new Organization();

        HttpRequest request = HttpRequest.POST("/organizations", organization);
        HttpResponse<Organization> response = client.toBlocking().exchange(request, Organization.class);
        Organization organizationReply = response.getBody().get();

        assertEquals(HttpStatus.CREATED, response.getStatus());
    }
}
