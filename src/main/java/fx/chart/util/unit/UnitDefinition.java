package fx.chart.util.unit;

import java.math.BigDecimal;


public enum UnitDefinition {
    // Length
    KILOMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "km", "Kilometer", new BigDecimal("1000.0"))),
    HECTOMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "hm", "Hectometer", new BigDecimal("100"))),
    METER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "m", "Meter", new BigDecimal("1.0"))),
    DECIMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "dm", "Decimeter", new BigDecimal("0.1"))),
    CENTIMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "cm", "Centimeter", new BigDecimal("0.01"))),
    MILLIMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "mm", "Millimeter", new BigDecimal("0.0010"))),
    MICROMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "\u00b5m", "Micrometer", new BigDecimal("1.0E-6"))),
    NANOMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "nm", "Nanometer", new BigDecimal("1.0E-9"))),
    ANGSTROM(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "\u00c5", "Angstrom", new BigDecimal("1.0E-10"))),
    PICOMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "pm", "Picometer", new BigDecimal("1.0E-12"))),
    FEMTOMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "fm", "Femtometer", new BigDecimal("1.0E-15"))),
    INCHES(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "in", "Inches", new BigDecimal("0.0254"))),
    MILES(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "mi", "Miles", new BigDecimal("1609.344"))),
    NAUTICAL_MILES(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "nmi", "Nautical Miles", new BigDecimal("1852.0"))),
    FEET(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "ft", "Feet", new BigDecimal("0.3048"))),
    YARD(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "yd", "Yard", new BigDecimal("0.9144"))),
    LIGHT_YEAR(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "l.y.", "Light-Year", new BigDecimal("9.46073E15"))),
    PARSEC(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "pc", "Parsec", new BigDecimal("3.085678E16"))),
    PIXEL(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "px", "Pixel", new BigDecimal("0.000264583"))),
    POINT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "pt", "Point", new BigDecimal("0.0003527778"))),
    PICA(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "p", "Pica", new BigDecimal("0.0042333333"))),
    EM(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LENGTH, "em", "Quad", new BigDecimal("0.0042175176"))),

    // Mass
    TON(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.MASS, "t", "Ton", new BigDecimal("1.0E3"))),
    KILOGRAM(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.MASS, "kg", "Kilogram", new BigDecimal("1.0"))),
    GRAM(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.MASS, "g", "Gram", new BigDecimal("1.0E-3"))),
    MILLIGRAM(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.MASS, "mg", "Milligram", new BigDecimal("1.0E-6"))),
    MICROGRAM(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.MASS, "\u00b5g", "Mikrogram", new BigDecimal("1.0E-6"))),
    NANOGRAM(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.MASS, "ng", "Nanogram", new BigDecimal("1.0E-9"))),
    PICOGRAM(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.MASS, "pg", "Picogram", new BigDecimal("1.0E-12"))),
    FEMTOGRAM(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.MASS, "fg", "Femtogram", new BigDecimal("1.0E-15"))),
    OUNCE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.MASS, "oz", "Ounce (US)", new BigDecimal("0.028"))),
    POUND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.MASS, "lb", "Pounds (US)", new BigDecimal("0.45359237"))),

    // Time
    WEEK(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TIME, "wk", "Week", new BigDecimal("604800"))),
    DAY(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TIME, "d", "Day", new BigDecimal("86400"))),
    HOUR(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TIME, "h", "Hour", new BigDecimal("3600"))),
    MINUTE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TIME, "m", "Minute", new BigDecimal("60"))),
    SECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TIME, "s", "Second", new BigDecimal("1.0"))),
    MILLISECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TIME, "ms", "Millisecond", new BigDecimal("1E-3"))),
    MICROSECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TIME, "\u00b5s", "Microsecond", new BigDecimal("1E-6"))),
    NANOSECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TIME, "ns", "Nanosecond", new BigDecimal("1E-9"))),
    PICOSECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TIME, "ps", "Picosecond", new BigDecimal("1E-12"))),
    FEMTOSECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TIME, "fs", "Femtosecond", new BigDecimal("1E-15"))),

    // Area
    SQUARE_KILOMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "km\u00b2", "Square Kilometer", new BigDecimal("1.0E6"))),
    SQUARE_METER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "m\u00b2", "Meter", new BigDecimal("1.0"))),
    SQUARE_CENTIMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "cm\u00b2", "Square Centimeter", new BigDecimal("1.0E-4"))),
    SQUARE_MILLIMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "mm\u00b2", "Square Millimeter", new BigDecimal("1.0E-6"))),
    SQUARE_MICROMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "\u00b5m\u00b2", "Square Mikrometer", new BigDecimal("1.0E-12"))),
    SQUARE_NANOMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "nm\u00b2", "Square Nanometer", new BigDecimal("1.0E-18"))),
    SQUARE_ANGSTROM(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "\u00c5\u00b2", "Square \u00c5ngstrom", new BigDecimal("1.0E-20"))),
    SQUARE_PICOMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "pm\u00b2", "Square Picometer", new BigDecimal("1.0E-24"))),
    SQUARE_FEMTOMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "fm\u00b2", "Square Femtometer", new BigDecimal("1.0E-30"))),
    HECTARE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "ha", "Hectare", new BigDecimal("1.0E5"))),
    ACRE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "ac", "Acre", new BigDecimal("4046.8564224"))),
    ARES(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "a", "Ares", new BigDecimal("100"))),
    SQUARE_INCH(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "in\u00b2", "Square Inch", new BigDecimal("0.00064516"))),
    SQUARE_FOOT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.AREA, "ft\u00b2", "Square Foot", new BigDecimal("0.09290304"))),

    // Temperature
    KELVIN(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TEMPERATURE, "K", "Kelvin", new BigDecimal("1.0"))),
    CELSIUS(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TEMPERATURE, "\u00b0C", "Celsius", new BigDecimal("1.0"), new BigDecimal("273.15"))),
    FAHRENHEIT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TEMPERATURE, "\u00b0F", "Fahrenheit", new BigDecimal("0.555555555555555"), new BigDecimal("459.67"))),

    // Angle
    DEGREE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ANGLE, "deg", "Degree", (Math.PI / 180.0))),
    RADIAN(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ANGLE, "rad", "Radian", new BigDecimal("1.0"))),
    GRAD(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ANGLE, "grad", "Gradian", new BigDecimal("0.9"))),

    // Volume
    CUBIC_MILLIMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.VOLUME, "mm\u00b3", "Cubic Millimeter", new BigDecimal("1.0E-9"))),
    MILLILITER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.VOLUME, "ml", "Milliliter", new BigDecimal("1.0E-6"))),
    LITER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.VOLUME, "l", "Liter", new BigDecimal("1.0E-3"))),
    CUBIC_METER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.VOLUME, "m\u00b3", "Cubic Meter", new BigDecimal("1.0E0"))),
    GALLON(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.VOLUME, "gal", "US Gallon", new BigDecimal("0.0037854118"))),
    CUBIC_FEET(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.VOLUME, "ft\u00b3", "Cubic Foot", new BigDecimal("0.0283168466"))),
    CUBIC_INCH(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.VOLUME, "in\u00b3", "Cubic Inch", new BigDecimal("0.0000163871"))),

    // Voltage
    MILLIVOLT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.VOLTAGE, "mV", "Millivolt", new BigDecimal("1.0E-3"))),
    VOLT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.VOLTAGE, "V", "Volt", new BigDecimal("1.0E0"))),
    KILOVOLT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.VOLTAGE, "kV", "Kilovolt", new BigDecimal("1.0E3"))),
    MEGAVOLT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.VOLTAGE, "MV", "Megavolt", new BigDecimal("1.0E6"))),

    // Current
    PICOAMPERE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.CURRENT, "pA", "Picoampere", new BigDecimal("1.0E-12"))),
    NANOAMPERE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.CURRENT, "nA", "Nanoampere", new BigDecimal("1.0E-9"))),
    MICROAMPERE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.CURRENT, "\u00b5A", "Microampere", new BigDecimal("1.0E-6"))),
    MILLIAMPERE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.CURRENT, "mA", "Milliampere", new BigDecimal("1.0E-3"))),
    AMPERE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.CURRENT, "A", "Ampere", new BigDecimal("1.0"))),
    KILOAMPERE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.CURRENT, "kA", "Kiloampere", new BigDecimal("1.0E3"))),

    // Speed
    MILLIMETER_PER_SECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.SPEED, "mm/s", "Millimeter per second", new BigDecimal("1.0E-3"))),
    METER_PER_SECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.SPEED, "m/s", "Meter per second", new BigDecimal("1.0E0"))),
    KILOMETER_PER_HOUR(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.SPEED, "km/h", "Kilometer per hour", new BigDecimal("0.2777777778"))),
    MILES_PER_HOUR(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.SPEED, "mph", "Miles per hour", new BigDecimal("0.44704"))),
    KNOT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.SPEED, "kt", "Knot", new BigDecimal("0.51444444444444"))),
    MACH(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.SPEED, "M", "Mach", new BigDecimal("0.00293866995797"))),

    // TemperatureGradient
    KELVIN_PER_SECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TEMPERATURE_GRADIENT, "K/s", "Kelvin per second", new BigDecimal("1.0"))),
    KELVIN_PER_MINUTE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TEMPERATURE_GRADIENT, "K/min", "Kelvin per minute", new BigDecimal("0.0166666667"))),
    KEVLIN_PER_HOUR(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TEMPERATURE_GRADIENT, "K/h", "Kelvin per hour", new BigDecimal("0.0002777778"))),

    // ElectricCharge
    ELEMENTARY_CHARGE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ELECTRIC_CHARGE, "e-", "Elementary charge", new BigDecimal("1.6022E-19"))),
    PICOCOULOMB(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ELECTRIC_CHARGE, "pC", "Picocoulomb", new BigDecimal("1.0E-12"))),
    NANOCOULOMB(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ELECTRIC_CHARGE, "nC", "Nanocoulomb", new BigDecimal("1.0E-9"))),
    MICROCOULOMB(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ELECTRIC_CHARGE, "\u00b5C", "Microcoulomb", new BigDecimal("1.0E-6"))),
    MILLICOULOMB(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ELECTRIC_CHARGE, "mC", "Millicoulomb", new BigDecimal("1.0E-3"))),
    COULOMB(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ELECTRIC_CHARGE, "C", "Coulomb", new BigDecimal("1.0E0"))),

    // Energy
    MILLIJOULE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ENERGY, "mJ", "Millijoule", new BigDecimal("1.0E-3"))),
    JOULE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ENERGY, "J", "Joule", new BigDecimal("1.0E0"))),
    KILOJOULE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ENERGY, "kJ", "Kilojoule", new BigDecimal("1.0E3"))),
    MEGAJOULE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ENERGY, "MJ", "Megajoule", new BigDecimal("1.0E6"))),
    CALORY(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ENERGY, "cal", "Calory", new BigDecimal("4.1868"))),
    KILOCALORY(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ENERGY, "kcal", "Kilocalory", new BigDecimal("4186.8"))),
    WATT_SECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ENERGY, "W*s", "Watt second", new BigDecimal("1.0E0"))),
    WATT_HOUR(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ENERGY, "W*h", "Watt hour", new BigDecimal("3600"))),
    KILOWATT_SECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ENERGY, "kW*s", "Kilowatt second", new BigDecimal("1000"))),
    KILOWATT_HOUR(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ENERGY, "kW*h", "Kilowatt hour", new BigDecimal("3600000"))),

    // Force
    NEWTON(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.FORCE, "N", "Newton", new BigDecimal("1.0"))),
    KILOGRAM_FORCE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.FORCE, "kp", "Kilogram-Force", new BigDecimal("9.80665"))),
    POUND_FORCE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.FORCE, "lbf", "Pound-Force", new BigDecimal("4.4482216153"))),

    // Humidity
    PERCENTAGE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.HUMIDITY, "%", "Percentage", BigDecimal.valueOf(1.0))),

    // Acceleration
    METER_PER_SQUARE_SECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ACCELERATION, "m/s\u00b2", "Meter per squaresecond", new BigDecimal("1.0E0"))),
    INCH_PER_SQUARE_SECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ACCELERATION, "in/s\u00b2", "Inch per squaresecond", new BigDecimal("0.0254"))),
    GRAVITY(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.ACCELERATION, "g", "Gravity", new BigDecimal("9.80665"))),

    // Pressure
    MILLIPASCAL(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.PRESSURE, "mPa", "Millipascal", new BigDecimal("1.0E-3"))),
    PASCAL(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.PRESSURE, "Pa", "Pascal", new BigDecimal("1.0E0"))),
    HECTOPASCAL(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.PRESSURE, "hPa", "Hectopascal", new BigDecimal("1.0E2"))),
    KILOPASCAL(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.PRESSURE, "kPa", "Kilopascal", new BigDecimal("1.0E3"))),
    BAR(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.PRESSURE, "bar", "Bar", new BigDecimal("1.0E5"))),
    MILLIBAR(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.PRESSURE, "mbar", "Millibar", new BigDecimal("1.0E2"))),
    TORR(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.PRESSURE, "Torr", "Torr", new BigDecimal("133.322368421"))),
    PSI(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.PRESSURE, "psi", "Pound per Square Inch", new BigDecimal("6894.757293178"))),
    PSF(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.PRESSURE, "psf", "Pound per Square Foot", new BigDecimal("47.88026"))),
    ATMOSPHERE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.PRESSURE, "atm", "Atmosphere", new BigDecimal("101325.0"))),

    // Torque
    NEWTON_METER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TORQUE, "Nm", "Newton Meter", new BigDecimal("1.0"))),
    METER_KILOGRAM(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TORQUE, "m kg", "Meter Kilogram", new BigDecimal("0.101971621"))),
    FOOT_POUND_FORCE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TORQUE, "ft lbf", "Foot-Pound Force", new BigDecimal("1.3558179483"))),
    INCH_POUND_FORCE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.TORQUE, "in lbf", "Inch-Pound Force", new BigDecimal("0.112984829"))),

    // Data
    BIT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "b", "Bit", new BigDecimal("1.0"))),
    KILOBIT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Kb", "KiloBit", new BigDecimal(String.valueOf(1024)))),
    MEGABIT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Mb", "Megabit", new BigDecimal(String.valueOf(Math.pow(1024, 2))))),
    GIGABIT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Gb", "Gigabit", new BigDecimal(String.valueOf(Math.pow(1024, 3))))),
    TERABIT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Tb", "Terabit", new BigDecimal(String.valueOf(Math.pow(1024, 4))))),
    PETABIT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Pb", "Petabit", new BigDecimal(String.valueOf(Math.pow(1024, 5))))),
    EXABIT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Eb", "Exabit", new BigDecimal(String.valueOf(Math.pow(1024, 6))))),
    ZETABIT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Zb", "Zetabit", new BigDecimal(String.valueOf(Math.pow(1024, 7))))),
    YOTABIT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Yb", "Yotabit", new BigDecimal(String.valueOf(Math.pow(1024, 8))))),
    BYTE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "B", "Byte", new BigDecimal("8"))),
    KILOBYTE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "KB", "Kilobyte", new BigDecimal(String.valueOf(8 * 1024)))),
    MEGABYTE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "MB", "Megabyte", new BigDecimal(String.valueOf(8 * Math.pow(1024, 2))))),
    GIGABYTE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "GB", "Gigabyte", new BigDecimal(String.valueOf(8 * Math.pow(1024, 3))))),
    TERABYTE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "TB", "Terabyte", new BigDecimal(String.valueOf(8 * Math.pow(1024, 4))))),
    PETABYTE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "PB", "Petabyte", new BigDecimal(String.valueOf(8 * Math.pow(1024, 5))))),
    EXABYTE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "EB", "Exabyte", new BigDecimal(String.valueOf(8 * Math.pow(1024, 6))))),
    ZETABYTE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "ZB", "Zetabyte", new BigDecimal(String.valueOf(8 * Math.pow(1024, 7))))),
    YOTABYTE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "YB", "Yotabyte", new BigDecimal(String.valueOf(8 * Math.pow(1024, 8))))),
    // Base 1000
    KILOBIT_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Kb", "KiloBit", new BigDecimal(String.valueOf(1000)))),
    MEGABIT_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Mb", "Megabit", new BigDecimal(String.valueOf(Math.pow(1000, 2))))),
    GIGABIT_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Gb", "Gigabit", new BigDecimal(String.valueOf(Math.pow(1000, 3))))),
    TERABIT_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Tb", "Terabit", new BigDecimal(String.valueOf(Math.pow(1000, 4))))),
    PETABIT_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Pb", "Petabit", new BigDecimal(String.valueOf(Math.pow(1000, 5))))),
    EXABIT_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Eb", "Exabit", new BigDecimal(String.valueOf(Math.pow(1000, 6))))),
    ZETABIT_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Zb", "Zetabit", new BigDecimal(String.valueOf(Math.pow(1000, 7))))),
    YOTABIT_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "Yb", "Yotabit", new BigDecimal(String.valueOf(Math.pow(1000, 8))))),
    KILOBYTE_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "KB", "Kilobyte", new BigDecimal(String.valueOf(8 * 1000)))),
    MEGABYTE_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "MB", "Megabyte", new BigDecimal(String.valueOf(8 * Math.pow(1000, 2))))),
    GIGABYTE_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "GB", "Gigabyte", new BigDecimal(String.valueOf(8 * Math.pow(1000, 3))))),
    TERABYTE_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "TB", "Terabyte", new BigDecimal(String.valueOf(8 * Math.pow(1000, 4))))),
    PETABYTE_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "PB", "Petabyte", new BigDecimal(String.valueOf(8 * Math.pow(1000, 5))))),
    EXABYTE_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "EB", "Exabyte", new BigDecimal(String.valueOf(8 * Math.pow(1000, 6))))),
    ZETABYTE_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "ZB", "Zetabyte", new BigDecimal(String.valueOf(8 * Math.pow(1000, 7))))),
    YOTABYTE_B1000(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.DATA, "YB", "Yotabyte", new BigDecimal(String.valueOf(8 * Math.pow(1000, 8))))),

    // Luminance
    CANDELA_SQUARE_METER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LUMINANCE, "cd/m\u00b2", "Candela per Square Meter", new BigDecimal("1.0"))),
    CANDELA_SQUARE_CENTIMETER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LUMINANCE, "cd/cm\u00b2", "Candela per Square CentiMeter", new BigDecimal("10000.0"))),
    CANDELA_SQUARE_INCH(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LUMINANCE, "cd/in\u00b2", "Candela per Square Inch", new BigDecimal("1550.0031"))),
    CANDELA_SQAURE_FOOT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LUMINANCE, "cd/ft\u00b2", "Candela per Square Foot", new BigDecimal("10.7639104167"))),
    LAMBERT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LUMINANCE, "L", "Lambert", new BigDecimal("3183.09886183"))),
    FOOT_LAMBERT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LUMINANCE, "fL", "Footlambert", new BigDecimal("3.4262590996"))),

    // Luminous flux
    LUX(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LUMINOUS_FLUX, "lm/m\u00b2", "Lux", new BigDecimal("1.0"))),
    PHOT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LUMINOUS_FLUX, "lm/cm\u00b2", "Phot", new BigDecimal("10000.0"))),
    FOOT_CANDLE(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LUMINOUS_FLUX, "lm/ft\u00b2", "Footcandle", new BigDecimal("10.7639104167"))),
    LUMEN_SQUARE_INCH(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.LUMINOUS_FLUX, "lm/in\u00b2", "Lumen per Square Inch", new BigDecimal("1550.0031"))),

    // Work
    MILLIWATT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.WORK, "mW", "Milliwatt", new BigDecimal("1.0E-3"))),
    WATT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.WORK, "W", "Watt", new BigDecimal("1.0E0"))),
    KILOWATT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.WORK, "kW", "Kilowatt", new BigDecimal("1.0E3"))),
    MEGAWATT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.WORK, "MW", "Megawatt", new BigDecimal("1.0E6"))),
    GIGAWATT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.WORK, "GW", "Gigawatt", new BigDecimal("1.0E9"))),
    HORSEPOWER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.WORK, "hp", "Horsepower", new BigDecimal("735.49875"))),
    JOULE_PER_SECOND(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.WORK, "J/s", "Joule per second", new BigDecimal("1.0E0"))),

    // Css Units
    PX(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.CSS_UNITS, "px", "Pixel", new BigDecimal("1.0"))),
    PT(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.CSS_UNITS, "pt", "Point", new BigDecimal("0.75"))),

    // Blood Glucose
    MILLIGRAM_PER_DECILITER(new fx.chart.util.unit.Unit(fx.chart.util.unit.Category.BLOOD_GLUCOSE, "mg/dl", "Milligram per deciliter", new BigDecimal("0.0555"))),
    MILLIMOL_PER_LITER(new fx.chart.util.unit.Unit(Category.BLOOD_GLUCOSE, "mmol/l", "Millimols per liter", new BigDecimal("1.0")));


    public final fx.chart.util.unit.Unit UNIT;

    UnitDefinition(final Unit UNIT) {
        this.UNIT = UNIT;
    }
}
