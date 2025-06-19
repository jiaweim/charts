package fx.chart.toolbox.properties;

public class ReadOnlyStringProperty extends ReadOnlyProperty<String> {
    protected fx.chart.toolbox.properties.StringProperty propertyToUpdate;

    // ******************** Constructors **************************************
    public ReadOnlyStringProperty() {
        super(null, null, "");
    }

    public ReadOnlyStringProperty(final String value) {
        super(null, null, value);
    }

    public ReadOnlyStringProperty(final Object bean, final String name, final String value) {
        super(bean, name, value);
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }


    // ******************** Methods *******************************************
    public String get() {return value;}

    protected void setPropertyToUpdate(final StringProperty property) {
        this.propertyToUpdate = property;
        this.bidirectional = false;
    }

    @Override
    protected void unsetPropertyToUpdate() {
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }
}
