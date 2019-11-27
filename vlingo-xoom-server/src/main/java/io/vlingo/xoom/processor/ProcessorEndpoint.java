package io.vlingo.xoom.processor;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.management.endpoint.annotation.Endpoint;
import io.micronaut.management.endpoint.annotation.Read;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@Endpoint(id = "processors", prefix = "custom", defaultEnabled = true, defaultSensitive = false)
@Requires(classes = State.class)
public class ProcessorEndpoint implements ApplicationEventListener<ProcessorStartupEvent> {
    private static Logger log = LoggerFactory.getLogger(ProcessorEndpoint.class);
    private Processor processor;

    @Read
    public Map<String, TransitionHandler> getMap() {
        Kernel kernel = processor.getKernel().await();
        return kernel.getTransitionMap().await();
    }

    @Override
    public void onApplicationEvent(ProcessorStartupEvent event) {
        log.info("Registered processor [" + event.getSource().getName().await() + "] with management endpoint");
        this.processor = event.getSource();
    }
}
