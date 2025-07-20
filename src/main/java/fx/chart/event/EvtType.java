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
public final class EvtType<T extends Evt> {

    public static final EvtType<Evt> ROOT = new EvtType<>("EVENT", null);

    private final EvtType<? super T> superType;
    private final String name;

    /**
     * Create a {@link EvtType} of given super type and null name
     *
     * @param superType super type
     */
    public EvtType(final EvtType<? super T> superType) {
        this(superType, null);
    }

    public EvtType(final String name) {
        this(ROOT, name);
    }

    /**
     * Create a {@link EvtType}
     *
     * @param superType super type
     * @param name      event name
     */
    public EvtType(@NonNull final EvtType<? super T> superType, final String name) {
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
    private EvtType(final String name, final EvtType<? super T> superType) {
        this.superType = superType;
        this.name = name;
    }

    /**
     * @return super type of this {@link EvtType}
     */
    public EvtType<? super T> getSuperType() {return superType;}

    /**
     * @return name of this {@link EvtType}
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
        EvtType<?> evtType = (EvtType<?>) o;
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
