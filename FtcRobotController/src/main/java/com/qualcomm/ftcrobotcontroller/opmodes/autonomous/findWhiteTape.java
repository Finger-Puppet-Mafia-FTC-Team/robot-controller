package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import android.util.Log;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.ftcrobotcontroller.opmodes.AutonomousHardware;
import com.qualcomm.robotcore.util.Range;

public class findWhiteTape extends step{
    public boolean done = false;

    @Override
    public boolean shouldContinue() {
        return done;
    }

    @Override
    public void initStep (Autonomous2 OpModeInstance, AutonomousHardware hardware) {

    }

    @Override
    public void runStep (Autonomous2 OpModeInstance, AutonomousHardware hardware) {
        hardware.collector.setPower(-1);

        double floorRed = OpModeInstance.getFloorColor()[0];
        double floorGreen = OpModeInstance.getFloorColor()[1];
        double floorBlue = OpModeInstance.getFloorColor()[2];
        double red = hardware.bottomColor.red();
        double green = hardware.bottomColor.green();
        double blue = hardware.bottomColor.blue();

        // modify drive speed to stay straight
        double headingError = hardware.gyro.getIntegratedZValue();
        double driveAdjustment = headingError * 0.006;

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
            Log.i("test", "Is white");
            Log.i("test", floorRed + "," + floorGreen + "," + floorBlue);
            Log.i("test", red + "," + green + "," + blue);
            hardware.motorLeft.setPower(0);
            hardware.motorRight.setPower(0);
            done = true;
        } else {
            double powerLeft = -0.5 - driveAdjustment;
            double powerRight = -0.5 + driveAdjustment;

            powerLeft = Range.clip(powerLeft, -1, 1);
            powerRight = Range.clip(powerRight, -1, 1);
            hardware.motorRight.setPower(powerLeft);
            hardware.motorLeft.setPower(powerRight);
        }
        OpModeInstance.addMessage("adjustment - " + driveAdjustment);
        OpModeInstance.addMessage("heading - " + headingError);
    }
}
