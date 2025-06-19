package fx.charts.series;

import fx.charts.ChartType;
import fx.charts.Symbol;
import fx.charts.data.MatrixItem;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;


public class MatrixItemSeries<T extends MatrixItem> extends Series {

    public MatrixItemSeries() {
        this(null, ChartType.MATRIX_HEATMAP, "");
    }

    public MatrixItemSeries(final List<T> ITEMS, final ChartType TYPE) {
        this(ITEMS, TYPE, "");
    }

    public MatrixItemSeries(final List<T> ITEMS, final ChartType TYPE, final String NAME) {
        super(ITEMS, TYPE, NAME, Symbol.NONE);
    }

    @Override
    public ObservableList<T> getItems() {return (ObservableList<T>) items;}

    public int getMinX() {return getItems().stream().min(Comparator.comparingInt(T::getX)).get().getX();}

    public int getMaxX() {return getItems().stream().max(Comparator.comparingInt(T::getX)).get().getX();}

    public int getMinY() {return getItems().stream().min(Comparator.comparingInt(T::getY)).get().getY();}

    public int getMaxY() {return getItems().stream().max(Comparator.comparingInt(T::getY)).get().getY();}

    public double getMinZ() {return getItems().stream().min(Comparator.comparingDouble(T::getZ)).get().getZ();}

    public double getMaxZ() {return getItems().stream().max(Comparator.comparingDouble(T::getZ)).get().getZ();}

    public int getRangeX() {return getMaxX() - getMinX();}

    public int getRangeY() {return getMaxY() - getMinY();}

    public double getRangeZ() {return getMaxZ() - getMinZ();}

    public double getAt(final int X, final int Y) {
        Optional<T> selectedItem = getItems().stream().filter(item -> item.getX() == X).filter(item -> item.getY() == Y).findFirst();
        return selectedItem.isPresent() ? selectedItem.get().getZ() : 0;
    }

    public void setAt(final int X, final int Y, final double Z) {
        Optional<T> selectedItem = getItems().stream().filter(item -> item.getX() == X).filter(item -> item.getY() == Y).findFirst();
        if (selectedItem.isPresent()) {
            selectedItem.get().setZ(Z);
        }
    }
}
