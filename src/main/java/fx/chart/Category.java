package fx.chart;

import fx.chart.event.ChartEvent;
import fx.chart.event.DefaultEventSource;
import fx.chart.property.ObjectLProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.property.ObjectProperty;
import javafx.scene.paint.Color;


public class Category extends DefaultEventSource implements Comparable<Category> {

    private final ChartEvent UPDATE_EVT = new ChartEvent(Category.this, ChartEvent.UPDATE);

    private final String name;
    private final ObjectLProperty<Color> fill_;
    private final ObjectLProperty<Color> stroke_;
    private final ObjectLProperty<Color> textFill_;
    private double _value;
    private DoubleProperty value;

    public Category(final String name) {this(name, Color.LIGHTGRAY, Color.TRANSPARENT, Color.BLACK);}

    public Category(final String name, final Color fill) {
        this(name, fill, Color.TRANSPARENT, Color.BLACK);
    }

    public Category(final String name, final Color fill, final Color stroke, final Color textFill) {

        this.name = name;
        this.fill_ = new ObjectLProperty<>(this, "fill", fill, () -> fireChartEvent(UPDATE_EVT));
        this.stroke_ = new ObjectLProperty<>(this, "stroke", stroke, () -> fireChartEvent(UPDATE_EVT));
        this.textFill_ = new ObjectLProperty<>(this, "textFill", textFill, () -> fireChartEvent(UPDATE_EVT));
        this._value = 0;
    }

    public String getName() {return name;}

    public Color getFill() {
        return this.fill_.get();
    }

    public void setFill(final Color fill) {
        this.fill_.set(fill);
    }

    public ObjectProperty<Color> fillProperty() {
        return this.fill_.getProperty();
    }

    public Color getStroke() {
        return this.stroke_.get();
    }

    public void setStroke(final Color stroke) {
        this.stroke_.set(stroke);
    }

    public ObjectProperty<Color> strokeProperty() {
        return this.stroke_.getProperty();
    }

    public Color getTextFill() {
        return textFill_.get();
    }

    public void setTextFill(final Color textFill) {
        textFill_.set(textFill);
    }

    public ObjectProperty<Color> textFillProperty() {
        return textFill_.getProperty();
    }

    public double getValue() {return null == value ? _value : value.get();}

    public void setValue(final double value) {
        if (null == this.value) {
            _value = value;
            fireChartEvent(UPDATE_EVT);
        } else {
            this.value.set(value);
        }
    }

    public DoubleProperty valueProperty() {
        if (null == value) {
            value = new DoublePropertyBase(_value) {
                @Override
                protected void invalidated() {fireChartEvent(UPDATE_EVT);}

                @Override
                public Object getBean() {return Category.this;}

                @Override
                public String getName() {return "value";}
            };
        }
        return value;
    }

    @Override
    public int compareTo(final Category other) {
        return getName().compareTo(other.getName());
    }
}
