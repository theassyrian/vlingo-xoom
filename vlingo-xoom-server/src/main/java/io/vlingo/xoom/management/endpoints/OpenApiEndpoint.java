package io.vlingo.xoom.management.endpoints;

import com.google.gson.GsonBuilder;
import io.micronaut.management.endpoint.annotation.Endpoint;
import io.micronaut.management.endpoint.annotation.Read;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.vlingo.xoom.VlingoServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Endpoint(id = "openapi", prefix = "custom", defaultEnabled = true, defaultSensitive = false)
public class OpenApiEndpoint {
    private final VlingoServer server;

    public OpenApiEndpoint(VlingoServer server) {
        this.server = server;
    }

    @Read
    @SuppressWarnings("unchecked")
    public Object getSpecification() {

        OpenAPI spec = new OpenAPI();
        Paths paths = new Paths();

        server.getEndpoints().forEach(e -> Stream.of(e.getHandlers()).forEach(r -> {
            PathItem pathItem = new PathItem();

            Operation operation = new Operation();

            String[] params = r.actionSignature.split(",");
            List<Parameter> parameters = Stream.of(params).map(p -> p.split(" "))
                    .filter(p -> p.length == 2)
                    .map(p -> {
                        Parameter parameter = new Parameter();
                        parameter.setName(p[1]);
                        parameter.setIn("path");
                        Schema schema = new Schema();
                        schema.setType(p[0]);
                        parameter.setSchema(schema);
                        return parameter;
                    }).collect(Collectors.toList());

            pathItem.setParameters(parameters);

            operation.addTagsItem(e.getName());

            if (r.bodyType != null) {
                RequestBody requestBody = new RequestBody();
                Content content = new Content();
                MediaType mediaType = new MediaType();
                try {
                    Object example = r.bodyType.newInstance();
                    GsonBuilder builder = new GsonBuilder().serializeNulls();
                    Map<String, Object> map = new HashMap<>();
                    map = builder.create()
                            .fromJson(builder.create().toJson(example),
                                    (Class<Map<String, Object>>) map.getClass());
                    mediaType.setExample(map);
                    content.addMediaType(e.getContentTypeResponseHeader().value, mediaType);
                    requestBody.setContent(content);
                    requestBody.setDescription(r.bodyType.getName());
                } catch (InstantiationException | IllegalAccessException ignored) {
                }
                operation.setRequestBody(requestBody);
            }

            switch (r.method) {
                case POST:
                    pathItem.setPost(operation);
                    break;
                case GET:
                case CONNECT:
                    pathItem.setGet(operation);
                    break;
                case PUT:
                    pathItem.setPut(operation);
                    break;
                case PATCH:
                    pathItem.setPatch(operation);
                    break;
                case DELETE:
                    pathItem.setDelete(operation);
                    break;
                case HEAD:
                    pathItem.setHead(operation);
                    break;
                case TRACE:
                    pathItem.setTrace(operation);
                    break;
                case OPTIONS:
                    pathItem.setOptions(operation);
                    break;
            }

            paths.addPathItem(r.path, pathItem);
        }));

        spec.setPaths(paths);
        return spec;
    }
}
