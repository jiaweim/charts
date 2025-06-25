package fx.chart.event;

import java.util.Objects;

import static fx.chart.util.Constants.*;


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


    public EvtType(final EvtType<? super T> superType) {
        this(superType, null);
    }

    public EvtType(final String name) {
        this(ROOT, name);
    }

    public EvtType(final EvtType<? super T> superType, final String name) {
        if (null == superType) {
            throw new NullPointerException("Event super type must not be null (EvtType.name: " + name + ")");
        }
        this.superType = superType;
        this.name = name;
    }

    EvtType(final String name, final EvtType<? super T> superType) {
        this.superType = superType;
        this.name = name;
    }


    public EvtType<? super T> getSuperType() {return superType;}

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
        return (null != name) ? CURLY_BRACKET_OPEN +
                QUOTES + "class" + QUOTES + COLON + QUOTES + getClass().getName() + QUOTES + COMMA +
                QUOTES + "name" + QUOTES + COLON + QUOTES + getName() + QUOTES + COMMA +
                QUOTES + "supertype" + QUOTES + COLON + QUOTES + getSuperType().name + QUOTES +
                CURLY_BRACKET_CLOSE :
                super.toString();
    }
}
