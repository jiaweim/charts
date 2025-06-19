package fx.chart.toolbox.properties;


public class ReadOnlyIntegerProperty extends ReadOnlyProperty<Integer> {

    protected fx.chart.toolbox.properties.IntegerProperty propertyToUpdate;

    public ReadOnlyIntegerProperty() {
        super(null, null, 0);
    }

    public ReadOnlyIntegerProperty(final int value) {
        super(null, null, value);
    }

    public ReadOnlyIntegerProperty(final Object bean, final String name, final int value) {
        super(bean, name, value);
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }


    // ******************** Methods *******************************************
    public int get() {return value;}

    protected void setPropertyToUpdate(final IntegerProperty property) {
        this.propertyToUpdate = property;
        this.bidirectional = false;
    }

    @Override
    protected void unsetPropertyToUpdate() {
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }
}
