package fx.chart;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Jiawei Mao
 * @version 1.0.0
 * @since 07 Jul 2025, 10:02 AM
 */
public class Axis1Test extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        double WIDTH = 20;
        Axis axis = Axis.bottom(-20, 20, WIDTH);
        axis.setZeroColor(Color.RED);
        AnchorPane root = new AnchorPane(axis);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Axis");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
