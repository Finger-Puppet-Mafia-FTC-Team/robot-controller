package com.qualcomm.ftcrobotcontroller.opmodes;

public class TelemetryHelpers {
    public String padText(String text, int length) {
        String result = "";
        for (int i = text.length(); i < length; i++) {
            text += " ";
        }

        return text;
    }
}
