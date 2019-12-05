package io.examples.order.domain.state;

import io.examples.order.domain.OrganizationStatus;
import io.vlingo.xoom.annotations.Resource;
import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.Transition;
import io.vlingo.xoom.processor.TransitionHandler;

import static io.vlingo.xoom.processor.TransitionBuilder.from;
import static io.vlingo.xoom.processor.TransitionHandler.handle;

@Resource
public class Defined extends State<Defined> {

    @Override
    public String getName() {
        return OrganizationStatus.DEFINED.name();
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                handle(from(this).to(new Enabled()).then(Transition::logResult)),
                handle(from(this).to(new Defined()).then(Transition::logResult))
        };
    }
}
