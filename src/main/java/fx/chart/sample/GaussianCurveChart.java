package fx.chart.sample;

import fx.chart.Axis;
import fx.chart.ChartType;
import fx.chart.XYChart;
import fx.chart.XYPane;
import fx.chart.data.XYChartItem;
import fx.chart.series.XYSeries;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pdk.util.data.Point2D;
import pdk.util.data.func.NormalDistributionFunc;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 28 Jul 2025, 4:03 PM
 */
public class GaussianCurveChart extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        NormalDistributionFunc f1 = new NormalDistributionFunc(0.0, 1.0);
        NormalDistributionFunc f2 = new NormalDistributionFunc(0.0, Math.sqrt(0.2));
        NormalDistributionFunc f3 = new NormalDistributionFunc(0.0, Math.sqrt(5.0));
        NormalDistributionFunc f4 = new NormalDistributionFunc(-2.0, Math.sqrt(0.5));

        Color[] plotly = new Color[]{
                Color.rgb(99, 110, 250),
                Color.rgb(239, 85, 59),
                Color.rgb(0, 204, 150),
                Color.rgb(171, 99, 250)
        };

        XYSeries<XYChartItem> s1 = createSeries(f1, plotly[0], "N1");
        s1.setStrokeWidth(1.5);

        XYSeries<XYChartItem> s2 = createSeries(f2, plotly[1], "N2");
        s2.setLineDashes(6.0, 4.0);

        XYSeries<XYChartItem> s3 = createSeries(f3, plotly[2], "N3");
        s3.setLineDashes(6.0, 4.0, 3.0, 3.0);

        XYSeries<XYChartItem> s4 = createSeries(f4, plotly[3], "N4");
        s4.setLineDashes(4.0, 4.0);

        double width = 60;

        Axis leftAxis = Axis.left(0, 1.0, width);
        leftAxis.setTitle("Y");
        leftAxis.setDecimals(1);
        leftAxis.setTickLabelFontSize(12);
        leftAxis.setAutoTickLabelFontSize(false);

        Axis bottomAxis = Axis.bottom(-5.5, 5.5, width);
        bottomAxis.setTitle("X");
        bottomAxis.setDecimals(1);
        bottomAxis.setTickLabelFontSize(12);
        bottomAxis.setAutoTickLabelFontSize(false);

        XYPane<XYChartItem> pane = new XYPane<>(s1, s2, s3, s4);
        XYChart<XYChartItem> chart = new XYChart<>(pane, leftAxis, bottomAxis);

        StackPane root = new StackPane(chart);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Gaussian Curves");
        primaryStage.show();
    }

    private static XYSeries<XYChartItem> createSeries(NormalDistributionFunc func, Color color, String name) {

        List<XYChartItem> items = new ArrayList<>();
        for (Point2D sample : func.sample(-5.1, 5.1, 121)) {
            items.add(new XYChartItem(sample.getX(), sample.getY()));
        }

        return new XYSeries.Builder<XYChartItem>()
                .name(name)
                .items(items)
                .chartType(ChartType.LINE)
                .stroke(color)
                .strokeWidth(2)
                .symbolsVisible(false)
                .build();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
