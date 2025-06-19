package fx.chart.toolbox.statemachine;

import fx.chart.toolbox.properties.ObjectProperty;


public interface StateMachine<T extends fx.chart.toolbox.statemachine.State> {

    // ******************** Public Methods ************************************
    State getState();

    void setState(final T state) throws StateChangeException;

    ObjectProperty<T> stateProperty();
}
