package fx.charts.areaheatmap;

import eu.hansolo.fx.heatmap.Mapping;
import fx.charts.areaheatmap.AreaHeatMap.Quality;
import fx.charts.data.DataPoint;
import javafx.beans.property.*;
import javafx.geometry.Dimension2D;

import java.util.HashMap;
import java.util.List;

public class AreaHeatMapBuilder<B extends AreaHeatMapBuilder<B>> {

    private HashMap<String, Property> properties = new HashMap<>();

    protected AreaHeatMapBuilder() {}

    public final static AreaHeatMapBuilder create() {
        return new AreaHeatMapBuilder();
    }

    public final B prefSize(final double WIDTH, final double HEIGHT) {
        return prefSize(new Dimension2D(WIDTH, HEIGHT));
    }

    public final B prefSize(final Dimension2D PREF_SIZE) {
        properties.put("prefSize", new SimpleObjectProperty<>(PREF_SIZE));
        return (B) this;
    }

    public final B dataPoints(final DataPoint... POINTS) {
        properties.put("dataPointsArray", new SimpleObjectProperty<>(POINTS));
        return (B) this;
    }

    public final B dataPoints(final List<DataPoint> POINTS) {
        properties.put("dataPointsList", new SimpleObjectProperty<>(POINTS));
        return (B) this;
    }

    public final B colorMapping(final Mapping COLOR_MAPPING) {
        properties.put("colorMapping", new SimpleObjectProperty<>(COLOR_MAPPING));
        return (B) this;
    }

    public final B useColorMapping(final boolean USE) {
        properties.put("useColorMapping", new SimpleBooleanProperty(USE));
        return (B) this;
    }

    public final B quality(final Quality QUALITY) {
        return quality(QUALITY.getFactor());
    }

    public final B quality(final int QUALITY) {
        properties.put("quality", new SimpleIntegerProperty(QUALITY));
        return (B) this;
    }

    public final B dataPointsVisible(final boolean VISIBLE) {
        properties.put("dataPointsVisible", new SimpleBooleanProperty(VISIBLE));
        return (B) this;
    }

    public final B smoothedHull(final boolean SMOOTHED) {
        properties.put("smoothedHull", new SimpleBooleanProperty(SMOOTHED));
        return (B) this;
    }

    public final B discreteColors(final boolean DISCRETE_COLORS) {
        properties.put("discreteColors", new SimpleBooleanProperty(DISCRETE_COLORS));
        return (B) this;
    }

    public final B heatMapOpacity(final double HEAT_MAP_OPACITY) {
        properties.put("heatMapOpacity", new SimpleDoubleProperty(HEAT_MAP_OPACITY));
        return (B) this;
    }

    public final B noOfCloserInfluentialPoints(final int NO_OF_POINTS) {
        properties.put("noOfCloserInfluentialPoints", new SimpleIntegerProperty(NO_OF_POINTS));
        return (B) this;
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
                case "quality" -> control.setQuality(((IntegerProperty) property).get());
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
