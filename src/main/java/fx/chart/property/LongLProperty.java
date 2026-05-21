package fx.chart.property;

import javafx.beans.property.LongProperty;
import javafx.beans.property.LongPropertyBase;
import org.jspecify.annotations.Nullable;

import java.util.function.LongUnaryOperator;

/**
 * {@link LProperty} for long value.
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 20 May 2026, 3:30 PM
 */
public class LongLProperty extends LProperty<Number, LongProperty> {

    private final LongUnaryOperator clampFunc_;

    /**
     * Create a {@link LProperty}
     *
     * @param bean         The bean to which this property belongs.
     * @param propertyName property name.
     * @param initialValue Initial value of the property.
     * @param onChanged    Operations triggered when the property value changes.
     */
    public LongLProperty(Object bean, String propertyName, @Nullable Long initialValue, Runnable onChanged) {
        super(bean, propertyName, initialValue, onChanged);
        this.clampFunc_ = null;
    }

    public LongLProperty(Object bean, String propertyName, @Nullable Number initialValue, Runnable onChanged, LongUnaryOperator clampFunc_) {
        super(bean, propertyName, initialValue, onChanged);
        this.clampFunc_ = clampFunc_;
    }

    @Override
    protected LongProperty createProperty(Number initialValue) {
        long v = initialValue.longValue();
        if (clampFunc_ != null) {
            v = clampFunc_.applyAsLong(v);
        }
        return new LongPropertyBase(v) {
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
                    long current = get();
                    long clamped = clampFunc_.applyAsLong(current);
                    if (clamped != current) {
                        set(clamped);
                        return;
                    }
                }
                onChanged_.run();
            }
        };
    }

    @Override
    public void set(Number newValue) {
        long v = newValue.longValue();
        if (clampFunc_ != null) {
            v = clampFunc_.applyAsLong(v);
        }
        super.set(v);
    }

    /**
     * @return property value.
     */
    public long getAsLong() {
        return get().longValue();
    }
}
