package fx.chart.toolbox.properties;


public class ReadOnlyObjectProperty<T> extends ReadOnlyProperty<T> {

    protected fx.chart.toolbox.properties.ObjectProperty<T> propertyToUpdate;


    public ReadOnlyObjectProperty() {
        super(null, null, null);
    }

    public ReadOnlyObjectProperty(final T value) {
        super(null, null, value);
    }

    public ReadOnlyObjectProperty(final Object bean, final String name, final T value) {
        super(bean, name, value);
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }

    public T get() {return value;}

    protected void setPropertyToUpdate(final ObjectProperty<T> property) {
        this.propertyToUpdate = property;
        this.bidirectional = false;
    }

    @Override
    protected void unsetPropertyToUpdate() {
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }
}
