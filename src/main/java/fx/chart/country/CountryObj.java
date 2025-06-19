package fx.chart.country;

import eu.hansolo.toolboxfx.ValueObject;
import fx.chart.country.flag.Flag;
import fx.chart.country.tools.CLocation;
import fx.chart.country.tools.Cities;
import fx.chart.country.tools.CountryPath;
import fx.chart.country.tools.Helper;
import fx.chart.country.tools.Records.Airport;
import fx.chart.country.tools.Records.Airport2;
import fx.chart.country.tools.Records.City;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CountryObj {
    private Country country;
    private ValueObject valueObject;
    private double value;
    private Color fill;
    private Color stroke;


    // ******************** Constructors **************************************
    public CountryObj(final Country country) {
        this.country = country;
        this.valueObject = null;
        this.value = 0;
        this.fill = null;
        this.stroke = null;
    }


    // ******************** Methods *******************************************
    public Country getCountry() {return country;}

    public String getName() {return country.name();}

    public String getIso2() {return country.name();}

    public ValueObject getValueObject() {return valueObject;}

    public void setValueObject(final ValueObject valueObject) {this.valueObject = valueObject;}

    public double getValue() {return value;}

    public void setValue(final double value) {this.value = value;}

    public Color getFill() {return fill;}

    public void setFill(final Color fill) {this.fill = fill;}

    public Color getStroke() {return stroke;}

    public void setStroke(final Color stroke) {this.stroke = stroke;}

    public String getDisplayName() {return country.getDisplayName();}

    public CLocation getLocation() {return country.getLocation();}

    public Flag getFlag() {
        return Flag.getAsList().parallelStream().filter(flag -> flag.getIso2().equals(country.name())).findFirst().orElse(Flag.NOT_FOUND);
    }

    public Optional<City> getCapital() {return Cities.INSTANCE.capitals().parallelStream().filter(city -> city.country() == country).findFirst();}

    public List<City> getCities() {return Helper.getCities().stream().filter(city -> city.country() == country).collect(Collectors.toList());}

    public List<Airport> getAirports() {return Helper.getAirports().entrySet().stream().filter(entry -> entry.getValue().country() == country).map(entry -> entry.getValue()).collect(Collectors.toList());}

    public List<Airport2> getAirports2() {return Helper.getAirports2().entrySet().stream().filter(entry -> entry.getValue().country() == country).map(entry -> entry.getValue()).collect(Collectors.toList());}

    public List<CountryPath> getPaths() {return country.getCountryPaths().get(country);}

    public List<CountryPath> getCopyOfPaths() {return country.getCopyOfCountryPaths().get(country);}
}
