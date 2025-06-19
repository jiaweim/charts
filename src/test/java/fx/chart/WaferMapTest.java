package fx.chart;

import fx.chart.heatmap.ColorMapping;
import fx.chart.wafermap.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Optional;


public class WaferMapTest extends Application {
    private WaferMap wafermap;
    private DieMap dieMap;


    @Override
    public void init() {
        String filename = WaferMapTest.class.getResource("12.KLA").toString().replace("file:", "");
        Optional<KLA> klaOpt = KLAParser.INSTANCE.parse(filename);

        //System.out.println(klaOpt.get());

        wafermap = WaferMapBuilder.create()
                .kla(klaOpt.get())
                .dieTextVisible(true)
                .densityColorsVisible(true)
                .defectsVisible(true)
                .heatmapVisible(true)
                .heatmapColorMapping(ColorMapping.BLUE_CYAN_GREEN_YELLOW_RED)
                .heatmapSpotRadius(7)
                .heatmapOpacity(0.75)
                .waferFill(Color.LIGHTGRAY)
                .waferStroke(Color.BLACK)
                .dieTextFill(Color.BLACK)
                .build();

        dieMap = DieMapBuilder.create()
                .dieTextFill(Color.LIGHTGRAY)
                .dieTextVisible(true)
                .densityColorsVisible(false)
                .build();

        wafermap.selectedDieProperty().addListener(o -> Platform.runLater(() -> dieMap.setDie(wafermap.getSelectedDie())));
    }

    @Override
    public void start(Stage stage) {
        HBox pane = new HBox(20, wafermap, dieMap);
        pane.setPadding(new Insets(10));
        Scene scene = new Scene(pane, Color.DARKGRAY);

        stage.setTitle("Wafermap");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop() {
        wafermap.dispose();
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
