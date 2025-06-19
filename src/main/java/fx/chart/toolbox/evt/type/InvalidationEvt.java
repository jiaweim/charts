package fx.chart.toolbox.evt.type;

import fx.chart.toolbox.evt.EvtPriority;
import fx.chart.toolbox.evt.EvtType;
import fx.chart.toolbox.properties.ReadOnlyProperty;

import java.util.Objects;


public class InvalidationEvt<T> extends fx.chart.toolbox.evt.type.ChangeEvt {
    public static final EvtType<InvalidationEvt> ANY = new EvtType<>(ChangeEvt.ANY, "ANY");
    public static final EvtType<InvalidationEvt> INVALIDATED = new EvtType<>(InvalidationEvt.ANY, "INVALIDATED");


    public InvalidationEvt(final EvtType<? extends InvalidationEvt<T>> evtType) {
        super(evtType);
    }

    public InvalidationEvt(final ReadOnlyProperty src, final EvtType<? extends InvalidationEvt<T>> evtType) {
        super(src, evtType);
    }

    public InvalidationEvt(final ReadOnlyProperty src, final EvtType<? extends InvalidationEvt<T>> evtType, final EvtPriority priority) {
        super(src, evtType, priority);
    }


    // ******************** Methods *******************************************
    @Override
    public EvtType<? extends InvalidationEvt<T>> getEvtType() {return (EvtType<? extends InvalidationEvt<T>>) super.getEvtType();}

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
        InvalidationEvt<?> that = (InvalidationEvt<?>) o;
        return Objects.equals(this, that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
