package com.raa.omnitext;
interface Colors {
    String lighter = "#c5efce";
    String light = "#b0d2b4";
    String medium = "#5a9e61";
    String dark = "#2a5828";
    String darker = "#083209";
    String rbg = "#e3a4a4";
    String rfg = "#7a0000";
    String white = "#ffffff";
    String textbox = "#083209";
    String black = "#000000";
}

public class OmniColors implements Colors {
    private static boolean lightTheme = true;

    boolean isLightTheme() {
        return lightTheme;
    }
}
