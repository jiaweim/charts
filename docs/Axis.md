# Axis

## 概述

基本组成：`Axis` 持有一个 `Pane` 作为子节点，`Pane` 包含一个 `Canvas`，所有渲染在 `Canvas` 中执行。

`Axis` 采用 `AnchorPane` 作为 layout。例如，对 bottom-axis（通常为 x-axis），将其 bottom-anchor 设置为 0，left-anchor 和 right-anchor 默认与其宽度相同。

`Axis` 的方向分为两类，由 `javafx.geometry.Orientation` 表示：

```java
public enum Orientation {
    HORIZONTAL,
    VERTICAL
}
```

`Axis` 的位置分为 5 种，由 `fx.chart.Position` 表示：

```java
public enum Position {TOP, RIGHT, BOTTOM, LEFT, CENTER}
```



## Size

尺寸相关属性：

- `width`: 渲染区域宽度
- `height`: 渲染区域高度
- `stepSize`: 坐标轴单位长度对应的像素值

尺寸相关的属性在 `resize()` 方法中初始化：

- `width`: 渲染区域宽度，等于 `width-leftInset-rightInset`
- `height`: 渲染区域高度，等于 `height-topInset-bottomInset`

实际渲染 `Axis` 的区域的左上角坐标为 (leftInset, topInset)，该属性存储在 `axisBounds` 中。

## type

坐标轴类型，分为 4 种，由 `AxisType` 类表示：

```java
public enum AxisType {
    LINEAR,
    LOGARITHMIC,
    TIME,
    TEXT
}
```

### time axis

time-axis 在 `drawTimeAxis()` 中渲染。



##  属性

- stepSize



- autoTitleFontSize

是否自动调整标题 font-size。

- autoTickLabelFontSize

是否自动调整 tick-label font-size。

- axisBackgroundColor

背景颜色。
