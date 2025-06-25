package fx.chart.event;

import java.util.EventObject;
import java.util.Objects;

import static fx.chart.util.Constants.*;

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
public class Evt extends EventObject implements Comparable<Evt> {

    public static final EvtType<Evt> ANY = EvtType.ROOT;
    protected final EvtType<? extends Evt> evtType;
    private final EvtPriority priority;

    public Evt(final EvtType<? extends Evt> evtType) {
        this(null, evtType, EvtPriority.NORMAL);
    }

    public Evt(final Object source, final EvtType<? extends Evt> evtType) {
        this(source, evtType, EvtPriority.NORMAL);
    }

    public Evt(final Object source, final EvtType<? extends Evt> evtType, final EvtPriority priority) {
        super(source);
        this.evtType = evtType;
        this.priority = priority;
    }

    @Override
    public Object getSource() {return source;}

    public EvtType<? extends Evt> getEvtType() {return evtType;}

    public EvtPriority getPriority() {return priority;}

    public int compareTo(final Evt evt) {
        return (evt.getPriority().getValue() - this.priority.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, evtType, priority);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        Evt evt = (Evt) obj;
        return (evt.getEvtType().equals(this.getEvtType()) &&
                evt.getPriority().getValue() == this.getPriority().getValue() &&
                evt.getSource().equals(this.getSource()));
    }

    @Override
    public String toString() {
        return CURLY_BRACKET_OPEN +
                QUOTES + "class" + QUOTES + COLON + QUOTES + getClass().getName() + QUOTES + COMMA +
                QUOTES + "type" + QUOTES + COLON + QUOTES + getEvtType().getClass().getName() + QUOTES + COMMA +
                QUOTES + "priority" + QUOTES + COLON + getPriority().getValue() + COMMA +
                QUOTES + "source" + QUOTES + COLON + QUOTES + (null == getSource() ? "null" : getSource().getClass().getName()) + QUOTES +
                CURLY_BRACKET_CLOSE;
    }
}
