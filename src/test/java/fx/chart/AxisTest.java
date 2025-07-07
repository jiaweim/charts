package fx.chart;

import fx.chart.util.unit.Converter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import static fx.chart.util.unit.Category.TEMPERATURE;
import static fx.chart.util.unit.UnitDefinition.CELSIUS;
import static fx.chart.util.unit.UnitDefinition.FAHRENHEIT;


/**
 * User: hansolo
 * Date: 22.07.17
 * Time: 14:12
 */
public class AxisTest extends Application {

    private static final double AXIS_WIDTH = 20;
    private static final double AXIS_HEIGHT = 20;
    private Axis xAxisBottom;
    private Axis xAxisTop;
    private Axis yAxisLeft;
    private Axis yAxisRight;

    @Override
    public void init() {
        xAxisBottom = Axis.bottom(-20, 20, AXIS_WIDTH);
        xAxisTop = Axis.top(0, 100, AXIS_HEIGHT);
        yAxisLeft = Axis.left(-20, 20, AXIS_WIDTH);

        Converter tempConverter = new Converter(TEMPERATURE, CELSIUS); // Type Temperature with BaseUnit Celsius
        double tempFahrenheitMin = tempConverter.convert(-20, FAHRENHEIT);
        double tempFahrenheitMax = tempConverter.convert(20, FAHRENHEIT);
        yAxisRight = Axis.right(tempFahrenheitMin, tempFahrenheitMax, false, AXIS_WIDTH);

        AnchorPane.setTopAnchor(yAxisLeft, AXIS_HEIGHT);
        AnchorPane.setTopAnchor(xAxisTop, 0d);
        AnchorPane.setTopAnchor(yAxisRight, AXIS_HEIGHT);
    }

    @Override
    public void start(Stage stage) {
        AnchorPane pane = new AnchorPane(xAxisBottom, xAxisTop, yAxisLeft, yAxisRight);
        pane.setPadding(new Insets(10));
        pane.setPrefSize(400, 400);

        Scene scene = new Scene(pane);

        stage.setTitle("Axis Test");
        stage.setScene(scene);
        stage.show();

        xAxisTop.setMinValue(50);
        xAxisTop.setMaxValue(150);
        //xAxisTop.setAutoFontSize(true);
        //xAxisTop.setTitleFontSize(20);
    }

    @Override
    public void stop() {
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
