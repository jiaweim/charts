package fx.chart.event.type;

import fx.chart.event.EventPriority;
import fx.chart.event.EventType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ListChangeEvent<T> extends ChangeEvent {

    public static final EventType<ListChangeEvent> ANY = new EventType<>(ChangeEvent.ANY, "ANY");
    public static final EventType<ListChangeEvent> CHANGED = new EventType<>(ListChangeEvent.ANY, "CHANGED");
    public static final EventType<ListChangeEvent> ADDED = new EventType<>(ListChangeEvent.ANY, "ADDED");
    public static final EventType<ListChangeEvent> REMOVED = new EventType<>(ListChangeEvent.ANY, "REMOVED");

    private final List<T> addedElements;
    private final List<T> removedElements;

    public ListChangeEvent(final List<T> src, final EventType<ListChangeEvent> evtType, final List<T> addedElements, final List<T> removedElements) {
        super(src, evtType);
        this.addedElements = null == addedElements ? List.of() : new ArrayList<>(addedElements);
        this.removedElements = null == removedElements ? List.of() : new ArrayList<>(removedElements);
    }

    public ListChangeEvent(final List<T> src, final EventType<? extends ListChangeEvent<T>> evtType, final EventPriority priority, final List<T> addedElements, final List<T> removedElements) {
        super(src, evtType, priority);
        this.addedElements = null == addedElements ? List.of() : new ArrayList<>(addedElements);
        this.removedElements = null == removedElements ? List.of() : new ArrayList<>(removedElements);
    }

    @Override
    public EventType<? extends ListChangeEvent<T>> getEventType() {return (EventType<? extends ListChangeEvent<T>>) super.getEventType();}

    public List<T> getAddedElements() {return addedElements;}

    public List<T> getRemovedElements() {return removedElements;}

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
        ListChangeEvent<?> that = (ListChangeEvent<?>) o;
        return Objects.equals(addedElements, that.addedElements) && Objects.equals(removedElements, that.removedElements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), addedElements, removedElements);
    }
}
