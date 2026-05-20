package fx.chart.wafermap;

public record SampleTest(int indexX, int indexY) {

    @Override
    public String toString() {
        return new StringBuilder().append("    ").append(indexX).append("    ").append(indexY).toString();
    }
}

