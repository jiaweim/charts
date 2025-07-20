package fx.chart.util;

import java.io.File;
import java.util.regex.Pattern;


public class Constants {

    private Constants() {}

    public enum Architecture {
        SPARC,
        AMD64,
        X86,
        X64,
        S390X,
        PPC64,
        AARCH64,
        ARM,
        ARMEL,
        ARMHF,
        MIPS,
        MIPSEL,
        PPC,
        PPC64LE,
        RISCV64,
        SPARCV9,
        IA64,
        NOT_FOUND;


        public static Architecture fromText(final String text) {
            if (null == text) {
                return NOT_FOUND;
            }
            switch (text) {
                case "aarch64", "AARCH64", "arm64", "ARM64", "armv8", "ARMV8" -> {
                    return AARCH64;
                }
                case "amd64", "AMD64", "_amd64" -> {
                    return AMD64;
                }
                case "aarch32", "AARCH32", "arm32", "ARM32", "armv6", "ARMV6", "armv7l", "ARMV7L", "armv7", "ARMV7",
                     "arm", "ARM" -> {
                    return ARM;
                }
                case "armel", "ARMEL" -> {
                    return ARMEL;
                }
                case "armhf", "ARMHF" -> {
                    return ARMHF;
                }
                case "mips", "MIPS" -> {
                    return MIPS;
                }
                case "mipsel", "MIPSEL" -> {
                    return MIPSEL;
                }
                case "ppc", "PPC" -> {
                    return PPC;
                }
                case "ppc64el", "PPC64EL", "ppc64le", "PPC64LE" -> {
                    return PPC64LE;
                }
                case "ppc64", "PPC64" -> {
                    return PPC64;
                }
                case "riscv64", "RISCV64" -> {
                    return RISCV64;
                }
                case "s390", "s390x", "S390X" -> {
                    return S390X;
                }
                case "sparc", "SPARC" -> {
                    return SPARC;
                }
                case "sparcv9", "SPARCV9" -> {
                    return SPARCV9;
                }
                case "x64", "X64", "x86-64", "X86-64", "x86_64", "X86_64", "x86lx64", "X86LX64" -> {
                    return X64;
                }
                case "x32", "x86", "X86", "286", "386", "486", "586", "686", "i386", "i486", "i586", "i686", "x86-32",
                     "x86lx32", "X86LX32" -> {
                    return X86;
                }
                case "ia64", "IA64", "ia-64", "IA-64" -> {
                    return IA64;
                }
                default -> {
                    return NOT_FOUND;
                }
            }
        }
    }

    public enum OperatingSystem {
        ALPINE_LINUX,
        LINUX,
        LINUX_MUSL,
        FREE_BSD,
        MACOS,
        WINDOWS,
        SOLARIS,
        QNX,
        AIX,
        NOT_FOUND;

        public static OperatingSystem fromText(final String text) {
            if (null == text) {
                return NOT_FOUND;
            }
            switch (text) {
                case "-linux", "linux", "Linux", "LINUX", "unix", "UNIX", "Unix", "-unix" -> {
                    return LINUX;
                }
                case "-linux-musl", "-linux_musl", "Linux-Musl", "linux-musl", "Linux_Musl", "LINUX_MUSL", "linux_musl",
                     "alpine", "ALPINE", "Alpine", "alpine-linux", "ALPINE-LINUX", "alpine_linux", "Alpine_Linux",
                     "ALPINE_LINUX", "Alpine Linux", "alpine linux", "ALPINE LINUX" -> {
                    return ALPINE_LINUX;
                }
                case "freeBSD", "FreeBSD", "-free-bsd", "-free_bsd", "FREE BSD", "FREEBSD", "freebsd", "free bsd" -> {
                    return FREE_BSD;
                }
                case "-solaris", "solaris", "SOLARIS", "Solaris" -> {
                    return SOLARIS;
                }
                case "-qnx", "qnx", "QNX" -> {
                    return QNX;
                }
                case "-aix", "aix", "AIX" -> {
                    return AIX;
                }
                case "darwin", "-darwin", "-macosx", "-MACOSX", "MacOS", "Mac OS", "mac_os", "Mac_OS", "mac-os",
                     "Mac-OS", "mac", "MAC", "macos", "MACOS", "osx", "OSX", "macosx", "MACOSX", "Mac OSX", "Mac OS X",
                     "mac osx" -> {
                    return MACOS;
                }
                case "-win", "windows", "Windows", "WINDOWS", "win", "Win", "WIN" -> {
                    return WINDOWS;
                }
                default -> {
                    return NOT_FOUND;
                }
            }
        }
    }

    public enum OperatingMode {
        NATIVE,
        EMULATED,
        NONE,
        NOT_FOUND;

        public static OperatingMode fromText(final String text) {
            if (null == text) {
                return NOT_FOUND;
            }
            switch (text) {
                case "native", "NATIVE", "Native" -> {
                    return NATIVE;
                }
                case "emulated", "EMULATED", "Emulated" -> {
                    return EMULATED;
                }
                default -> {
                    return NOT_FOUND;
                }
            }
        }
    }

    public static final double EPSILON = 1E-6;
    public static final Pattern INT_PATTERN = Pattern.compile("[0-9]+");
    public static final Pattern FLOAT_PATTERN = Pattern.compile("[-+]?[0-9]*\\.?[0-9]+");
    public static final Pattern HEX_PATTERN = Pattern.compile("#?([A-Fa-f0-9]{8}|[A-Fa-f0-9]{6})");
    public static final String HOME_FOLDER = System.getProperty("user.home") + File.separator;
    public static final String QUOTES = "\"";
    public static final String QUOTES_COLON = "\":";
    public static final String NEW_LINE = "\n";
    public static final String INDENT = "  ";
    public static final String DEGREE = "\u00B0";
}
