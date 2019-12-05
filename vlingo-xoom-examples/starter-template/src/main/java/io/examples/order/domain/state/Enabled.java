package io.examples.order.domain.state;

import io.examples.order.domain.OrganizationStatus;
import io.vlingo.xoom.annotations.Resource;
import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.Transition;
import io.vlingo.xoom.processor.TransitionHandler;

import static io.vlingo.xoom.processor.TransitionBuilder.from;
import static io.vlingo.xoom.processor.TransitionHandler.handle;

@Resource
public class Enabled extends State<Enabled> {

    @Override
    public String getName() {
        return OrganizationStatus.ENABLED.name();
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handle(from(this).to(new Disabled()).then(Transition::logResult))
        };
    }
}
