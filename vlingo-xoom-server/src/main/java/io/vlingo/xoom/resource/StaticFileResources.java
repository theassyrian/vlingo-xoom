package io.vlingo.xoom.resource;

import io.vlingo.common.Completes;
import io.vlingo.http.Body;
import io.vlingo.http.Header;
import io.vlingo.http.Response;
import io.vlingo.http.ResponseHeader;
import io.vlingo.http.resource.*;

import javax.activation.MimetypesFileTypeMap;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.vlingo.http.RequestHeader.ContentType;
import static io.vlingo.http.Response.Status.*;
import static io.vlingo.http.ResponseHeader.ContentLength;
import static io.vlingo.http.resource.ResourceBuilder.get;
import static io.vlingo.http.resource.ResourceBuilder.resource;

public class StaticFileResources extends ResourceHandler {

    public StaticFileResources() {
    }

    @Override
    public Resource<?> routes() {
        final RequestHandler0.Handler0 serve0 = this::serve;
        final RequestHandler1.Handler1<String> serve1 = this::serve;
        final RequestHandler2.Handler2<String, String> serve2 = this::serve;
        final RequestHandler3.Handler3<String, String, String> serve3 = this::serve;
        final RequestHandler4.Handler4<String, String, String, String> serve4 = this::serve;

        return resource("Static File Resource", 10,
                get("")
                        .handle(() -> redirectToApp("/")),
                get("/")
                        .handle(serve0),
                get("/static")
                        .handle(serve0),
                get("/static/")
                        .handle(() -> redirectToApp("/static")),
                get("/static/{file}")
                        .param(String.class)
                        .handle(serve1),
                get("/static/{path1}/{file}")
                        .param(String.class)
                        .param(String.class)
                        .handle(serve2),
                get("/static/{path1}/{path2}/{file}")
                        .param(String.class)
                        .param(String.class)
                        .param(String.class)
                        .handle(serve3),
                get("/static/{path1}/{path2}/{path3}/{file}")
                        .param(String.class)
                        .param(String.class)
                        .param(String.class)
                        .param(String.class)
                        .handle(serve4)
        );
    }

    private Completes<Response> redirectToApp(String path) {
        return Completes.withSuccess(
                Response.of(MovedPermanently, Header.Headers.of(
                        ResponseHeader.of("Location", path),
                        ResponseHeader.of(ContentLength, 0)), ""));
    }

    private Completes<Response> serve(final String... pathSegments) {
        if (pathSegments.length == 0)
            return serve("index.html");

        String path = pathFrom(pathSegments);
        try {
            byte[] content = readFileFromClasspath(path);
            return Completes.withSuccess(
                    Response.of(Ok,
                            Header.Headers.of(
                                    ResponseHeader.of(ContentType, guessContentType(path)),
                                    ResponseHeader.of(ContentLength, content.length)
                            ),
                            Body.from(content, Body.Encoding.UTF8).content()
                    ));
        } catch (FileNotFoundException e) {
            return Completes.withSuccess(Response.of(NotFound, path + " not found."));
        } catch (IOException e) {
            return Completes.withSuccess(Response.of(InternalServerError));
        }
    }

    private String guessContentType(final String path) throws IOException {
        // This implementation uses javax.activation.MimetypesFileTypeMap; the mime types are defined
        // in META-INF/mime.types as JDK8's java.nio.file.Files#probeContentType is highly platform dependent
        // and reportedly not reliable, see e.g. https://bugs.openjdk.java.net/browse/JDK-8186071
        MimetypesFileTypeMap m = new MimetypesFileTypeMap();
        String contentType = m.getContentType(Paths.get(path).toFile());
        return (contentType != null) ? contentType : "application/octet-stream";
    }

    private String pathFrom(final String[] pathSegments) {
        return Stream.of(pathSegments)
                .map(p -> p.startsWith("/") ? p.substring(1) : p)
                .map(p -> p.endsWith("/") ? p.substring(0, p.length() - 1) : p)
                .collect(Collectors.joining("/", "static/", ""));
    }

    private byte[] readFileFromClasspath(final String path) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(path);

        if (is == null)
            throw new FileNotFoundException();

        return read(is);
    }

    private static byte[] read(final InputStream is) throws IOException {
        byte[] readBytes;
        byte[] buffer = new byte[4096];

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            int read;
            while ((read = is.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
            }
            readBytes = baos.toByteArray();
        } finally {
            is.close();
        }
        return readBytes;
    }
}