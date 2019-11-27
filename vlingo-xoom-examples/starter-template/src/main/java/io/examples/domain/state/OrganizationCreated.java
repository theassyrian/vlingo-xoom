package io.examples.domain.state;

import io.examples.domain.OrganizationStatus;
import io.vlingo.xoom.annotations.Resource;
import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.Transition;
import io.vlingo.xoom.processor.TransitionHandler;

import static io.vlingo.xoom.processor.TransitionBuilder.from;

@Resource
public class OrganizationCreated extends State<OrganizationCreated> {

    @Override
    public String getName() {
        return OrganizationStatus.CREATED.name();
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
                TransitionHandler.handle(from(this).to(new OrganizationPending())
                        .then(Transition::logResult))
        };
    }
}
