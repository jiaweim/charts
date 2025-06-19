package fx.chart.toolbox.evt.type;

import fx.chart.toolbox.evt.EvtPriority;
import fx.chart.toolbox.evt.EvtType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ListChangeEvt<T> extends fx.chart.toolbox.evt.type.ChangeEvt {
    public static final EvtType<ListChangeEvt> ANY = new EvtType<>(ChangeEvt.ANY, "ANY");
    public static final EvtType<ListChangeEvt> CHANGED = new EvtType<>(ListChangeEvt.ANY, "CHANGED");
    public static final EvtType<ListChangeEvt> ADDED = new EvtType<>(ListChangeEvt.ANY, "ADDED");
    public static final EvtType<ListChangeEvt> REMOVED = new EvtType<>(ListChangeEvt.ANY, "REMOVED");

    private final List<T> addedElements;
    private final List<T> removedElements;


    // ******************** Constructors **************************************
    public ListChangeEvt(final List<T> src, final EvtType<ListChangeEvt> evtType, final List<T> addedElements, final List<T> removedElements) {
        super(src, evtType);
        this.addedElements = null == addedElements ? List.of() : new ArrayList<>(addedElements);
        this.removedElements = null == removedElements ? List.of() : new ArrayList<>(removedElements);
    }

    public ListChangeEvt(final List<T> src, final EvtType<? extends ListChangeEvt<T>> evtType, final EvtPriority priority, final List<T> addedElements, final List<T> removedElements) {
        super(src, evtType, priority);
        this.addedElements = null == addedElements ? List.of() : new ArrayList<>(addedElements);
        this.removedElements = null == removedElements ? List.of() : new ArrayList<>(removedElements);
    }


    // ******************** Methods *******************************************
    @Override
    public EvtType<? extends ListChangeEvt<T>> getEvtType() {return (EvtType<? extends ListChangeEvt<T>>) super.getEvtType();}

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
        ListChangeEvt<?> that = (ListChangeEvt<?>) o;
        return Objects.equals(addedElements, that.addedElements) && Objects.equals(removedElements, that.removedElements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), addedElements, removedElements);
    }
}
