package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import android.util.Log;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.ftcrobotcontroller.opmodes.AutonomousHardware;

public class NormalizePosition extends step{
    @Override
    public void initStep (Autonomous2 instance, AutonomousHardware hardware) {

    }

    @Override
    public boolean shouldContinue() {
        return done;
    }


    @Override
    public void runStep (Autonomous2 instance, AutonomousHardware hardware) {
        if(instance.getIsBlue()) {
            double floorRed = instance.getFloorColor()[0];
            double floorGreen = instance.getFloorColor()[1];
            double floorBlue = instance.getFloorColor()[2];
            double red = hardware.bottomColor.red();
            double green = hardware.bottomColor.green();
            double blue = hardware.bottomColor.blue();


            boolean isWhite = true;

//        double brightness = hardware.lightSensor.getLightDetected();

            // decide if it is grey. max can only do two numbers at a time
            double maxColor = Math.max(red, green);
            maxColor = Math.max(maxColor, blue);

            double minColor = Math.min(red, green);
            minColor = Math.min(minColor, blue);

            // All colors should be about the same size, resulting in
            // a number near zero when dividing the max color by the min color
            if(maxColor / minColor > 1.5) {
                Log.i("test", "One number too large");
                isWhite = false;
            }

            //decide if it is a lighter grey than the floor
            // The white tape's color should be more than twice as high
            // as the floor's color.
            if(floorRed * 2 > red) {
                Log.i("test", "Red large");
                isWhite = false;
            }
            if(floorGreen * 2 > green) {
                Log.i("test", "Green large");

                isWhite = false;
            }
            if(floorBlue * 2 > blue) {
                Log.i("test", "Blue large");

                isWhite = false;
            }
            if(blue == 0 && green == 0 && red == 0) {
                Log.i("test", "is zeros");

                isWhite = false;
            }
            if(isWhite) {
                hardware.motorRight.setPower(-0.4);
                hardware.motorLeft.setPower(-0.4);
            } else {
                done = true;
                return;
            }
        } else {
            done = true;
            return;
        }
    }
}
