package fx.chart.event;

import fx.chart.data.MapConnection;

public class MapConnectionEvent<T extends MapConnection> {

    private final EventType<? extends FxEvent> TYPE;
    private final T MAP_CONNECTION;

    public MapConnectionEvent(final EventType<? extends FxEvent> TYPE) {
        this(null, TYPE);
    }

    public MapConnectionEvent(final T MAP_CONNECTION) {
        this(MAP_CONNECTION, ChartEvent.UPDATE);
    }

    public MapConnectionEvent(final T MAP_CONNECTION, final EventType<? extends FxEvent> TYPE) {
        this.MAP_CONNECTION = MAP_CONNECTION;
        this.TYPE = TYPE;
    }


    // ******************** Methods *******************************************
    public T getMapConnection() {return MAP_CONNECTION;}

    public EventType<? extends FxEvent> getEventType() {return TYPE;}
}

