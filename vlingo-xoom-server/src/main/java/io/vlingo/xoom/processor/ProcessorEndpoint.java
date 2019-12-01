package io.vlingo.xoom.processor;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.management.endpoint.annotation.Endpoint;
import io.micronaut.management.endpoint.annotation.Read;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Stream;

@Endpoint(id = "processors", prefix = "custom", defaultEnabled = true, defaultSensitive = false)
@Requires(classes = State.class)
public class ProcessorEndpoint implements ApplicationEventListener<ProcessorStartupEvent> {
    private static Logger log = LoggerFactory.getLogger(ProcessorEndpoint.class);
    private Processor processor;

    @Read
    public Map<String, Object> getMap() {
        Kernel kernel = processor.getKernel().await();

        Set<String> nodes = new HashSet<>();

        List<State> states = kernel.getStates().await();
        states.forEach(state -> {
            nodes.add(state.getName().split("::")[0]);
        });

        Map<String, Integer> portCount = new HashMap<>();
        Set<Map<String, Object>> links = new HashSet<>();

        states.stream().map(state -> Stream.of(state.getTransitionHandlers())).flatMap(handler -> handler)
                .forEach(handler -> {
                    Map<String, Object> link = new HashMap<>();
                    link.put("source", handler.getStateTransition().getSourceName());
                    link.put("target", handler.getStateTransition().getTargetName());
                    String[] address = handler.getAddress().split("::");
                    link.put("label", address.length == 3 ? address[2] : "TO");
                    Integer port = address.length != 3 ?
                            portCount.compute(handler.getStateTransition().getSourceName(),
                                    (s, integer) -> Optional.ofNullable(integer)
                                            .map(i -> i + 1)
                                            .orElse(0)) : 1;

                    Integer targetPort = address.length != 3 ?
                            portCount.compute("TO" + "::" + handler.getStateTransition().getTargetName(),
                                    (s, integer) -> Optional.ofNullable(integer)
                                            .map(i -> i + 1)
                                            .orElse(1)) : 0;

                    link.put("port", port + targetPort);
                    links.add(link);
                });

        Map<String, Object> results = new HashMap<>();
        results.put("nodes", nodes);
        results.put("edges", links);
        results.put("processor", processor.getName().await());

        return results;
    }

    @Override
    public void onApplicationEvent(ProcessorStartupEvent event) {
        log.info("Registered processor [" + event.getSource().getName().await() + "] with management endpoint");
        this.processor = event.getSource();
    }
}
