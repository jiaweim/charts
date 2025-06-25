package fx.chart.util.geo;

import javafx.beans.property.*;

import java.util.HashMap;


public class GeoLocationBuilder<B extends GeoLocationBuilder<B>> {
    private HashMap<String, ReadOnlyProperty> properties = new HashMap<>();


    protected GeoLocationBuilder() {}

    public static final GeoLocationBuilder create() {
        return new GeoLocationBuilder();
    }

    public final B name(final String name) {
        properties.put("name", new ReadOnlyStringWrapper(name));
        return (B) this;
    }

    public final B timestamp(final long timestamp) {
        properties.put("timestamp", new ReadOnlyLongWrapper(timestamp));
        return (B) this;
    }

    public final B latitude(final double latitude) {
        properties.put("latitude", new ReadOnlyDoubleWrapper(latitude));
        return (B) this;
    }

    public final B longitude(final double longitude) {
        properties.put("longitude", new ReadOnlyDoubleWrapper(longitude));
        return (B) this;
    }

    public final B altitude(final double altitude) {
        properties.put("altitude", new ReadOnlyDoubleWrapper(altitude));
        return (B) this;
    }

    public final B accuracy(final double accuracy) {
        properties.put("accuracy", new ReadOnlyDoubleWrapper(accuracy));
        return (B) this;
    }

    public final B info(final String info) {
        properties.put("info", new ReadOnlyStringWrapper(info));
        return (B) this;
    }

    public final fx.chart.util.geo.GeoLocation build() {
        fx.chart.util.geo.GeoLocation location = new GeoLocation();
        properties.forEach((key, property) -> {
            switch (key) {
                case "name" -> location.setName(((ReadOnlyStringWrapper) property).get());
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
