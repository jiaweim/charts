package fx.chart.wafermap;

import javafx.beans.property.*;

import java.util.HashMap;


public class DefectBuilder<B extends DefectBuilder<B>> {
    private final HashMap<String, ReadOnlyProperty> properties = new HashMap<>();
    private final int id;


    // ******************** Constructors **************************************
    protected DefectBuilder(final int id) {
        this.id = id;
    }


    // ******************** Methods *******************************************
    public static final DefectBuilder create(final int id) {
        return new DefectBuilder(id);
    }

    public final B relX(final double relX) {
        properties.put(DefectRecordField.X_REL.name, new SimpleDoubleProperty(relX));
        return (B) this;
    }

    public final B relY(final double relY) {
        properties.put(DefectRecordField.Y_REL.name, new SimpleDoubleProperty(relY));
        return (B) this;
    }

    public final B relXY(final double xRel, final double yRel) {
        properties.put("xRel", new SimpleDoubleProperty(xRel));
        properties.put("yRel", new SimpleDoubleProperty(yRel));
        return (B) this;
    }

    public final B indexX(final int indexX) {
        properties.put(DefectRecordField.X_INDEX.name, new SimpleIntegerProperty(indexX));
        return (B) this;
    }

    public final B indexY(final int indexY) {
        properties.put(DefectRecordField.Y_INDEX.name, new SimpleIntegerProperty(indexY));
        return (B) this;
    }

    public final B indexXY(final int indexX, final int indexY) {
        properties.put(DefectRecordField.X_INDEX.name, new SimpleIntegerProperty(indexX));
        properties.put(DefectRecordField.Y_INDEX.name, new SimpleIntegerProperty(indexY));
        return (B) this;
    }

    public final B sizeX(final double sizeX) {
        properties.put(DefectRecordField.X_SIZE.name, new SimpleDoubleProperty(sizeX));
        return (B) this;
    }

    public final B sizeY(final double sizeY) {
        properties.put(DefectRecordField.Y_SIZE.name, new SimpleDoubleProperty(sizeY));
        return (B) this;
    }

    public final B sizeXY(final double sizeX, final double sizeY) {
        properties.put(DefectRecordField.X_SIZE.name, new SimpleDoubleProperty(sizeX));
        properties.put(DefectRecordField.Y_SIZE.name, new SimpleDoubleProperty(sizeY));
        return (B) this;
    }

    public final B absoluteX(final double absoluteX) {
        properties.put("absoluteX", new SimpleDoubleProperty(absoluteX));
        return (B) this;
    }

    public final B absoluteY(final double absoluteY) {
        properties.put("absoluteY", new SimpleDoubleProperty(absoluteY));
        return (B) this;
    }

    public final B absoluteXY(final double absoluteX, final double absoluteY) {
        properties.put("absoluteX", new SimpleDoubleProperty(absoluteX));
        properties.put("absoluteY", new SimpleDoubleProperty(absoluteY));
        return (B) this;
    }

    public final B defectArea(final double defectArea) {
        properties.put(DefectRecordField.DEFECT_AREA.name, new SimpleDoubleProperty(defectArea));
        return (B) this;
    }

    public final B sizeD(final double sizeD) {
        properties.put(DefectRecordField.D_SIZE.name, new SimpleDoubleProperty(sizeD));
        return (B) this;
    }

    public final B classNumber(final int classNumber) {
        properties.put(DefectRecordField.CLASS_NUMBER.name, new SimpleIntegerProperty(classNumber));
        return (B) this;
    }

    public final B roughBinNumber(final int roughBinNumber) {
        properties.put(DefectRecordField.ROUGH_BIN_NUMBER.name, new SimpleIntegerProperty(roughBinNumber));
        return (B) this;
    }

    public final B fineBinNumber(final int fineBinNumber) {
        properties.put(DefectRecordField.FINE_BIN_NUMBER.name, new SimpleIntegerProperty(fineBinNumber));
        return (B) this;
    }

    public final B test(final int test) {
        properties.put(DefectRecordField.TEST.name, new SimpleIntegerProperty(test));
        return (B) this;
    }

    public final B clusterNumber(final int clusterNumber) {
        properties.put(DefectRecordField.CLUSTER_NUMBER.name, new SimpleIntegerProperty(clusterNumber));
        return (B) this;
    }

    public final B imageCount(final int imageCount) {
        properties.put(DefectRecordField.IMAGE_COUNT.name, new SimpleIntegerProperty(imageCount));
        return (B) this;
    }

    public final B imageList(final String... imageList) {
        properties.put(DefectRecordField.IMAGE_LIST.name, new SimpleObjectProperty(imageList));
        return (B) this;
    }


    public final Defect build() {
        final double xRel = properties.containsKey(DefectRecordField.X_REL.name) ? (((DoubleProperty) properties.get(DefectRecordField.X_REL.name)).get()) : -1;
        final double yRel = properties.containsKey(DefectRecordField.Y_REL.name) ? (((DoubleProperty) properties.get(DefectRecordField.Y_REL.name)).get()) : -1;
        final int indexX = properties.containsKey(DefectRecordField.X_INDEX.name) ? (((IntegerProperty) properties.get(DefectRecordField.X_INDEX.name)).get()) : -1;
        final int indexY = properties.containsKey(DefectRecordField.Y_INDEX.name) ? (((IntegerProperty) properties.get(DefectRecordField.Y_INDEX.name)).get()) : -1;
        final double absoluteX = properties.containsKey("absoluteX") ? (((DoubleProperty) properties.get("absoluteX")).get()) : -1;
        final double absoluteY = properties.containsKey("absoluteY") ? (((DoubleProperty) properties.get("absoluteY")).get()) : -1;
        final double sizeX = properties.containsKey(DefectRecordField.X_SIZE.name) ? (((DoubleProperty) properties.get(DefectRecordField.X_SIZE.name)).get()) : -1;
        final double sizeY = properties.containsKey(DefectRecordField.Y_SIZE.name) ? (((DoubleProperty) properties.get(DefectRecordField.Y_SIZE.name)).get()) : -1;
        final double defectArea = properties.containsKey(DefectRecordField.DEFECT_AREA.name) ? (((DoubleProperty) properties.get(DefectRecordField.DEFECT_AREA.name)).get()) : -1;
        final double sizeD = properties.containsKey(DefectRecordField.D_SIZE.name) ? (((DoubleProperty) properties.get(DefectRecordField.D_SIZE.name)).get()) : -1;
        final int classNumber = properties.containsKey(DefectRecordField.CLASS_NUMBER.name) ? (((IntegerProperty) properties.get(DefectRecordField.CLASS_NUMBER.name)).get()) : 0;
        final int roughBinNumber = properties.containsKey(DefectRecordField.ROUGH_BIN_NUMBER.name) ? (((IntegerProperty) properties.get(DefectRecordField.ROUGH_BIN_NUMBER.name)).get()) : 0;
        final int fineBinNumber = properties.containsKey(DefectRecordField.FINE_BIN_NUMBER.name) ? (((IntegerProperty) properties.get(DefectRecordField.FINE_BIN_NUMBER.name)).get()) : 0;
        final int test = properties.containsKey(DefectRecordField.TEST.name) ? (((IntegerProperty) properties.get(DefectRecordField.TEST.name)).get()) : 0;
        final int clusterNumber = properties.containsKey(DefectRecordField.CLUSTER_NUMBER.name) ? (((IntegerProperty) properties.get(DefectRecordField.CLUSTER_NUMBER.name)).get()) : 0;
        final int imageCount = properties.containsKey(DefectRecordField.IMAGE_COUNT.name) ? (((IntegerProperty) properties.get(DefectRecordField.IMAGE_COUNT.name)).get()) : 0;
        final String[] imageFilenames = properties.containsKey(DefectRecordField.IMAGE_LIST.name) ? (((ObjectProperty<String[]>) properties.get(DefectRecordField.IMAGE_LIST.name)).get()) : new String[]{};


        final Defect defect = new Defect(this.id, xRel, yRel, indexX, indexY, sizeX, sizeY, defectArea, sizeD, classNumber, roughBinNumber, fineBinNumber, test, clusterNumber, imageCount, imageFilenames);
        return defect;
    }
}
