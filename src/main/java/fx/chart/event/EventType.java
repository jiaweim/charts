package fx.chart.event;

import org.jspecify.annotations.NonNull;

import java.util.Objects;

import static java.util.Objects.requireNonNull;


/**
 * Event type
 *
 * @author Gerrit Grunwald
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 25 Jun 2025, 10:46 AM
 */
public final class EventType<T extends FxEvent> {
    /**
     * The root event type
     */
    public static final EventType<FxEvent> ROOT = new EventType<>("EVENT", null);

    private final EventType<? super T> superType;
    private final String name;

    /**
     * Create a {@link EventType} of given super type and null name
     *
     * @param superType super type
     */
    public EventType(final EventType<? super T> superType) {
        this(superType, null);
    }

    public EventType(final String name) {
        this(ROOT, name);
    }

    /**
     * Create a {@link EventType}
     *
     * @param superType super type
     * @param name      event name
     */
    public EventType(@NonNull final EventType<? super T> superType, final String name) {
        requireNonNull(superType, "Event super type must not be null (EvtType.name: " + name + ")");

        this.superType = superType;
        this.name = name;
    }

    /**
     * inner constructor to allow null super type
     *
     * @param name      event type name
     * @param superType super type
     */
    private EventType(final String name, final EventType<? super T> superType) {
        this.superType = superType;
        this.name = name;
    }

    /**
     * @return super type of this {@link EventType}
     */
    public EventType<? super T> getSuperType() {return superType;}

    /**
     * @return name of this {@link EventType}
     */
    public String getName() {return name;}

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventType<?> evtType = (EventType<?>) o;
        return superType.equals(evtType.superType) && name.equals(evtType.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(superType, name);
    }

    @Override
    public String toString() {
        return (name != null) ? "{\"class\":\"" + getClass().getName()
                + "\",\"name\":\"" + getName()
                + "\",\"supertype\":\"" + getSuperType().name + "\"}" :
                super.toString();
    }
}
