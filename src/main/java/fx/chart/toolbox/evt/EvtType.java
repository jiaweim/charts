package fx.chart.toolbox.evt;


import java.util.Objects;

import static fx.chart.toolbox.Constants.*;


public final class EvtType<T extends fx.chart.toolbox.evt.Evt> {
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


    // ******************** Methods *******************************************
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
        return (null != name) ? new StringBuilder().append(CURLY_BRACKET_OPEN)
                .append(QUOTES).append("class").append(QUOTES).append(COLON).append(QUOTES).append(getClass().getName()).append(QUOTES).append(COMMA)
                .append(QUOTES).append("name").append(QUOTES).append(COLON).append(QUOTES).append(getName()).append(QUOTES).append(COMMA)
                .append(QUOTES).append("supertype").append(QUOTES).append(COLON).append(QUOTES).append(getSuperType().name).append(QUOTES)
                .append(CURLY_BRACKET_CLOSE)
                .toString() :
                super.toString();
    }
}
