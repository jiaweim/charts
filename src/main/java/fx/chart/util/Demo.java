package fx.chart.util;

import fx.chart.event.type.GeoLocationChangeEvt;
import fx.chart.util.Helper.SystemSummary;
import fx.chart.util.geo.GeoLocation;
import fx.chart.util.geo.GeoLocationBuilder;
import fx.chart.util.time.DateTimes;
import fx.chart.util.time.Dates;
import fx.chart.util.time.Times;
import fx.chart.util.unit.Converter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Locale;

import static fx.chart.util.unit.Category.*;
import static fx.chart.util.unit.UnitDefinition.*;


public class Demo {

    public Demo() {
        converterDemo();

        datesDemo();

        timesDemo();

        dateTimesDemo();

        helperDemo();

        geoDemo();
    }

    private void converterDemo() {
        System.out.println("\n-------------------- converter demo --------------------");
        Converter temperatureConverter = new Converter(TEMPERATURE, CELSIUS); // Type Temperature with BaseUnit Celsius
        double celsius = 32.0;
        double fahrenheit = temperatureConverter.convert(celsius, FAHRENHEIT);
        double kelvin = temperatureConverter.convert(celsius, KELVIN);
        System.out.println(celsius + Constants.DEGREE + "C   =>   " + fahrenheit + Constants.DEGREE + "F    =>   " + kelvin + Constants.DEGREE + "K");

        /*
        Converter cssConverter = new Converter(CSS_UNITS, PX);
        double    point = 12;
        double    pixel = cssConverter.convert(point, PX);
        System.out.println(point + "pt  =>  " + pixel + "px");
        */

        Converter lengthConverter = new Converter(LENGTH, METER); // Type Length with BaseUnit Meter
        double meter = 1.0;
        double inches = lengthConverter.convert(meter, INCHES);
        double nanometer = lengthConverter.convert(inches, NANOMETER);
        System.out.println(meter + " " + lengthConverter.getUnitShort() + "   =>   " + inches + " in   =>   " + nanometer + " nm");


        Converter volumeConverter = new Converter(VOLUME, CUBIC_METER);
        double cubicMeter = 3;
        double liters = volumeConverter.convert(cubicMeter, LITER);
        System.out.println(cubicMeter + " cubic meter -> " + liters + " liter");

        Converter literConverter = new Converter(VOLUME, LITER);
        double liter = 3000;
        double cubicMeters = literConverter.convert(liter, CUBIC_METER);
        System.out.println(liter + " liter -> " + cubicMeters + " cubic meter");

        Converter glucoseConverter = new Converter(BLOOD_GLUCOSE, MILLIMOL_PER_LITER);
        double millimolPerLiter = 6.0;
        double milligramPerDeciliter = glucoseConverter.convert(millimolPerLiter, MILLIGRAM_PER_DECILITER);
        System.out.println(millimolPerLiter + "mmol/l -> " + milligramPerDeciliter + "mg/dl");

        Converter mgdlConverter = new Converter(BLOOD_GLUCOSE, MILLIGRAM_PER_DECILITER);
        double mgdl = 108.108108;
        double mmoll = mgdlConverter.convert(mgdl, MILLIMOL_PER_LITER);
        System.out.println(mgdl + " mg/dl -> " + mmoll + " mmol/l");

        // Convert meter to centimeter
        System.out.println(lengthConverter.convertToString(meter, CENTIMETER));

        // Shorten long numbers
        System.out.println(Converter.format(1_500_000, 1));

        System.out.println(Converter.format(1_000_000, 0));
    }

    private void datesDemo() {
        System.out.println("\n-------------------- dates demo --------------------");
        LocalDate localDate = LocalDate.of(2022, 12, 03);
        System.out.println(Dates.dd_MM_yyyy.format(localDate));
        System.out.println(Dates.dd_MMMM_yyyy.format(localDate));
        System.out.println(Dates.yyyy_w.format(localDate));
        System.out.println(Dates.yyyy_w_e.format(localDate));
        System.out.println(Dates.yyyywe.format(localDate));
    }

    private void timesDemo() {
        System.out.println("\n-------------------- times demo --------------------");
        LocalTime localTime = LocalTime.now();
        System.out.println(Times.HH_mm_ss_SSSS.format(localTime));
        System.out.println(Times.HH_mm.format(localTime));
        System.out.println(Times.HHmmss.format(localTime));
        System.out.println(Times.HHmmss_SSSS.format(localTime));
    }

    private void dateTimesDemo() {
        System.out.println("\n-------------------- date times demo --------------------");
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(DateTimes.toEpoch(zonedDateTime));
        System.out.println(DateTimes.dd_MM_yyyy_HH_mm_ss_SSSS.format(zonedDateTime));
    }

    private void helperDemo() {
        System.out.println("\n-------------------- helper demo --------------------");
        SystemSummary systemSummary = Helper.getSystemSummary();
        System.out.println(systemSummary.toBeautifiedString());
    }

    private void geoDemo() {
        System.out.println("\n-------------------- geo demo --------------------");
        GeoLocation home = GeoLocationBuilder.create()
                .name("Home")
                .latitude(51.912781150242054)
                .longitude(7.633729751419756)
                .altitude(66)
                .build();

        GeoLocation azul = GeoLocationBuilder.create()
                .name("Azul")
                .latitude(37.40668261833162)
                .longitude(-122.01573123930172)
                .altitude(20)
                .build();


        home.addGeoLocationObserver(GeoLocationChangeEvt.NAME_CHANGED, e -> System.out.println("Name changed from: " + e.getOldGeoLocation().getName() + " to " + e.getGeoLocation().getName()));

        System.out.println("Distance from Home to Azul: " + String.format(Locale.US, "%.2f km", (home.getDistanceTo(azul) / 1000)));
        home.setName("Home of Han Solo");
    }

    public static void main(String[] args) {
        new Demo();
    }
}
