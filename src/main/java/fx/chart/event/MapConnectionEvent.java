package fx.chart.event;

import fx.chart.data.MapConnection;

public class MapConnectionEvent<T extends MapConnection> {

    private final EvtType<? extends Evt> TYPE;
    private final T MAP_CONNECTION;

    public MapConnectionEvent(final EvtType<? extends Evt> TYPE) {
        this(null, TYPE);
    }

    public MapConnectionEvent(final T MAP_CONNECTION) {
        this(MAP_CONNECTION, ChartEvt.UPDATE);
    }

    public MapConnectionEvent(final T MAP_CONNECTION, final EvtType<? extends Evt> TYPE) {
        this.MAP_CONNECTION = MAP_CONNECTION;
        this.TYPE = TYPE;
    }


    // ******************** Methods *******************************************
    public T getMapConnection() {return MAP_CONNECTION;}

    public EvtType<? extends Evt> getEventType() {return TYPE;}
}

