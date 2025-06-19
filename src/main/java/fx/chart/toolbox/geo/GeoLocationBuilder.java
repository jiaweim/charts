package fx.chart.toolbox.geo;

import fx.chart.toolbox.properties.DoubleProperty;
import fx.chart.toolbox.properties.LongProperty;
import fx.chart.toolbox.properties.ReadOnlyProperty;
import fx.chart.toolbox.properties.StringProperty;

import java.util.HashMap;


public class GeoLocationBuilder<B extends GeoLocationBuilder<B>> {
    private HashMap<String, ReadOnlyProperty> properties = new HashMap<>();


    protected GeoLocationBuilder() {}

    public static final GeoLocationBuilder create() {
        return new GeoLocationBuilder();
    }

    public final B name(final String name) {
        properties.put("name", new StringProperty(name));
        return (B) this;
    }

    public final B timestamp(final long timestamp) {
        properties.put("timestamp", new LongProperty(timestamp));
        return (B) this;
    }

    public final B latitude(final double latitude) {
        properties.put("latitude", new DoubleProperty(latitude));
        return (B) this;
    }

    public final B longitude(final double longitude) {
        properties.put("longitude", new DoubleProperty(longitude));
        return (B) this;
    }

    public final B altitude(final double altitude) {
        properties.put("altitude", new DoubleProperty(altitude));
        return (B) this;
    }

    public final B accuracy(final double accuracy) {
        properties.put("accuracy", new DoubleProperty(accuracy));
        return (B) this;
    }

    public final B info(final String info) {
        properties.put("info", new StringProperty(info));
        return (B) this;
    }

    public final fx.chart.toolbox.geo.GeoLocation build() {
        fx.chart.toolbox.geo.GeoLocation location = new GeoLocation();
        properties.forEach((key, property) -> {
            switch (key) {
                case "name" -> location.setName(((StringProperty) property).get());
                case "timestamp" -> location.setTimestamp(((LongProperty) property).get());
                case "latitude" -> location.setLatitude(((DoubleProperty) property).get());
                case "longitude" -> location.setLongitude(((DoubleProperty) property).get());
                case "altitude" -> location.setAltitude(((DoubleProperty) property).get());
                case "accuracy" -> location.setAccuracy(((DoubleProperty) property).get());
                case "info" -> location.setInfo(((StringProperty) property).get());
            }
        });
        return location;
    }
}
