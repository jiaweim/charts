package fx.chart.property;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.IntegerPropertyBase;
import org.jspecify.annotations.Nullable;

/**
 * {@link LProperty} for integer type.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 21 May 2026, 9:29 AM
 */
public class IntegerLProperty extends LProperty<Number, IntegerProperty> {
    /**
     * Create a {@link LProperty}
     *
     * @param bean         The bean to which this property belongs.
     * @param propertyName property name.
     * @param initialValue Initial value of the property.
     * @param onChanged    Operations triggered when the property value changes.
     */
    public IntegerLProperty(Object bean, String propertyName, @Nullable Number initialValue,
            Runnable onChanged) {
        super(bean, propertyName, initialValue, onChanged);
    }

    /**
     * Create a {@link LProperty}
     *
     * @param bean         The bean to which this property belongs.
     * @param propertyName property name.
     * @param initialValue Initial value of the property.
     */
    public IntegerLProperty(Object bean, String propertyName, @Nullable Number initialValue) {
        super(bean, propertyName, initialValue);
    }

    @Override
    protected IntegerProperty createProperty(Number initialValue) {
        return new IntegerPropertyBase(initialValue.intValue()) {
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

    public int getAsInt() {
        Number number = get();
        if (number == null) return 0;
        else return number.intValue();
    }
}
