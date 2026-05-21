package fx.chart.property;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;

import java.util.function.DoubleUnaryOperator;

/**
 * LazyProperty for double value.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 20 May 2026, 2:04 PM
 */
public class DoubleLProperty extends LProperty<Number, DoubleProperty> {

    private final DoubleUnaryOperator clampFunc_;

    /**
     * Create a {@link DoubleLProperty}.
     *
     * @param bean         The bean to which this property belongs.
     * @param name         property name。
     * @param initialValue Initial value of the property.
     * @param onChanged    Operations triggered when the property value changes.
     * @param clampFunc    {@link DoubleUnaryOperator} used to limit the range of property values.
     */
    public DoubleLProperty(Object bean, String name, Number initialValue, Runnable onChanged,
            DoubleUnaryOperator clampFunc) {
        super(bean, name, initialValue, onChanged);
        this.clampFunc_ = clampFunc;
    }

    /**
     * Create a {@link DoubleLProperty} without {@code clampFunc}.
     *
     * @param bean         The bean to which this property belongs.
     * @param name         property name
     * @param initialValue Initial value of the property.
     * @param onChanged    Operations triggered when the property value changes.
     */
    public DoubleLProperty(Object bean, String name, Number initialValue, Runnable onChanged) {
        super(bean, name, initialValue, onChanged);
        this.clampFunc_ = null;
    }

    public DoubleLProperty(Object bean, String name, Number initialValue) {
        super(bean, name, initialValue);
        this.clampFunc_ = null;
    }

    @Override
    protected DoubleProperty createProperty(Number initialValue) {
        double v = initialValue.doubleValue();
        if (clampFunc_ != null) {
            v = clampFunc_.applyAsDouble(v);
        }
        return new DoublePropertyBase(v) {
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
                if (clampFunc_ != null) {
                    double current = get();
                    double clamped = clampFunc_.applyAsDouble(current);
                    if (clamped != current) {
                        set(clamped); // onChanged will be called in set().
                        return;
                    }
                }
                onChanged_.run();
            }
        };
    }

    @Override
    public void set(Number newValue) {
        double v = newValue.doubleValue();
        if (clampFunc_ != null) {
            v = clampFunc_.applyAsDouble(v);
        }
        super.set(v);
    }

    /**
     * Return property value.
     *
     * @return property value.
     */
    public double getAsDouble() {
        return get().doubleValue();
    }
}
