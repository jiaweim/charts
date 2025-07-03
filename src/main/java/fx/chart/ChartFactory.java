package fx.chart;

import fx.chart.data.XYChartItem;
import fx.chart.series.XYSeries;
import fx.chart.series.XYSeriesBuilder;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 03 Jul 2025, 3:46 PM
 */
public final class ChartFactory {

    /**
     * Creates a line chart
     *
     * @param xAxisLabel x-axis label
     * @param yAxisLabel y-axis label
     * @param dataset    {@link XYSeries}
     * @return {@link XYChartItem}
     */
    public static XYChart<XYChartItem> createXYLineChart(
            String xAxisLabel, double minX, double maxX, int xDecimal,
            String yAxisLabel, double minY, double maxY, int yDecimal,
            Color lineColor, List<XYChartItem> dataset) {

        XYSeries series = XYSeriesBuilder.create()
                .items(dataset)
                .chartType(ChartType.LINE)
                .fill(Color.TRANSPARENT)
                .stroke(lineColor)
                .symbolsVisible(false)
                .build();
        Axis leftAxis = Axis.left(minY, maxY, 36.);
        leftAxis.setTitle(yAxisLabel);
        leftAxis.setDecimals(yDecimal);
        leftAxis.setTickLabelFontSize(12);
        leftAxis.setAutoFontSize(false);

        Axis bottomAxis = Axis.bottom(minX, maxX, 36.);
        bottomAxis.setTitle(xAxisLabel);
        bottomAxis.setDecimals(xDecimal);
        bottomAxis.setTickLabelFontSize(12);
        bottomAxis.setAutoFontSize(false);

        XYPane pane = new XYPane(series);

        XYChart<XYChartItem> chart = new XYChart<>(pane, leftAxis, bottomAxis);
        Grid grid = new Grid(leftAxis, bottomAxis);
        grid.setMinorGridLinesVisible(false);
        grid.setMediumGridLineVisible(false);
        grid.setGridLineDashes(2.0, 2.0);
        chart.setGrid(grid);

        return chart;
    }

}
