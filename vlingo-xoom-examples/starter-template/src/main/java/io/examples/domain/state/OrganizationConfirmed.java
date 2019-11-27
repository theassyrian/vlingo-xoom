package io.examples.domain.state;

import io.examples.domain.OrganizationStatus;
import io.vlingo.xoom.annotations.Resource;
import io.vlingo.xoom.processor.State;
import io.vlingo.xoom.processor.TransitionHandler;

@Resource
public class OrganizationConfirmed extends State<OrganizationConfirmed> {

    @Override
    public String getName() {
        return OrganizationStatus.CONFIRMED.name();
    }

    @Override
    public TransitionHandler[] getTransitionHandlers() {
        return new TransitionHandler[]{
        };
    }
}
