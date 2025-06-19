package fx.chart.toolbox.evt.type;

import fx.chart.toolbox.evt.EvtPriority;
import fx.chart.toolbox.evt.EvtType;
import fx.chart.toolbox.properties.ReadOnlyProperty;

import java.util.Objects;


public class PropertyChangeEvt<T> extends fx.chart.toolbox.evt.type.ChangeEvt {

    public static final EvtType<PropertyChangeEvt> ANY = new EvtType<>(ChangeEvt.ANY, "ANY");
    public static final EvtType<PropertyChangeEvt> CHANGED = new EvtType<>(PropertyChangeEvt.ANY, "CHANGED");

    private final T oldValue;
    private final T value;


    public PropertyChangeEvt(final EvtType<? extends PropertyChangeEvt<T>> evtType, final T oldValue, final T value) {
        super(evtType);
        this.value = value;
        this.oldValue = oldValue;
    }

    public PropertyChangeEvt(final ReadOnlyProperty src, final EvtType<? extends PropertyChangeEvt<T>> evtType, final T oldValue, final T value) {
        super(src, evtType);
        this.value = value;
        this.oldValue = oldValue;
    }

    public PropertyChangeEvt(final ReadOnlyProperty src, final EvtType<? extends PropertyChangeEvt<T>> evtType, final EvtPriority priority, final T oldValue, final T value) {
        super(src, evtType, priority);
        this.value = value;
        this.oldValue = oldValue;
    }


    @Override
    public EvtType<? extends PropertyChangeEvt<T>> getEvtType() {return (EvtType<? extends PropertyChangeEvt<T>>) super.getEvtType();}

    public T getOldValue() {return oldValue;}

    public T getValue() {return value;}

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PropertyChangeEvt<?> that = (PropertyChangeEvt<?>) o;
        return Objects.equals(oldValue, that.oldValue) && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oldValue, value);
    }
}
