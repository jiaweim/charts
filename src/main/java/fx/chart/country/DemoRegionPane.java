package fx.chart.country;

import fx.chart.toolboxfx.geom.Poi;
import fx.chart.toolboxfx.geom.PoiBuilder;
import fx.chart.toolboxfx.geom.PoiSize;
import fx.chart.toolboxfx.geom.Point;
import fx.chart.country.tools.*;
import fx.chart.heatmap.OpacityDistribution;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;


public class DemoRegionPane extends Application {

    private RegionPane regionPane;
    private List<Point> heatmapSpots;

    @Override
    public void init() {
        CRegion region = BusinessRegion.EUROPEAN_UNION;
        List<Poi> capitals = Helper.getCapitals()
                .stream()
                .filter(city -> region.getCountries().contains(city.country()))
                .map(city -> PoiBuilder.create().lat(city.lat()).lon(city.lon()).name(city.name()).fill(Color.CYAN).pointSize(PoiSize.NORMAL).build())
                .collect(Collectors.toList());

        CLocation fmo = CLocationBuilder.create().name("FMO").latitude(52.1307).longitude(7.6941).connectionPartType(ConnectionPartType.SOURCE).build();
        CLocation arn = CLocationBuilder.create().name("ARN").latitude(59.6498).longitude(17.9238).connectionPartType(ConnectionPartType.TARGET).build();
        CLocation mad = CLocationBuilder.create().name("MAD").latitude(40.4983).longitude(-3.5676).connectionPartType(ConnectionPartType.SOURCE).build();
        CLocation lis = CLocationBuilder.create().name("LIS").latitude(38.7756).longitude(-9.1354).connectionPartType(ConnectionPartType.SOURCE).build();
        Connection madToArn = ConnectionBuilder.create(mad, arn).arrowsVisible(true).lineWidth(2).stroke(Color.MAGENTA).build();
        Connection lisToArn = ConnectionBuilder.create(lis, arn).arrowsVisible(true).lineWidth(2).stroke(Color.YELLOW).build();
        Connection fmoToArn = ConnectionBuilder.create(fmo, arn).arrowsVisible(true).lineWidth(2).stroke(Color.CYAN).build();

        heatmapSpots = Helper.getCities()
                .stream()
                .filter(city -> region.getCountries().contains(city.country()))
                .filter(city -> !city.isCapital())
                .filter(city -> city.population() > 200_000)
                .map(city -> new Point(city.lon(), city.lat())) // keep in mind that longitude = x and latitude = y
                .collect(Collectors.toList());

        regionPane = RegionPaneBuilder.create(region)
                .poisVisible(true)
                .poiTextVisible(true)
                .heatmapVisible(true)
                .heatmapSpotRadius(5)
                .heatmapOpacityDistribution(OpacityDistribution.LINEAR)
                .heatmapSpots(heatmapSpots)
                .pois(capitals)
                .connections(List.of(fmoToArn, madToArn, lisToArn))
                .overlayVisible(true)
                .hoverEnabled(true)
                .selectionEnabled(true)
                .build();

        regionPane.selectedCountryProperty().addListener((o, ov, nv) -> System.out.println(nv));
    }

    @Override
    public void start(final Stage stage) {
        StackPane pane = new StackPane(regionPane);
        pane.setPrefSize(600, 400);
        pane.setPadding(new Insets(10));

        Scene scene = new Scene(pane);

        stage.setTitle("RegionPane:");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }


    @Override
    public void stop() {
        // Remove event handlers

        // Shutdown
        Platform.exit();
        System.exit(0);
    }


    // ******************** Launching *******************************
    public static void main(final String[] args) {
        launch(args);
    }
}
