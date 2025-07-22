package fx.chart.event;

import java.util.EventObject;
import java.util.Objects;

/**
 * The root class from which all event state objects shall be derived.
 * <p>
 * All Events are constructed with a reference to the object, the "source",
 * that is logically deemed to be the object upon which the Event in question initially occurred upon.
 *
 * @author Gerrit Grunwald
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 25 Jun 2025, 10:51 AM
 */
public class FxEvent extends EventObject implements Comparable<FxEvent> {

    /**
     * The root event
     */
    public static final EventType<FxEvent> ANY = EventType.ROOT;

    protected final EventType<? extends FxEvent> eventType_;
    private final EventPriority priority;

    /**
     * Create a {@link FxEvent} with {@link EventPriority#NORMAL}
     *
     * @param source  the object on which the Event initially occurred
     * @param evtType {@link EventType} of the event
     */
    public FxEvent(final Object source, final EventType<? extends FxEvent> evtType) {
        this(source, evtType, EventPriority.NORMAL);
    }

    /**
     * Create a {@link FxEvent}
     *
     * @param source   the object on which the Event initially occurred
     * @param evtType  {@link EventType} of the event
     * @param priority {@link EventPriority} of the event
     */
    public FxEvent(final Object source, final EventType<? extends FxEvent> evtType, final EventPriority priority) {
        super(source);
        this.eventType_ = evtType;
        this.priority = priority;
    }

    /**
     * @return {@link EventType} of the event
     */
    public EventType<? extends FxEvent> getEventType() {return eventType_;}

    /**
     * @return priority of the event
     */
    public EventPriority getPriority() {return priority;}

    @Override
    public int compareTo(final FxEvent evt) {
        return (evt.getPriority().getValue() - this.priority.getValue());
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FxEvent fxEvent)) return false;
        return Objects.equals(source, fxEvent.source) &&
                Objects.equals(eventType_, fxEvent.eventType_)
                && priority == fxEvent.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, eventType_, priority);
    }

    @Override
    public String toString() {
        return "{\"class\":\"" + getClass().getName() + "\"," +
                "\"type\":\"" + getEventType().getClass().getName()
                + "\",\"priority\":" + getPriority().getValue() + ",\"source\":\"" +
                (getSource() == null ? "null" : getSource().getClass().getName()) + "\"}";
    }
}
