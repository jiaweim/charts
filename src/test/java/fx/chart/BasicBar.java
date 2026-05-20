package fx.chart;

import fx.chart.data.ChartItem;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * https://echarts.apache.org/examples/en/editor.html?c=bar-simple
 *
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 20 May 2026, 7:38 PM
 */
public class BasicBar extends Application {

    static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        String[] category = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
        double[] values = new double[]{120, 200, 150, 80, 70, 110, 130};
        ArrayList<ChartItem> items = new ArrayList<>(category.length);
        for (int i = 0; i < category.length; i++) {
            items.add(new ChartItem(category[i], values[i]));
        }

        BarChart<ChartItem> chart = new BarChart<>(items);
        chart.setOrientation(Orientation.VERTICAL);

        StackPane root = new StackPane();
        root.getChildren().add(chart);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
