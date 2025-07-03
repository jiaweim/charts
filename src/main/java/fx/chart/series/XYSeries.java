package fx.chart.series;

import fx.chart.ChartType;
import fx.chart.Symbol;
import fx.chart.data.XYItem;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Comparator;
import java.util.List;


/**
 * Created by hansolo on 16.07.17.
 */
public class XYSeries<T extends XYItem> extends Series {

    public XYSeries() {
        this(null, ChartType.SCATTER, "", Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> ITEMS, final ChartType TYPE) {
        this(ITEMS, TYPE, "", Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> ITEMS, final ChartType TYPE, final boolean SHOW_POINTS) {
        this(ITEMS, TYPE, "", Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE, SHOW_POINTS);
    }

    public XYSeries(final List<T> ITEMS, final ChartType TYPE, final Paint STROKE) {
        this(ITEMS, TYPE, "", Color.TRANSPARENT, STROKE, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> ITEMS, final ChartType TYPE, final Paint FILL, final Paint STROKE) {
        this(ITEMS, TYPE, "", FILL, STROKE, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> ITEMS, final ChartType TYPE, final String NAME) {
        this(ITEMS, TYPE, NAME, Color.TRANSPARENT, Color.BLACK, Symbol.CIRCLE, true);
    }

    public XYSeries(final List<T> ITEMS, final ChartType TYPE, final String NAME, final Paint FILL, final Paint STROKE, final boolean SHOW_POINTS) {
        this(ITEMS, TYPE, NAME, FILL, STROKE, Symbol.CIRCLE, SHOW_POINTS);
    }

    public XYSeries(final List<T> ITEMS, final ChartType TYPE, final String NAME, final Paint FILL, final Paint STROKE, final Symbol SYMBOL, final boolean SYMBOLS_VISIBLE) {
        super(ITEMS, TYPE, NAME, FILL, STROKE, SYMBOL);
        setSymbolsVisible(SYMBOLS_VISIBLE);
    }

    @Override
    public ObservableList<T> getItems() {return items;}

    public double getMinX() {return getItems().stream().min(Comparator.comparingDouble(T::getX)).get().getX();}

    public double getMaxX() {return getItems().stream().max(Comparator.comparingDouble(T::getX)).get().getX();}

    public double getMinY() {return getItems().stream().min(Comparator.comparingDouble(T::getY)).get().getY();}

    public double getMaxY() {return getItems().stream().max(Comparator.comparingDouble(T::getY)).get().getY();}

    public double getRangeX() {return getMaxX() - getMinX();}

    public double getRangeY() {return getMaxY() - getMinY();}

    public double getSumOfXValues() {return getItems().stream().mapToDouble(T::getX).sum();}

    public double getSumOfYValues() {return getItems().stream().mapToDouble(T::getY).sum();}
}
