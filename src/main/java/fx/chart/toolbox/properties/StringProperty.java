package fx.chart.toolbox.properties;

import fx.chart.toolbox.evt.type.InvalidationEvt;
import fx.chart.toolbox.evt.type.PropertyChangeEvt;


public class StringProperty extends ReadOnlyStringProperty {

    protected StringProperty propertyBoundTo;
    protected boolean bound;


    public StringProperty() {
        this(null, null, "");
    }

    public StringProperty(final String value) {
        this(null, null, value);
    }

    public StringProperty(final String name, final String value) {
        this(null, name, value);
    }

    public StringProperty(final Object bean, final String name, final String value) {
        super(bean, name, value);
        this.propertyBoundTo = null;
        this.bound = false;
    }


    public void set(final String value) {setValue(value);}

    public void setValue(final String value) {
        if (bound && !bidirectional) {
            throw new IllegalArgumentException("A bound value cannot be set.");
        }
        setValue(value, null);
    }

    protected void setValue(final String value, final StringProperty property) {
        if (!value.equals(getValue())) {
            willChange(this.value, value);
            final String oldValue = this.value;
            this.value = value;
            if (null == property && null != this.propertyToUpdate) {
                this.propertyToUpdate.setValue(value, this);
            }
            fireEvent(new PropertyChangeEvt(this, PropertyChangeEvt.CHANGED, oldValue, this.value));
            didChange(oldValue, this.value);
        }
        invalidated();
    }

    @Override
    public void invalidated() {
        fireEvent(new InvalidationEvt(this, InvalidationEvt.INVALIDATED));
    }

    public void unset() {setValue(getInitialValue());}

    public void setInitialValue(final String initialValue) {this.initialValue = initialValue;}

    public void bind(final StringProperty property) {
        this.propertyBoundTo = property;
        this.value = this.propertyBoundTo.getValue();
        propertyBoundTo.setPropertyToUpdate(this);
        propertyToUpdate = null;
        this.bound = true;
    }

    public boolean isBound() {return this.bound;}

    public void bindBidirectional(final StringProperty property) {
        setPropertyToUpdate(property, true);
        property.setPropertyToUpdate(this, true);
        this.propertyBoundTo = property;
        this.bound = true;
    }

    public boolean isBoundBidirectional() {return this.bidirectional;}

    public void unbind() {
        if (null != this.propertyToUpdate) {
            this.propertyToUpdate.unsetPropertyToUpdate();
            this.propertyToUpdate.unbind();
            this.propertyToUpdate = null;
        }
        if (null != this.propertyBoundTo) {
            this.propertyBoundTo.unsetPropertyToUpdate();
            this.propertyBoundTo = null;
        }
        this.bound = false;
        this.bidirectional = false;
    }

    protected void setPropertyToUpdate(final StringProperty property, final boolean bidirectional) {
        this.propertyToUpdate = property;
        if (null == property) {
            this.bidirectional = false;
        } else {
            this.value = property.getValue();
            this.bidirectional = bidirectional;
        }
    }
}
