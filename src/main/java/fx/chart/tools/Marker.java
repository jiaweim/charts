package fx.chart.tools;

import fx.chart.Axis;
import fx.chart.Position;
import javafx.scene.paint.Color;


public class Marker {

    private Axis axis;
    private double value;
    private Color stroke;
    private double lineWidth;
    private String text;
    private Color textFill;
    private String formatString;
    private LineStyle lineStyle;


    public Marker(final Axis axis, final double value) {
        this(axis, value, Color.RED, 1, LineStyle.SOLID, "", Color.RED, "%.0f");
    }

    public Marker(final Axis axis, final double value, final Color stroke, final double lineWidth, final LineStyle lineStyle, final String text, final Color textFill, final String formatString) {
        if (null == axis) {
            throw new IllegalArgumentException("Given axis cannot be null");
        }
        if ((Position.LEFT != axis.getPosition() && Position.BOTTOM != axis.getPosition())) {
            throw new IllegalArgumentException("Marker axis position has to be either LEFT or BOTTOM");
        }
        this.axis = axis;
        this.value = Math.clamp(value, axis.getMinValue(), axis.getMaxValue());
        this.stroke = null == stroke ? Color.RED : stroke;
        this.lineWidth = Math.clamp(lineWidth, 1, 10);
        this.text = null == text ? "" : text;
        this.textFill = null == textFill ? Color.RED : textFill;
        this.formatString = null == formatString || formatString.isEmpty() ? "%.0f" : formatString;
        this.lineStyle = null == lineStyle ? LineStyle.SOLID : lineStyle;
    }

    public Axis getAxis() {return axis;}

    public double getValue() {return value;}

    public Color getStroke() {return stroke;}

    public void setStroke(final Color stroke) {this.stroke = null == stroke ? Color.RED : stroke;}

    public double getLineWidth() {return lineWidth;}

    public void setLineWidth(final double lineWidth) {this.lineWidth = Math.clamp(lineWidth, 1, 10);}

    public LineStyle getLineStyle() {return lineStyle;}

    public void setLineStyle(final LineStyle lineStyle) {this.lineStyle = null == lineStyle ? LineStyle.SOLID : lineStyle;}

    public String getText() {return text;}

    public void setText(final String text) {this.text = null == text ? "" : text;}

    public Color getTextFill() {return textFill;}

    public void setTextFill(final Color textFill) {this.textFill = null == textFill ? Color.RED : textFill;}

    public String getFormatString() {return formatString;}

    public void setFormatString(final String formatString) {this.formatString = null == formatString || formatString.isEmpty() ? "%.0f" : formatString;}
}
