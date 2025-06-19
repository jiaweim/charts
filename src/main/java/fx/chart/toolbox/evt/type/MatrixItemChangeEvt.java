package fx.chart.toolbox.evt.type;

import fx.chart.toolbox.evt.EvtPriority;
import fx.chart.toolbox.evt.EvtType;
import fx.chart.toolbox.observables.ObservableMatrix;

import java.util.Objects;


public class MatrixItemChangeEvt<T> extends fx.chart.toolbox.evt.type.ChangeEvt {
    public static final EvtType<MatrixItemChangeEvt> ANY = new EvtType<>(ChangeEvt.ANY, "ANY");
    public static final EvtType<MatrixItemChangeEvt> ITEM_ADDED = new EvtType<>(MatrixItemChangeEvt.ANY, "ITEM_ADDED");
    public static final EvtType<MatrixItemChangeEvt> ITEM_CHANGED = new EvtType<>(MatrixItemChangeEvt.ANY, "ITEM_CHANGED");
    public static final EvtType<MatrixItemChangeEvt> ITEM_REMOVED = new EvtType<>(MatrixItemChangeEvt.ANY, "ITEM_REMOVED");

    private final T oldItem;
    private final T item;
    private final int x;
    private final int y;


    // ******************** Constructors **************************************
    public MatrixItemChangeEvt(final ObservableMatrix<T> src, final EvtType<MatrixItemChangeEvt> evtType, final int x, final int y, final T oldItem, final T item) {
        super(src, evtType);
        this.x = x;
        this.y = y;
        this.oldItem = oldItem;
        this.item = item;
    }

    public MatrixItemChangeEvt(final ObservableMatrix<T> src, final EvtType<? extends MatrixItemChangeEvt<T>> evtType, final EvtPriority priority, final int x, final int y, final T oldItem, final T item) {
        super(src, evtType, priority);
        this.x = x;
        this.y = y;
        this.oldItem = oldItem;
        this.item = item;
    }


    // ******************** Methods *******************************************
    @Override
    public EvtType<? extends MatrixItemChangeEvt<T>> getEvtType() {return (EvtType<? extends MatrixItemChangeEvt<T>>) super.getEvtType();}

    public int getX() {return x;}

    public int getY() {return y;}

    public T getOldItem() {return oldItem;}

    public T getItem() {return item;}

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
        MatrixItemChangeEvt<?> that = (MatrixItemChangeEvt<?>) o;
        return Objects.equals(oldItem, that.oldItem) && Objects.equals(item, that.item) && x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oldItem, item, x, y);
    }
}
