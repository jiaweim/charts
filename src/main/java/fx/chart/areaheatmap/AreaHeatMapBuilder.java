package fx.chart.areaheatmap;

import fx.chart.areaheatmap.AreaHeatMap.Quality;
import fx.chart.data.DataPoint;
import fx.chart.heatmap.Mapping;
import javafx.beans.property.*;
import javafx.geometry.Dimension2D;

import java.util.HashMap;
import java.util.List;

/**
 * builder for {@link AreaHeatMap}
 */
public class AreaHeatMapBuilder {

    private final HashMap<String, Property> properties = new HashMap<>();

    protected AreaHeatMapBuilder() {}

    public static AreaHeatMapBuilder create() {
        return new AreaHeatMapBuilder();
    }

    public final AreaHeatMapBuilder prefSize(final double width, final double height) {
        return prefSize(new Dimension2D(width, height));
    }

    public final AreaHeatMapBuilder prefSize(final Dimension2D prefSize) {
        properties.put("prefSize", new SimpleObjectProperty<>(prefSize));
        return this;
    }

    public final AreaHeatMapBuilder dataPoints(final DataPoint... POINTS) {
        properties.put("dataPointsArray", new SimpleObjectProperty<>(POINTS));
        return this;
    }

    public final AreaHeatMapBuilder dataPoints(final List<DataPoint> POINTS) {
        properties.put("dataPointsList", new SimpleObjectProperty<>(POINTS));
        return this;
    }

    public final AreaHeatMapBuilder colorMapping(final Mapping COLOR_MAPPING) {
        properties.put("colorMapping", new SimpleObjectProperty<>(COLOR_MAPPING));
        return this;
    }

    public final AreaHeatMapBuilder useColorMapping(final boolean USE) {
        properties.put("useColorMapping", new SimpleBooleanProperty(USE));
        return this;
    }

    public final AreaHeatMapBuilder quality(final Quality QUALITY) {
        properties.put("quality", new SimpleObjectProperty(QUALITY));
        return this;
    }

    public final AreaHeatMapBuilder dataPointsVisible(final boolean VISIBLE) {
        properties.put("dataPointsVisible", new SimpleBooleanProperty(VISIBLE));
        return this;
    }

    public final AreaHeatMapBuilder smoothedHull(final boolean SMOOTHED) {
        properties.put("smoothedHull", new SimpleBooleanProperty(SMOOTHED));
        return this;
    }

    public final AreaHeatMapBuilder discreteColors(final boolean DISCRETE_COLORS) {
        properties.put("discreteColors", new SimpleBooleanProperty(DISCRETE_COLORS));
        return this;
    }

    public final AreaHeatMapBuilder heatMapOpacity(final double HEAT_MAP_OPACITY) {
        properties.put("heatMapOpacity", new SimpleDoubleProperty(HEAT_MAP_OPACITY));
        return this;
    }

    public final AreaHeatMapBuilder noOfCloserInfluentialPoints(final int NO_OF_POINTS) {
        properties.put("noOfCloserInfluentialPoints", new SimpleIntegerProperty(NO_OF_POINTS));
        return this;
    }

    public final AreaHeatMap build() {
        final AreaHeatMap control = new AreaHeatMap();
        properties.forEach((key, property) -> {
            switch (key) {
                case "prefSize" -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) property).get();
                    control.setPrefSize(dim.getWidth(), dim.getHeight());
                }
                case "colorMapping" -> control.setColorMapping(((ObjectProperty<Mapping>) property).get());
                case "useColorMapping" -> control.setUseColorMapping(((BooleanProperty) property).get());
                case "quality" -> control.setQuality(((ObjectProperty<Quality>) property).get());
                case "heatMapOpacity" -> control.setHeatMapOpacity(((DoubleProperty) property).get());
                case "dataPointsVisible" -> control.setDataPointsVisible(((BooleanProperty) property).get());
                case "smoothedHull" -> control.setSmoothedHull(((BooleanProperty) property).get());
                case "discreteColors" -> control.setDiscreteColors(((BooleanProperty) property).get());
                case "noOfCloserInfluentialPoints" ->
                        control.setNoOfCloserInfluentialPoints(((IntegerProperty) property).get());
            }
        });
        if (properties.keySet().contains("dataPointsArray")) {
            control.setDataPoints(((ObjectProperty<DataPoint[]>) properties.get("dataPointsArray")).get());
        }
        if (properties.keySet().contains("dataPointsList")) {
            control.setDataPoints(((ObjectProperty<List<DataPoint>>) properties.get("dataPointsList")).get());
        }
        return control;
    }
}
