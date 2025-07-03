package fx.chart;

import fx.chart.data.XYChartItem;
import javafx.application.Application;
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
 * @since 03 Jul 2025, 4:15 PM
 */
public class NormalDistributionDemo2 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        NormalDistributionFunc2D func2D = new NormalDistributionFunc2D(0, 1);
        List<Point2D> samples = func2D.sample(-5, 5, 101);
        List<XYChartItem> list = new ArrayList<>(samples.size());
        for (Point2D sample : samples) {
            list.add(new XYChartItem(sample.getX(), sample.getY()));
        }

        XYChart<XYChartItem> chart = ChartFactory.createXYLineChart("", -5, 5, 0,
                "", 0, 0.5, 2,
                Color.RED, list);
        StackPane root = new StackPane(chart);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
