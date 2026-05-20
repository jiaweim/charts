package fx.chart.property;

import javafx.beans.property.Property;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

/**
 * Lightweight lazy-init property for custom JavaFX chart components.
 * <p>
 * Designed for custom JavaFX components, not thread-safe.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 20 May 2026, 1:17 PM
 */
public abstract class LazyProperty<T, P extends Property<T>> {

    protected final Object bean_;
    protected final String name_;
    protected final Runnable onChanged_;
    private T value_;
    private P property_;

    /**
     * Create a {@link LazyProperty}
     *
     * @param bean         The bean to which this property belongs.
     * @param propertyName property name.
     * @param initialValue Initial value of the property.
     * @param onChanged    Operations triggered when the property value changes.
     */
    public LazyProperty(Object bean, String propertyName, @Nullable T initialValue, Runnable onChanged) {
        this.bean_ = bean;
        this.name_ = Objects.requireNonNull(propertyName);
        this.value_ = initialValue;
        this.onChanged_ = Objects.requireNonNull(onChanged);
    }

    /**
     * Return the property value.
     *
     * @return property value.
     */
    public @Nullable T get() {
        return property_ == null ? value_ : property_.getValue();
    }

    /**
     * Set the property value.
     *
     * @param newValue property value
     */
    public void set(@Nullable T newValue) {
        if (property_ == null) {
            value_ = newValue;
            onChanged_.run();
        } else {
            property_.setValue(newValue);
        }
    }

    /**
     * Create property type.
     *
     * @param initialValue the initial value for the property.
     * @return created Property.
     */
    protected abstract P createProperty(T initialValue);

    /**
     * Return the property.
     *
     * @return property.
     */
    public P getProperty() {
        if (property_ == null) {
            property_ = createProperty(value_);
            value_ = null;
        }
        return property_;
    }


    /**
     * Return the bean.
     *
     * @return bean.
     */
    public Object getBean() {
        return bean_;
    }

    /**
     * Return the property name.
     *
     * @return property name.
     */
    public String getName() {
        return name_;
    }
}
