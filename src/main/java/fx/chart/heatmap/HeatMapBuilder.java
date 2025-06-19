package fx.chart.heatmap;

import javafx.beans.property.*;
import javafx.geometry.Dimension2D;

import java.util.HashMap;
import java.util.Map.Entry;


public class HeatMapBuilder<B extends HeatMapBuilder<B>> {
    private final HashMap<String, Property> properties = new HashMap<>();


    protected HeatMapBuilder() {}


    public final static HeatMapBuilder create() {
        return new HeatMapBuilder();
    }

    public final B prefSize(final double width, final double height) {
        return prefSize(new Dimension2D(width, height));
    }

    public final B prefSize(final Dimension2D prefSize) {
        properties.put("prefSize", new SimpleObjectProperty<>(prefSize));
        return (B) this;
    }

    public final B width(final double width) {
        properties.put("width", new SimpleDoubleProperty(width));
        return (B) this;
    }

    public final B height(final double height) {
        properties.put("height", new SimpleDoubleProperty(height));
        return (B) this;
    }

    public final B colorMapping(final Mapping colorMapping) {
        properties.put("colorMapping", new SimpleObjectProperty<>(colorMapping));
        return (B) this;
    }

    public final B spotRadius(final double spotRadius) {
        properties.put("spotRadius", new SimpleDoubleProperty(spotRadius));
        return (B) this;
    }

    public final B fadeColors(final boolean fadeColors) {
        properties.put("fadeColors", new SimpleBooleanProperty(fadeColors));
        return (B) this;
    }

    public final B heatMapOpacity(final double heatMapOpacity) {
        properties.put("heatMapOpacity", new SimpleDoubleProperty(heatMapOpacity));
        return (B) this;
    }

    public final B opacityDistribution(final OpacityDistribution opacityDistribution) {
        properties.put("opacityDistribution", new SimpleObjectProperty<>(opacityDistribution));
        return (B) this;
    }


    public final HeatMap build() {
        double width = 400;
        double height = 400;
        Mapping colorMapping = ColorMapping.LIME_YELLOW_RED;
        double spotRadius = 15.5;
        boolean fadeColors = false;
        double heatMapOpacity = 0.5;
        OpacityDistribution opacityDistribution = OpacityDistribution.CUSTOM;

        for (Entry<String, Property> entry : properties.entrySet()) {
            switch (entry.getKey()) {
                case "prefSize" -> {
                    Dimension2D dim = ((ObjectProperty<Dimension2D>) entry.getValue()).get();
                    width = dim.getWidth();
                    height = dim.getHeight();
                }
                case "width" -> width = ((DoubleProperty) entry.getValue()).get();
                case "height" -> height = ((DoubleProperty) entry.getValue()).get();
                case "colorMapping" -> colorMapping = ((ObjectProperty<Mapping>) entry.getValue()).get();
                case "spotRadius" -> spotRadius = ((DoubleProperty) entry.getValue()).get();
                case "fadeColors" -> fadeColors = ((BooleanProperty) entry.getValue()).get();
                case "heatMapOpacity" -> heatMapOpacity = ((DoubleProperty) entry.getValue()).get();
                case "opacityDistribution" ->
                        opacityDistribution = ((ObjectProperty<OpacityDistribution>) entry.getValue()).get();
            }
        }
        return new HeatMap(width, height, colorMapping, spotRadius, fadeColors, heatMapOpacity, opacityDistribution);
    }
}
