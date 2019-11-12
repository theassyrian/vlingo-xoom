package io.vlingo;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.BootstrapContextCompatible;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Primary;
import io.micronaut.discovery.event.ServiceStartedEvent;
import io.micronaut.runtime.ApplicationConfiguration;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.runtime.server.event.ServerStartupEvent;
import io.vlingo.config.ServerConfiguration;
import io.vlingo.http.resource.Configuration;
import io.vlingo.http.resource.Resource;
import io.vlingo.http.resource.Resources;
import io.vlingo.http.resource.Server;
import io.vlingo.resource.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.PreDestroy;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.stream.Stream;

/**
 * The {@link VlingoServer} is a Micronaut bootstrapper for loading and auto-configuring a vlingo/http server.
 * Vlingo/http is a reactive client-server framework built on the actor model. Micronaut is a JVM microframework
 * for building light-weight compile-time native JVM applications. This class implements an
 * {@link EmbeddedServer} and provides lifecycle application context management and configuration classes at
 * startup.
 *
 * @author Kenny Bastani
 * @author Graeme Rocher
 */
@Primary
@BootstrapContextCompatible
@Context
public class VlingoServer implements EmbeddedServer {
    private static final Logger log = LoggerFactory.getLogger(VlingoServer.class);
    private Server server;
    private VlingoScene vlingoScene;
    private Resource[] resources;
    private ApplicationContext applicationContext;
    private ApplicationConfiguration applicationConfiguration;
    private boolean isRunning = false;
    private VlingoEmbeddedServerInstance serviceInstance;

    /**
     * Bootstrap the application context and configuration for starting the vlingo/http server.
     *
     * @param applicationContext       is the Micronaut application context.
     * @param applicationConfiguration is the application configuration for vlingo/http.
     * @param vlingoScene              is the vlingo/actors scene for the vlingo/http server.
     * @param endpoints                is a stream of HTTP request/response endpoints for the vlingo/http server.
     */
    public VlingoServer(ApplicationContext applicationContext, ApplicationConfiguration applicationConfiguration,
                        VlingoScene vlingoScene, Stream<Endpoint> endpoints) {
        // Load the world context with auto-configured settings
        this.applicationContext = applicationContext;
        this.applicationConfiguration = applicationConfiguration;
        this.vlingoScene = vlingoScene;
        this.resources = endpoints.map(Endpoint::getResource).toArray(Resource[]::new);
    }

    public Server getServer() {
        return server;
    }

    public VlingoScene getVlingoScene() {
        return vlingoScene;
    }

    public Resource[] getResources() {
        return resources;
    }

    @Override
    public int getPort() {
        return Math.toIntExact(vlingoScene.getServerConfiguration().getPort());
    }

    @Override
    public String getHost() {
        return "localhost";
    }

    @Override
    public String getScheme() {
        return vlingoScene.getServerConfiguration().getScheme();
    }

    @Override
    public URL getURL() {
        try {
            return getURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public URI getURI() {
        return URI.create(getScheme() + "://" + getHost() + ":" + getPort());
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public ApplicationConfiguration getApplicationConfiguration() {
        return applicationConfiguration;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public @Nonnull
    VlingoServer start() {
        if (!isRunning) {
            if (!vlingoScene.isRunning()) {
                vlingoScene.start();
            }
            // Start the server with auto-configured settings
            this.server = Server.startWith(vlingoScene.getWorld().stage(), Resources.are(resources),
                    vlingoScene.getServerConfiguration().getPort(), Configuration.Sizing.define(), Configuration.Timing.define());
            isRunning = true;
            applicationContext.publishEvent(new ServerStartupEvent(this));
            vlingoScene.getApplicationConfiguration().getName().ifPresent(id -> {
                this.serviceInstance = applicationContext
                        .createBean(VlingoEmbeddedServerInstance.class, id, this);
                applicationContext.publishEvent(new ServiceStartedEvent(serviceInstance));
            });
            log.info(ServerConfiguration.getBanner());
            log.info("Started embedded Vlingo Zoom server at " + getURI().toASCIIString());
        } else {
            throw new RuntimeException("A Vlingo Zoom server is already running in the current Micronaut context");
        }
        return this;
    }

    @Override
    public @Nonnull
    VlingoServer stop() {
        applicationContext.stop();
        return this;
    }

    @Override
    @PreDestroy
    public void close() {
        if (isRunning) {
            vlingoScene.close();
            server.stop();
            isRunning = false;
            log.info("Stopped embedded Vlingo Zoom server at " + getURI().toASCIIString());
        } else {
            throw new RuntimeException("A Vlingo Zoom server is not running in the current Micronaut context");
        }
    }

}
