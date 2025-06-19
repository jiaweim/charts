package fx.charts.series;

import fx.charts.ChartType;
import fx.charts.Symbol;
import fx.charts.data.XYZItem;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.List;


public class XYZSeries<T extends XYZItem> extends Series {

    public XYZSeries() {
        this(null, ChartType.BUBBLE, "");
    }

    public XYZSeries(final List<T> ITEMS, final ChartType TYPE) {
        this(ITEMS, TYPE, "");
    }

    public XYZSeries(final List<T> ITEMS, final ChartType TYPE, final String NAME) {
        super(ITEMS, TYPE, NAME);
    }

    public XYZSeries(final List<T> ITEMS, final ChartType TYPE, final String NAME, final Symbol SYMBOL) {
        super(ITEMS, TYPE, NAME, SYMBOL);
    }

    @Override
    public ObservableList<T> getItems() {return items;}

    public double getMinX() {return getItems().stream().min(Comparator.comparingDouble(T::getX)).get().getX();}

    public double getMaxX() {return getItems().stream().max(Comparator.comparingDouble(T::getX)).get().getX();}

    public double getMinY() {return getItems().stream().min(Comparator.comparingDouble(T::getY)).get().getY();}

    public double getMaxY() {return getItems().stream().max(Comparator.comparingDouble(T::getY)).get().getY();}

    public double getMinZ() {return getItems().stream().min(Comparator.comparingDouble(T::getZ)).get().getZ();}

    public double getMaxZ() {return getItems().stream().max(Comparator.comparingDouble(T::getZ)).get().getZ();}

    public double getRangeX() {return getMaxX() - getMinX();}

    public double getRangeY() {return getMaxY() - getMinY();}

    public double getRangeZ() {return getMaxZ() - getMinZ();}

    public double getSumOfXValues() {return getItems().stream().mapToDouble(T::getX).sum();}

    public double getSumOfYValues() {return getItems().stream().mapToDouble(T::getY).sum();}

    public double getSumOfZValues() {return getItems().stream().mapToDouble(T::getZ).sum();}
}

