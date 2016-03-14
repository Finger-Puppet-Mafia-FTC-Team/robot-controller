package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;


import com.qualcomm.robotcore.hardware.ColorSensor;

class Color {
    ColorSensor sensor;

    public Color (ColorSensor _sensor) {
        sensor = _sensor;
    }

    public boolean isWhite() {
        double green = sensor.green();
        double red = sensor.red();
        double blue = sensor.blue();

        if (green > 220 && red > 220 && blue > 220) {
            // must be near white, or at least is not dark grey, or bright red or blue.
            return true;
        }
        return false;
    }
}
