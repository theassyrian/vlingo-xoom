package io.vlingo;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.context.env.Environment;
import io.micronaut.core.annotation.Internal;
import io.micronaut.core.convert.value.ConvertibleValues;
import io.micronaut.core.util.CollectionUtils;
import io.micronaut.discovery.cloud.ComputeInstanceMetadata;
import io.micronaut.discovery.cloud.ComputeInstanceMetadataResolver;
import io.micronaut.discovery.metadata.ServiceInstanceMetadataContributor;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.runtime.server.EmbeddedServerInstance;

import javax.annotation.Nullable;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implements the {@link EmbeddedServerInstance} interface for Vlingo.
 *
 * @author graemerocher
 * @since 1.0
 */
@Prototype
@Internal
class VlingoEmbeddedServerInstance implements EmbeddedServerInstance {

    private final String id;
    private final VlingoServer vlingoServer;
    private final Environment environment;
    private final ComputeInstanceMetadataResolver computeInstanceMetadataResolver;
    private final List<ServiceInstanceMetadataContributor> metadataContributors;

    private ConvertibleValues<String> instanceMetadata;

    /**
     * @param id                              The id
     * @param vlingoServer                 The {@link VlingoServer}
     * @param environment                     The Environment
     * @param computeInstanceMetadataResolver The {@link ComputeInstanceMetadataResolver}
     * @param metadataContributors            The {@link ServiceInstanceMetadataContributor}
     */
    VlingoEmbeddedServerInstance(
        @Parameter String id,
        @Parameter VlingoServer vlingoServer,
        Environment environment,
        @Nullable ComputeInstanceMetadataResolver computeInstanceMetadataResolver,
        List<ServiceInstanceMetadataContributor> metadataContributors) {

        this.id = id;
        this.vlingoServer = vlingoServer;
        this.environment = environment;
        this.computeInstanceMetadataResolver = computeInstanceMetadataResolver;
        this.metadataContributors = metadataContributors;
    }

    @Override
    public EmbeddedServer getEmbeddedServer() {
        return vlingoServer;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public URI getURI() {
        return vlingoServer.getURI();
    }

    @Override
    public ConvertibleValues<String> getMetadata() {
        if (instanceMetadata == null) {
            Map<String, String> cloudMetadata = new HashMap<>();
            if (computeInstanceMetadataResolver != null) {
                Optional<? extends ComputeInstanceMetadata> resolved = computeInstanceMetadataResolver.resolve(environment);
                if (resolved.isPresent()) {
                    cloudMetadata = resolved.get().getMetadata();
                }
            }
            if (CollectionUtils.isNotEmpty(metadataContributors)) {
                for (ServiceInstanceMetadataContributor metadataContributor : metadataContributors) {
                    metadataContributor.contribute(this, cloudMetadata);
                }
            }
            Map<String, String> metadata = vlingoServer.getVlingoScene()
                .getApplicationConfiguration()
                .getInstance()
                .getMetadata();
            if (cloudMetadata != null) {
                cloudMetadata.putAll(metadata);
            }
            instanceMetadata = ConvertibleValues.of(cloudMetadata);
        }
        return instanceMetadata;
    }
}
