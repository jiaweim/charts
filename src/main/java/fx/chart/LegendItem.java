package fx.chart;

import fx.chart.font.Fonts;
import fx.chart.util.HelperFX;
import fx.chart.util.Dimension;
import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.collections.ObservableList;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;


/**
 * User: hansolo
 * Date: 05.01.18
 * Time: 20:33
 */
/**
 *
 *
 * @author Jiawei Mao
 * @author Gerrit Grunwald
 * @version 1.0.0
 * @since 2025-07-07, 21:57
 */
@DefaultProperty("children")
public class LegendItem extends Region {

    private static final double PREFERRED_WIDTH = 100;
    private static final double PREFERRED_HEIGHT = 18;
    private static final double MINIMUM_WIDTH = 20;
    private static final double MINIMUM_HEIGHT = 8;
    private static final double MAXIMUM_WIDTH = 1024;
    private static final double MAXIMUM_HEIGHT = 1024;

    private double size;
    private double width;
    private double height;
    private Symbol symbol_;
    private ObjectProperty<Symbol> symbolProperty;
    private String text_;
    private StringProperty textProperty;
    private Color symbolFill_;
    private ObjectProperty<Color> symbolFillProperty;
    private Color symbolStroke_;
    private ObjectProperty<Color> symbolStrokeProperty;
    private Color textColor_;
    private ObjectProperty<Color> textColorProperty;
    private double symbolSize;
    private Canvas canvas;
    private GraphicsContext ctx;
    private Font font;
    private Dimension textDim;
    private Pane pane;

    public LegendItem(final String TEXT, final Color SYMBOL_COLOR) {
        this(Symbol.CIRCLE, TEXT, SYMBOL_COLOR, Color.WHITE, Color.BLACK);
    }

    public LegendItem(final Symbol SYMBOL, final String TEXT, final Color SYMBOL_FILL) {
        this(SYMBOL, TEXT, SYMBOL_FILL, Color.WHITE, Color.BLACK);
    }

    public LegendItem(final Symbol SYMBOL, final String TEXT, final Color SYMBOL_FILL, final Color SYMBOL_STROKE) {
        this(SYMBOL, TEXT, SYMBOL_FILL, SYMBOL_STROKE, Color.BLACK);
    }

    /**
     * Create a {@link LegendItem}
     *
     * @param symbol
     * @param text
     * @param symbolFill
     * @param symbolStroke
     * @param textColor
     */
    public LegendItem(final Symbol symbol, final String text, final Color symbolFill, final Color symbolStroke, final Color textColor) {
        symbol_ = symbol;
        text_ = text;
        symbolFill_ = symbolFill;
        symbolStroke_ = symbolStroke;
        textColor_ = textColor;
        initGraphics();
        registerListeners();
    }


    // ******************** Initialization ************************************
    private void initGraphics() {
        if (Double.compare(getPrefWidth(), 0.0) <= 0 || Double.compare(getPrefHeight(), 0.0) <= 0 || Double.compare(getWidth(), 0.0) <= 0 ||
                Double.compare(getHeight(), 0.0) <= 0) {
            if (getPrefWidth() > 0 && getPrefHeight() > 0) {
                setPrefSize(getPrefWidth(), getPrefHeight());
            } else {
                setPrefSize(PREFERRED_WIDTH, PREFERRED_HEIGHT);
            }
        }

        canvas = new Canvas(PREFERRED_HEIGHT, PREFERRED_HEIGHT);
        ctx = canvas.getGraphicsContext2D();
        ctx.setTextAlign(TextAlignment.LEFT);
        ctx.setTextBaseline(VPos.CENTER);

        pane = new Pane(canvas);

        getChildren().setAll(pane);
    }

    private void registerListeners() {
        widthProperty().addListener(o -> resize());
        heightProperty().addListener(o -> resize());
    }


    // ******************** Methods *******************************************
    @Override
    public void layoutChildren() {
        super.layoutChildren();
    }

    @Override
    protected double computeMinWidth(final double HEIGHT) {return MINIMUM_WIDTH;}

    @Override
    protected double computeMinHeight(final double WIDTH) {return MINIMUM_HEIGHT;}

    @Override
    protected double computePrefWidth(final double HEIGHT) {return super.computePrefWidth(HEIGHT);}

    @Override
    protected double computePrefHeight(final double WIDTH) {return super.computePrefHeight(WIDTH);}

    @Override
    protected double computeMaxWidth(final double HEIGHT) {return MAXIMUM_WIDTH;}

    @Override
    protected double computeMaxHeight(final double WIDTH) {return MAXIMUM_HEIGHT;}

    private void handleControlPropertyChanged(final String PROPERTY) {
        if ("".equals(PROPERTY)) {

        }
    }

    @Override
    public ObservableList<Node> getChildren() {return super.getChildren();}

    public Symbol getSymbol() {return null == symbolProperty ? symbol_ : symbolProperty.get();}

    public void setSymbol(final Symbol SYMBOL) {
        if (null == symbolProperty) {
            symbol_ = SYMBOL;
            redraw();
        } else {
            symbolProperty.set(SYMBOL);
        }
    }

    public ObjectProperty<Symbol> symbolProperty() {
        if (null == symbolProperty) {
            symbolProperty = new ObjectPropertyBase<Symbol>(symbol_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return LegendItem.this;}

                @Override
                public String getName() {return "symbol";}
            };
            symbol_ = null;
        }
        return symbolProperty;
    }

    public String getText() {return null == textProperty ? text_ : textProperty.get();}

    public void setText(final String TEXT) {
        if (null == textProperty) {
            text_ = TEXT;
            resize();
        } else {
            textProperty.set(TEXT);
        }
    }

    public StringProperty textProperty() {
        if (null == textProperty) {
            textProperty = new StringPropertyBase(text_) {
                @Override
                protected void invalidated() {resize();}

                @Override
                public Object getBean() {return LegendItem.this;}

                @Override
                public String getName() {return "text";}
            };
            text_ = null;
        }
        return textProperty;
    }

    public Color getSymbolFill() {return null == symbolFillProperty ? symbolFill_ : symbolFillProperty.get();}

    public void setSymbolFill(final Color COLOR) {
        if (null == symbolFillProperty) {
            symbolFill_ = COLOR;
            redraw();
        } else {
            symbolFillProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> symbolFillProperty() {
        if (null == symbolFillProperty) {
            symbolFillProperty = new ObjectPropertyBase<Color>(symbolFill_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return LegendItem.this;}

                @Override
                public String getName() {return "symbolFill";}
            };
            symbolFill_ = null;
        }
        return symbolFillProperty;
    }

    public Color getSymbolStroke() {return null == symbolStrokeProperty ? symbolStroke_ : symbolStrokeProperty.get();}

    public void setSymbolStroke(final Color COLOR) {
        if (null == symbolStrokeProperty) {
            symbolStroke_ = COLOR;
            redraw();
        } else {
            symbolStrokeProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> symbolStrokeProperty() {
        if (null == symbolStrokeProperty) {
            symbolStrokeProperty = new ObjectPropertyBase<Color>(symbolStroke_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return LegendItem.this;}

                @Override
                public String getName() {return "symbolStroke";}
            };
            symbolStroke_ = null;
        }
        return symbolStrokeProperty;
    }

    public Color getTextColor() {return null == textColorProperty ? textColor_ : textColorProperty.get();}

    public void setTextColor(final Color COLOR) {
        if (null == textColorProperty) {
            textColor_ = COLOR;
            redraw();
        } else {
            textColorProperty.set(COLOR);
        }
    }

    public ObjectProperty<Color> textColorProperty() {
        if (null == textColorProperty) {
            textColorProperty = new ObjectPropertyBase<Color>(textColor_) {
                @Override
                protected void invalidated() {redraw();}

                @Override
                public Object getBean() {return LegendItem.this;}

                @Override
                public String getName() {return "textColor";}
            };
            textColor_ = null;
        }
        return textColorProperty;
    }


    // ******************** Resizing ******************************************
    private void resize() {
        width = getWidth() - getInsets().getLeft() - getInsets().getRight();
        height = getHeight() - getInsets().getTop() - getInsets().getBottom();
        size = width < height ? width : height;

        if (width > 0 && height > 0) {
            font = Fonts.latoRegular(size * 0.8);
            textDim = HelperFX.getTextDimension(getText(), font);
            double requiredWidth = height + height * 0.22 + textDim.getWidth();
            pane.setMaxSize(requiredWidth, height);
            pane.setPrefSize(requiredWidth, height);
            pane.relocate((getWidth() - requiredWidth) * 0.5, (getHeight() - height) * 0.5);

            canvas.setWidth(requiredWidth);
            canvas.setHeight(height);

            symbolSize = height * 0.8;

            redraw();

            setMaxWidth(requiredWidth);
        }
    }

    private void redraw() {
        ctx.clearRect(0, 0, width, height);

        drawSymbol(height * 0.5, height * 0.5, getSymbolFill(), getSymbolStroke(), getSymbol());

        ctx.setFill(getTextColor());
        ctx.setFont(font);
        ctx.fillText(getText(), height + height * 0.22, height * 0.5);
    }

    private void drawSymbol(final double X, final double Y, final Paint FILL, final Paint STROKE, final Symbol SYMBOL) {
        double halfSymbolSize = symbolSize * 0.5;
        ctx.save();
        switch (SYMBOL) {
            case NONE:
                break;
            case SQUARE:
                ctx.setStroke(STROKE);
                ctx.setFill(FILL);
                ctx.fillRect(X - halfSymbolSize, Y - halfSymbolSize, symbolSize, symbolSize);
                ctx.strokeRect(X - halfSymbolSize, Y - halfSymbolSize, symbolSize, symbolSize);
                break;
            case TRIANGLE:
                ctx.setStroke(STROKE);
                ctx.setFill(FILL);
                ctx.beginPath();
                ctx.moveTo(X, Y - halfSymbolSize);
                ctx.lineTo(X + halfSymbolSize, Y + halfSymbolSize);
                ctx.lineTo(X - halfSymbolSize, Y + halfSymbolSize);
                ctx.lineTo(X, Y - halfSymbolSize);
                ctx.closePath();
                ctx.fill();
                ctx.stroke();
                break;
            case STAR:
                ctx.setStroke(STROKE);
                ctx.setFill(null);
                ctx.strokeLine(X - halfSymbolSize, Y, X + halfSymbolSize, Y);
                ctx.strokeLine(X, Y - halfSymbolSize, X, Y + halfSymbolSize);
                ctx.strokeLine(X - halfSymbolSize, Y - halfSymbolSize, X + halfSymbolSize, Y + halfSymbolSize);
                ctx.strokeLine(X + halfSymbolSize, Y - halfSymbolSize, X - halfSymbolSize, Y + halfSymbolSize);
                break;
            case CROSS:
                ctx.setStroke(STROKE);
                ctx.setFill(null);
                ctx.strokeLine(X - halfSymbolSize, Y, X + halfSymbolSize, Y);
                ctx.strokeLine(X, Y - halfSymbolSize, X, Y + halfSymbolSize);
                break;
            case CIRCLE:
            default:
                ctx.setStroke(STROKE);
                ctx.setFill(FILL);
                ctx.fillOval(X - halfSymbolSize, Y - halfSymbolSize, symbolSize, symbolSize);
                ctx.strokeOval(X - halfSymbolSize, Y - halfSymbolSize, symbolSize, symbolSize);
                break;
        }
        ctx.restore();
    }
}
