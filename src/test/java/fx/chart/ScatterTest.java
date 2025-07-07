package fx.chart;

import fx.chart.data.XYChartItem;
import fx.chart.series.XYSeries;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 21 Jun 2025, 15:37
 */
public class ScatterTest extends Application {

    private static final int NO_OF_X_VALUES = 100;
    private static final Double AXIS_WIDTH = 25d;
    private static final Random RND = new Random();
    private static final Color[] COLORS = {Color.rgb(200, 0, 0, 0.75), Color.rgb(0, 0, 200, 0.75), Color.rgb(0, 200, 200, 0.75), Color.rgb(0, 200, 0, 0.75)};

    private XYSeries<XYChartItem> xySeries5;

    private XYChart<XYChartItem> scatterChart;
    private Axis scatterChartXAxisCenter;
    private Axis scatterChartYAxisCenter;

    /**
     * Application initialization method. This method is called immediately after
     * the Application class is loaded and constructed. An application may override this method
     * to perform initialization prior to the actual starting of the application.
     * <p>
     * The implementation of this method provided by the Application class does nothing.
     * <p>
     * This method is not called on the JavaFX Application Thread. An application must not
     * construct a Scene or a Stage in this method. An Application may construct other JavaFX
     * objects in this method.
     */
    @Override
    public void init() {
        List<XYChartItem> xyItems4 = new ArrayList<>(40);
        for (int i = -20; i < 20; i++) {
            xyItems4.add(new XYChartItem(i, RND.nextDouble() * 40 - 20, "P" + i, COLORS[RND.nextInt(3)], "P" + i));
        }

        xySeries5 = XYSeries.builder()
                .items(xyItems4)
                .chartType(ChartType.SCATTER)
                .fill(Color.TRANSPARENT)
                .stroke(Color.MAGENTA)
                .symbolFill(Color.RED)
                .symbolStroke(Color.TRANSPARENT)
                .symbolsVisible(true)
                .build();

        scatterChartXAxisCenter = Axis.centerX(-20, 20, true, 30);
        scatterChartYAxisCenter = Axis.centerY(-20, 20, true, AXIS_WIDTH);
        scatterChart = new XYChart<>(new XYPane(xySeries5), scatterChartYAxisCenter, scatterChartXAxisCenter);
        scatterChart.getXYPane().setCrossHairVisible(false);

//        scatterChartXAxisCenter.setAxisColor(Color.CRIMSON);
//        scatterChartYAxisCenter.setAxisColor(Color.CRIMSON);
        scatterChartXAxisCenter.setAxisColor(Color.BLACK);
        scatterChartYAxisCenter.setAxisColor(Color.BLACK);
//        scatterChartXAxisCenter.setTickLabelFont(Axis.DEFAULT_TICK_LABEL_FONT);
        scatterChartXAxisCenter.setTickLabelFontSize(12);
        scatterChartYAxisCenter.setTickLabelFontSize(16);
        scatterChartXAxisCenter.setAutoTickLabelFontSize(false);

    }

    @Override
    public void start(Stage stage) throws Exception {
        StackPane root = new StackPane();
        root.getChildren().add(scatterChart);
        Scene scene = new Scene(root);
        stage.setTitle("Scatter Chart");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
