package fx.chart.util.unit;

import fx.chart.util.Helper;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

import static fx.chart.util.Constants.*;


public class Converter {
    public static final String[] ABBREVIATIONS = {"k", "M", "G", "T", "P", "E", "Z", "Y"};
    public static final int MAX_NO_OF_DECIMALS = 12;
    private static final EnumMap<fx.chart.util.unit.Category, UnitDefinition> BASE_UNITS = new EnumMap<>(fx.chart.util.unit.Category.class) {
        {
            put(fx.chart.util.unit.Category.ACCELERATION, UnitDefinition.METER_PER_SQUARE_SECOND);
            put(fx.chart.util.unit.Category.ANGLE, UnitDefinition.RADIAN);
            put(fx.chart.util.unit.Category.AREA, UnitDefinition.SQUARE_METER);
            put(fx.chart.util.unit.Category.CURRENT, UnitDefinition.AMPERE);
            put(fx.chart.util.unit.Category.DATA, UnitDefinition.BIT);
            put(fx.chart.util.unit.Category.ELECTRIC_CHARGE, UnitDefinition.ELEMENTARY_CHARGE);
            put(fx.chart.util.unit.Category.ENERGY, UnitDefinition.JOULE);
            put(fx.chart.util.unit.Category.FORCE, UnitDefinition.NEWTON);
            put(fx.chart.util.unit.Category.HUMIDITY, UnitDefinition.PERCENTAGE);
            put(fx.chart.util.unit.Category.LENGTH, UnitDefinition.METER);
            put(fx.chart.util.unit.Category.LUMINANCE, UnitDefinition.CANDELA_SQUARE_METER);
            put(fx.chart.util.unit.Category.LUMINOUS_FLUX, UnitDefinition.LUX);
            put(fx.chart.util.unit.Category.MASS, UnitDefinition.KILOGRAM);
            put(fx.chart.util.unit.Category.PRESSURE, UnitDefinition.PASCAL);
            put(fx.chart.util.unit.Category.SPEED, UnitDefinition.METER_PER_SECOND);
            put(fx.chart.util.unit.Category.TEMPERATURE, UnitDefinition.KELVIN);
            put(fx.chart.util.unit.Category.TEMPERATURE_GRADIENT, UnitDefinition.KELVIN_PER_SECOND);
            put(fx.chart.util.unit.Category.TIME, UnitDefinition.SECOND);
            put(fx.chart.util.unit.Category.TORQUE, UnitDefinition.NEWTON_METER);
            put(fx.chart.util.unit.Category.VOLUME, UnitDefinition.CUBIC_METER);
            put(fx.chart.util.unit.Category.VOLTAGE, UnitDefinition.VOLT);
            put(fx.chart.util.unit.Category.WORK, UnitDefinition.WATT);
            put(fx.chart.util.unit.Category.BLOOD_GLUCOSE, UnitDefinition.MILLIMOL_PER_LITER);
        }
    };
    private UnitDefinition baseUnitDefinition;
    private fx.chart.util.unit.Unit bean;
    private Locale locale;
    private int decimals;
    private String formatString;


    // ******************** Constructors **************************************
    public Converter(final fx.chart.util.unit.Category category) {
        this(category, BASE_UNITS.get(category));
    }

    public Converter(final fx.chart.util.unit.Category category, final UnitDefinition baseUnitDefinition) {
        this.baseUnitDefinition = baseUnitDefinition;
        this.bean = BASE_UNITS.get(category).UNIT;
        this.locale = Locale.US;
        this.decimals = 2;
        this.formatString = "%.2f";
    }


    public fx.chart.util.unit.Category getCategory() {return bean.getCategory();}

    public UnitDefinition getBaseUnitDefinition() {return baseUnitDefinition;}

    public void setBaseUnitDefinition(final UnitDefinition baseUnitDefinition) {
        if (baseUnitDefinition.UNIT.getCategory() == getCategory()) {
            this.baseUnitDefinition = baseUnitDefinition;
        }
    }

    public BigDecimal getFactor() {return bean.getFactor();}

    public BigDecimal getOffset() {return bean.getOffset();}

    public String getUnitName() {return bean.getUnitName();}

    public String getUnitShort() {return bean.getUnitShort();}

    public Locale getLocale() {return locale;}

    public void setLocale(final Locale locale) {this.locale = locale;}

    public int getDecimals() {return decimals;}

    public void setDecimals(final int decimals) {
        if (decimals < 0) {
            this.decimals = 0;
        } else if (decimals > MAX_NO_OF_DECIMALS) {
            this.decimals = MAX_NO_OF_DECIMALS;
        } else {
            this.decimals = decimals;
        }
        formatString = new StringBuilder("%.").append(this.decimals).append("f").toString();
    }

    public String getFormatString() {return formatString;}

    public final boolean isActive() {return bean.isActive();}

    public final void setActive(final boolean active) {bean.setActive(active);}

    public final double convert(final double value, final UnitDefinition unitDefinition) {
        if (unitDefinition.UNIT.getCategory() != getCategory()) {
            throw new IllegalArgumentException("units have to be of the same type");
        }
        return ((((value + baseUnitDefinition.UNIT.getOffset().doubleValue()) * baseUnitDefinition.UNIT.getFactor().doubleValue()) + bean.getOffset().doubleValue()) * bean.getFactor().doubleValue()) / unitDefinition.UNIT
                .getFactor().doubleValue() - unitDefinition.UNIT.getOffset().doubleValue();
    }

    public final String convertToString(final double value, final UnitDefinition unitDefinition) {
        return String.join(" ", String.format(locale, formatString, convert(value, unitDefinition)), unitDefinition.UNIT.getUnitShort());
    }

    public final double convertToBaseUnit(final double value, final UnitDefinition unitDefinition) {
        return ((((value + unitDefinition.UNIT.getOffset().doubleValue()) * unitDefinition.UNIT.getFactor().doubleValue()) + bean.getOffset().doubleValue()) * bean.getFactor().doubleValue()) / baseUnitDefinition.UNIT
                .getFactor().doubleValue() - baseUnitDefinition.UNIT.getOffset().doubleValue();
    }

    public final Pattern getPattern() {
        final StringBuilder PATTERN_BUILDER = new StringBuilder();
        PATTERN_BUILDER.append("^([-+]?\\d*\\.?\\d*)\\s?(");

        for (UnitDefinition unitDefinition : UnitDefinition.values()) {
            PATTERN_BUILDER.append(unitDefinition.UNIT.getUnitShort().replace("*", "\\*")).append("|");
        }

        PATTERN_BUILDER.deleteCharAt(PATTERN_BUILDER.length() - 1);

        //PATTERN_BUILDER.append("){1}$");
        PATTERN_BUILDER.append(")?$");

        return Pattern.compile(PATTERN_BUILDER.toString());
    }

    public final List<Unit> getAvailableUnits(final fx.chart.util.unit.Category category) {
        return getAllUnitDefinitions().get(category).stream().map(unitDefinition -> unitDefinition.UNIT).toList();
    }

    public final EnumMap<fx.chart.util.unit.Category, ArrayList<UnitDefinition>> getAllUnitDefinitions() {
        final EnumMap<fx.chart.util.unit.Category, ArrayList<UnitDefinition>> UNIT_TYPES = new EnumMap<>(fx.chart.util.unit.Category.class);
        final ArrayList<fx.chart.util.unit.Category> CATEGORY_LIST = new ArrayList<>(fx.chart.util.unit.Category.values().length);
        CATEGORY_LIST.addAll(Arrays.asList(fx.chart.util.unit.Category.values()));
        CATEGORY_LIST.forEach(category -> UNIT_TYPES.put(category, new ArrayList<>()));
        for (UnitDefinition unitDefinition : UnitDefinition.values()) {
            UNIT_TYPES.get(unitDefinition.UNIT.getCategory()).add(unitDefinition);
        }
        return UNIT_TYPES;
    }

    public final EnumMap<fx.chart.util.unit.Category, ArrayList<UnitDefinition>> getAllActiveUnitDefinitions() {
        final EnumMap<fx.chart.util.unit.Category, ArrayList<UnitDefinition>> UNIT_DEFINITIONS = new EnumMap<>(fx.chart.util.unit.Category.class);
        final ArrayList<fx.chart.util.unit.Category> CATEGORY_LIST = new ArrayList<>(fx.chart.util.unit.Category.values().length);
        CATEGORY_LIST.addAll(Arrays.asList(Category.values()));
        CATEGORY_LIST.forEach(category -> UNIT_DEFINITIONS.put(category, new ArrayList<>()));
        for (UnitDefinition unitDefinition : UnitDefinition.values()) {
            if (unitDefinition.UNIT.isActive()) {
                UNIT_DEFINITIONS.get(unitDefinition.UNIT.getCategory()).add(unitDefinition);
            }
        }
        return UNIT_DEFINITIONS;
    }

    public static final String format(final double number, final int decimals) {
        return format(number, Helper.clamp(0, 12, decimals), Locale.US);
    }

    public static final String format(final double number, final int decimals, final Locale locale) {
        String formatString = new StringBuilder("%.").append(Helper.clamp(0, 12, decimals)).append("f").toString();
        double value;
        for (int i = ABBREVIATIONS.length - 1; i >= 0; i--) {
            value = Math.pow(1000, i + 1.0);
            if (Double.compare(number, -value) <= 0 || Double.compare(number, value) >= 0) {
                return String.format(locale, formatString, (number / value)) + ABBREVIATIONS[i];
            }
        }
        return String.format(locale, formatString, number);
    }


    @Override
    public String toString() {
        return new StringBuilder().append(CURLY_BRACKET_OPEN)
                .append(QUOTES).append("category").append(QUOTES).append(COLON).append(QUOTES).append(getCategory()).append(QUOTES)
                .append(CURLY_BRACKET_CLOSE)
                .toString();
    }
}
