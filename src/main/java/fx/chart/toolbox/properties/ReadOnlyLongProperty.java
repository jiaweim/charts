package fx.chart.toolbox.properties;


public class ReadOnlyLongProperty extends ReadOnlyProperty<Long> {
    protected fx.chart.toolbox.properties.LongProperty propertyToUpdate;


    public ReadOnlyLongProperty() {
        super(null, null, 0l);
    }

    public ReadOnlyLongProperty(final long value) {
        super(null, null, value);
    }

    public ReadOnlyLongProperty(final Object bean, final String name, final long value) {
        super(bean, name, value);
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }


    // ******************** Methods *******************************************
    public long get() {return value;}

    protected void setPropertyToUpdate(final LongProperty property) {
        this.propertyToUpdate = property;
        this.bidirectional = false;
    }

    @Override
    protected void unsetPropertyToUpdate() {
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }
}
