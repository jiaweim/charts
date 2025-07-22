package fx.chart.event.type;

import fx.chart.event.EventPriority;
import fx.chart.event.EventType;
import fx.chart.util.Bounds;

import java.util.Objects;


public class BoundsEvent extends ChangeEvent {

    public static final EventType<BoundsEvent> ANY = new EventType<>(ChangeEvent.ANY, "ANY");
    public static final EventType<BoundsEvent> BOUNDS = new EventType<>(BoundsEvent.ANY, "BOUNDS");

    private final Bounds bounds;

    public BoundsEvent(final Object src, final EventType<? extends BoundsEvent> evtType, final Bounds bounds) {
        super(src, evtType);
        this.bounds = bounds;
    }

    public BoundsEvent(final Object src, final EventType<? extends BoundsEvent> evtType, final EventPriority priority, final Bounds bounds) {
        super(src, evtType, priority);
        this.bounds = bounds;
    }

    @Override
    public EventType<? extends BoundsEvent> getEventType() {
        return (EventType<? extends BoundsEvent>) super.getEventType();
    }

    public Bounds getBounds() {return bounds;}

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
        BoundsEvent boundsEvt = (BoundsEvent) o;
        return Objects.equals(bounds, boundsEvt.bounds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bounds);
    }
}
