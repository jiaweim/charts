package fx.chart.toolbox;

import java.util.Collections;
import java.util.List;


public class Statistics {
    public static double getMean(final List<Double> data) {return data.stream().mapToDouble(v -> v).sum() / data.size();}

    public static double getVariance(final List<Double> data) {
        double mean = getMean(data);
        double temp = 0;
        for (double a : data) {
            temp += ((a - mean) * (a - mean));
        }
        return temp / data.size();
    }

    public static double getStdDev(final List<Double> data) {return Math.sqrt(getVariance(data));}

    public static double getMedian(final List<Double> data) {
        int size = data.size();
        Collections.sort(data);
        return size % 2 == 0 ? (data.get((size / 2) - 1) + data.get(size / 2)) / 2.0 : data.get(size / 2);
    }

    public static double getMin(final List<Double> data) {return data.stream().mapToDouble(v -> v).min().orElse(0);}

    public static double getMax(final List<Double> data) {return data.stream().mapToDouble(v -> v).max().orElse(0);}

    public static double getAverage(final List<Double> data) {
        return data.stream().mapToDouble(d -> d.doubleValue()).average().orElse(-1);
    }

    public static double percentile(List<Double> entries, double percentile) {
        Collections.sort(entries);
        final int index = (int) Math.ceil(percentile / 100.0 * entries.size());
        return entries.get(index - 1);
    }
}
