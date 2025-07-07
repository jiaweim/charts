package fx.chart;

import fx.chart.tools.TickLabelFormat;
import javafx.beans.property.*;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;


/**
 * Builder for Axis
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
 * @version 1.0.0
 * @since 24 Jun 2025, 10:01 AM
 */
public class AxisBuilder {

    private HashMap<String, Property> properties = new LinkedHashMap<>();
    private Orientation orientation;
    private Position position;

    protected AxisBuilder(final Orientation ORIENTATION, final Position POSITION) {
        orientation = ORIENTATION;
        position = POSITION;
    }

    public static AxisBuilder create(final Orientation orientation, final Position position) {
        return new AxisBuilder(orientation, position);
    }

    public final AxisBuilder minValue(final double MIN_VALUE) {
        properties.put("minValue", new SimpleDoubleProperty(MIN_VALUE));
        return this;
    }

    public final AxisBuilder maxValue(final double MAX_VALUE) {
        properties.put("maxValue", new SimpleDoubleProperty(MAX_VALUE));
        return this;
    }

    public final AxisBuilder setStart(final long EPOCH_SECONDS) {
        if (0 > EPOCH_SECONDS) {
            throw new IllegalArgumentException("Epoch seconds cannot be smaller than 0");
        }
        properties.put("start", new SimpleObjectProperty<>(LocalDateTime.ofInstant(Instant.ofEpochSecond(EPOCH_SECONDS), ZoneId.systemDefault())));
        return this;
    }

    public final AxisBuilder setStart(final long EPOCH_SECONDS, final ZoneId ZONE_ID) {
        if (0 > EPOCH_SECONDS || null == ZONE_ID) {
            throw new IllegalArgumentException("Epoch seconds cannot be smaller than 0 and zone id cannot be null");
        }
        properties.put("start", new SimpleObjectProperty<>(LocalDateTime.ofInstant(Instant.ofEpochSecond(EPOCH_SECONDS), ZONE_ID)));
        return this;
    }

    public final AxisBuilder setStart(final Instant INSTANT) {
        if (null == INSTANT) {
            throw new IllegalArgumentException("Instant cannot be null");
        }
        properties.put("start", new SimpleObjectProperty<>(LocalDateTime.ofInstant(INSTANT, ZoneId.systemDefault())));
        return this;
    }

    public final AxisBuilder setStart(final Instant INSTANT, final ZoneId ZONE_ID) {
        if (null == INSTANT || null == ZONE_ID) {
            throw new IllegalArgumentException("Instant or zone id cannot be null");
        }
        properties.put("start", new SimpleObjectProperty<>(LocalDateTime.ofInstant(INSTANT, ZONE_ID)));
        return this;
    }

    public final AxisBuilder start(final LocalDateTime DATE_TIME) {
        properties.put("start", new SimpleObjectProperty<>(DATE_TIME));
        return this;
    }

    public final AxisBuilder setEnd(final long EPOCH_SECONDS) {
        if (0 > EPOCH_SECONDS) {
            throw new IllegalArgumentException("Epoch seconds cannot be smaller than 0");
        }
        properties.put("end", new SimpleObjectProperty<>(LocalDateTime.ofInstant(Instant.ofEpochSecond(EPOCH_SECONDS), ZoneId.systemDefault())));
        return this;
    }

    public final AxisBuilder setEnd(final long EPOCH_SECONDS, final ZoneId ZONE_ID) {
        if (0 > EPOCH_SECONDS || null == ZONE_ID) {
            throw new IllegalArgumentException("Epoch seconds cannot be smaller than 0 and zone id cannot be null");
        }
        properties.put("end", new SimpleObjectProperty<>(LocalDateTime.ofInstant(Instant.ofEpochSecond(EPOCH_SECONDS), ZONE_ID)));
        return this;
    }

    public final AxisBuilder setEnd(final Instant INSTANT) {
        if (null == INSTANT) {
            throw new IllegalArgumentException("Instant cannot be null");
        }
        properties.put("end", new SimpleObjectProperty<>(LocalDateTime.ofInstant(INSTANT, ZoneId.systemDefault())));
        return this;
    }

    public final AxisBuilder setEnd(final Instant INSTANT, final ZoneId ZONE_ID) {
        if (null == INSTANT || null == ZONE_ID) {
            throw new IllegalArgumentException("Instant or zone id cannot be null");
        }
        properties.put("end", new SimpleObjectProperty<>(LocalDateTime.ofInstant(INSTANT, ZONE_ID)));
        return this;
    }

    public final AxisBuilder end(final LocalDateTime DATE_TIME) {
        properties.put("end", new SimpleObjectProperty<>(DATE_TIME));
        return this;
    }

    public final AxisBuilder autoScale(final boolean AUTO) {
        properties.put("autoScale", new SimpleBooleanProperty(AUTO));
        return this;
    }

    public final AxisBuilder title(final String TITLE) {
        properties.put("title", new SimpleStringProperty(TITLE));
        return this;
    }

    public final AxisBuilder unit(final String UNIT) {
        properties.put("unit", new SimpleStringProperty(UNIT));
        return this;
    }

    public final AxisBuilder type(final AxisType TYPE) {
        properties.put("axisType", new SimpleObjectProperty<>(TYPE));
        return this;
    }

    public final AxisBuilder foregroundColor(final Color COLOR) {
        properties.put("foregroundColor", new SimpleObjectProperty<>(COLOR));
        return this;
    }

    public final AxisBuilder axisBackgroundColor(final Color COLOR) {
        properties.put("axisBackgroundColor", new SimpleObjectProperty<>(COLOR));
        return this;
    }

    public final AxisBuilder axisColor(final Color COLOR) {
        properties.put("axisColor", new SimpleObjectProperty<>(COLOR));
        return this;
    }

    public final AxisBuilder tickLabelColor(final Color COLOR) {
        properties.put("tickLabelColor", new SimpleObjectProperty<>(COLOR));
        return this;
    }

    public final AxisBuilder titleColor(final Color COLOR) {
        properties.put("titleColor", new SimpleObjectProperty<>(COLOR));
        return this;
    }

    public final AxisBuilder tickMarkColor(final Color COLOR) {
        properties.put("tickMarkColor", new SimpleObjectProperty<>(COLOR));
        return this;
    }

    public final AxisBuilder minorTickMarkColor(final Color COLOR) {
        properties.put("minorTickMarkColor", new SimpleObjectProperty<>(COLOR));
        return this;
    }

    public final AxisBuilder mediumTickMarkColor(final Color COLOR) {
        properties.put("mediumTickMarkColor", new SimpleObjectProperty<>(COLOR));
        return this;
    }

    public final AxisBuilder majorTickMarkColor(final Color COLOR) {
        properties.put("majorTickMarkColor", new SimpleObjectProperty<>(COLOR));
        return this;
    }

    public final AxisBuilder tickMarksVisible(final boolean VISIBLE) {
        properties.put("tickMarksVisible", new SimpleBooleanProperty(VISIBLE));
        return this;
    }

    public final AxisBuilder minorTickMarksVisible(final boolean VISIBLE) {
        properties.put("minorTickMarksVisible", new SimpleBooleanProperty(VISIBLE));
        return this;
    }

    public final AxisBuilder mediumTickMarksVisible(final boolean VISIBLE) {
        properties.put("mediumTickMarksVisible", new SimpleBooleanProperty(VISIBLE));
        return this;
    }

    public final AxisBuilder majorTickMarksVisible(final boolean VISIBLE) {
        properties.put("majorTickMarksVisible", new SimpleBooleanProperty(VISIBLE));
        return this;
    }

    public final AxisBuilder sameTickMarkLength(final boolean SAME_LENGTH) {
        properties.put("sameTickMarkLength", new SimpleBooleanProperty(SAME_LENGTH));
        return this;
    }

    public final AxisBuilder zeroColor(final Color COLOR) {
        properties.put("zeroColor", new SimpleObjectProperty<>(COLOR));
        return this;
    }

    public final AxisBuilder minorTickSpace(final double SPACE) {
        properties.put("minorTickSpace", new SimpleDoubleProperty(SPACE));
        return this;
    }

    public final AxisBuilder majorTickSpace(final double SPACE) {
        properties.put("majorTickSpace", new SimpleDoubleProperty(SPACE));
        return this;
    }

    public final AxisBuilder tickLabelsVisible(final boolean VISIBLE) {
        properties.put("tickLabelsVisible", new SimpleBooleanProperty(VISIBLE));
        return this;
    }

    public final AxisBuilder mediumTimeAxisTickLabelsVisible(final boolean VISIBLE) {
        properties.put("mediumTimeAxisTickLabelsVisible", new SimpleBooleanProperty(VISIBLE));
        return this;
    }

    public final AxisBuilder onlyFirstAndLastTickLabelVisible(final boolean VISIBLE) {
        properties.put("onlyFirstAndLastTickLabelVisible", new SimpleBooleanProperty(VISIBLE));
        return this;
    }

    public final AxisBuilder locale(final Locale LOCALE) {
        properties.put("locale", new SimpleObjectProperty<>(LOCALE));
        return this;
    }

    public final AxisBuilder decimals(final int DECIMALS) {
        properties.put("decimals", new SimpleIntegerProperty(DECIMALS));
        return this;
    }

    public final AxisBuilder tickLabelOrientation(final TickLabelOrientation ORIENTATION) {
        properties.put("tickLabelOrientation", new SimpleObjectProperty<>(ORIENTATION));
        return this;
    }

    public final AxisBuilder tickLabelFormat(final TickLabelFormat FORMAT) {
        properties.put("tickLabelFormat", new SimpleObjectProperty<>(FORMAT));
        return this;
    }

    public final AxisBuilder autoTitleFontSize(final boolean AUTO) {
        properties.put("autoTitleFontSize", new SimpleBooleanProperty(AUTO));
        return this;
    }

    public final AxisBuilder autoFontSize(final boolean AUTO) {
        properties.put("autoFontSize", new SimpleBooleanProperty(AUTO));
        return this;
    }

    public final AxisBuilder tickLabelFontSize(final double SIZE) {
        properties.put("tickLabelFontSize", new SimpleDoubleProperty(SIZE));
        return this;
    }

    public final AxisBuilder titleFontSize(final double SIZE) {
        properties.put("titleFontSize", new SimpleDoubleProperty(SIZE));
        return this;
    }

    public final AxisBuilder zoneId(final ZoneId ID) {
        properties.put("zoneId", new SimpleObjectProperty<>(ID));
        return this;
    }

    public final AxisBuilder dateTimeFormatPattern(final String PATTERN) {
        properties.put("dateTimeFormatPattern", new SimpleStringProperty(PATTERN));
        return this;
    }

    public final AxisBuilder numberFormatter(final StringConverter<Number> FORMATTER) {
        properties.put("numberFormatter", new SimpleObjectProperty<>(FORMATTER));
        return this;
    }

    public final AxisBuilder categories(final String... CATEGORIES) {
        properties.put("categoriesArray", new SimpleObjectProperty<>(CATEGORIES));
        return this;
    }

    public final AxisBuilder categories(final List<String> CATEGORIES) {
        properties.put("categoriesList", new SimpleObjectProperty(CATEGORIES));
        return this;
    }

    // General properties
    public final AxisBuilder prefSize(final double WIDTH, final double HEIGHT) {
        properties.put("prefSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return this;
    }

    public final AxisBuilder minSize(final double WIDTH, final double HEIGHT) {
        properties.put("minSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return this;
    }

    public final AxisBuilder maxSize(final double WIDTH, final double HEIGHT) {
        properties.put("maxSize", new SimpleObjectProperty<>(new Dimension2D(WIDTH, HEIGHT)));
        return this;
    }

    public final AxisBuilder prefWidth(final double PREF_WIDTH) {
        properties.put("prefWidth", new SimpleDoubleProperty(PREF_WIDTH));
        return this;
    }

    public final AxisBuilder prefHeight(final double PREF_HEIGHT) {
        properties.put("prefHeight", new SimpleDoubleProperty(PREF_HEIGHT));
        return this;
    }

    public final AxisBuilder minWidth(final double MIN_WIDTH) {
        properties.put("minWidth", new SimpleDoubleProperty(MIN_WIDTH));
        return this;
    }

    public final AxisBuilder minHeight(final double MIN_HEIGHT) {
        properties.put("minHeight", new SimpleDoubleProperty(MIN_HEIGHT));
        return this;
    }

    public final AxisBuilder maxWidth(final double MAX_WIDTH) {
        properties.put("maxWidth", new SimpleDoubleProperty(MAX_WIDTH));
        return this;
    }

    public final AxisBuilder maxHeight(final double MAX_HEIGHT) {
        properties.put("maxHeight", new SimpleDoubleProperty(MAX_HEIGHT));
        return this;
    }

    public final AxisBuilder scaleX(final double SCALE_X) {
        properties.put("scaleX", new SimpleDoubleProperty(SCALE_X));
        return this;
    }

    public final AxisBuilder scaleY(final double SCALE_Y) {
        properties.put("scaleY", new SimpleDoubleProperty(SCALE_Y));
        return this;
    }

    public final AxisBuilder layoutX(final double LAYOUT_X) {
        properties.put("layoutX", new SimpleDoubleProperty(LAYOUT_X));
        return this;
    }

    public final AxisBuilder layoutY(final double LAYOUT_Y) {
        properties.put("layoutY", new SimpleDoubleProperty(LAYOUT_Y));
        return this;
    }

    public final AxisBuilder translateX(final double TRANSLATE_X) {
        properties.put("translateX", new SimpleDoubleProperty(TRANSLATE_X));
        return this;
    }

    public final AxisBuilder translateY(final double TRANSLATE_Y) {
        properties.put("translateY", new SimpleDoubleProperty(TRANSLATE_Y));
        return this;
    }

    public final AxisBuilder padding(final Insets INSETS) {
        properties.put("padding", new SimpleObjectProperty<>(INSETS));
        return this;
    }

    public final AxisBuilder topAnchor(final double VALUE) {
        properties.put("topAnchor", new SimpleDoubleProperty(VALUE));
        return this;
    }

    public final AxisBuilder rightAnchor(final double VALUE) {
        properties.put("rightAnchor", new SimpleDoubleProperty(VALUE));
        return this;
    }

    public final AxisBuilder bottomAnchor(final double VALUE) {
        properties.put("bottomAnchor", new SimpleDoubleProperty(VALUE));
        return this;
    }

    public final AxisBuilder leftAnchor(final double VALUE) {
        properties.put("leftAnchor", new SimpleDoubleProperty(VALUE));
        return this;
    }


    public final Axis build() {
        final Axis axis = Axis.linear(orientation, position);

        if (properties.keySet().contains("categoriesArray")) {
            axis.setCategories(((ObjectProperty<String[]>) properties.get("categoriesArray")).get());
        }
        if (properties.keySet().contains("categoriesList")) {
            axis.setCategories(((ObjectProperty<List<String>>) properties.get("categoriesList")).get());
        }

        if (properties.keySet().contains("axisType")) {
            AxisType type = ((ObjectProperty<AxisType>) properties.get("axisType")).get();
            axis.setType(type);
            if (type == AxisType.TIME) {
                LocalDateTime start = null;
                LocalDateTime end = null;
                if (properties.keySet().contains("start")) {
                    start = ((ObjectProperty<LocalDateTime>) properties.get("start")).get();
                }
                if (properties.keySet().contains("end")) {
                    end = ((ObjectProperty<LocalDateTime>) properties.get("end")).get();
                }
                if (null == start || null == end) {
                    throw new IllegalArgumentException("Start and end have to be defined for axis type TIME");
                }
                if (end.isBefore(start)) {
                    throw new IllegalArgumentException("End cannot be before start");
                }
                if (start.isAfter(end)) {
                    throw new IllegalArgumentException("Start cannot be after end");
                }
                axis.setStart(start);
                axis.setEnd(end);
            }
        }

        properties.forEach((key, property) -> {
            switch (key) {
                case "prefSize" -> {
                    Dimension2D dimPrefSize = ((ObjectProperty<Dimension2D>) property).get();
                    axis.setPrefSize(dimPrefSize.getWidth(), dimPrefSize.getHeight());
                }
                case "minSize" -> {
                    Dimension2D dimMinSize = ((ObjectProperty<Dimension2D>) property).get();
                    axis.setMinSize(dimMinSize.getWidth(), dimMinSize.getHeight());
                }
                case "maxSize" -> {
                    Dimension2D dimMaxSize = ((ObjectProperty<Dimension2D>) property).get();
                    axis.setMaxSize(dimMaxSize.getWidth(), dimMaxSize.getHeight());
                }
                case "prefWidth" -> axis.setPrefWidth(((DoubleProperty) property).get());
                case "prefHeight" -> axis.setPrefHeight(((DoubleProperty) property).get());
                case "minWidth" -> axis.setMinWidth(((DoubleProperty) property).get());
                case "minHeight" -> axis.setMinHeight(((DoubleProperty) property).get());
                case "maxWidth" -> axis.setMaxWidth(((DoubleProperty) property).get());
                case "maxHeight" -> axis.setMaxHeight(((DoubleProperty) property).get());
                case "scaleX" -> axis.setScaleX(((DoubleProperty) property).get());
                case "scaleY" -> axis.setScaleY(((DoubleProperty) property).get());
                case "layoutX" -> axis.setLayoutX(((DoubleProperty) property).get());
                case "layoutY" -> axis.setLayoutY(((DoubleProperty) property).get());
                case "translateX" -> axis.setTranslateX(((DoubleProperty) property).get());
                case "translateY" -> axis.setTranslateY(((DoubleProperty) property).get());
                case "padding" -> axis.setPadding(((ObjectProperty<Insets>) property).get());
                // Control specific properties
                case "minValue" -> axis.setMinValue(((DoubleProperty) property).get());
                case "maxValue" -> axis.setMaxValue(((DoubleProperty) property).get());
                case "autoScale" -> axis.setAutoScale(((BooleanProperty) property).get());
                case "title" -> axis.setTitle(((StringProperty) property).get());
                case "unit" -> axis.setUnit(((StringProperty) property).get());
                case "foregroundColor" -> {
                    axis.setAxisColor(((ObjectProperty<Color>) property).get());
                    axis.setTickMarkColor(((ObjectProperty<Color>) property).get());
                    axis.setTickLabelColor(((ObjectProperty<Color>) property).get());
                }
                case "axisBackgroundColor" -> axis.setAxisBackgroundColor(((ObjectProperty<Color>) property).get());
                case "axisColor" -> axis.setAxisColor(((ObjectProperty<Color>) property).get());
                case "tickLabelColor" -> axis.setTickLabelColor(((ObjectProperty<Color>) property).get());
                case "titleColor" -> axis.setTitleColor(((ObjectProperty<Color>) property).get());
                case "tickMarkColor" -> axis.setTickMarkColor(((ObjectProperty<Color>) property).get());
                case "minorTickMarkColor" -> axis.setMinorTickMarkColor(((ObjectProperty<Color>) property).get());
                case "mediumTickMarkColor" -> axis.setMediumTickMarkColor(((ObjectProperty<Color>) property).get());
                case "majorTickMarkColor" -> axis.setMajorTickMarkColor(((ObjectProperty<Color>) property).get());
                case "tickMarksVisible" -> axis.setTickMarksVisible(((BooleanProperty) property).get());
                case "minorTickMarksVisible" -> axis.setMinorTickMarksVisible(((BooleanProperty) property).get());
                case "mediumTickMarksVisible" -> axis.setMediumTickMarksVisible(((BooleanProperty) property).get());
                case "majorTickMarksVisible" -> axis.setMajorTickMarksVisible(((BooleanProperty) property).get());
                case "sameTickMarkLength" -> axis.setSameTickMarkLength(((BooleanProperty) property).get());
                case "zeroColor" -> axis.setZeroColor(((ObjectProperty<Color>) property).get());
                case "minorTickSpace" -> axis.setMinorTickSpace(((DoubleProperty) property).get());
                case "majorTickSpace" -> axis.setMajorTickSpace(((DoubleProperty) property).get());
                case "tickLabelsVisible" -> axis.setTickLabelsVisible(((BooleanProperty) property).get());
                case "mediumTimeAxisTickLabelsVisible" ->
                        axis.setMediumTimeAxisTickLabelsVisible(((BooleanProperty) property).get());
                case "onlyFirstAndLastTickLabel" ->
                        axis.setOnlyFirstAndLastTickLabelVisible(((BooleanProperty) property).get());
                case "local" -> axis.setLocale(((ObjectProperty<Locale>) property).get());
                case "decimals" -> axis.setDecimals(((IntegerProperty) property).get());
                case "tickLabelOrientation" ->
                        axis.setTickLabelOrientation(((ObjectProperty<TickLabelOrientation>) property).get());
                case "tickLabelFormat" -> axis.setTickLabelFormat(((ObjectProperty<TickLabelFormat>) property).get());
                case "autoTitleFontSize" -> axis.setAutoTitleFontSize(((BooleanProperty) property).get());
                case "autoFontSize" -> axis.setAutoTickLabelFontSize(((BooleanProperty) property).get());
                case "tickLabelFontSize" -> axis.setTickLabelFontSize(((DoubleProperty) property).get());
                case "titleFontSize" -> axis.setTitleFontSize(((DoubleProperty) property).get());
                case "zoneId" -> axis.setZoneId(((ObjectProperty<ZoneId>) property).get());
                case "dateTimeFormatPattern" -> axis.setDateTimeFormatPattern(((StringProperty) property).get());
                case "numberFormatter" -> axis.setNumberFormatter(((ObjectProperty<StringConverter>) property).get());
                case "topAnchor" -> AnchorPane.setTopAnchor(axis, ((DoubleProperty) property).get());
                case "rightAnchor" -> AnchorPane.setRightAnchor(axis, ((DoubleProperty) property).get());
                case "bottomAnchor" -> AnchorPane.setBottomAnchor(axis, ((DoubleProperty) property).get());
                case "leftAnchor" -> AnchorPane.setLeftAnchor(axis, ((DoubleProperty) property).get());
            }
        });
        return axis;
    }
}