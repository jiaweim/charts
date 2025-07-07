package fx.chart.data;

import fx.chart.Symbol;
import fx.chart.util.TimeUtils;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.scene.paint.Color;

import java.time.LocalDateTime;


public class TYChartItem extends XYChartItem {

    private LocalDateTime _t;
    private ObjectProperty<LocalDateTime> tProperty;

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
        super(T.toEpochSecond(TimeUtils.getZoneOffset()), Y, NAME, FILL, Color.TRANSPARENT, SYMBOL);
        _t = T;
    }

    public LocalDateTime getT() {return null == tProperty ? _t : tProperty.get();}

    public void setT(final LocalDateTime T) {
        if (null == tProperty) {
            _t = T;
            super.setX(_t.toEpochSecond(TimeUtils.getZoneOffset()));
        } else {
            tProperty.set(T);
        }
    }

    public ObjectProperty<LocalDateTime> tProperty() {
        if (null == tProperty) {
            tProperty = new ObjectPropertyBase<>(_t) {
                @Override
                protected void invalidated() {TYChartItem.super.setX(get().toEpochSecond(TimeUtils.getZoneOffset()));}

                @Override
                public Object getBean() {return TYChartItem.this;}

                @Override
                public String getName() {return "t";}
            };
            _t = null;
        }
        return tProperty;
    }


    @Override
    public String toString() {
        return "{\n" +
                "  \"name\":\"" + getName() + "\",\n" +
                "  \"t\":" + getT() + ",\n" +
                "  \"y\":" + getY() + ",\n" +
                "  \"symbol\":\"" + getSymbol().name() + "\"\n" +
                "}";
    }
}
