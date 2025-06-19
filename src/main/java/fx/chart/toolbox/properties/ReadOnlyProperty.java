package fx.chart.toolbox.properties;

import fx.chart.toolbox.evt.EvtObserver;
import fx.chart.toolbox.evt.type.InvalidationEvt;
import fx.chart.toolbox.evt.type.PropertyChangeEvt;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;


public abstract class ReadOnlyProperty<T> {

    protected CopyOnWriteArrayList<EvtObserver<PropertyChangeEvt<T>>> observers;
    protected CopyOnWriteArrayList<EvtObserver<InvalidationEvt<T>>> invalidationObservers;
    protected Object bean;
    protected String name;
    protected T initialValue;
    protected T value;
    protected fx.chart.toolbox.properties.Property<T> propertyToUpdate;
    protected boolean bidirectional;

    public ReadOnlyProperty() {
        this(null, null, null, null);
    }

    public ReadOnlyProperty(final T value) {
        this(null, null, value, value);
    }

    public ReadOnlyProperty(final Object bean, final String name, final T value) {
        this(bean, name, value, value);
    }

    public ReadOnlyProperty(final Object bean, final String name, final T value, final T initialValue) {
        this.bean = bean;
        this.name = name;
        this.value = value;
        this.initialValue = initialValue;
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }

    public final T getValue() {return value;}

    public final T getInitialValue() {return initialValue;}

    public final boolean isSet() {return Objects.equals(getValue(), getInitialValue());}

    protected void willChange(final T oldValue, final T newValue) {}

    protected void didChange(final T oldValue, final T newValue) {}

    protected void invalidated() {}

    public Object getBean() {return bean;}

    public String getName() {return name;}

    protected void setPropertyToUpdate(final Property<T> property) {
        this.propertyToUpdate = property;
        this.bidirectional = false;
    }

    protected void unsetPropertyToUpdate() {
        this.propertyToUpdate = null;
        this.bidirectional = false;
    }

    public List<EvtObserver<PropertyChangeEvt<T>>> getObservers() {
        if (null == observers) {
            observers = new CopyOnWriteArrayList<>();
        }
        return observers;
    }

    public List<EvtObserver<InvalidationEvt<T>>> getInvalidationObservers() {
        if (null == invalidationObservers) {
            invalidationObservers = new CopyOnWriteArrayList<>();
        }
        return invalidationObservers;
    }


    // ******************** Event Handling ************************************
    public void addOnChange(final EvtObserver<PropertyChangeEvt<T>> observer) {
        addObserver(observer);
    }

    public void addObserver(final EvtObserver<PropertyChangeEvt<T>> observer) {
        if (null == observer) {
            return;
        }
        if (null == observers) {
            observers = new CopyOnWriteArrayList<>();
        }
        if (observers.contains(observer)) {
            return;
        }
        observers.add(observer);
    }

    public void removeObserver(final EvtObserver<PropertyChangeEvt<T>> observer) {
        if (null == observer || null == observers) {
            return;
        }
        if (observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    public void removeAllObservers() {
        if (null == observers) {
            return;
        }
        observers.clear();
    }

    public void fireEvent(final PropertyChangeEvt<T> evt) {
        if (null == evt || null == observers) {
            return;
        }
        observers.forEach(observer -> observer.handle(evt));
    }


    public void addOnInvalidation(final EvtObserver<InvalidationEvt<T>> observer) {addInvalidationObserver(observer);}

    public void addInvalidationObserver(final EvtObserver<InvalidationEvt<T>> observer) {
        if (null == observer) {
            return;
        }
        if (null == invalidationObservers) {
            invalidationObservers = new CopyOnWriteArrayList<>();
        }
        if (invalidationObservers.contains(observer)) {
            return;
        }
        invalidationObservers.add(observer);
    }

    public void removeInvalidationObserver(final EvtObserver<InvalidationEvt<T>> observer) {
        if (null == invalidationObservers || null == observer) {
            return;
        }
        if (invalidationObservers.contains(observer)) {
            invalidationObservers.remove(observer);
        }
    }

    public void removeAllInvalidationObservers() {
        if (null == invalidationObservers) {
            return;
        }
        invalidationObservers.clear();
    }

    public void fireEvent(final InvalidationEvt evt) {
        if (null == evt || null == invalidationObservers) {
            return;
        }
        invalidationObservers.forEach(observer -> observer.handle(evt));
    }
}
