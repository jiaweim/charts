package fx.chart.country.tools;

import java.util.Arrays;
import java.util.List;


public enum OperatingSystem implements Api {

    LINUX("Linux", "linux"),
    MACOS("Mac OS", "macos"),
    WINDOWS("Windows", "windows"),
    SOLARIS("Solaris", "solaris"),
    NONE("-", ""),
    NOT_FOUND("", "");

    private final String uiString;
    private final String apiString;

    // ******************** Constructors **************************************
    OperatingSystem(String uiString, String apiString) {
        this.uiString = uiString;
        this.apiString = apiString;
    }


    // ******************** Methods *******************************************
    @Override
    public String getUiString() {
        return this.uiString;
    }

    @Override
    public String getApiString() {return this.apiString;}

    @Override
    public OperatingSystem getDefault() {return NONE;}

    @Override
    public OperatingSystem getNotFound() {return NOT_FOUND;}

    @Override
    public OperatingSystem[] getAll() {return values();}

    public static OperatingSystem fromText(String text) {
        if (null == text) return NOT_FOUND;
        return switch (text) {
            case "-linux", "linux", "Linux", "LINUX" -> LINUX;
            case "-solaris", "solaris", "SOLARIS", "Solaris" -> SOLARIS;
            case "darwin", "-darwin", "-macosx", "-MACOSX", "MacOS", "Mac OS", "mac_os", "Mac_OS", "mac-os", "Mac-OS",
                 "mac", "MAC", "macos", "MACOS", "osx", "OSX", "macosx", "MACOSX", "Mac OSX", "mac osx" -> MACOS;
            case "-win", "windows", "Windows", "WINDOWS", "win", "Win", "WIN" -> WINDOWS;
            default -> NOT_FOUND;
        };
    }

    public static List<OperatingSystem> getAsList() {return Arrays.asList(values());}

    public String toString() {return this.uiString;}
}
