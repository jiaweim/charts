package fx.chart.country.tools;

public interface Api {
    String getUiString();

    String getApiString();

    Api getDefault();

    Api getNotFound();

    Api[] getAll();

    static Api fromText(String text) {
        return null;
    }
}
