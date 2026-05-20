package fx.chart.property;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;

/**
 * General object type property.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 20 May 2026, 1:59 PM
 */
public class ObjectLazyProperty<T> extends LazyProperty<T, ObjectProperty<T>> {

    /**
     * Create a {@link LazyProperty}
     *
     * @param bean         The bean to which this property belongs.
     * @param propertyName property name
     * @param initialValue Initial value of the property.
     * @param onChanged    Operations triggered when the property value changes.
     */
    public ObjectLazyProperty(Object bean, String propertyName, T initialValue, Runnable onChanged) {
        super(bean, propertyName, initialValue, onChanged);
    }

    @Override
    protected ObjectProperty<T> createProperty(T initialValue) {
        return new ObjectPropertyBase<>(initialValue) {
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
