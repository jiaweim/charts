package fx.chart.property;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;
import org.jspecify.annotations.Nullable;

/**
 * {@link LProperty} for boolean.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 20 May 2026, 2:47 PM
 */
public class BooleanLProperty extends LProperty<Boolean, BooleanProperty> {

    /**
     * Create a {@link BooleanLProperty}
     *
     * @param bean         The bean to which this property belongs.
     * @param name         property name
     * @param initialValue Initial value of the property.
     * @param onChanged    Operations triggered when the property value changes.
     */
    public BooleanLProperty(Object bean, String name, Boolean initialValue, Runnable onChanged) {
        super(bean, name, initialValue, onChanged);
    }

    public BooleanLProperty(Object bean, String propertyName, @Nullable Boolean initialValue) {
        super(bean, propertyName, initialValue);
    }

    @Override
    protected BooleanProperty createProperty(Boolean initialValue) {
        return new BooleanPropertyBase(initialValue) {

            @Override
            public Object getBean() {
                return bean_;
            }

            @Override
            public String getName() {
                return name_;
            }

            @Override
            protected void invalidated() {
                onChanged_.run();
            }
        };
    }

    public boolean getAsBoolean() {
        return get().booleanValue();
    }
}
