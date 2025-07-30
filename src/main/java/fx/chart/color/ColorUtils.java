package fx.chart.color;

import javafx.animation.Interpolator;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;

import java.util.*;

/**
 * Color utilities
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 03 Jul 2025, 3:48 PM
 */
public final class ColorUtils {

    /**
     * Get the color with given opacity
     *
     * @param color   the original color
     * @param opacity the new opacity
     * @return new {@link Color}
     */
    public static Color getColorWithOpacity(final Color color, final double opacity) {
        double red = color.getRed();
        double green = color.getGreen();
        double blue = color.getBlue();
        double op = Math.clamp(opacity, 0, 1);
        return Color.color(red, green, blue, op);
    }

    /**
     * convert RGB to HSL
     *
     * @param red   ref
     * @param green green
     * @param blue  blue
     * @return HSL
     */
    public static double[] rgbToHSL(final double red, final double green, final double blue) {
        //	Minimum and Maximum RGB values are used in the HSL calculations
        double min = Math.min(red, Math.min(green, blue));
        double max = Math.max(red, Math.max(green, blue));

        //  Calculate the Hue
        double hue = 0;

        if (max == min) {
            hue = 0;
        } else if (max == red) {
            hue = ((60 * (green - blue) / (max - min)) + 360) % 360;
        } else if (max == green) {
            hue = (60 * (blue - red) / (max - min)) + 120;
        } else if (max == blue) {
            hue = (60 * (red - green) / (max - min)) + 240;
        }

        //  Calculate the Luminance
        double luminance = (max + min) / 2;

        //  Calculate the Saturation
        double saturation = 0;
        if (Double.compare(max, min) == 0) {
            saturation = 0;
        } else if (luminance <= .5) {
            saturation = (max - min) / (max + min);
        } else {
            saturation = (max - min) / (2 - max - min);
        }

        return new double[]{hue, saturation, luminance};
    }

    public static double[] toHSL(final Color color) {
        return rgbToHSL(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static Color hslToRGB(double hue, double saturation, double luminance) {
        return hslToRGB(hue, saturation, luminance, 1);
    }

    public static Color hslToRGB(double hue, double saturation, double luminance, double opacity) {
        saturation = Math.clamp(saturation, 0, 1);
        luminance = Math.clamp(luminance, 0, 1);
        opacity = Math.clamp(opacity, 0, 1);

        hue = hue % 360.0;
        hue /= 360;

        double q = luminance < 0.5 ? luminance * (1 + saturation) : (luminance + saturation) - (saturation * luminance);
        double p = 2 * luminance - q;

        double r = Math.clamp(hueToRGB(p, q, hue + (1.0 / 3.0)), 0, 1);
        double g = Math.clamp(hueToRGB(p, q, hue), 0, 1);
        double b = Math.clamp(hueToRGB(p, q, hue - (1.0 / 3.0)), 0, 1);

        return Color.color(r, g, b, opacity);
    }

    private static double hueToRGB(double p, double q, double t) {
        if (t < 0) t += 1;
        if (t > 1) t -= 1;
        if (6 * t < 1) {
            return p + ((q - p) * 6 * t);
        }
        if (2 * t < 1) {
            return q;
        }
        if (3 * t < 2) {
            return p + ((q - p) * 6 * ((2.0 / 3.0) - t));
        }
        return p;
    }

    public static Color hsbToRGB(final double hue, final double saturation, final double brightness) {
        int r = 0, g = 0, b = 0;
        if (saturation == 0) {
            r = g = b = (int) (brightness * 255.0f + 0.5f);
        } else {
            double h = (hue - Math.floor(hue)) * 6.0;
            double f = h - Math.floor(h);
            double p = brightness * (1.0 - saturation);
            double q = brightness * (1.0 - saturation * f);
            double t = brightness * (1.0 - (saturation * (1.0 - f)));
            switch ((int) h) {
                case 0:
                    r = (int) (brightness * 255.0 + 0.5);
                    g = (int) (t * 255.0 + 0.5);
                    b = (int) (p * 255.0 + 0.5);
                    break;
                case 1:
                    r = (int) (q * 255.0 + 0.5);
                    g = (int) (brightness * 255.0 + 0.5);
                    b = (int) (p * 255.0 + 0.5);
                    break;
                case 2:
                    r = (int) (p * 255.0 + 0.5);
                    g = (int) (brightness * 255.0 + 0.5);
                    b = (int) (t * 255.0 + 0.5);
                    break;
                case 3:
                    r = (int) (p * 255.0 + 0.5);
                    g = (int) (q * 255.0 + 0.5);
                    b = (int) (brightness * 255.0 + 0.5);
                    break;
                case 4:
                    r = (int) (t * 255.0 + 0.5);
                    g = (int) (p * 255.0 + 0.5);
                    b = (int) (brightness * 255.0 + 0.5);
                    break;
                case 5:
                    r = (int) (brightness * 255.0 + 0.5);
                    g = (int) (p * 255.0 + 0.5);
                    b = (int) (q * 255.0 + 0.5);
                    break;
            }
        }
        return Color.rgb(r, g, b);
    }

    public static double[] colorToHSB(final Color color) {
        int r = (int) (color.getRed() * 255.0);
        int g = (int) (color.getGreen() * 255.0);
        int b = (int) (color.getBlue() * 255.0);
        double[] hsbValues = new double[3];
        double hue;
        double saturation;
        double brightness;

        int cmax = (r > g) ? r : g;
        if (b > cmax) {
            cmax = b;
        }
        int cmin = (r < g) ? r : g;
        if (b < cmin) {
            cmin = b;
        }

        brightness = ((double) cmax) / 255.0;
        if (cmax != 0) {
            saturation = ((float) (cmax - cmin)) / ((double) cmax);
        } else {
            saturation = 0;
        }
        if (saturation == 0) {
            hue = 0;
        } else {
            double redc = ((double) (cmax - r)) / ((double) (cmax - cmin));
            double greenc = ((double) (cmax - g)) / ((double) (cmax - cmin));
            double bluec = ((double) (cmax - b)) / ((double) (cmax - cmin));
            if (r == cmax) {
                hue = bluec - greenc;
            } else if (g == cmax) {
                hue = 2.0 + redc - bluec;
            } else {
                hue = 4.0 + greenc - redc;
            }
            hue = hue / 6.0;
            if (hue < 0) {
                hue = hue + 1.0;
            }
        }
        hsbValues[0] = hue;
        hsbValues[1] = saturation;
        hsbValues[2] = brightness;
        return hsbValues;
    }

    /**
     * convert javafx {@link Color} to rgb
     *
     * @param color {@link Color} instance
     * @return
     */
    public static String colorToRGB(final Color color) {
        String hex = color.toString().replace("0x", "");
        String hexRed = hex.substring(0, 2).toUpperCase();
        String hexGreen = hex.substring(2, 4).toUpperCase();
        String hexBlue = hex.substring(4, 6).toUpperCase();

        String intRed = Integer.toString(Integer.parseInt(hexRed, 16));
        String intGreen = Integer.toString(Integer.parseInt(hexGreen, 16));
        String intBlue = Integer.toString(Integer.parseInt(hexBlue, 16));

        return String.join("", "colorToRGB(", intRed, ", ", intGreen, ", ", intBlue, ")");
    }

    public static String colorToRGBA(final Color COLOR) {return colorToRGBA(COLOR, COLOR.getOpacity());}

    public static String colorToRGBA(final Color COLOR, final double ALPHA) {
        String hex = COLOR.toString().replace("0x", "");
        String hexRed = hex.substring(0, 2).toUpperCase();
        String hexGreen = hex.substring(2, 4).toUpperCase();
        String hexBlue = hex.substring(4, 6).toUpperCase();

        String intRed = Integer.toString(Integer.parseInt(hexRed, 16));
        String intGreen = Integer.toString(Integer.parseInt(hexGreen, 16));
        String intBlue = Integer.toString(Integer.parseInt(hexBlue, 16));
        String alpha = String.format(Locale.US, "%.3f", Math.clamp(ALPHA, 0, 1));

        return String.join("", "colorToRGBA(", intRed, ", ", intGreen, ", ", intBlue, ",", alpha, ")");
    }


    public static String colorToWeb(final Color COLOR) {
        return COLOR.toString().replace("0x", "#").substring(0, 7);
    }

    public static double[] colorToYUV(final Color COLOR) {
        final double WEIGHT_FACTOR_RED = 0.299;
        final double WEIGHT_FACTOR_GREEN = 0.587;
        final double WEIGHT_FACTOR_BLUE = 0.144;
        final double U_MAX = 0.436;
        final double V_MAX = 0.615;
        double y = Math.clamp(WEIGHT_FACTOR_RED * COLOR.getRed() + WEIGHT_FACTOR_GREEN * COLOR.getGreen() + WEIGHT_FACTOR_BLUE * COLOR.getBlue(), 0, 1);
        double u = Math.clamp(U_MAX * ((COLOR.getBlue() - y) / (1 - WEIGHT_FACTOR_BLUE)), -U_MAX, U_MAX);
        double v = Math.clamp(V_MAX * ((COLOR.getRed() - y) / (1 - WEIGHT_FACTOR_RED)), -V_MAX, V_MAX);
        return new double[]{y, u, v};
    }

    public static boolean isBright(final Color COLOR) {return Double.compare(colorToYUV(COLOR)[0], 0.5) >= 0.0;}

    public static boolean isDark(final Color COLOR) {return colorToYUV(COLOR)[0] < 0.5;}

    public static Color getContrastColor(final Color COLOR) {
        return COLOR.getBrightness() > 0.5 ? Color.BLACK : Color.WHITE;
    }

    public static List<Color> createColorPalette(final Color FROM_COLOR, final Color TO_COLOR, final int NO_OF_COLORS) {
        int steps = Math.clamp(NO_OF_COLORS, 1, 50) - 1;
        double step = 1.0 / steps;
        double deltaRed = (TO_COLOR.getRed() - FROM_COLOR.getRed()) * step;
        double deltaGreen = (TO_COLOR.getGreen() - FROM_COLOR.getGreen()) * step;
        double deltaBlue = (TO_COLOR.getBlue() - FROM_COLOR.getBlue()) * step;
        double deltaOpacity = (TO_COLOR.getOpacity() - FROM_COLOR.getOpacity()) * step;

        List<Color> palette = new ArrayList<>(NO_OF_COLORS);
        Color currentColor = FROM_COLOR;
        palette.add(currentColor);
        for (int i = 0; i < steps; i++) {
            double red = Math.clamp((currentColor.getRed() + deltaRed), 0d, 1d);
            double green = Math.clamp((currentColor.getGreen() + deltaGreen), 0d, 1d);
            double blue = Math.clamp((currentColor.getBlue() + deltaBlue), 0d, 1d);
            double opacity = Math.clamp((currentColor.getOpacity() + deltaOpacity), 0d, 1d);
            currentColor = Color.color(red, green, blue, opacity);
            palette.add(currentColor);
        }
        return palette;
    }

    public static Color getComplementaryColor(final Color COLOR) {
        return Color.hsb(COLOR.getHue() + 180, COLOR.getSaturation(), COLOR.getBrightness());
    }

    public static Color getColorAt(final LinearGradient GRADIENT, final double FRACTION) {
        List<Stop> stops = GRADIENT.getStops();
        double fraction = FRACTION < 0f ? 0f : (FRACTION > 1 ? 1 : FRACTION);
        Stop lowerStop = new Stop(0.0, stops.get(0).getColor());
        Stop upperStop = new Stop(1.0, stops.get(stops.size() - 1).getColor());

        for (Stop stop : stops) {
            double currentFraction = stop.getOffset();
            if (Double.compare(currentFraction, fraction) == 0) {
                return stop.getColor();
            } else if (Double.compare(currentFraction, fraction) < 0) {
                lowerStop = new Stop(currentFraction, stop.getColor());
            } else {
                upperStop = new Stop(currentFraction, stop.getColor());
                break;
            }
        }

        double interpolationFraction = (fraction - lowerStop.getOffset()) / (upperStop.getOffset() - lowerStop.getOffset());
        return (Color) Interpolator.LINEAR.interpolate(lowerStop.getColor(), upperStop.getColor(), interpolationFraction);
    }


    public static Color getColorAt(final List<Stop> stopList, final double positionOfColor) {
        Map<Double, Stop> stops = new TreeMap<>();
        for (Stop stop : stopList) {
            stops.put(stop.getOffset(), stop);
        }

        if (stops.isEmpty()) return Color.BLACK;

        double minFraction = Collections.min(stops.keySet());
        double maxFraction = Collections.max(stops.keySet());

        if (Double.compare(minFraction, 0d) > 0) {
            stops.put(0.0, new Stop(0.0, stops.get(minFraction).getColor()));
        }
        if (Double.compare(maxFraction, 1d) < 0) {
            stops.put(1.0, new Stop(1.0, stops.get(maxFraction).getColor()));
        }

        final double position = Math.clamp(positionOfColor, 0d, 1d);
        final Color color;
        if (stops.size() == 1) {
            final Map<Double, Color> ONE_ENTRY = (Map<Double, Color>) stops.entrySet().iterator().next();
            color = stops.get(ONE_ENTRY.keySet().iterator().next()).getColor();
        } else {
            Stop lowerBound = stops.get(0.0);
            Stop upperBound = stops.get(1.0);
            for (Map.Entry<Double, Stop> entry : stops.entrySet()) {
                final double fraction = entry.getKey();
                final Stop stop = entry.getValue();
                if (Double.compare(fraction, position) < 0) {
                    lowerBound = stop;
                }
                if (Double.compare(fraction, position) > 0) {
                    upperBound = stop;
                    break;
                }
            }
            color = interpolateColor(lowerBound, upperBound, position);
        }
        return color;
    }

    public static Color interpolateColor(final Stop lowerBound, final Stop upperBound, final double position) {
        final double pos = (position - lowerBound.getOffset()) / (upperBound.getOffset() - lowerBound.getOffset());

        final double deltaRed = (upperBound.getColor().getRed() - lowerBound.getColor().getRed()) * pos;
        final double deltaGreen = (upperBound.getColor().getGreen() - lowerBound.getColor().getGreen()) * pos;
        final double deltaBlue = (upperBound.getColor().getBlue() - lowerBound.getColor().getBlue()) * pos;
        final double deltaOpacity = (upperBound.getColor().getOpacity() - lowerBound.getColor().getOpacity()) * pos;

        double red = Math.clamp((lowerBound.getColor().getRed() + deltaRed), 0, 1);
        double green = Math.clamp((lowerBound.getColor().getGreen() + deltaGreen), 0, 1);
        double blue = Math.clamp((lowerBound.getColor().getBlue() + deltaBlue), 0, 1);
        double opacity = Math.clamp((lowerBound.getColor().getOpacity() + deltaOpacity), 0, 1);

        return Color.color(red, green, blue, opacity);
    }
}


