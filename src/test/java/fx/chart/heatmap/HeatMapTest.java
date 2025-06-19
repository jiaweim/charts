package fx.chart.heatmap;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 19 Jun 2025, 3:56 PM
 */
public class HeatMapTest extends Application {

    private HeatMap heatMap;

    @Override
    public void init() {
        heatMap = new HeatMap(400, 400, ColorMapping.BLUE_RED_YELLOW, 15.5, true, 0.5, OpacityDistribution.EXPONENTIAL);
    }


    @Override
    public void start(Stage stage) throws Exception {
        StackPane pane = new StackPane(heatMap, new Label("Click to add spot"));
        heatMap.addSpot(100, 100);
        heatMap.addSpot(105, 105);
        heatMap.addSpot(200, 200);
        heatMap.addSpot(200, 200);

        Scene scene = new Scene(pane, 400, 400);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> heatMap.addSpot(e.getSceneX(), e.getSceneY()));

        stage.setTitle("HeatMap");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}