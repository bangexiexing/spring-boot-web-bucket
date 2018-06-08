package bucket.component.statemachine;

import bucket.user.UserEvent;
import bucket.user.UserStates;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;

import java.util.EnumSet;

/**
 * @Author: qyl
 * @Description:
 * @Date: Created in 22:03 2018/6/6
 */
public class UserStateMachine {
    public static StateMachine<UserStates,UserEvent> buildMachine() throws Exception {
        StateMachineBuilder.Builder<UserStates,UserEvent> builder = StateMachineBuilder.builder();
        builder.configureStates().withStates().initial(UserStates.OFF_LINE).states(EnumSet.allOf(UserStates.class));
        builder.configureTransitions()
                .withExternal()
                .source(UserStates.OFF_LINE).target(UserStates.ON_LINE)
                .event(UserEvent.ON_LINE)
                .and()
                .withExternal()
                .source(UserStates.ON_LINE).target(UserStates.OFF_LINE)
                .event(UserEvent.OFF_LINE);
        return builder.build();
    }
}
