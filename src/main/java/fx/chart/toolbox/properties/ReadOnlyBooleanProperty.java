package fx.chart.toolbox.properties;


public class ReadOnlyBooleanProperty extends ReadOnlyProperty<Boolean> {

    protected fx.chart.toolbox.properties.BooleanProperty propertyToUpdate;

    public ReadOnlyBooleanProperty() {
        super(null, null, false);
    }

    public ReadOnlyBooleanProperty(final boolean value) {
        super(null, null, Boolean.valueOf(value));
    }

    public ReadOnlyBooleanProperty(final Object bean, final String name, final boolean value) {
        super(bean, name, value);
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }

    public boolean get() {return value;}

    protected void setPropertyToUpdate(final BooleanProperty property) {
        this.propertyToUpdate = property;
        this.bidirectional = false;
    }

    @Override
    protected void unsetPropertyToUpdate() {
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }
}
