package fx.chart.toolbox.geo;

import fx.chart.toolbox.properties.BooleanProperty;
import fx.chart.toolbox.properties.ObjectProperty;
import fx.chart.toolbox.properties.ReadOnlyProperty;
import fx.chart.toolbox.properties.StringProperty;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class GeoFenceBuilder<B extends GeoFenceBuilder<B>> {

    private HashMap<String, ReadOnlyProperty> properties = new HashMap<>();


    // ******************** Constructors **************************************
    protected GeoFenceBuilder() {}


    // ******************** Methods *******************************************
    public static final GeoFenceBuilder create() {
        return new GeoFenceBuilder();
    }

    public final B name(final String name) {
        properties.put("name", new StringProperty(name));
        return (B) this;
    }

    public final B info(final String info) {
        properties.put("info", new StringProperty(info));
        return (B) this;
    }

    public final B group(final String group) {
        properties.put("group", new StringProperty(group));
        return (B) this;
    }

    public final B isActive(final boolean isActive) {
        properties.put("isActive", new BooleanProperty(isActive));
        return (B) this;
    }

    public final B isTimeBased(final boolean isTimeBased) {
        properties.put("isTimeBased", new BooleanProperty(isTimeBased));
        return (B) this;
    }

    public final B startTime(final LocalTime startTime) {
        properties.put("startTime", new ObjectProperty<>(startTime));
        return (B) this;
    }

    public final B endTime(final LocalTime endTime) {
        properties.put("endTime", new ObjectProperty<>(endTime));
        return (B) this;
    }

    public final B zoneId(final ZoneId zoneId) {
        properties.put("zoneId", new ObjectProperty<>(zoneId));
        return (B) this;
    }

    public final B days(final DayOfWeek... days) {
        properties.put("days", new ObjectProperty<Set<DayOfWeek>>(new HashSet<>(Arrays.asList(days))));
        return (B) this;
    }

    public final B days(final Set<DayOfWeek> days) {
        properties.put("days", new ObjectProperty<Set<DayOfWeek>>(new HashSet<>(days)));
        return (B) this;
    }

    public final B tags(final String... tags) {
        properties.put("tags", new ObjectProperty<Set<String>>(new HashSet<>(Arrays.asList(tags))));
        return (B) this;
    }

    public final B tags(final Set<String> tags) {
        properties.put("tags", new ObjectProperty<Set<String>>(new HashSet<>(tags)));
        return (B) this;
    }

    public final B polygon(final fx.chart.toolbox.geo.Polygon polygon) {
        properties.put("polygon", new ObjectProperty<>(polygon));
        return (B) this;
    }

    public final fx.chart.toolbox.geo.GeoFence build() {
        fx.chart.toolbox.geo.GeoFence geoFence = new GeoFence();
        properties.forEach((key, property) -> {
            switch (key) {
                case "name" -> geoFence.setName(((StringProperty) property).get());
                case "info" -> geoFence.setInfo(((StringProperty) property).get());
                case "group" -> geoFence.setGroup(((StringProperty) property).get());
                case "isActive" -> geoFence.setActive(((BooleanProperty) property).get());
                case "isTimeBased" -> geoFence.setTimeBased(((BooleanProperty) property).get());
                case "startTime" -> geoFence.setStartTime(((ObjectProperty<LocalTime>) property).get());
                case "endTime" -> geoFence.setEndTime(((ObjectProperty<LocalTime>) property).get());
                case "zoneId" -> geoFence.setZoneId(((ObjectProperty<ZoneId>) property).get());
                case "days" -> geoFence.setDays(((ObjectProperty<Set<DayOfWeek>>) property).get());
                case "tags" -> geoFence.setTags(((ObjectProperty<Set<String>>) property).get());
                case "polygon" -> geoFence.setPolygon(((ObjectProperty<Polygon>) property).get());
            }
        });
        return geoFence;
    }
}
