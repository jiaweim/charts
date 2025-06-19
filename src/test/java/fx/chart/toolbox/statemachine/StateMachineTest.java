package fx.chart.toolbox.statemachine;

import fx.chart.toolbox.properties.ObjectProperty;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;


public class StateMachineTest {

    @Test
    void testStateMachine() {
        System.out.println("\n-------------------- state machine test --------------------");
        enum MyState implements fx.chart.toolbox.statemachine.State {
            // Available states
            IDLE("IDLE"), BUSY("BUSY"), ERROR("ERROR"), FINISHED("FINISHED");


            // Definition of state transitions
            static {
                IDLE.canTransitionTo(IDLE, BUSY, ERROR);
                BUSY.canTransitionTo(IDLE, BUSY, ERROR);
                ERROR.canTransitionTo(IDLE, ERROR);
            }

            private final String name;
            private Set transitions;


            // ******************** Constructor ***************************************
            MyState(String name) {
                this.name = name;
            }


            // ******************** Private Methods ***********************************
            private void canTransitionTo(final MyState... transitions) {this.transitions = EnumSet.copyOf(Arrays.asList(transitions));}


            // ******************** Public Methods ************************************
            @Override
            public Set<fx.chart.toolbox.statemachine.State> getTransitions() {return this.transitions;}

            @Override
            public boolean canChangeTo(final fx.chart.toolbox.statemachine.State state) {return this.transitions.contains(state);}

            @Override
            public String getName() {return this.name;}
        }
        fx.chart.toolbox.statemachine.StateMachine<MyState> stateMachine = new StateMachine<>() {
            private ObjectProperty<MyState> state = new ObjectProperty<>(MyState.IDLE);


            // ******************** Public Methods ****************************
            @Override
            public State getState() {return this.state.get();}

            @Override
            public void setState(final MyState state) throws fx.chart.toolbox.statemachine.StateChangeException {
                if (this.state.get().canChangeTo(state)) {
                    this.state.set(state);
                } else {
                    throw new fx.chart.toolbox.statemachine.StateChangeException("Not allowed to change from " + this.state.get().getName() + " to " + state.getName());
                }
            }

            @Override
            public ObjectProperty<MyState> stateProperty() {return state;}
        };

        // Add listener to state property
        stateMachine.stateProperty().addObserver(e -> System.out.println("State changed from " + e.getOldValue().getName() + " to " + e.getValue().getName()));

        // Test different state changes
        try {
            stateMachine.setState(MyState.BUSY);
        } catch (fx.chart.toolbox.statemachine.StateChangeException e) {
            System.out.println(e.getMessage() + " -> StateMachine still in state: " + stateMachine.getState().getName());
        }
        assert stateMachine.getState() == MyState.BUSY;

        try {
            stateMachine.setState(MyState.IDLE);
        } catch (fx.chart.toolbox.statemachine.StateChangeException e) {
            System.out.println(e.getMessage() + " -> StateMachine still in state: " + stateMachine.getState().getName());
        }
        assert stateMachine.getState() == MyState.IDLE;

        try {
            stateMachine.setState(MyState.ERROR);
        } catch (fx.chart.toolbox.statemachine.StateChangeException e) {
            System.out.println(e.getMessage() + " -> StateMachine still in state: " + stateMachine.getState().getName());
        }
        assert stateMachine.getState() == MyState.ERROR;

        try {
            stateMachine.setState(MyState.BUSY);
            System.out.println(stateMachine.getState());
        } catch (StateChangeException e) {
            assert stateMachine.getState() == MyState.ERROR;
            System.out.println(e.getMessage() + " -> StateMachine still in state: " + stateMachine.getState().getName());
        }
    }
}
