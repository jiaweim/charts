package fx.chart.property;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.BooleanPropertyBase;

/**
 * {@link LazyProperty} for boolean.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 20 May 2026, 2:47 PM
 */
public class BooleanLazyProperty extends LazyProperty<Boolean, BooleanProperty> {

    /**
     * Create a {@link BooleanLazyProperty}
     *
     * @param bean         The bean to which this property belongs.
     * @param name         property name
     * @param initialValue Initial value of the property.
     * @param onChanged    Operations triggered when the property value changes.
     */
    public BooleanLazyProperty(Object bean, String name, Boolean initialValue, Runnable onChanged) {
        super(bean, name, initialValue, onChanged);
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
