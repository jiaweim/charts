package fx.chart;

import fx.chart.data.XYChartItem;
import fx.chart.series.XYSeries;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import pdk.util.data.Point2D;
import pdk.util.data.func.NormalDistributionFunc2D;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 03 Jul 2025, 1:56 PM
 */
public class NormalDistributionDemo1 extends Application {

    private XYChart<XYChartItem> chart;

    @Override
    public void init() {

        XYSeries<XYChartItem> series = XYSeries.builder()
                .items(createData())
                .chartType(ChartType.LINE)
                .fill(Color.TRANSPARENT)
                .stroke(Color.RED)
                .symbolsVisible(false)
                .build();

        double axisWidth = 36.0;

        Axis left = AxisBuilder.create(Orientation.VERTICAL, Position.LEFT)
                .decimals(2)
                .autoScale(true)
                .minValue(0)
                .maxValue(0.5)
                .prefWidth(30)
                .titleFontSize(12)
                .build();

        Axis bottom = Axis.bottom(-5.0, 5.0, true, axisWidth);

        XYPane pane = new XYPane(series);
        pane.setChartBackground(Color.WHITE);
        chart = new XYChart<>(pane, left, bottom);

        Grid grid = new Grid(left, bottom);
        grid.setGridLineDashes(2.0, 2.0);

        grid.setMinorHGridLinesVisible(false);
        grid.setMediumHGridLinesVisible(false);
        grid.setMinorVGridLinesVisible(false);
        grid.setMediumVGridLinesVisible(false);
        chart.setGrid(grid);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        root.getChildren().add(chart);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Normal Distribution");
        stage.show();
    }

    public static List<XYChartItem> createData() {
        NormalDistributionFunc2D func = new NormalDistributionFunc2D(0.0, 1.0);
        List<Point2D> samples = func.sample(-5.0, 5.0, 100);

        List<XYChartItem> itemList = new ArrayList<>(samples.size());
        for (Point2D p : samples) {
            XYChartItem item = new XYChartItem(p.getX(), p.getY());
            itemList.add(item);
        }
        return itemList;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
