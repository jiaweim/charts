package fx.chart.data;

import fx.chart.Symbol;
import fx.chart.tools.Helper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;


public class TYChartItem extends XYChartItem {

    private LocalDateTime _t;
    private ObjectProperty<LocalDateTime> t;


    public TYChartItem() {
        this(LocalDateTime.now(), 0, "", Color.RED, Symbol.NONE);
    }

    public TYChartItem(final LocalDateTime T, final double Y) {
        this(T, Y, "", Color.RED, Symbol.NONE);
    }

    public TYChartItem(final LocalDateTime T, final double Y, final Color FILL) {
        this(T, Y, "", FILL, Symbol.NONE);
    }

    public TYChartItem(final LocalDateTime T, final double Y, final String NAME) {
        this(T, Y, NAME, Color.RED, Symbol.NONE);
    }

    public TYChartItem(final LocalDateTime T, final double Y, final String NAME, final Color COLOR) {
        this(T, Y, NAME, COLOR, Symbol.NONE);
    }

    public TYChartItem(final LocalDateTime T, final double Y, final String NAME, final Color FILL, final Symbol SYMBOL) {
        super(T.toEpochSecond(Helper.getZoneOffset()), Y, NAME, FILL, Color.TRANSPARENT, SYMBOL);
        _t = T;
    }


    // ******************** Methods ***************************************
    public LocalDateTime getT() {return null == t ? _t : t.get();}

    public void setT(final LocalDateTime T) {
        if (null == t) {
            _t = T;
            super.setX(_t.toEpochSecond(Helper.getZoneOffset()));
        } else {
            t.set(T);
        }
    }

    public ObjectProperty<LocalDateTime> tProperty() {
        if (null == t) {
            t = new ObjectPropertyBase<LocalDateTime>(_t) {
                @Override
                protected void invalidated() {TYChartItem.super.setX(get().toEpochSecond(Helper.getZoneOffset()));}

                @Override
                public Object getBean() {return TYChartItem.this;}

                @Override
                public String getName() {return "t";}
            };
            _t = null;
        }
        return t;
    }


    @Override
    public String toString() {
        return new StringBuilder().append("{\n")
                .append("  \"name\":\"").append(getName()).append("\",\n")
                .append("  \"t\":").append(getT()).append(",\n")
                .append("  \"y\":").append(getY()).append(",\n")
                .append("  \"symbol\":\"").append(getSymbol().name()).append("\"\n")
                .append("}")
                .toString();
    }
}
