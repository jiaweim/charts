package fx.chart;

import fx.chart.event.ChartEvent;
import fx.chart.event.ChartEventListener;
import fx.chart.event.EventType;
import fx.chart.tools.Helper;
import fx.chart.tools.Helper.Interval;
import fx.chart.tools.TickLabelFormat;
import fx.chart.util.Bounds;
import fx.chart.util.TimeUtils;
import javafx.beans.DefaultProperty;
import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static javafx.geometry.Orientation.VERTICAL;
import static pdk.util.ArgUtils.checkNotNull;


/**
 * Axis
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
 * @version 1.0.0
 * @since 24 Jun 2025, 9:33 AM
 */
@DefaultProperty("children")
public class Axis extends Region {

    /**
     * Create a linear axis in range [0,100]
     *
     * @param orientation {@link Orientation}
     * @param position    {@link Position}
     * @return {@link Axis}
     */
    public static Axis linear(final Orientation orientation, final Position position) {
        return new Axis(0, 100, orientation, AxisType.LINEAR, position, "");
    }

    /**
     * Create a linear axis on left
     *
     * @param min       min value
     * @param max       max value
     * @param axisWidth region width
     * @return Axis instance
     */
    public static Axis left(final double min, final double max, final double axisWidth) {
        return createAxis(min, max, "", true, axisWidth, axisWidth, Orientation.VERTICAL, Position.LEFT);
    }

    /**
     * Create a linear axis on left with given title
     *
     * @param min       min value
     * @param max       max value
     * @param title     axis title
     * @param axisWidth region width
     * @return Axis instance
     */
    public static Axis left(final double min, final double max, final String title, final double axisWidth) {
        return createAxis(min, max, title, true, axisWidth, axisWidth, Orientation.VERTICAL, Position.LEFT);
    }

    /**
     * Create a linear axis on left with given title
     *
     * @param min       min value
     * @param max       max value
     * @param autoScale true if auto-scale
     * @param axisWidth region width
     * @return Axis instance
     */
    public static Axis left(final double min, final double max, final boolean autoScale, final double axisWidth) {
        return createAxis(min, max, "", autoScale, axisWidth, axisWidth, Orientation.VERTICAL, Position.LEFT);
    }

    /**
     * Create a linear axis
     *
     * @param min       min value
     * @param max       max value
     * @param title     axis title
     * @param autoScale true if auto-scale
     * @param axisWidth region width
     * @return Axis instance
     */
    public static Axis left(final double min, final double max, final String title, final boolean autoScale, final double axisWidth) {
        return createAxis(min, max, title, autoScale, axisWidth, axisWidth, Orientation.VERTICAL, Position.LEFT);
    }

    /**
     * Create a linear Y axis at center
     *
     * @param min       min value
     * @param max       max value
     * @param autoscale true if auto-scale
     * @param axisWidth region width
     * @return Axis instance
     */
    public static Axis centerY(final double min, final double max, final boolean autoscale, final double axisWidth) {
        return createAxis(min, max, "", autoscale, axisWidth, axisWidth, Orientation.VERTICAL, Position.CENTER);
    }

    /**
     * Create a linear x axis at ceter
     *
     * @param min       min value
     * @param max       max value
     * @param autoScale true if auto-scale
     * @param axisWidth region width
     * @return Axis instance
     */
    public static Axis centerX(final double min, final double max, final boolean autoScale, final double axisWidth) {
        return createAxis(min, max, "", autoScale, axisWidth, axisWidth, Orientation.HORIZONTAL, Position.CENTER);
    }

    /**
     * Create a linear axis at right
     *
     * @param min       min value
     * @param max       max value
     * @param autoScale true if auto-scale
     * @param axisWidth region width
     * @return Axis instance
     */
    public static Axis right(final double min, final double max, final boolean autoScale, final double axisWidth) {
        return createAxis(min, max, "", autoScale, axisWidth, axisWidth, Orientation.VERTICAL, Position.RIGHT);
    }

    /**
     * Crate a linear axis at top
     *
     * @param min       min value
     * @param max       max value
     * @param axisWidth region width
     * @return Axis instance
     */
    public static Axis top(final double min, final double max, final double axisWidth) {
        return createAxis(min, max, "", true, axisWidth, axisWidth, Orientation.HORIZONTAL, Position.TOP);
    }

    /**
     * Create a bottom-axis
     *
     * @param min       min value of the axis
     * @param max       max value of the axis
     * @param axisWidth region width
     * @return Axis instance
     */
    public static Axis bottom(final double min, final double max, final double axisWidth) {
        return createAxis(min, max, "", true, axisWidth, axisWidth, Orientation.HORIZONTAL, Position.BOTTOM);
    }


    /**
     * Create a bottom-axis
     *
     * @param min       min value
     * @param max       max value
     * @param autoScale true if auto-scale
     * @param axisWidth region width
     * @return Axis instance
     */
    public static Axis bottom(final double min, final double max, final boolean autoScale, final double axisWidth) {
        return createAxis(min, max, "", autoScale, axisWidth, axisWidth, Orientation.HORIZONTAL, Position.BOTTOM);
    }

    /**
     * Create a bottom-axis
     *
     * @param min       min value
     * @param max       max value
     * @param title     axis title
     * @param autoScale true if auto-scale
     * @param axisWidth region width
     * @return Axis instance
     */
    public static Axis bottom(final double min, final double max, final String title, final boolean autoScale, final double axisWidth) {
        return createAxis(min, max, title, autoScale, axisWidth, axisWidth, Orientation.HORIZONTAL, Position.BOTTOM);
    }

    /**
     * Create a bottom time axis
     *
     * @param start       start time
     * @param end         end time
     * @param pattern     pattern used to format time, such as "MM:yyyy"
     * @param autoScale   true if auto-scale
     * @param axisWidth   region width
     * @param anchorLeft  left anchor
     * @param anchorRight right anchor
     * @return Axis instance
     */
    public static Axis bottomTime(final LocalDateTime start, final LocalDateTime end, final String pattern,
            final boolean autoScale, final double axisWidth, final double anchorLeft, final double anchorRight) {
        Axis axis = new Axis(start, end, Orientation.HORIZONTAL, Position.BOTTOM);
        axis.setDateTimeFormatPattern(pattern);
        axis.setPrefHeight(axisWidth);

        AnchorPane.setBottomAnchor(axis, 0d);
        AnchorPane.setLeftAnchor(axis, anchorLeft);
        AnchorPane.setRightAnchor(axis, anchorRight);

        return axis;
    }

    /**
     * Create an axis
     *
     * @param min         min value
     * @param max         max value
     * @param autoScale   true if auto-scale
     * @param axisWidth   region width
     * @param orientation {@link Orientation}
     * @param position    {@link Position}
     * @return Axis instance
     */
    public static Axis axis(final double min, final double max, final boolean autoScale, final double axisWidth,
            final Orientation orientation, final Position position) {
        return createAxis(min, max, "", autoScale, axisWidth, axisWidth, orientation, position);
    }

    /**
     * Create an axis
     *
     * @param min         min value of the axis
     * @param max         max value of the axis
     * @param title       axis title
     * @param autoScale   true if auto-scale
     * @param axisWidth   axis region width
     * @param anchor      anchor value in the {@link AnchorPane}, if you need it to be right next to the border, choose the same
     *                    value as {@code axisWidth}
     * @param orientation {@link Orientation}
     * @param position    {@link Position}
     * @return Axis instance
     */
    public static Axis createAxis(final double min, final double max, final String title, final boolean autoScale,
            final double axisWidth, final double anchor, final Orientation orientation, final Position position) {
        Axis axis = new Axis(min, max, orientation, AxisType.LINEAR, position, title);
        axis.setAutoScale(autoScale);

        if (orientation == Orientation.HORIZONTAL) {
            axis.setPrefHeight(axisWidth);
        } else {
            axis.setPrefWidth(axisWidth);
        }

        switch (position) {
            case LEFT:
                AnchorPane.setTopAnchor(axis, 0d);
                AnchorPane.setBottomAnchor(axis, anchor);
                AnchorPane.setLeftAnchor(axis, 0d);
                break;
            case CENTER:
                break;
            case RIGHT:
                AnchorPane.setRightAnchor(axis, 0.0);
                AnchorPane.setTopAnchor(axis, 0.0);
                AnchorPane.setBottomAnchor(axis, anchor);
                break;
            case TOP:
                AnchorPane.setTopAnchor(axis, anchor);
                AnchorPane.setLeftAnchor(axis, anchor);
                AnchorPane.setRightAnchor(axis, anchor);
                break;
            case BOTTOM:
                AnchorPane.setBottomAnchor(axis, 0.0);
                AnchorPane.setLeftAnchor(axis, anchor);
                AnchorPane.setRightAnchor(axis, anchor);
                break;
        }
        return axis;
    }

    /**
     * The default axis label font {@code Font("SansSerif", Font.PLAIN, 12)}
     */
    public static final Font DEFAULT_AXIS_LABEL_FONT = Font.font("SansSerif", FontWeight.NORMAL, 12);

    /**
     * The default tick label font ({@code Font("SansSerif", Font.PLAIN, 10)}).
     */
    public static final Font DEFAULT_TICK_LABEL_FONT = Font.font("SansSerif", FontWeight.NORMAL, 10);

    private static final double MINIMUM_WIDTH = 0;
    private static final double MINIMUM_HEIGHT = 0;
    private static final double MAXIMUM_WIDTH = 4096;
    private static final double MAXIMUM_HEIGHT = 4096;

    private static final double MIN_MAJOR_LINE_WIDTH = 1;
    private static final double MIN_MEDIUM_LINE_WIDTH = 0.75;
    private static final double MIN_MINOR_LINE_WIDTH = 0.5;

    /**
     * Axis range change event
     */
    private final ChartEvent AXIS_RANGE_CHANGED_EVT = new ChartEvent(Axis.this, ChartEvent.AXIS_RANGE_CHANGED);

    private final Map<EventType<ChartEvent>, List<ChartEventListener<ChartEvent>>> listeners = new ConcurrentHashMap<>();

    private double size;
    /**
     * width of the axis area
     */
    private double width;
    /**
     * height of the axis area
     */
    private double height;
    /**
     * {@link Canvas} to draw axis
     */
    private Canvas axisCanvas;
    private GraphicsContext axisGC_;
    /**
     * pane is used to hold Canvas
     */
    private Pane pane;

    private double minValue_;
    private DoubleProperty minValueProperty;

    private LocalDateTime startTime_;
    private ObjectProperty<LocalDateTime> startTimeProperty;

    private double maxValue_;
    private DoubleProperty maxValueProperty;

    private LocalDateTime endTime_;
    private ObjectProperty<LocalDateTime> endTimeProperty;

    private boolean autoScale_;
    private BooleanProperty autoScaleProperty;

    /**
     * pixels per unit, calculated in the {@link #resize()}
     */
    private double stepSize;
    /**
     * Rendering area
     */
    private Bounds axisBounds_;

    private String title_;
    private StringProperty titleProperty;

    private String _unit;
    private StringProperty unitProperty;

    private AxisType type_;
    private ObjectProperty<AxisType> typeProperty;

    private Orientation orientation_;
    private ObjectProperty<Orientation> orientationProperty;

    private Position position_;
    private ObjectProperty<Position> positionProperty;

    private Color axisBackgroundColor_;
    private ObjectProperty<Color> axisBackgroundColorProperty;

    private Color _axisColor;
    private ObjectProperty<Color> axisColorProperty;

    private Color _tickLabelColor;
    private ObjectProperty<Color> tickLabelColorProperty;

    private Color _titleColor;
    private ObjectProperty<Color> titleColorProperty;

    private Color _minorTickMarkColor;
    private ObjectProperty<Color> minorTickMarkColorProperty;

    private Color _mediumTickMarkColor;
    private ObjectProperty<Color> mediumTickMarkColorProperty;

    private Color _majorTickMarkColor;
    private ObjectProperty<Color> majorTickMarkColor;

    private Color _zeroColor;
    private ObjectProperty<Color> zeroColorProperty;

    private double _zeroPosition;
    private DoubleProperty zeroPositionProperty;

    private double minorTickSpace_;
    private double majorTickSpace_;

    private boolean majorTickMarksVisible_;
    private BooleanProperty majorTickMarksVisibleProperty;

    private boolean mediumTickMarksVisible_;
    private BooleanProperty mediumTickMarksVisibleProperty;

    private boolean _minorTickMarksVisible;
    private BooleanProperty minorTickMarksVisibleProperty;

    private boolean _sameTickMarkLength;
    private BooleanProperty sameTickMarkLengthProperty;

    private boolean _tickLabelsVisible;
    private BooleanProperty tickLabelsVisibleProperty;

    private boolean _mediumTimeAxisTickLabelsVisible;
    private BooleanProperty mediumTimeAxisTickLabelsVisibleProperty;

    private boolean _onlyFirstAndLastTickLabelVisible;
    private BooleanProperty onlyFirstAndLastTickLabelVisibleProperty;

    private Locale locale_;
    private ObjectProperty<Locale> localeProperty;

    /**
     * set the number of decimal for the tick label
     */
    private int decimals_;
    private IntegerProperty decimalsProperty;

    /**
     * the format string to format tick labels
     */
    private String tickLabelFormatString;
    private StringConverter<Number> numberFormatter;

    private TickLabelOrientation _tickLabelOrientation;
    private ObjectProperty<TickLabelOrientation> tickLabelOrientationProperty;

    private TickLabelFormat _tickLabelFormat;
    private ObjectProperty<TickLabelFormat> tickLabelFormat;

    /**
     * The font used to display the tick labels.
     */
    private Font tickLabelFont_;
    private ObjectProperty<Font> tickLabelFontProperty;

    private boolean autoTitleFontSize_;
    private BooleanProperty autoTitleFontSizeProperty;

    private boolean autoTickLabelFontSize_;
    private BooleanProperty autoTickLabelFontSizeProperty;

    private Font titleFont_;
    private ObjectProperty<Font> titleFontProperty;

    private ZoneId _zoneId;
    private ObjectProperty<ZoneId> zoneIdProperty;

    private String _dateTimeFormatPattern;
    private StringProperty dateTimeFormatPatternProperty;

    private List<String> categories;
    private DateTimeFormatter dateTimeFormatter;
    private Interval currentInterval;
    /**
     * draw axis when {@link ChartEvent#AXIS_RANGE_CHANGED} event happen
     */
    private ChartEventListener<ChartEvent> eventListener_;

    /**
     * Create an axis with {@code minValue=0} and {@code maxValue=100}
     *
     * @param orientation axis {@link Orientation}
     * @param type        {@link AxisType}
     * @param position    {@link Position}
     */
    public Axis(final Orientation orientation, final AxisType type, final Position position) {
        this(0, 100, orientation, type, position, "");
    }

    /**
     * Create a linear axis without title
     *
     * @param minValue    min value of this axis
     * @param maxValue    max value of this axis
     * @param orientation {@link Orientation} of this axis
     * @param position    axis position in the pane
     */
    public Axis(final double minValue, final double maxValue, final Orientation orientation, final Position position) {
        this(minValue, maxValue, orientation, AxisType.LINEAR, position, "");
    }

    /**
     * Create an axis without title
     *
     * @param minValue    min value of this axis
     * @param maxValue    max value of this axis
     * @param orientation {@link Orientation} of this axis
     * @param type        axis type
     * @param position    axis position in the pane
     */
    public Axis(final double minValue, final double maxValue, final Orientation orientation, final AxisType type, final Position position) {
        this(minValue, maxValue, orientation, type, position, "");
    }

    /**
     * Create a value axis
     *
     * @param minValue    min value of this axis
     * @param maxValue    max value of this axis
     * @param orientation {@link Orientation} of this axis
     * @param type        axis type
     * @param position    axis position in the pane
     * @param title       axis title
     */
    public Axis(final double minValue, final double maxValue, final Orientation orientation,
            final AxisType type, final Position position, final String title) {
        if (orientation == VERTICAL) {
            if (Position.LEFT != position && Position.RIGHT != position && Position.CENTER != position) {
                throw new IllegalArgumentException("Wrong combination of orientation and position!");
            }
        } else {
            if (Position.TOP != position && Position.BOTTOM != position && Position.CENTER != position) {
                throw new IllegalArgumentException("Wrong combination of orientation and position!");
            }
        }

        minValue_ = minValue;
        maxValue_ = maxValue;

        type_ = type;
        autoScale_ = true;
        title_ = title;
        _unit = "";
        orientation_ = orientation;
        position_ = position;
        axisBackgroundColor_ = Color.TRANSPARENT;
        _axisColor = Color.BLACK;
        _tickLabelColor = Color.BLACK;
        _titleColor = Color.BLACK;
        _minorTickMarkColor = Color.BLACK;
        _mediumTickMarkColor = Color.BLACK;
        _majorTickMarkColor = Color.BLACK;
        _zeroColor = Color.BLACK;
        _zeroPosition = 0;
        minorTickSpace_ = 1;
        majorTickSpace_ = 10;
        majorTickMarksVisible_ = true;
        mediumTickMarksVisible_ = true;
        _minorTickMarksVisible = true;
        _sameTickMarkLength = false;
        _tickLabelsVisible = true;
        _mediumTimeAxisTickLabelsVisible = false;
        _onlyFirstAndLastTickLabelVisible = false;
        locale_ = Locale.US;
        decimals_ = 0;
        _tickLabelOrientation = TickLabelOrientation.HORIZONTAL;
        _tickLabelFormat = TickLabelFormat.NUMBER;
        autoTitleFontSize_ = true;
        autoTickLabelFontSize_ = true;
        tickLabelFont_ = DEFAULT_TICK_LABEL_FONT;
        titleFont_ = DEFAULT_AXIS_LABEL_FONT;
        _zoneId = ZoneId.systemDefault();
        _dateTimeFormatPattern = "dd.MM.YY HH:mm:ss";
        axisBounds_ = new Bounds();
        currentInterval = Interval.SECOND_1;
        eventListener_ = e -> drawAxis();
        dateTimeFormatter = DateTimeFormatter.ofPattern(_dateTimeFormatPattern, locale_);
        categories = new LinkedList<>();
        tickLabelFormatString = "%." + decimals_ + "f";

        initGraphics();
        registerListeners();
    }

    public Axis(final LocalDateTime START, final LocalDateTime END, final Orientation ORIENTATION, final Position POSITION) {
        this(START, END, ORIENTATION, POSITION, "");
    }

    /**
     * Create a time axis
     *
     * @param start       start time
     * @param end         end time
     * @param orientation
     * @param position
     * @param title
     */
    public Axis(final LocalDateTime start, final LocalDateTime end, final Orientation orientation,
            final Position position, final String title) {

        if (orientation == VERTICAL) {
            if (Position.LEFT != position && Position.RIGHT != position && Position.CENTER != position) {
                throw new IllegalArgumentException("Wrong combination of orientation and position!");
            }
        } else {
            if (position != Position.TOP && position != Position.BOTTOM && Position.CENTER != position) {
                throw new IllegalArgumentException("Wrong combination of orientation and position!");
            }
        }

        getStylesheets().add(Axis.class.getResource("chart.css").toExternalForm());
        minValue_ = start.toEpochSecond(TimeUtils.getZoneOffset());
        startTime_ = start;
        maxValue_ = end.toEpochSecond(TimeUtils.getZoneOffset());
        endTime_ = end;

        type_ = AxisType.TIME;
        autoScale_ = true;
        title_ = title;
        _unit = "";
        orientation_ = orientation;
        position_ = position;
        axisBackgroundColor_ = Color.TRANSPARENT;
        _axisColor = Color.BLACK;
        _tickLabelColor = Color.BLACK;
        _minorTickMarkColor = Color.BLACK;
        _mediumTickMarkColor = Color.BLACK;
        _majorTickMarkColor = Color.BLACK;
        _zeroColor = Color.BLACK;
        _zeroPosition = 0;
        minorTickSpace_ = 1;
        majorTickSpace_ = 10;
        majorTickMarksVisible_ = true;
        mediumTickMarksVisible_ = true;
        _minorTickMarksVisible = true;
        _sameTickMarkLength = false;
        _tickLabelsVisible = true;
        _mediumTimeAxisTickLabelsVisible = false;
        _onlyFirstAndLastTickLabelVisible = false;
        locale_ = Locale.US;
        decimals_ = 0;
        _tickLabelOrientation = TickLabelOrientation.HORIZONTAL;
        _tickLabelFormat = TickLabelFormat.NUMBER;
        autoTitleFontSize_ = true;
        autoTickLabelFontSize_ = true;
        titleFont_ = DEFAULT_AXIS_LABEL_FONT;
        tickLabelFont_ = DEFAULT_TICK_LABEL_FONT;

        _zoneId = ZoneId.systemDefault();
        _dateTimeFormatPattern = "dd.MM.YY HH:mm:ss";
        axisBounds_ = new Bounds();
        currentInterval = Interval.SECOND_1;
        dateTimeFormatter = DateTimeFormatter.ofPattern(_dateTimeFormatPattern, locale_);
        tickLabelFormatString = "%." + decimals_ + "f";

        initGraphics();
        registerListeners();
    }

    private void initGraphics() {
        if (Double.compare(getPrefWidth(), 0.0) <= 0 || Double.compare(getPrefHeight(), 0.0) <= 0
                || Double.compare(getWidth(), 0.0) <= 0 || Double.compare(getHeight(), 0.0) <= 0) {
            if (getPrefWidth() != 0 && getPrefHeight() != 0) {
                if (getOrientation() == VERTICAL) {
                    setPrefSize(20, 250);
                } else {
                    setPrefSize(250, 20);
                }
            }
        }

        getStyleClass().add("axis");

        axisCanvas = new Canvas(width, height);
        axisGC_ = axisCanvas.getGraphicsContext2D();
        axisBounds_.set(0, 0, width, height);

        pane = new Pane(axisCanvas);

        getChildren().setAll(pane);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
        addChartEventListener(ChartEvent.AXIS_RANGE_CHANGED, eventListener_);
    }

    @Override
    protected double computeMinWidth(final double HEIGHT) {return MINIMUM_WIDTH;}

    @Override
    protected double computeMinHeight(final double WIDTH) {return MINIMUM_HEIGHT;}

    @Override
    protected double computePrefWidth(final double HEIGHT) {return super.computePrefWidth(HEIGHT);}

    @Override
    protected double computePrefHeight(final double WIDTH) {return super.computePrefHeight(WIDTH);}

    @Override
    protected double computeMaxWidth(final double HEIGHT) {return MAXIMUM_WIDTH;}

    @Override
    protected double computeMaxHeight(final double WIDTH) {return MAXIMUM_HEIGHT;}

    @Override
    public ObservableList<Node> getChildren() {return super.getChildren();}

    /**
     * @return min value of this axis
     */
    public double getMinValue() {
        return minValueProperty == null ? minValue_ : minValueProperty.get();
    }

    /**
     * set the min time to display
     *
     * @param START min time
     */
    public void setMinValue(final LocalDateTime START) {
        setMinValue(START.toEpochSecond(TimeUtils.getZoneOffset(getZoneId())));
    }

    public void setMinValue(final double VALUE) {
        if (minValueProperty == null) {
            if (VALUE > getMaxValue()) {
                setMaxValue(VALUE);
            }
            minValue_ = Math.clamp(VALUE, -Double.MAX_VALUE, getMaxValue());
            fireChartEvent(AXIS_RANGE_CHANGED_EVT);
        } else {
            minValueProperty.set(VALUE);
        }
    }

    public DoubleProperty minValueProperty() {
        if (null == minValueProperty) {
            minValueProperty = new DoublePropertyBase(minValue_) {
                @Override
                protected void invalidated() {
                    if (getValue() > getMaxValue()) {
                        setMaxValue(getValue());
                    }
                    fireChartEvent(AXIS_RANGE_CHANGED_EVT);
                }

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "minValue";}
            };
        }
        return minValueProperty;
    }

    /**
     * @return start {@link LocalDateTime} to display
     */
    public LocalDateTime getStartTime() {
        return startTimeProperty == null ? startTime_ : startTimeProperty.get();
    }

    public void setStart(final long epochSeconds) {
        if (0 > epochSeconds) {
            throw new IllegalArgumentException("Epoch seconds cannot be smaller than 0");
        }
        setStart(Instant.ofEpochSecond(epochSeconds));
    }

    public void setStart(final long EPOCH_SECONDS, final ZoneId ZONE_ID) {
        if (0 > EPOCH_SECONDS || null == ZONE_ID) {
            throw new IllegalArgumentException("Epoch seconds cannot be smaller than 0 and zone id cannot be null");
        }
        setStart(Instant.ofEpochSecond(EPOCH_SECONDS), ZONE_ID);
    }

    public void setStart(final Instant INSTANT) {
        setStart(INSTANT, ZoneId.systemDefault());
    }

    public void setStart(final Instant instant, final ZoneId zoneId) {
        if (null == instant || null == zoneId) {
            throw new IllegalArgumentException("Instant cannot be null");
        }
        startTime(LocalDateTime.ofInstant(instant, zoneId));
    }

    /**
     * set the start time for time axis
     *
     * @param dateTime {@link LocalDateTime}
     */
    public void startTime(final LocalDateTime dateTime) {
        if (getType() != AxisType.TIME) {
            throw new IllegalArgumentException("Axis type has to be TIME");
        }
        if (null == startTimeProperty) {
            setMinValue(dateTime.toEpochSecond(TimeUtils.getZoneOffset()));
            startTime_ = dateTime;
        } else {
            startTimeProperty.set(dateTime);
        }
    }

    public ObjectProperty<LocalDateTime> startProperty() {
        if (startTimeProperty == null) {
            startTimeProperty = new ObjectPropertyBase<>(startTime_) {
                @Override
                protected void invalidated() {
                    if (getType() != AxisType.TIME) {
                        throw new IllegalArgumentException("Axis type has to be TIME");
                    }
                    setMinValue(get().toEpochSecond(TimeUtils.getZoneOffset()));
                }

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "start";}
            };
            startTime_ = null;
        }
        return startTimeProperty;
    }

    /**
     * @return max value of the axis
     */
    public double getMaxValue() {
        return maxValueProperty == null ? maxValue_ : maxValueProperty.get();
    }

    public void setMaxValue(final LocalDateTime END) {
        setMaxValue(END.toEpochSecond(TimeUtils.getZoneOffset(getZoneId())));
    }

    /**
     * set the max value represent by this axis
     *
     * @param value a value
     */
    public void setMaxValue(final double value) {
        if (maxValueProperty == null) {
            if (value < getMinValue()) {
                setMinValue(value);
            }
            maxValue_ = Math.clamp(value, getMinValue(), Double.MAX_VALUE);
            fireChartEvent(AXIS_RANGE_CHANGED_EVT);
        } else {
            maxValueProperty.set(value);
        }
    }

    public DoubleProperty maxValueProperty() {
        if (maxValueProperty == null) {
            maxValueProperty = new DoublePropertyBase(maxValue_) {
                @Override
                protected void invalidated() {
                    if (get() < getMinValue())
                        setMinValue(get());
                    fireChartEvent(AXIS_RANGE_CHANGED_EVT);
                }

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "maxValue";}
            };
        }
        return maxValueProperty;
    }

    /**
     * @return the end time to display
     */
    public LocalDateTime getEndTime() {
        return endTimeProperty == null ? endTime_ : endTimeProperty.get();
    }

    public void setEnd(final Instant INSTANT) {
        setEnd(INSTANT, ZoneId.systemDefault());
    }

    public void setEnd(final long EPOCH_SECONDS) {
        if (0 > EPOCH_SECONDS) {
            throw new IllegalArgumentException("Epoch seconds cannot be smaller than 0");
        }
        setEnd(Instant.ofEpochSecond(EPOCH_SECONDS));
    }

    public void setEnd(final long EPOCH_SECONDS, final ZoneId ZONE_ID) {
        if (0 > EPOCH_SECONDS || null == ZONE_ID) {
            throw new IllegalArgumentException("Epoch seconds cannot be smaller than 0 and zone id cannot be null");
        }
        setEnd(Instant.ofEpochSecond(EPOCH_SECONDS), ZONE_ID);
    }

    public void setEnd(final Instant INSTANT, final ZoneId ZONE_ID) {
        if (null == INSTANT || null == ZONE_ID) {
            throw new IllegalArgumentException("Instant cannot be null");
        }
        endTime(LocalDateTime.ofInstant(INSTANT, ZONE_ID));
    }

    /**
     * set the end time to dispaly
     *
     * @param DATE_TIME {@link LocalDateTime}
     */
    public void endTime(final LocalDateTime DATE_TIME) {
        if (null == endTimeProperty) {
            setMaxValue(DATE_TIME.toEpochSecond(TimeUtils.getZoneOffset()));
            endTime_ = DATE_TIME;
        } else {
            endTimeProperty.set(DATE_TIME);
        }
    }

    public ObjectProperty<LocalDateTime> endTimeProperty() {
        if (null == endTimeProperty) {
            endTimeProperty = new ObjectPropertyBase<>(endTime_) {
                @Override
                protected void invalidated() {
                    setMaxValue(get().toEpochSecond(TimeUtils.getZoneOffset()));
                }

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "end";}
            };
            endTime_ = null;
        }
        return endTimeProperty;
    }

    /**
     * Whether to set the scale of the axis automatically, default to be true
     *
     * @return true if set scale automatically
     */
    public boolean isAutoScale() {
        return autoScaleProperty == null ? autoScale_ : autoScaleProperty.get();
    }

    public void setAutoScale(final boolean AUTO_SCALE) {
        if (null == autoScaleProperty) {
            autoScale_ = AUTO_SCALE;
            redraw();
        } else {
            autoScaleProperty.set(AUTO_SCALE);
        }
    }

    public BooleanProperty autoScaleProperty() {
        if (null == autoScaleProperty) {
            autoScaleProperty = new BooleanPropertyBase(autoScale_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "autoScale";}
            };
        }
        return autoScaleProperty;
    }

    /**
     * @return axis title
     */
    public String getTitle() {return null == titleProperty ? title_ : titleProperty.get();}

    /**
     * set the axis title
     *
     * @param title new title
     */
    public void setTitle(final String title) {
        if (titleProperty == null) {
            title_ = title;
            redraw();
        } else {
            titleProperty.set(title);
        }
    }

    /**
     * @return title property
     */
    public StringProperty titleProperty() {
        if (titleProperty == null) {
            titleProperty = new StringPropertyBase(title_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "title";}
            };
            title_ = null;
        }
        return titleProperty;
    }

    public String getUnit() {return null == unitProperty ? _unit : unitProperty.get();}

    public void setUnit(final String UNIT) {
        if (null == unitProperty) {
            _unit = UNIT;
            redraw();
        } else {
            unitProperty.set(UNIT);
        }
    }

    public StringProperty unitProperty() {
        if (null == unitProperty) {
            unitProperty = new StringPropertyBase(_unit) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "unit";}
            };
            _unit = null;
        }
        return unitProperty;
    }

    public AxisType getType() {return null == typeProperty ? type_ : typeProperty.get();}

    /**
     * set the {@link AxisType}
     *
     * @param type {@link AxisType}
     */
    public void setType(final AxisType type) {
        if (null == typeProperty) {
            type_ = type;
            redraw();
        } else {
            typeProperty.set(type);
        }
    }

    public ObjectProperty<AxisType> typeProperty() {
        if (null == typeProperty) {
            typeProperty = new ObjectPropertyBase<AxisType>(type_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "axisType";}
            };
            type_ = null;
        }
        return typeProperty;
    }

    /**
     * Return the {@link Orientation} of this axis
     *
     * @return {@link Orientation}
     */
    public Orientation getOrientation() {
        return orientationProperty == null ? orientation_ : orientationProperty.get();
    }

    /**
     * set the {@link Orientation} of this axis
     *
     * @param orientation {@link Orientation}
     */
    public void setOrientation(final Orientation orientation) {
        if (null == orientationProperty) {
            orientation_ = orientation;
            redraw();
        } else {
            orientationProperty.set(orientation);
        }
    }

    /**
     * Get the {@code orientationProperty}
     *
     * @return orientation property
     */
    public ObjectProperty<Orientation> orientationProperty() {
        if (null == orientationProperty) {
            orientationProperty = new ObjectPropertyBase<>(orientation_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "orientation";}
            };
            orientation_ = null;
        }
        return orientationProperty;
    }

    public Position getPosition() {return null == positionProperty ? position_ : positionProperty.get();}

    public void setPosition(final Position POSITION) {
        if (null == positionProperty) {
            position_ = POSITION;
            redraw();
        } else {
            positionProperty.set(POSITION);
        }
    }

    public ObjectProperty<Position> positionProperty() {
        if (null == positionProperty) {
            positionProperty = new ObjectPropertyBase<>(position_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "position";}
            };
            position_ = null;
        }
        return positionProperty;
    }

    public void setForegroundColor(final Color COLOR) {
        setAxisColor(COLOR);
        setTickMarkColor(COLOR);
        setTickLabelColor(COLOR);
    }

    /**
     * Return the axis background color, which default to be {@link Color#TRANSPARENT}
     *
     * @return {@link Color}
     */
    public Color getAxisBackgroundColor() {
        return axisBackgroundColorProperty == null ? axisBackgroundColor_ : axisBackgroundColorProperty.get();
    }

    /**
     * set the axis background color
     *
     * @param color {@link Color}
     */
    public void setAxisBackgroundColor(final Color color) {
        if (axisBackgroundColorProperty == null) {
            axisBackgroundColor_ = color;
            redraw();
        } else {
            axisBackgroundColorProperty.set(color);
        }
    }

    /**
     * @return axis background property
     */
    public ObjectProperty<Color> axisBackgroundColorProperty() {
        if (axisBackgroundColorProperty == null) {
            axisBackgroundColorProperty = new ObjectPropertyBase<>(axisBackgroundColor_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "axisBackgroundColor";}
            };
            axisBackgroundColor_ = null;
        }
        return axisBackgroundColorProperty;
    }

    public Color getAxisColor() {return null == axisColorProperty ? _axisColor : axisColorProperty.get();}

    public void setAxisColor(final Color COLOR) {
        if (null == axisColorProperty) {
            _axisColor = COLOR;
            redraw();
        } else {
            axisColorProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> axisColorProperty() {
        if (null == axisColorProperty) {
            axisColorProperty = new ObjectPropertyBase<Color>(_axisColor) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "axisColor";}
            };
            _axisColor = null;
        }
        return axisColorProperty;
    }

    public Color getTickLabelColor() {return null == tickLabelColorProperty ? _tickLabelColor : tickLabelColorProperty.get();}

    public void setTickLabelColor(final Color COLOR) {
        if (null == tickLabelColorProperty) {
            _tickLabelColor = COLOR;
            redraw();
        } else {
            tickLabelColorProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> tickLabelColorProperty() {
        if (null == tickLabelColorProperty) {
            tickLabelColorProperty = new ObjectPropertyBase<Color>(_tickLabelColor) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "tickLabelColor";}
            };
            _tickLabelColor = null;
        }
        return tickLabelColorProperty;
    }

    public Color getTitleColor() {return null == titleColorProperty ? _titleColor : titleColorProperty.get();}

    public void setTitleColor(final Color COLOR) {
        if (null == titleColorProperty) {
            _titleColor = COLOR;
            redraw();
        } else {
            titleColorProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> titleColorProperty() {
        if (null == titleColorProperty) {
            titleColorProperty = new ObjectPropertyBase<Color>(_titleColor) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "titleColor";}
            };
            _titleColor = null;
        }
        return titleColorProperty;
    }

    public Color getMinorTickMarkColor() {return null == minorTickMarkColorProperty ? _minorTickMarkColor : minorTickMarkColorProperty.get();}

    public void setMinorTickMarkColor(final Color COLOR) {
        if (null == minorTickMarkColorProperty) {
            _minorTickMarkColor = COLOR;
            redraw();
        } else {
            minorTickMarkColorProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> minorTickMarkColorProperty() {
        if (null == minorTickMarkColorProperty) {
            minorTickMarkColorProperty = new ObjectPropertyBase<Color>(_minorTickMarkColor) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "minorTickMarkColor";}
            };
            _minorTickMarkColor = null;
        }
        return minorTickMarkColorProperty;
    }

    public Color getMediumTickMarkColor() {return null == mediumTickMarkColorProperty ? _mediumTickMarkColor : mediumTickMarkColorProperty.get();}

    public void setMediumTickMarkColor(final Color COLOR) {
        if (null == mediumTickMarkColorProperty) {
            _mediumTickMarkColor = COLOR;
            redraw();
        } else {
            mediumTickMarkColorProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> mediumTickMarkColorProperty() {
        if (null == mediumTickMarkColorProperty) {
            mediumTickMarkColorProperty = new ObjectPropertyBase<Color>(_mediumTickMarkColor) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "mediumTickMarkColor";}
            };
            _mediumTickMarkColor = null;
        }
        return mediumTickMarkColorProperty;
    }

    public Color getMajorTickMarkColor() {return null == majorTickMarkColor ? _majorTickMarkColor : majorTickMarkColor.get();}

    public void setMajorTickMarkColor(final Color COLOR) {
        if (null == majorTickMarkColor) {
            _majorTickMarkColor = COLOR;
            redraw();
        } else {
            majorTickMarkColor.set(COLOR);
        }
    }

    public ObjectProperty<Color> majorTickMarkColorProperty() {
        if (null == majorTickMarkColor) {
            majorTickMarkColor = new ObjectPropertyBase<Color>(_majorTickMarkColor) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "majorTickMarkColor";}
            };
            _majorTickMarkColor = null;
        }
        return majorTickMarkColor;
    }

    public Color getZeroColor() {return null == zeroColorProperty ? _zeroColor : zeroColorProperty.get();}

    public void setZeroColor(final Color COLOR) {
        if (null == zeroColorProperty) {
            _zeroColor = COLOR;
            redraw();
        } else {
            zeroColorProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> zeroColorProperty() {
        if (null == zeroColorProperty) {
            zeroColorProperty = new ObjectPropertyBase<Color>(_zeroColor) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "zeroColor";}
            };
            _zeroColor = null;
        }
        return zeroColorProperty;
    }

    public double getZeroPosition() {return null == zeroPositionProperty ? _zeroPosition : zeroPositionProperty.get();}

    private void setZeroPosition(final double POSITION) {
        if (null == zeroPositionProperty) {
            _zeroPosition = POSITION;
        } else {
            zeroPositionProperty.set(POSITION);
        }
    }

    public ReadOnlyDoubleProperty zeroPositionProperty() {
        if (null == zeroPositionProperty) {
            zeroPositionProperty = new DoublePropertyBase(_zeroPosition) {
                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "zeroPosition";}
            };
        }
        return zeroPositionProperty;
    }

    /**
     * @return the data space between two major ticks
     */
    protected double getMajorTickSpace() {return majorTickSpace_;}

    /**
     * set the major tick space
     *
     * @param SPACE delta value between two major ticks
     */
    protected void setMajorTickSpace(final double SPACE) {
        majorTickSpace_ = SPACE;
    }

    /**
     * @return the data space between two minor ticks
     */
    protected double getMinorTickSpace() {return minorTickSpace_;}

    protected void setMinorTickSpace(final double SPACE) {minorTickSpace_ = SPACE;}

    public boolean getMajorTickMarksVisible() {return null == majorTickMarksVisibleProperty ? majorTickMarksVisible_ : majorTickMarksVisibleProperty.get();}

    public void setMajorTickMarksVisible(final boolean VISIBLE) {
        if (null == majorTickMarksVisibleProperty) {
            majorTickMarksVisible_ = VISIBLE;
            redraw();
        } else {
            majorTickMarksVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty majorTickMarksVisibleProperty() {
        if (null == majorTickMarksVisibleProperty) {
            majorTickMarksVisibleProperty = new BooleanPropertyBase(majorTickMarksVisible_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "majorTickMarksVisible";}
            };
        }
        return majorTickMarksVisibleProperty;
    }

    public boolean getMediumTickMarksVisible() {return null == mediumTickMarksVisibleProperty ? mediumTickMarksVisible_ : mediumTickMarksVisibleProperty.get();}

    public void setMediumTickMarksVisible(final boolean VISIBLE) {
        if (null == mediumTickMarksVisibleProperty) {
            mediumTickMarksVisible_ = VISIBLE;
            redraw();
        } else {
            mediumTickMarksVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty mediumTickMarksVisibleProperty() {
        if (null == mediumTickMarksVisibleProperty) {
            mediumTickMarksVisibleProperty = new BooleanPropertyBase(mediumTickMarksVisible_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "mediumTickMarksVisible";}
            };
        }
        return mediumTickMarksVisibleProperty;
    }

    public boolean getMinorTickMarksVisible() {return null == minorTickMarksVisibleProperty ? _minorTickMarksVisible : minorTickMarksVisibleProperty.get();}

    public void setMinorTickMarksVisible(final boolean VISIBLE) {
        if (null == minorTickMarksVisibleProperty) {
            _minorTickMarksVisible = VISIBLE;
            redraw();
        } else {
            minorTickMarksVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty minorTickMarksVisibleProperty() {
        if (null == minorTickMarksVisibleProperty) {
            minorTickMarksVisibleProperty = new BooleanPropertyBase(_minorTickMarksVisible) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "minorTickMarksVisible";}
            };
        }
        return minorTickMarksVisibleProperty;
    }

    /**
     * Whether all tick marks the same length?
     *
     * @return true if all tick marks the same length
     */
    public boolean getSameTickMarkLength() {return null == sameTickMarkLengthProperty ? _sameTickMarkLength : sameTickMarkLengthProperty.get();}

    public void setSameTickMarkLength(final boolean sameLength) {
        if (null == sameTickMarkLengthProperty) {
            _sameTickMarkLength = sameLength;
            redraw();
        } else {
            sameTickMarkLengthProperty.set(sameLength);
        }
    }

    public BooleanProperty sameTickMarkLengthProperty() {
        if (null == sameTickMarkLengthProperty) {
            sameTickMarkLengthProperty = new BooleanPropertyBase(_sameTickMarkLength) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "sameTickMarkLength";}
            };
        }
        return sameTickMarkLengthProperty;
    }

    public boolean getTickLabelsVisible() {return null == tickLabelsVisibleProperty ? _tickLabelsVisible : tickLabelsVisibleProperty.get();}

    public void setTickLabelsVisible(final boolean VISIBLE) {
        if (null == tickLabelsVisibleProperty) {
            _tickLabelsVisible = VISIBLE;
            redraw();
        } else {
            tickLabelsVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty tickLabelsVisibleProperty() {
        if (null == tickLabelsVisibleProperty) {
            tickLabelsVisibleProperty = new BooleanPropertyBase(_tickLabelsVisible) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "tickLabelsVisible";}
            };
        }
        return tickLabelsVisibleProperty;
    }

    public boolean getMediumTimeAxisTickLabelsVisible() {return null == mediumTimeAxisTickLabelsVisibleProperty ? _mediumTimeAxisTickLabelsVisible : mediumTimeAxisTickLabelsVisibleProperty.get();}

    public void setMediumTimeAxisTickLabelsVisible(final boolean VISIBLE) {
        if (null == mediumTimeAxisTickLabelsVisibleProperty) {
            _mediumTimeAxisTickLabelsVisible = VISIBLE;
            redraw();
        } else {
            mediumTimeAxisTickLabelsVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty mediumTimeAxisTickLabelsVisibleProperty() {
        if (null == mediumTimeAxisTickLabelsVisibleProperty) {
            mediumTimeAxisTickLabelsVisibleProperty = new BooleanPropertyBase(_mediumTimeAxisTickLabelsVisible) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "mediumTickLabelsVisible";}
            };
        }
        return mediumTimeAxisTickLabelsVisibleProperty;
    }

    public boolean isOnlyFirstAndLastTickLabelVisible() {return null == onlyFirstAndLastTickLabelVisibleProperty ? _onlyFirstAndLastTickLabelVisible : onlyFirstAndLastTickLabelVisibleProperty.get();}

    public void setOnlyFirstAndLastTickLabelVisible(final boolean VISIBLE) {
        if (null == onlyFirstAndLastTickLabelVisibleProperty) {
            _onlyFirstAndLastTickLabelVisible = VISIBLE;
            redraw();
        } else {
            onlyFirstAndLastTickLabelVisibleProperty.set(VISIBLE);
        }
    }

    public BooleanProperty onlyFirstAndLastTickLabelVisibleProperty() {
        if (null == onlyFirstAndLastTickLabelVisibleProperty) {
            onlyFirstAndLastTickLabelVisibleProperty = new BooleanPropertyBase(_onlyFirstAndLastTickLabelVisible) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "onlyFirstAndLastTickLabelVisible";}
            };
        }
        return onlyFirstAndLastTickLabelVisibleProperty;
    }

    /**
     * @return {@link Locale} used to format numbers
     */
    public Locale getLocale() {
        return localeProperty == null ? locale_ : localeProperty.get();
    }

    public void setLocale(final Locale LOCALE) {
        if (localeProperty == null) {
            locale_ = LOCALE;
            tickLabelFormatString = "%." + getDecimals() + "f";
            redraw();
        } else {
            localeProperty.set(LOCALE);
        }
    }

    public ObjectProperty<Locale> localeProperty() {
        if (null == localeProperty) {
            localeProperty = new ObjectPropertyBase<>(locale_) {
                @Override
                protected void invalidated() {
                    tickLabelFormatString = "%." + getDecimals() + "f";
                    redraw();
                }

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "locale";}
            };
            locale_ = null;
        }
        return localeProperty;
    }

    /**
     * @return the number of decimal for tick labels
     */
    public int getDecimals() {
        return decimalsProperty == null ? decimals_ : decimalsProperty.get();
    }

    public void setDecimals(final int DECIMALS) {
        if (decimalsProperty == null) {
            decimals_ = Math.clamp(DECIMALS, 0, 12);
            tickLabelFormatString = "%." + decimals_ + "f";
            redraw();
        } else {
            decimalsProperty.set(DECIMALS);
        }
    }

    public IntegerProperty decimals() {
        if (null == decimalsProperty) {
            decimalsProperty = new IntegerPropertyBase(decimals_) {
                @Override
                protected void invalidated() {
                    set(Math.clamp(get(), 0, 12));
                    tickLabelFormatString = "%." + get() + "f";
                    redraw();
                }

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "decimals";}
            };
        }
        return decimalsProperty;
    }

    public TickLabelOrientation getTickLabelOrientation() {return null == tickLabelOrientationProperty ? _tickLabelOrientation : tickLabelOrientationProperty.get();}

    public void setTickLabelOrientation(final TickLabelOrientation ORIENTATION) {
        if (null == tickLabelOrientationProperty) {
            _tickLabelOrientation = ORIENTATION;
            redraw();
        } else {
            tickLabelOrientationProperty.set(ORIENTATION);
        }
    }

    public ObjectProperty<TickLabelOrientation> tickLabelOrientationProperty() {
        if (null == tickLabelOrientationProperty) {
            tickLabelOrientationProperty = new ObjectPropertyBase<>(_tickLabelOrientation) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "tickLabelOrientation";}
            };
            _tickLabelOrientation = null;
        }
        return tickLabelOrientationProperty;
    }

    public ZoneId getZoneId() {return null == zoneIdProperty ? _zoneId : zoneIdProperty.get();}

    public void setZoneId(final ZoneId ZONE_ID) {
        if (null == zoneIdProperty) {
            _zoneId = ZONE_ID;
            redraw();
        } else {
            zoneIdProperty.set(ZONE_ID);
        }
    }

    public ObjectProperty<ZoneId> zoneIdProperty() {
        if (null == zoneIdProperty) {
            zoneIdProperty = new ObjectPropertyBase<ZoneId>(_zoneId) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "zoneId";}
            };
            _zoneId = null;
        }
        return zoneIdProperty;
    }

    public String getDateTimeFormatPattern() {return null == dateTimeFormatPatternProperty ? _dateTimeFormatPattern : dateTimeFormatPatternProperty.get();}

    public void setDateTimeFormatPattern(final String PATTERN) {
        if (null == dateTimeFormatPatternProperty) {
            _dateTimeFormatPattern = PATTERN;
            dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN);
            redraw();
        } else {
            dateTimeFormatPatternProperty.set(PATTERN);
        }
    }

    public StringProperty dateTimeFormatPatternProperty() {
        if (null == dateTimeFormatPatternProperty) {
            dateTimeFormatPatternProperty = new StringPropertyBase(_dateTimeFormatPattern) {
                @Override
                protected void invalidated() {
                    dateTimeFormatter = DateTimeFormatter.ofPattern(get());
                    redraw();
                }

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "dateTimeFormat";}
            };
            _dateTimeFormatPattern = null;
        }
        return dateTimeFormatPatternProperty;
    }

    public StringConverter<Number> getNumberFormatter() {return numberFormatter;}

    public void setNumberFormatter(final StringConverter<Number> FORMATTER) {
        numberFormatter = FORMATTER;
        redraw();
    }

    public TickLabelFormat getTickLabelFormat() {return null == tickLabelFormat ? _tickLabelFormat : tickLabelFormat.get();}

    public void setTickLabelFormat(final TickLabelFormat FORMAT) {
        if (null == tickLabelFormat) {
            _tickLabelFormat = FORMAT;
            redraw();
        } else {
            tickLabelFormat.set(FORMAT);
        }
    }

    public ObjectProperty<TickLabelFormat> tickLabelFormatProperty() {
        if (null == tickLabelFormat) {
            tickLabelFormat = new ObjectPropertyBase<TickLabelFormat>(_tickLabelFormat) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "tickLabelFormat";}
            };
            _tickLabelFormat = null;
        }
        return tickLabelFormat;
    }

    /**
     * Whether to automatically adjust the title font size
     *
     * @return true if adjust title font size automatically
     */
    public boolean isAutoTitleFontSize() {return null == autoTitleFontSizeProperty ? autoTitleFontSize_ : autoTitleFontSizeProperty.get();}

    public void setAutoTitleFontSize(final boolean auto) {
        if (null == autoTitleFontSizeProperty) {
            autoTitleFontSize_ = auto;
            redraw();
        } else {
            autoTitleFontSizeProperty.set(auto);
        }
    }

    public BooleanProperty autoTitleFontSizeProperty() {
        if (null == autoTitleFontSizeProperty) {
            autoTitleFontSizeProperty = new BooleanPropertyBase(autoTitleFontSize_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "autoTitleFontSize";}
            };
        }
        return autoTitleFontSizeProperty;
    }

    public boolean isAutoTickLabelFontSize() {
        return autoTickLabelFontSizeProperty == null ? autoTickLabelFontSize_ : autoTickLabelFontSizeProperty.get();
    }

    public void setAutoTickLabelFontSize(final boolean auto) {
        if (null == autoTickLabelFontSizeProperty) {
            autoTickLabelFontSize_ = auto;
            redraw();
        } else {
            autoTickLabelFontSizeProperty.set(auto);
        }
    }

    public BooleanProperty autoTickLabelFontSizeProperty() {
        if (null == autoTickLabelFontSizeProperty) {
            autoTickLabelFontSizeProperty = new BooleanPropertyBase(autoTickLabelFontSize_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return Axis.this;}

                @Override
                public String getName() {return "autoFontSize";}
            };
        }
        return autoTickLabelFontSizeProperty;
    }

    /**
     * Returns the font used for the tick labels (if showing)
     *
     * @return The font
     */
    public Font getTickLabelFont() {
        return tickLabelFontProperty == null ? tickLabelFont_ : tickLabelFontProperty.get();
    }

    public void setTickLabelFont(final Font font) {
        checkNotNull(font);
        if (tickLabelFontProperty == null) {
            tickLabelFont_ = font;
            redraw();
        } else {
            tickLabelFontProperty.set(font);
        }
    }

    /**
     * @return tick label font property
     */
    public ObjectProperty<Font> tickLabelFontProperty() {
        if (tickLabelFontProperty == null) {
            tickLabelFontProperty = new ObjectPropertyBase<>(tickLabelFont_) {
                @Override
                protected void invalidated() {
                    redraw();
                }

                @Override
                public Object getBean() {
                    return Axis.this;
                }

                @Override
                public String getName() {
                    return "tickLabelFont";
                }
            };
            tickLabelFont_ = null;
        }
        return tickLabelFontProperty;
    }

    /**
     * @return tick label font size
     */
    public double getTickLabelFontSize() {
        return getTickLabelFont().getSize();
    }

    /**
     * set the tick label font
     *
     * @param size new size
     */
    public void setTickLabelFontSize(final double size) {
        Font oldFont = getTickLabelFont();
        Font newFont = Font.font(oldFont.getFamily(), size);
        setTickLabelFont(newFont);
    }

    /**
     * Return the title-font size
     *
     * @return font-size
     */
    public double getTitleFontSize() {
        return getTitleFont().getSize();
    }

    public void setTitleFontSize(final double size) {
        Font oldFont = getTitleFont();
        Font newFont = Font.font(oldFont.getFamily(), size);
        setTitleFont(newFont);
    }

    /**
     * Return the title-font
     *
     * @return {@link Font}
     */
    public Font getTitleFont() {
        return titleFontProperty == null ? titleFont_ : titleFontProperty.get();
    }

    /**
     * set the title-font
     *
     * @param font {@link Font}
     */
    public void setTitleFont(Font font) {
        if (titleFontProperty == null) {
            titleFont_ = font;
            redraw();
        } else {
            titleFontProperty.set(font);
        }
    }

    /**
     * Return the title-font property
     *
     * @return {@link ObjectProperty}
     */
    public ObjectProperty<Font> titleFontProperty() {
        if (titleFontProperty == null) {
            titleFontProperty = new ObjectPropertyBase<>(titleFont_) {
                @Override
                protected void invalidated() {
                    redraw();
                }

                @Override
                public Object getBean() {
                    return Axis.this;
                }

                @Override
                public String getName() {
                    return "titleFont";
                }
            };
        }
        return titleFontProperty;
    }

    public List<String> getCategories() {return categories;}

    public void setCategories(final String... CATEGORIES) {setCategories(Arrays.asList(CATEGORIES));}

    public void setCategories(final List<String> CATEGORIES) {
        categories.clear();
        CATEGORIES.forEach(category -> categories.add(category));
        redraw();
    }

    public boolean isValueOnAxis(final Double VALUE) {
        return Double.compare(VALUE, getMinValue()) >= 0 && Double.compare(VALUE, getMaxValue()) <= 0;
    }

    public boolean isValueOnAxis(final LocalDateTime DATE_TIME) {
        return DATE_TIME.isAfter(getStartTime()) && DATE_TIME.isBefore(getEndTime());
    }

    public void setMinMax(final double MIN_VALUE, final double MAX_VALUE) {
        setMinValue(MIN_VALUE);
        setMaxValue(MAX_VALUE);
        resize();
    }

    public void setStartEnd(final LocalDateTime start, final LocalDateTime end) {
        startTime(start);
        endTime(end);
        resize();
    }

    public void setStartEnd(final Instant start, final Instant end) {
        setStart(start);
        setEnd(end);
        resize();
    }

    public void setStartEnd(final Instant start, final Instant end, final ZoneId zoneId) {
        setStart(start, zoneId);
        setEnd(end, zoneId);
        resize();
    }

    public void setStartEnd(final long startEpochSeconds, final long endEpochSeconds, final ZoneId zoneId) {
        if (startEpochSeconds < Instant.MIN.getEpochSecond()) {
            throw new IllegalArgumentException("Start cannot be before " + Instant.MIN.getEpochSecond());
        }
        if (endEpochSeconds > Instant.MAX.getEpochSecond()) {
            throw new IllegalArgumentException("End cannot be after " + Instant.MAX.getEpochSecond());
        }
        if (startEpochSeconds > endEpochSeconds) {
            throw new IllegalArgumentException("Start cannot be after end");
        }
        if (endEpochSeconds < startEpochSeconds) {
            throw new IllegalArgumentException("End cannot be before start");
        }
        setStart(startEpochSeconds, zoneId);
        setEnd(endEpochSeconds, zoneId);
        resize();
    }

    /**
     * Return the range of values represented by this axis
     *
     * @return value range
     */
    public double getRange() {return getMaxValue() - getMinValue();}

    public void setTickMarkColor(final Color COLOR) {
        setMinorTickMarkColor(COLOR);
        setMediumTickMarkColor(COLOR);
        setMajorTickMarkColor(COLOR);
    }

    public void setTickMarksVisible(final boolean VISIBLE) {
        setMinorTickMarksVisible(VISIBLE);
        setMediumTickMarksVisible(VISIBLE);
        setMajorTickMarksVisible(VISIBLE);
    }

    public void shift(final double VALUE) {
        setMinMax(getMinValue() + VALUE, getMaxValue() + VALUE);
    }

    public double getValueForDisplay(final double posInAxis) {
        return posInAxis / width * Helper.calcNiceNumber((getMaxValue() - getMinValue()), false) + getMinValue();
    }

    /**
     * pixel per data unit
     *
     * @return step size
     */
    public double getStepSize() {return stepSize;}

    public Bounds getAxisBounds() {return axisBounds_;}

    public void dispose() {
        removeChartEvtObserver(ChartEvent.AXIS_RANGE_CHANGED, eventListener_);
    }

    /**
     * Calculate the major and minor tick spaces and min and max value
     */
    private void calcAutoScale() {
        double maxNoOfMajorTicks = 10;
        double maxNoOfMinorTicks = 10;
        double niceRange = Helper.calcNiceNumber((getMaxValue() - getMinValue()), false);
        setMajorTickSpace(Helper.calcNiceNumber(niceRange / (maxNoOfMajorTicks - 1), true));
        setMinorTickSpace(Helper.calcNiceNumber(getMajorTickSpace() / (maxNoOfMinorTicks - 1), true));
        double niceMinValue = (Math.floor(getMinValue() / getMajorTickSpace()) * getMajorTickSpace());
        double niceMaxValue = (Math.ceil(getMaxValue() / getMajorTickSpace()) * getMajorTickSpace());

        setMinValue(niceMinValue);
        setMaxValue(niceMaxValue);
    }

    private void calcScale() {
        double maxNoOfMajorTicks = 10;
        double maxNoOfMinorTicks = 10;
        // Only set major and minor tickspace if they are at their default values of 10 and 1
        if (getMajorTickSpace() == 10) {
            setMajorTickSpace(Helper.calcNiceNumber(getRange() / (maxNoOfMajorTicks - 1), false));
        }
        if (getMinorTickSpace() == 1) {
            setMinorTickSpace(Helper.calcNiceNumber(getMajorTickSpace() / (maxNoOfMinorTicks - 1), false));
        }
    }

    /**
     * Calculate the width of text in a specified font
     *
     * @param font  {@link Font}
     * @param aText text
     * @return text width
     */
    private double calcTextWidth(final Font font, final String aText) {
        Text text = new Text(aText);
        text.setFont(font);
        return text.getBoundsInParent().getWidth();
    }

    private LocalDateTime toLocalDateTime(final long SECONDS) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(SECONDS), ZoneId.systemDefault());
    }

    private List<LocalDateTime> createTickValues(final double WIDTH, final LocalDateTime START, final LocalDateTime END) {
        List<LocalDateTime> dateList = new ArrayList<>();
        LocalDateTime dateTime = LocalDateTime.now();

        if (null == START || null == END) return dateList;

        // The preferred gap which should be between two tick marks.
        double majorTickSpace = 100;
        double noOfTicks = WIDTH / majorTickSpace;

        List<LocalDateTime> previousDateList = new ArrayList<>();
        Interval previousInterval = Interval.values()[0];

        // Starting with the greatest interval, add one of each dateTime unit.
        for (Interval interval : Interval.values()) {
            // Reset the dateTime.
            dateTime = LocalDateTime.of(START.toLocalDate(), START.toLocalTime());
            // Clear the list.
            dateList.clear();
            previousDateList.clear();
            currentInterval = interval;

            // Loop as long we exceeded the END bound.
            while (dateTime.isBefore(END)) {
                dateList.add(dateTime);
                dateTime = dateTime.plus(interval.getAmount(), interval.getInterval());
            }

            // Then check the size of the list. If it is greater than the amount of ticks, take that list.
            if (dateList.size() > noOfTicks) {
                dateTime = LocalDateTime.of(START.toLocalDate(), START.toLocalTime());
                // Recheck if the previous interval is better suited.
                while (dateTime.isBefore(END) || dateTime.isEqual(END)) {
                    previousDateList.add(dateTime);
                    dateTime = dateTime.plus(previousInterval.getAmount(), previousInterval.getInterval());
                }
                break;
            }

            previousInterval = interval;
        }
        if (previousDateList.size() - noOfTicks > noOfTicks - dateList.size()) {
            dateList = previousDateList;
            currentInterval = previousInterval;
        }

        // At last add the END bound.
        dateList.add(END);

        List<LocalDateTime> evenDateList = makeDatesEven(dateList);
        // If there are at least three dates, check if the gap between the START date and the second date is at least half the gap of the second and third date.
        // Do the same for the END bound.
        // If gaps between dates are to small, remove one of them.
        // This can occur, e.g. if the START bound is 25.12.2013 and years are shown. Then the next year shown would be 2014 (01.01.2014) which would be too narrow to 25.12.2013.
        if (evenDateList.size() > 2) {
            LocalDateTime secondDate = evenDateList.get(1);
            LocalDateTime thirdDate = evenDateList.get(2);
            LocalDateTime lastDate = evenDateList.get(dateList.size() - 2);
            LocalDateTime previousLastDate = evenDateList.get(dateList.size() - 3);

            // If the second date is too near by the START bound, remove it.
            if (secondDate.toEpochSecond(ZoneOffset.ofHours(0)) - START.toEpochSecond(ZoneOffset.ofHours(0)) < thirdDate.toEpochSecond(ZoneOffset.ofHours(0)) - secondDate.toEpochSecond(ZoneOffset.ofHours(0))) {
                evenDateList.remove(secondDate);
            }

            // If difference from the END bound to the last date is less than the half of the difference of the previous two dates,
            // we better remove the last date, as it comes to close to the END bound.
            if (END.toEpochSecond(ZoneOffset.ofHours(0)) - lastDate.toEpochSecond(ZoneOffset.ofHours(0)) < ((lastDate.toEpochSecond(ZoneOffset.ofHours(0)) - previousLastDate.toEpochSecond(ZoneOffset.ofHours(0)) * 0.5))) {
                evenDateList.remove(lastDate);
            }
        }
        return evenDateList;
    }

    private List<LocalDateTime> makeDatesEven(List<LocalDateTime> dates) {
        // If the dates contain more dates than just the lower and upper bounds, make the dates in between even.
        if (dates.size() > 2) {
            List<LocalDateTime> evenDates = new ArrayList<>();
            LocalDateTime dateTime;
            // For each interval, modify the date slightly by a few millis, to make sure they are different days.
            // This is because Axis stores each value and won't update the tick labels, if the value is already known.
            // This happens if you display days and then add a date many years in the future the tick label will still be displayed as day.
            for (int i = 0; i < dates.size(); i++) {
                dateTime = dates.get(i);
                switch (currentInterval.getInterval()) {
                    case YEARS:
                        // If its not the first or last date (lower and upper bound), make the year begin with first month and let the months begin with first day.
                        if (i != 0 && i != dates.size() - 1) {
                            dateTime.withMonth(1);
                            dateTime.withDayOfMonth(1);
                        }
                        dateTime.withHour(0);
                        dateTime.withMinute(0);
                        dateTime.withSecond(0);
                        dateTime.withNano(6000000);
                        break;
                    case MONTHS:
                        // If its not the first or last date (lower and upper bound), make the months begin with first day.
                        if (i != 0 && i != dates.size() - 1) {
                            dateTime.withDayOfMonth(1);
                        }
                        dateTime.withHour(0);
                        dateTime.withMinute(0);
                        dateTime.withSecond(0);
                        dateTime.withNano(5000000);
                        break;
                    case WEEKS:
                        // Make weeks begin with first day of week?
                        dateTime.withHour(0);
                        dateTime.withMinute(0);
                        dateTime.withSecond(0);
                        dateTime.withNano(4000000);
                        break;
                    case DAYS:
                        dateTime.withHour(0);
                        dateTime.withMinute(0);
                        dateTime.withSecond(0);
                        dateTime.withNano(3000000);
                        break;
                    case HOURS:
                        if (i != 0 && i != dates.size() - 1) {
                            dateTime.withMinute(0);
                            dateTime.withSecond(0);
                        }
                        dateTime.withNano(2000000);
                        break;
                    case MINUTES:
                        if (i != 0 && i != dates.size() - 1) {
                            dateTime.withSecond(0);
                        }
                        dateTime.withNano(1000000);
                        break;
                    case SECONDS:
                        dateTime.withSecond(0);
                        break;

                }
                evenDates.add(dateTime);
            }

            return evenDates;
        } else {
            return dates;
        }
    }

    private String formatNumber(final Locale locale, final double number) {
        if (numberFormatter == null) {
            return String.format(locale, tickLabelFormatString, number);
        } else {
            return numberFormatter.toString(number);
        }
    }

    /**
     * add a {@link ChartEventListener} of given {@link EventType}
     *
     * @param type     {@link EventType}
     * @param listener {@link ChartEventListener}
     */
    public void addChartEventListener(final EventType<ChartEvent> type, final ChartEventListener<ChartEvent> listener) {
        if (!listeners.containsKey(type)) {
            listeners.put(type, new CopyOnWriteArrayList<>());
        }
        if (listeners.get(type).contains(listener)) {
            return;
        }
        listeners.get(type).add(listener);
    }

    public void removeChartEvtObserver(final EventType type, final ChartEventListener<ChartEvent> observer) {
        if (listeners.containsKey(type)) {
            if (listeners.get(type).contains(observer)) {
                listeners.get(type).remove(observer);
            }
        }
    }

    /**
     * remove all {@link ChartEventListener}
     */
    public void removeAllChartEventListeners() {
        listeners.clear();
    }

    /**
     * fire a given {@link ChartEvent}
     *
     * @param evt {@link ChartEvent}
     */
    public void fireChartEvent(final ChartEvent evt) {
        final EventType type = evt.getEventType();
        listeners.entrySet().stream()
                .filter(entry -> entry.getKey().equals(ChartEvent.ANY))
                .forEach(entry -> entry.getValue().forEach(observer -> observer.handle(evt)));
        if (listeners.containsKey(type) && !type.equals(ChartEvent.ANY)) {
            listeners.get(type).forEach(observer -> observer.handle(evt));
        }
    }

    /**
     * perform the rendering work
     */
    private void drawAxis() {
        if (Double.compare(stepSize, 0) <= 0) {
            return;
        }

        stepSize = getOrientation() == VERTICAL ? Math.abs(height / getRange()) : Math.abs(width / getRange());
        double maxNoOfMajorTicks = 10;
        double maxNoOfMinorTicks = 10;
        if (isAutoScale()) {
            double niceRange = (Helper.calcNiceNumber((getMaxValue() - getMinValue()), false));
            setMajorTickSpace(Helper.calcNiceNumber(niceRange / (maxNoOfMajorTicks - 1), true));
            setMinorTickSpace(Helper.calcNiceNumber(getMajorTickSpace() / (maxNoOfMinorTicks - 1), true));
        } else {
            // Only set major and minor tick space if they are at their default values of 10 and 1
            if (getMajorTickSpace() == 10) {
                setMajorTickSpace(Helper.calcNiceNumber(getRange() / (maxNoOfMajorTicks - 1), false));
            }
            if (getMinorTickSpace() == 1) {
                setMinorTickSpace(Helper.calcNiceNumber(getMajorTickSpace() / (maxNoOfMinorTicks - 1), false));
            }
        }

        axisGC_.clearRect(0, 0, width, height);
        axisGC_.setFill(getAxisBackgroundColor());
        axisGC_.fillRect(0, 0, width, height);
        axisGC_.setFont(getTickLabelFont());
        axisGC_.setTextBaseline(VPos.CENTER);

        AxisType axisType = getType();
        boolean isAutoScale = isAutoScale();
        double minValue = getMinValue();
        double maxValue = getMaxValue();
        boolean tickLabelsVisible = getTickLabelsVisible();
        boolean isOnlyFirstAndLastTickLabelVisible = isOnlyFirstAndLastTickLabelVisible();
        double tickLabelFontSize = getTickLabelFontSize();
        TickLabelFormat tickLabelFormat = getTickLabelFormat();
        Color tickLabelColor = getTickLabelColor();
        Color zeroColor = getZeroColor();
        Color majorTickMarkColor = getMajorTickMarkColor();
        boolean majorTickMarksVisible = getMajorTickMarksVisible();
        Color mediumTickMarkColor = getMediumTickMarkColor();
        boolean mediumTickMarksVisible = getMediumTickMarksVisible();
        Color minorTickMarkColor = getMinorTickMarkColor();
        boolean minorTickMarksVisible = getMinorTickMarksVisible();
        boolean sameTickMarkLength = getSameTickMarkLength();
        double majorLineWidth = size * 0.007 < MIN_MAJOR_LINE_WIDTH ? MIN_MAJOR_LINE_WIDTH : size * 0.007;
        double mediumLineWidth = size * 0.006 < MIN_MEDIUM_LINE_WIDTH ? MIN_MEDIUM_LINE_WIDTH : size * 0.005;
        double minorLineWidth = size * 0.005 < MIN_MINOR_LINE_WIDTH ? MIN_MINOR_LINE_WIDTH : size * 0.003;
        double maxMajorTickMarkLength;
        double maxMediumTickMarkLength;
        double maxMinorTickMarkLength;
        double textPosition;
        double minPosition;
        double maxPosition;
        if (VERTICAL == getOrientation()) {
            minPosition = 0;
            maxPosition = height;
            textPosition = width * 0.3;
            maxMajorTickMarkLength = sameTickMarkLength ? width * 0.175 : width * 0.2;
            maxMediumTickMarkLength = width * 0.175;
            maxMinorTickMarkLength = sameTickMarkLength ? width * 0.175 : width * 0.1;
        } else {
            minPosition = 0;
            maxPosition = width;
            textPosition = height * 0.5;
            maxMajorTickMarkLength = sameTickMarkLength ? height * 0.175 : height * 0.2;
            maxMediumTickMarkLength = height * 0.175;
            maxMinorTickMarkLength = sameTickMarkLength ? height * 0.175 : height * 0.1;
        }

        Locale locale = getLocale();
        Orientation orientation = getOrientation();
        Position position = getPosition();
        double anchorX = (position == Position.LEFT || position == Position.CENTER) ? 0 : getZeroPosition();
        double anchorXPlusOffset = anchorX + width;
        double anchorY = (position == Position.BOTTOM || position == Position.CENTER) ? 0 : getZeroPosition();
        double anchorYPlusOffset = anchorY + height;
        boolean isMinValue;
        boolean isZero;
        boolean isMaxValue;
        double innerPointX;
        double innerPointY;
        double outerPointX;
        double outerPointY;
        double mediumPointX;
        double mediumPointY;
        double minorPointX;
        double minorPointY;
        double textPointX;
        double textPointY;
        double maxTextWidth;

        if (position == Position.RIGHT) {
            anchorX = 0;
        }

        if (axisType == AxisType.LINEAR || axisType == AxisType.TEXT) {
            // ******************** Linear ************************************
            boolean fullRange = (minValue < 0 && maxValue > 0);
            double minorTickSpace = getMinorTickSpace();
            double majorTickSpace = getMajorTickSpace();
            double tmpStepSize = minorTickSpace;
            BigDecimal minorTickSpaceBD = BigDecimal.valueOf(minorTickSpace);
            BigDecimal majorTickSpaceBD = BigDecimal.valueOf(majorTickSpace);
            BigDecimal mediumCheck2 = BigDecimal.valueOf(2 * minorTickSpace);
            BigDecimal mediumCheck5 = BigDecimal.valueOf(5 * minorTickSpace);
            BigDecimal counterBD = BigDecimal.valueOf(minValue);
            double counter = minValue;
            double range = getRange();
            int noOfCategories = categories.size();

            axisGC_.setStroke(getAxisColor());
            axisGC_.setLineWidth(majorLineWidth);

            // Draw axis
            if (orientation == VERTICAL) {
                switch (position) {
                    case LEFT:
                        axisGC_.strokeLine(anchorXPlusOffset, minPosition, anchorXPlusOffset, maxPosition);
                        break;
                    case RIGHT:
                        axisGC_.strokeLine(anchorX, minPosition, anchorX, maxPosition);
                        break;
                    default:
                        axisGC_.strokeLine(anchorX, minPosition, anchorX, maxPosition);
                        break;
                }
            } else {
                switch (position) {
                    case BOTTOM:
                        axisGC_.strokeLine(minPosition, anchorY, maxPosition, anchorY);
                        break;
                    case TOP:
                        axisGC_.strokeLine(minPosition, anchorYPlusOffset, maxPosition, anchorYPlusOffset);
                        break;
                    default:
                        axisGC_.strokeLine(minPosition, anchorY, maxPosition, anchorY);
                        break;
                }
            }

            // Main Loop for tick marks and labels
            BigDecimal tmpStepBD = new BigDecimal(tmpStepSize);
            tmpStepBD = tmpStepBD.setScale(6, RoundingMode.HALF_UP); // newScale == number of decimals taken into account
            double tmpStep = tmpStepBD.doubleValue();
            int tickMarkCounter = 0;
            int tickLabelCounter = 0;
            for (double i = 0; Double.compare(-range - tmpStep, i) <= 0; i -= tmpStep) {
                double fixedPosition = (counter - minValue) * stepSize;
                if (VERTICAL == orientation) {
                    if (Position.LEFT == position) {
                        innerPointX = anchorXPlusOffset - maxMajorTickMarkLength;
                        innerPointY = fixedPosition;
                        mediumPointX = anchorXPlusOffset - maxMediumTickMarkLength;
                        mediumPointY = fixedPosition;
                        minorPointX = anchorXPlusOffset - maxMinorTickMarkLength;
                        minorPointY = fixedPosition;
                        outerPointX = anchorXPlusOffset;
                        outerPointY = fixedPosition;
                        textPointX = anchorXPlusOffset - textPosition;
                        textPointY = fixedPosition;
                        maxTextWidth = 0.6 * width;
                    } else if (Position.RIGHT == position) {
                        innerPointX = anchorX + maxMajorTickMarkLength;
                        innerPointY = fixedPosition;
                        mediumPointX = anchorX + maxMediumTickMarkLength;
                        mediumPointY = fixedPosition;
                        minorPointX = anchorX + maxMinorTickMarkLength;
                        minorPointY = fixedPosition;
                        outerPointX = anchorX;
                        outerPointY = fixedPosition;
                        textPointX = width;
                        textPointY = fixedPosition;
                        maxTextWidth = textPosition;
                    } else {
                        innerPointX = anchorX + maxMajorTickMarkLength;
                        innerPointY = fixedPosition;
                        mediumPointX = anchorX + maxMediumTickMarkLength;
                        mediumPointY = fixedPosition;
                        minorPointX = anchorX + maxMinorTickMarkLength;
                        minorPointY = fixedPosition;
                        outerPointX = anchorX;
                        outerPointY = fixedPosition;
                        textPointX = anchorXPlusOffset;
                        textPointY = fixedPosition;
                        maxTextWidth = textPosition;
                    }
                } else {
                    if (Position.BOTTOM == position) {
                        innerPointX = fixedPosition;
                        innerPointY = anchorY + maxMajorTickMarkLength;
                        mediumPointX = fixedPosition;
                        mediumPointY = anchorY + maxMediumTickMarkLength;
                        minorPointX = fixedPosition;
                        minorPointY = anchorY + maxMinorTickMarkLength;
                        outerPointX = fixedPosition;
                        outerPointY = anchorY;
                        textPointX = fixedPosition;
                        textPointY = innerPointY + textPosition - tickLabelFontSize * 0.8;
                        maxTextWidth = majorTickSpace * stepSize;
                    } else if (Position.TOP == position) {
                        innerPointX = fixedPosition;
                        innerPointY = anchorYPlusOffset - maxMajorTickMarkLength;
                        mediumPointX = fixedPosition;
                        mediumPointY = anchorYPlusOffset - maxMediumTickMarkLength;
                        minorPointX = fixedPosition;
                        minorPointY = anchorYPlusOffset - maxMinorTickMarkLength;
                        outerPointX = fixedPosition;
                        outerPointY = anchorYPlusOffset;
                        textPointX = fixedPosition;
                        textPointY = innerPointY - textPosition + tickLabelFontSize * 0.5;
                        maxTextWidth = majorTickSpace * stepSize;
                    } else {
                        innerPointX = fixedPosition;
                        innerPointY = anchorY + maxMajorTickMarkLength;
                        mediumPointX = fixedPosition;
                        mediumPointY = anchorY + maxMediumTickMarkLength;
                        minorPointX = fixedPosition;
                        minorPointY = anchorY + maxMinorTickMarkLength;
                        outerPointX = fixedPosition;
                        outerPointY = anchorY;
                        textPointX = fixedPosition;
                        textPointY = innerPointY + textPosition - tickLabelFontSize * 0.8;
                        maxTextWidth = majorTickSpace * stepSize;
                    }
                }

                if (Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(majorTickSpaceBD).doubleValue(), 0.0) == 0) {
                    // Draw major tick mark
                    isMinValue = Double.compare(minValue, counter) == 0;
                    isMaxValue = Double.compare(maxValue, counter) == 0;
                    if (VERTICAL == orientation) {
                        isZero = Double.compare(0.0, maxValue - counter + minValue) == 0;
                    } else {
                        isZero = Double.compare(0.0, counter) == 0;
                    }

                    if (isZero) {
                        setZeroPosition(fixedPosition);
                    }

                    if (majorTickMarksVisible) {
                        drawTickMark((fullRange && isZero) ? zeroColor : majorTickMarkColor, majorLineWidth, innerPointX, innerPointY, outerPointX, outerPointY);
                    } else if (minorTickMarksVisible) {
                        drawTickMark((fullRange && isZero) ? zeroColor : minorTickMarkColor, minorLineWidth, minorPointX, minorPointY, outerPointX, outerPointY);
                    }

                    // Draw tick labels
                    if (tickLabelsVisible && tickLabelFontSize > 6) {
                        String tickLabelString;
                        if (AxisType.LINEAR == axisType) {
                            if (TickLabelFormat.NUMBER == tickLabelFormat) {
                                tickLabelString = orientation == Orientation.HORIZONTAL ? formatNumber(locale, (minValue - i)) : formatNumber(locale, maxValue - counter + minValue);
                            } else {
                                tickLabelString = Orientation.HORIZONTAL == orientation ? TimeUtils.secondsToHHMMString(TimeUtils.toSeconds(TimeUtils.toDateTime((long) (minValue - i)), TimeUtils.getZoneOffset())) : formatNumber(locale, maxValue - counter + minValue);
                            }
                        } else if (AxisType.TEXT == axisType) {
                            if (tickLabelCounter < noOfCategories) {
                                tickLabelString = categories.get(tickLabelCounter);
                            } else {
                                tickLabelString = "";
                            }
                            if (isAutoScale) {
                                tickLabelCounter += (int) majorTickSpace;
                            } else {
                                tickLabelCounter++;
                            }
                        } else {
                            // Date Axis
                            tickLabelString = dateTimeFormatter.format(toLocalDateTime((long) (minValue - i) * 1000));
                        }
                        drawTickLabel(isOnlyFirstAndLastTickLabelVisible, isZero, isMinValue, isMaxValue, fullRange, zeroColor, tickLabelColor, textPointX, textPointY, maxTextWidth, tickLabelString, orientation);
                    }
                } else if (mediumTickMarksVisible && Double.compare(minorTickSpaceBD.setScale(12, RoundingMode.HALF_UP).remainder(mediumCheck2).doubleValue(), 0.0) != 0.0 &&
                        Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(mediumCheck5).doubleValue(), 0.0) == 0.0) {
                    // Draw medium tick mark
                    drawTickMark(mediumTickMarkColor, mediumLineWidth, mediumPointX, mediumPointY, outerPointX, outerPointY);
                } else if (minorTickMarksVisible && Double.compare(counterBD.setScale(12, RoundingMode.HALF_UP).remainder(minorTickSpaceBD).doubleValue(), 0.0) == 0) {
                    // Draw minor tick mark
                    drawTickMark(minorTickMarkColor, minorLineWidth, minorPointX, minorPointY, outerPointX, outerPointY);
                } else if (!isAutoScale && tickMarkCounter % 10 == 0) {
                    // Draw major tick mark based on number of tick marks
                    isMinValue = Double.compare(minValue, counter) == 0;
                    isMaxValue = Double.compare(maxValue, counter) == 0;
                    if (VERTICAL == orientation) {
                        isZero = Double.compare(0.0, maxValue - counter + minValue) == 0;
                    } else {
                        isZero = Double.compare(0.0, counter) == 0;
                    }

                    if (isZero) {
                        setZeroPosition(fixedPosition);
                    }

                    if (minorTickMarksVisible) {
                        drawTickMark((fullRange && isZero) ? zeroColor : minorTickMarkColor, minorLineWidth, innerPointX, innerPointY, outerPointX, outerPointY);
                    }

                    // Draw tick labels
                    if (tickLabelsVisible) {
                        String tickLabelString;
                        if (TickLabelFormat.NUMBER == getTickLabelFormat()) {
                            tickLabelString = Orientation.HORIZONTAL == orientation ? formatNumber(locale, (minValue - i)) : formatNumber(locale, maxValue - counter + minValue);
                        } else {
                            tickLabelString = Orientation.HORIZONTAL == orientation ? TimeUtils.secondsToHHMMString(TimeUtils.toSeconds(TimeUtils.toDateTime((long) (minValue - i)), TimeUtils.getZoneOffset())) : formatNumber(locale, maxValue - counter + minValue);
                        }
                        drawTickLabel(isOnlyFirstAndLastTickLabelVisible, isZero, isMinValue, isMaxValue, fullRange, zeroColor, tickLabelColor, textPointX, textPointY, maxTextWidth, tickLabelString, orientation);
                    }
                } else if (tickMarkCounter % 1 == 0) {
                    if (minorTickMarksVisible) {
                        drawTickMark(minorTickMarkColor, minorLineWidth, minorPointX, minorPointY, outerPointX, outerPointY);
                    }
                }

                counterBD = counterBD.add(minorTickSpaceBD);
                counter = counterBD.doubleValue();
                if (counter > maxValue) break;
            }
        } else if (AxisType.LOGARITHMIC == axisType) {
            // ******************** Logarithmic *******************************
            tickLabelFormatString = "%6.0e";
            double logLowerBound = Math.log10(getMinValue());
            double logUpperBound = Math.log10(getMaxValue());
            double section;

            // Draw axis
            if (VERTICAL == orientation) {
                section = height / logUpperBound;
                if (Position.LEFT == position) {
                    axisGC_.strokeLine(anchorXPlusOffset, minPosition, anchorXPlusOffset, maxPosition);
                } else if (Position.RIGHT == position) {
                    axisGC_.strokeLine(anchorX, minPosition, anchorX, maxPosition);
                }
            } else {
                section = width / logUpperBound;
                if (Position.BOTTOM == position) {
                    axisGC_.strokeLine(minPosition, anchorY, maxPosition, anchorY);
                } else if (Position.TOP == position) {
                    axisGC_.strokeLine(minPosition, anchorYPlusOffset, maxPosition, anchorYPlusOffset);
                }
            }

            for (double i = 0; i <= logUpperBound; i += 1) {
                for (double j = 1; j <= 9; j++) {
                    BigDecimal value = new BigDecimal(j * Math.pow(10, i));
                    double stepSize = i > 0 ? (Math.log10(value.doubleValue()) % i) : Math.log10(value.doubleValue());
                    double fixedPosition;
                    if (VERTICAL == orientation) {
                        isMinValue = Double.compare(i, logUpperBound) == 0;
                        isMaxValue = i == 0;
                        fixedPosition = maxPosition - i * section - (stepSize * section);
                        if (Position.LEFT == position) {
                            innerPointX = anchorXPlusOffset - maxMajorTickMarkLength;
                            innerPointY = fixedPosition;
                            minorPointX = anchorXPlusOffset - maxMinorTickMarkLength;
                            minorPointY = fixedPosition;
                            outerPointX = anchorXPlusOffset;
                            outerPointY = fixedPosition;
                            textPointX = anchorXPlusOffset - textPosition;
                            textPointY = fixedPosition;
                            maxTextWidth = 0.6 * width;
                        } else {
                            innerPointX = anchorX + maxMajorTickMarkLength;
                            innerPointY = fixedPosition;
                            minorPointX = anchorX + maxMinorTickMarkLength;
                            minorPointY = fixedPosition;
                            outerPointX = anchorX;
                            outerPointY = fixedPosition;
                            textPointX = anchorXPlusOffset;
                            textPointY = fixedPosition;
                            maxTextWidth = width;
                        }
                    } else {
                        isMinValue = i == 0;
                        isMaxValue = Double.compare(i, logUpperBound) == 0;
                        fixedPosition = i * section + (stepSize * section);
                        if (Position.BOTTOM == position) {
                            innerPointX = fixedPosition;
                            innerPointY = anchorY + maxMajorTickMarkLength;
                            minorPointX = fixedPosition;
                            minorPointY = anchorY + maxMinorTickMarkLength;
                            outerPointX = fixedPosition;
                            outerPointY = anchorY;
                            textPointX = fixedPosition;
                            textPointY = anchorY + textPosition - tickLabelFontSize * 0.2;
                            maxTextWidth = section;
                        } else {
                            innerPointX = fixedPosition;
                            innerPointY = anchorYPlusOffset - maxMajorTickMarkLength;
                            minorPointX = fixedPosition;
                            minorPointY = anchorYPlusOffset - maxMinorTickMarkLength;
                            outerPointX = fixedPosition;
                            outerPointY = anchorYPlusOffset;
                            textPointX = fixedPosition;
                            textPointY = anchorY - textPosition + tickLabelFontSize * 0.5;
                            maxTextWidth = section;
                        }
                    }

                    if (Helper.isPowerOf10(value.intValue())) {
                        if (majorTickMarksVisible) {
                            drawTickMark(majorTickMarkColor, majorLineWidth, innerPointX, innerPointY, outerPointX, outerPointY);
                        } else if (minorTickMarksVisible) {
                            drawTickMark(minorTickMarkColor, minorLineWidth, minorPointX, minorPointY, outerPointX, outerPointY);
                        }
                        // Draw tick labels
                        if (tickLabelsVisible) {
                            axisGC_.setFill(tickLabelColor);
                            if (VERTICAL == orientation) {
                                axisGC_.setTextAlign(TextAlignment.RIGHT);
                            }
                            drawTickLabel(isOnlyFirstAndLastTickLabelVisible, false, isMinValue, isMaxValue, false, zeroColor, tickLabelColor, textPointX, textPointY, maxTextWidth, formatNumber(locale, value.doubleValue()), orientation);
                        }
                    } else {
                        if (minorTickMarksVisible) {
                            drawTickMark(minorTickMarkColor, minorLineWidth, minorPointX, minorPointY, outerPointX, outerPointY);
                        }
                    }
                }
            }
        }
        drawAxisTitle(orientation, position);
    }

    private void drawTimeAxis() {
        if (Double.compare(stepSize, 0) <= 0) return;

        axisGC_.setFill(getAxisBackgroundColor());
        axisGC_.clearRect(0, 0, width, height);
        axisGC_.setFont(tickLabelFont_);
        axisGC_.setTextBaseline(VPos.CENTER);

        double minValue = TimeUtils.toSeconds(getStartTime());
        double maxValue = TimeUtils.toSeconds(getEndTime());
        boolean tickLabelsVisible = getTickLabelsVisible();
        boolean mediumTickLabelsVisible = getMediumTimeAxisTickLabelsVisible();
        boolean isOnlyFirstAndLastTickLabelVisible = isOnlyFirstAndLastTickLabelVisible();
        double tickLabelFontSize = getTickLabelFontSize();
        Color tickLabelColor = getTickLabelColor();
        Color majorTickMarkColor = getMajorTickMarkColor();
        boolean majorTickMarksVisible = getMajorTickMarksVisible();
        Color mediumTickMarkColor = getMediumTickMarkColor();
        boolean mediumTickMarksVisible = getMediumTickMarksVisible();
        Color minorTickMarkColor = getMinorTickMarkColor();
        boolean minorTickMarksVisible = getMinorTickMarksVisible();
        boolean sameTickMarkLength = getSameTickMarkLength();
        double majorLineWidth = Math.max(size * 0.007, MIN_MAJOR_LINE_WIDTH);
        double mediumLineWidth = size * 0.006 < MIN_MEDIUM_LINE_WIDTH ? MIN_MEDIUM_LINE_WIDTH : size * 0.005;
        double minorLineWidth = size * 0.005 < MIN_MINOR_LINE_WIDTH ? MIN_MINOR_LINE_WIDTH : size * 0.003;
        double minPosition;
        double maxPosition;
        if (VERTICAL == getOrientation()) {
            minPosition = 0;
            maxPosition = height;
        } else {
            minPosition = 0;
            maxPosition = width;
        }

        Orientation orientation = getOrientation();
        Position position = getPosition();
        double anchorX = position == Position.LEFT ? 0 : getZeroPosition();
        double anchorXPlusOffset = anchorX + width;
        double anchorY = Position.BOTTOM == position ? 0 : getZeroPosition();
        double anchorYPlusOffset = anchorY + height;
        boolean isMinValue;
        boolean isMaxValue;
        double innerPointX;
        double innerPointY;
        double outerPointX;
        double outerPointY;
        double mediumPointX;
        double mediumPointY;
        double minorPointX;
        double minorPointY;
        double textPointX;
        double textPointY;
        double maxTextWidth;

        // ******************** Date **************************************
        createTickValues(width, getStartTime(), getEndTime());
        long minValueInSeconds = getStartTime().toEpochSecond(TimeUtils.getZoneOffset());
        long maxValueInSeconds = getEndTime().toEpochSecond(TimeUtils.getZoneOffset());
        long rangeInSeconds = Duration.between(getStartTime(), getEndTime()).getSeconds();
        double stepSize = VERTICAL == orientation ? height / rangeInSeconds : width / rangeInSeconds;
        long majorTickSpace = currentInterval.getMajorTickSpace();
        long mediumTickSpace = currentInterval.getMediumTickSpace();
        long minorTickSpace = currentInterval.getMinorTickSpace();
        long counter = minValueInSeconds;

        axisGC_.setLineWidth(majorLineWidth);

        // Draw time-axis
        if (VERTICAL == orientation) {
            switch (position) {
                case LEFT:
                    axisGC_.strokeLine(anchorXPlusOffset, minPosition, anchorXPlusOffset, maxPosition);
                    break;
                case RIGHT:
                    axisGC_.strokeLine(anchorX, minPosition, anchorX, maxPosition);
                    break;
                default:
                    axisGC_.strokeLine(anchorX, minPosition, anchorX, maxPosition);
                    break;
            }
        } else {
            switch (position) {
                case BOTTOM:
                    axisGC_.strokeLine(minPosition, anchorY, maxPosition, anchorY);
                    break;
                case TOP:
                    axisGC_.strokeLine(minPosition, anchorYPlusOffset, maxPosition, anchorYPlusOffset);
                    break;
                default:
                    axisGC_.strokeLine(minPosition, anchorY, maxPosition, anchorY);
                    break;
            }
        }

        double majorTickMarkLengthFactor = sameTickMarkLength ? 0.4 : 0.5;
        double mediumTickMarkLengthFactor = 0.4;
        double minorTickMarkLengthFactor = sameTickMarkLength ? 0.4 : 0.3;

        double majorTickMarkLength = VERTICAL == orientation ? majorTickMarkLengthFactor * width : majorTickMarkLengthFactor * height;
        double mediumTickMarkLength = VERTICAL == orientation ? mediumTickMarkLengthFactor * width : mediumTickMarkLengthFactor * height;
        double minorTickMarkLength = VERTICAL == orientation ? minorTickMarkLengthFactor * width : minorTickMarkLengthFactor * height;

        ZoneOffset zoneOffset = TimeUtils.getZoneOffset();
        long duration = getEndTime().toEpochSecond(zoneOffset) - getStartTime().toEpochSecond(zoneOffset);
        long step;
        if (duration > 31536000) {
            step = 3600;
        } else if (duration > 2592000) {
            step = 60;
        } else {
            step = 1;
        }

        // Main Loop for tick marks and labels
        for (long i = minValueInSeconds; i <= maxValueInSeconds; i += step) {
            double fixedPosition = (counter - minValueInSeconds) * stepSize * step;

            if (VERTICAL == orientation) {
                if (Position.LEFT == position) {
                    innerPointX = anchorXPlusOffset - majorTickMarkLength;
                    innerPointY = fixedPosition;
                    mediumPointX = anchorXPlusOffset - mediumTickMarkLength;
                    mediumPointY = fixedPosition;
                    minorPointX = anchorXPlusOffset - minorTickMarkLength;
                    minorPointY = fixedPosition;
                    outerPointX = anchorXPlusOffset;
                    outerPointY = fixedPosition;
                    textPointX = anchorXPlusOffset - 0.6 * width;
                    textPointY = fixedPosition;
                    maxTextWidth = 0.6 * width;
                } else if (Position.RIGHT == position) {
                    innerPointX = anchorX + majorTickMarkLength;
                    innerPointY = fixedPosition;
                    mediumPointX = anchorX + mediumTickMarkLength;
                    mediumPointY = fixedPosition;
                    minorPointX = anchorX + minorTickMarkLength;
                    minorPointY = fixedPosition;
                    outerPointX = anchorX;
                    outerPointY = fixedPosition;
                    textPointX = anchorXPlusOffset;
                    textPointY = fixedPosition;
                    maxTextWidth = width;
                } else {
                    innerPointX = anchorX - 0.25 * width;
                    innerPointY = fixedPosition;
                    mediumPointX = anchorX - 0.2 * width;
                    mediumPointY = fixedPosition;
                    minorPointX = anchorX - 0.15 * width;
                    minorPointY = fixedPosition;
                    outerPointX = anchorX;
                    outerPointY = fixedPosition;
                    textPointX = anchorXPlusOffset;
                    textPointY = fixedPosition;
                    maxTextWidth = width;
                }
            } else {
                if (Position.BOTTOM == position) {
                    innerPointX = fixedPosition;
                    innerPointY = anchorY + majorTickMarkLength;
                    mediumPointX = fixedPosition;
                    mediumPointY = anchorY + mediumTickMarkLength;
                    minorPointX = fixedPosition;
                    minorPointY = anchorY + minorTickMarkLength;
                    outerPointX = fixedPosition;
                    outerPointY = anchorY;
                    textPointX = fixedPosition;
                    textPointY = anchorY + 0.8 * height;
                    maxTextWidth = majorTickSpace * stepSize;
                } else if (Position.TOP == position) {
                    innerPointX = fixedPosition;
                    innerPointY = anchorYPlusOffset - majorTickMarkLength;
                    mediumPointX = fixedPosition;
                    mediumPointY = anchorYPlusOffset - mediumTickMarkLength;
                    minorPointX = fixedPosition;
                    minorPointY = anchorYPlusOffset - minorTickMarkLength;
                    outerPointX = fixedPosition;
                    outerPointY = anchorYPlusOffset;
                    textPointX = fixedPosition;
                    textPointY = anchorY + 0.2 * height;
                    maxTextWidth = majorTickSpace * stepSize;
                } else {
                    innerPointX = fixedPosition;
                    innerPointY = anchorY - 0.25 * height;
                    mediumPointX = fixedPosition;
                    mediumPointY = anchorY - 0.2 * height;
                    minorPointX = fixedPosition;
                    minorPointY = anchorY - 0.15 * height;
                    outerPointX = fixedPosition;
                    outerPointY = anchorY;
                    textPointX = fixedPosition;
                    textPointY = anchorY + 0.2 * height;
                    maxTextWidth = majorTickSpace * stepSize;
                }
            }

            if (i % majorTickSpace == 0) {
                // Draw major tick mark
                isMinValue = i == minValueInSeconds;
                isMaxValue = i == maxValueInSeconds;

                if (majorTickMarksVisible) {
                    axisGC_.setStroke(majorTickMarkColor);
                    axisGC_.setLineWidth(majorLineWidth);
                    axisGC_.strokeLine(innerPointX, innerPointY, outerPointX, outerPointY);
                } else if (minorTickMarksVisible) {
                    axisGC_.setStroke(minorTickMarkColor);
                    axisGC_.setLineWidth(minorLineWidth);
                    axisGC_.strokeLine(minorPointX, minorPointY, outerPointX, outerPointY);
                }

                // Draw tick labels
                if (tickLabelsVisible && tickLabelFontSize > 6) {
                    if (!isOnlyFirstAndLastTickLabelVisible) {
                        axisGC_.setFill(tickLabelColor);
                    } else {
                        if (isMinValue || isMaxValue) {
                            axisGC_.setFill(tickLabelColor);
                        } else {
                            axisGC_.setFill(Color.TRANSPARENT);
                        }
                    }

                    if (VERTICAL == orientation) {
                        axisGC_.setTextAlign(TextAlignment.RIGHT);
                        if (isMinValue) {
                            axisGC_.fillText(dateTimeFormatter.format(toLocalDateTime((long) (minValue - i) * 1000)), textPointX, textPointY + size * 0.15, maxTextWidth);
                        } else if (isMaxValue) {
                            axisGC_.fillText(dateTimeFormatter.format(toLocalDateTime((long) (minValue - i) * 1000)), textPointX, textPointY - size * 0.15, maxTextWidth);
                        } else {
                            axisGC_.fillText(dateTimeFormatter.format(toLocalDateTime((long) (minValue - i) * 1000)), textPointX, textPointY, maxTextWidth);
                        }
                    } else {
                        if (isMinValue) {
                            axisGC_.setTextAlign(TextAlignment.LEFT);
                        } else if (isMaxValue) {
                            axisGC_.setTextAlign(TextAlignment.RIGHT);
                        } else {
                            axisGC_.setTextAlign(TextAlignment.CENTER);
                            LocalDateTime currentDateTime = toLocalDateTime(i);
                            double halfLabelWidth = calcTextWidth(tickLabelFont_, dateTimeFormatter.format(currentDateTime)) * 0.5;
                            if (textPointX - halfLabelWidth < 0) {
                                textPointX = halfLabelWidth;
                            } else if (textPointX + halfLabelWidth > width) {
                                textPointX = width - halfLabelWidth;
                            }
                        }
                        drawTickLabel(isOnlyFirstAndLastTickLabelVisible, false, isMinValue, isMaxValue, false, majorTickMarkColor, tickLabelColor, textPointX, textPointY, maxTextWidth, dateTimeFormatter.format(toLocalDateTime(i)), orientation);
                    }
                }
            } else if (mediumTickMarksVisible && i % mediumTickSpace == 0) {
                // Draw medium tick mark
                axisGC_.setStroke(mediumTickMarkColor);
                axisGC_.setLineWidth(mediumLineWidth);
                axisGC_.strokeLine(mediumPointX, mediumPointY, outerPointX, outerPointY);

                // Draw tick labels
                if (tickLabelsVisible && mediumTickLabelsVisible && tickLabelFontSize > 6) {
                    axisGC_.setFill(getTickLabelColor());
                    if (VERTICAL == orientation) {
                        axisGC_.setTextAlign(TextAlignment.RIGHT);
                        axisGC_.fillText(dateTimeFormatter.format(toLocalDateTime((long) (minValue - i) * 1000)), textPointX, textPointY, maxTextWidth);
                    } else {
                        axisGC_.setTextAlign(TextAlignment.CENTER);
                        LocalDateTime currentDateTime = toLocalDateTime(i);
                        double halfLabelWidth = calcTextWidth(tickLabelFont_, dateTimeFormatter.format(currentDateTime)) * 0.5;
                        if (textPointX - halfLabelWidth < 0) {
                            textPointX = halfLabelWidth;
                        } else if (textPointX + halfLabelWidth > width) {
                            textPointX = width - halfLabelWidth;
                        }
                        drawTickLabel(isOnlyFirstAndLastTickLabelVisible, false, false, false, false, majorTickMarkColor, tickLabelColor, textPointX, textPointY, maxTextWidth, dateTimeFormatter.format(toLocalDateTime(i)), orientation);
                    }
                }
            } else if (minorTickMarksVisible && i % minorTickSpace == 0) {
                // Draw minor tick mark
                axisGC_.setStroke(minorTickMarkColor);
                axisGC_.setLineWidth(minorLineWidth);
                axisGC_.strokeLine(minorPointX, minorPointY, outerPointX, outerPointY);
            }

            counter++; // 1 Second
            if (counter > maxValue) break;
        }

        drawAxisTitle(orientation, position);
    }

    /**
     * draw axis title
     *
     * @param orientation axis {@link Orientation}
     * @param position    axis {@link Position}
     */
    private void drawAxisTitle(final Orientation orientation, final Position position) {
        // Draw axis title
        Font titleFont = getTitleFont();
        double titleFontSize = titleFont.getSize();

        axisGC_.setFont(titleFont);
        axisGC_.setFill(getTitleColor());
        axisGC_.setTextAlign(TextAlignment.CENTER);
        axisGC_.setTextBaseline(VPos.CENTER);
        if (orientation == Orientation.HORIZONTAL) {
            switch (position) {
                case TOP -> axisGC_.fillText(getTitle(), width * 0.5, titleFontSize * 0.5);
                case BOTTOM -> axisGC_.fillText(getTitle(), width * 0.5, height - titleFontSize * 0.5);
            }
        } else {
            switch (position) {
                case LEFT:
                    axisGC_.save();
                    axisGC_.translate(titleFontSize * 0.5, height * 0.5);
                    axisGC_.rotate(270);
                    axisGC_.fillText(getTitle(), 0, 0);
                    axisGC_.restore();
                    break;
                case RIGHT:
                    axisGC_.save();
                    axisGC_.translate(width - titleFontSize * 0.5, height * 0.5);
                    axisGC_.rotate(90);
                    axisGC_.fillText(getTitle(), 0, 0);
                    axisGC_.restore();
                    break;
            }
        }
    }

    private void drawTickMark(final Color COLOR, final double LINE_WIDTH, final double START_X, final double START_Y, final double END_X, final double END_Y) {
        axisGC_.setStroke(COLOR);
        axisGC_.setLineWidth(LINE_WIDTH);
        axisGC_.strokeLine(START_X, START_Y, END_X, END_Y);
    }

    private void drawTickLabel(final boolean ONLY_FIRST_AND_LAST_VISIBLE, final boolean IS_ZERO, final boolean IS_MIN, final boolean IS_MAX, final boolean FULL_RANGE,
            final Color ZERO_COLOR, final Color COLOR, final double TEXT_X, final double TEXT_Y, final double MAX_WIDTH, final String TEXT, final Orientation ORIENTATION) {
        if (!ONLY_FIRST_AND_LAST_VISIBLE) {
            if (IS_ZERO) {
                axisGC_.setFill(FULL_RANGE ? ZERO_COLOR : COLOR);
            } else {
                axisGC_.setFill(COLOR);
            }
        } else {
            if (IS_MIN || IS_MAX) {
                if (IS_ZERO) {
                    axisGC_.setFill(FULL_RANGE ? ZERO_COLOR : COLOR);
                } else {
                    axisGC_.setFill(COLOR);
                }
            } else {
                axisGC_.setFill(Color.TRANSPARENT);
            }
        }

        if (VERTICAL == ORIENTATION) {
            axisGC_.setTextAlign(TextAlignment.RIGHT);
            double fontSize = getTickLabelFontSize();
            double textY;
            if (TEXT_Y < fontSize) {
                textY = fontSize * 0.5;
            } else if (TEXT_Y > height - fontSize) {
                textY = height - fontSize * 0.5;
            } else {
                textY = TEXT_Y;
            }
            axisGC_.fillText(TEXT, TEXT_X, textY, MAX_WIDTH);
        } else {
            if (IS_MIN) {
                axisGC_.setTextAlign(TextAlignment.LEFT);
            } else if (IS_MAX) {
                axisGC_.setTextAlign(TextAlignment.RIGHT);
            } else {
                axisGC_.setTextAlign(TextAlignment.CENTER);
            }

            double tickLabelWidth = calcTextWidth(tickLabelFont_, TEXT);
            if (axisGC_.getTextAlign() == TextAlignment.CENTER && TEXT_X + tickLabelWidth * 0.5 > width) {
                axisGC_.fillText(TEXT, width - tickLabelWidth * 0.5, TEXT_Y, MAX_WIDTH);
            } else {
                axisGC_.fillText(TEXT, TEXT_X, TEXT_Y, MAX_WIDTH);
            }
        }
    }

    public void resize() {
        width = getWidth() - getInsets().getLeft() - getInsets().getRight(); // width of content-area
        height = getHeight() - getInsets().getTop() - getInsets().getBottom(); // height of content-area
        size = Math.min(width, height);

        double aspectRatio = width / height;

        if (width > 0 && height > 0) {
            if (isAutoTitleFontSize()) {
                setTitleFontSize(Math.clamp(0.175 * size, 8, 24));
            }

            if (isAutoTickLabelFontSize()) {
                setTickLabelFontSize(Math.clamp(0.175 * size, 8, 24));
            }

            axisBounds_.set(getInsets().getLeft(), getInsets().getTop(), width, height);

            if (getOrientation() == VERTICAL) {
                width = height * aspectRatio;
                size = Math.min(width, height);
                stepSize = Math.abs(height / getRange());
            } else {
                height = width / aspectRatio;
                size = Math.min(width, height);
                stepSize = Math.abs(width / getRange());
            }

            pane.setMaxSize(width, height);
            pane.setMinSize(width, height);
            pane.setPrefSize(width, height);
            pane.relocate(axisBounds_.getX(), axisBounds_.getY());

            axisCanvas.setWidth(width);
            axisCanvas.setHeight(height);

            redraw();
        }
    }

    protected void redraw() {
        if (getType() == AxisType.TIME) {
            drawTimeAxis();
        } else {
            if (isAutoScale()) {
                calcAutoScale();
            } else {
                calcScale();
            }
            drawAxis();
        }
    }
}
