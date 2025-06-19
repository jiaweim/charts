package fx.chart.toolbox.properties;


public class ReadOnlyDoubleProperty extends ReadOnlyProperty<Double> {
    protected fx.chart.toolbox.properties.DoubleProperty propertyToUpdate;

    public ReadOnlyDoubleProperty() {
        super(null, null, 0d);
    }

    public ReadOnlyDoubleProperty(final double value) {
        super(null, null, value);
    }

    public ReadOnlyDoubleProperty(final Object bean, final String name, final double value) {
        super(bean, name, value);
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }


    // ******************** Methods *******************************************
    public double get() {return value;}

    protected void setPropertyToUpdate(final DoubleProperty property) {
        this.propertyToUpdate = property;
        this.bidirectional = false;
    }

    @Override
    protected void unsetPropertyToUpdate() {
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }
}
