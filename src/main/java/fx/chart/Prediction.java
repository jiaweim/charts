package fx.chart;

import eu.hansolo.toolboxfx.geom.Point;
import fx.chart.data.TYChartItem;
import fx.chart.series.XYSeries;
import fx.chart.tools.Constants.OverlayType;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;


public class Prediction<T extends TYChartItem> implements XYPaneOverlay {

    private LocalDateTime start;
    private ChronoUnit timeStep;
    private XYSeries<T> predictionSeries;
    private XYSeries<T> quantile50Series;
    private XYSeries<T> quantile90Series;

    public Prediction(final LocalDateTime start, final ChronoUnit timeStep, final XYSeries<T> predictionSeries, final XYSeries<T> quantile50Series, final XYSeries<T> quantile90Series) {
        this.start = start;
        this.timeStep = timeStep;
        this.predictionSeries = predictionSeries;
        this.quantile50Series = quantile50Series;
        this.quantile90Series = quantile90Series;
    }


    public LocalDateTime getStart() {return this.start;}

    public ChronoUnit getTimeStep() {return this.timeStep;}

    public XYSeries<T> getPredictionSeries() {return this.predictionSeries;}

    public XYSeries<T> getQuantile50Series() {return this.quantile50Series;}

    public XYSeries<T> getQuantile90Series() {return this.quantile90Series;}

    public List<Point> getQuantile50Points() {
        List<Point> quantile50Points = new LinkedList<>();
        for (int i = 0; i < predictionSeries.getItems().size(); i++) {
            final T pItem = predictionSeries.getItems().get(i);
            final T item = quantile50Series.getItems().get(i);
            final double dY = Math.abs(pItem.getY() - item.getY());
            final Point p = new Point(item.getX(), pItem.getY() - dY);
            quantile50Points.add(p);
        }
        for (int i = predictionSeries.getItems().size() - 1; i >= 0; i--) {
            final T pItem = predictionSeries.getItems().get(i);
            final T item = quantile50Series.getItems().get(i);
            final double dY = Math.abs(pItem.getY() - item.getY());
            final Point p = new Point(item.getX(), pItem.getY() + dY);
            quantile50Points.add(p);
        }
        return quantile50Points;
    }

    public List<Point> getQuantile90Points() {
        List<Point> quantile90Points = new LinkedList<>();
        for (int i = 0; i < predictionSeries.getItems().size(); i++) {
            final T pItem = predictionSeries.getItems().get(i);
            final T item = quantile90Series.getItems().get(i);
            final double dY = Math.abs(pItem.getY() - item.getY());
            final Point p = new Point(item.getX(), pItem.getY() - dY);
            quantile90Points.add(p);
        }
        for (int i = predictionSeries.getItems().size() - 1; i >= 0; i--) {
            final T pItem = predictionSeries.getItems().get(i);
            final T item = quantile90Series.getItems().get(i);
            final double dY = Math.abs(pItem.getY() - item.getY());
            final Point p = new Point(item.getX(), pItem.getY() + dY);
            quantile90Points.add(p);
        }
        return quantile90Points;
    }


    @Override
    public OverlayType getType() {return OverlayType.PREDICTION;}
}
