package ir.kasra_sh.nanoserver.utils;

public class OS {
    private static final boolean nix;
    static {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("nix")||osName.contains("nux")||osName.contains("mac")) {
            nix = true;
        } else {
            nix = false;
        }
    }
    public static boolean isNix() {
        return nix;
    }
}
