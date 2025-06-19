package fx.chart.toolboxfx;

import fx.chart.toolboxfx.evt.type.LocationChangeEvt;
import fx.chart.toolboxfx.geom.Bounds;
import fx.chart.toolboxfx.geom.Location;


public class Demo {

    public Demo() {
        Bounds bounds = new Bounds();
        locationDemo();
    }


    private void locationDemo() {
        Location home = new Location(7.38, 51.51);
        home.addLocationObserver(LocationChangeEvt.LOCATION_CHANGED, e -> System.out.println("Location observer: " + e.getOldLocation() + "\n---------\n" + e.getLocation()));
        home.addLocationObserver(LocationChangeEvt.ALTITUDE_CHANGED, e -> System.out.println("Altitude observer: " + e.getLocation().getAltitude()));
        home.setLatitude(8.0);
    }

    public static void main(String[] args) {
        new Demo();
    }
}
