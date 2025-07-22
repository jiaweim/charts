package fx.chart.event.type;

import fx.chart.event.EventPriority;
import fx.chart.event.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;


public class MapChangeEvent<K, V> extends ChangeEvent {

    public static final EventType<MapChangeEvent> ANY = new EventType<>(ChangeEvent.ANY, "ANY");
    public static final EventType<MapChangeEvent> MODIFIED = new EventType<>(MapChangeEvent.ANY, "MODIFIED");
    public static final EventType<MapChangeEvent> ADDED = new EventType<>(MapChangeEvent.ANY, "ADDED");
    public static final EventType<MapChangeEvent> REMOVED = new EventType<>(MapChangeEvent.ANY, "REMOVED");

    private final List<Entry<K, V>> addedEntries;
    private final List<Entry<K, V>> modifiedEntries;
    private final List<Entry<K, V>> removedEntries;


    public MapChangeEvent(final Map<K, V> src, final EventType<MapChangeEvent> evtType, final List<Entry<K, V>> addedEntries, final List<Entry<K, V>> modifiedEntries, final List<Entry<K, V>> removedEntries) {
        super(src, evtType);
        this.addedEntries = null == addedEntries ? List.of() : new ArrayList<>(addedEntries);
        this.modifiedEntries = null == modifiedEntries ? List.of() : new ArrayList<>(modifiedEntries);
        this.removedEntries = null == removedEntries ? List.of() : new ArrayList<>(removedEntries);
    }

    public MapChangeEvent(final Map<K, V> src, final EventType<? extends MapChangeEvent<K, V>> evtType, final EventPriority priority, final List<Entry<K, V>> addedEntries, final List<Entry<K, V>> modifiedEntries, final List<Entry<K, V>> removedEntries) {
        super(src, evtType, priority);
        this.addedEntries = null == addedEntries ? List.of() : new ArrayList<>(addedEntries);
        this.modifiedEntries = null == modifiedEntries ? List.of() : new ArrayList<>(modifiedEntries);
        this.removedEntries = null == removedEntries ? List.of() : new ArrayList<>(removedEntries);
    }


    // ******************** Methods *******************************************
    @Override
    public EventType<? extends MapChangeEvent<K, V>> getEventType() {return (EventType<? extends MapChangeEvent<K, V>>) super.getEventType();}

    public List<Entry<K, V>> getAddedEntries() {return addedEntries;}

    public List<Entry<K, V>> getModifiedEntries() {return modifiedEntries;}

    public List<Entry<K, V>> getRemovedEntries() {return removedEntries;}

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
        MapChangeEvent<?, ?> that = (MapChangeEvent<?, ?>) o;
        return Objects.equals(addedEntries, that.addedEntries) && Objects.equals(modifiedEntries, that.modifiedEntries) && Objects.equals(removedEntries, that.removedEntries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), addedEntries, modifiedEntries, removedEntries);
    }
}
