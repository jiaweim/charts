package fx.chart.property;

import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;

/**
 * {@link LazyProperty} for string.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 20 May 2026, 2:13 PM
 */
public class StringLazyProperty extends LazyProperty<String, StringProperty> {

    /**
     * Create a {@link LazyProperty}
     *
     * @param bean         The bean to which this property belongs.
     * @param propertyName property name
     * @param initialValue Initial value of the property.
     * @param onChanged    Operations triggered when the property value changes.
     */
    public StringLazyProperty(Object bean, String propertyName, String initialValue, Runnable onChanged) {
        super(bean, propertyName, initialValue, onChanged);
    }

    @Override
    protected StringProperty createProperty(String initialValue) {
        return new StringPropertyBase(initialValue) {
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
}
