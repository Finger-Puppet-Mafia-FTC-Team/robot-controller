package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import android.util.Log;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.robotcore.util.Range;

import java.util.Date;


public class findWhiteTape extends step {
  public boolean done = false;
  public double startTime;

  double slideStartTime = 0;
  boolean didInit = false;
  boolean slideDone = false;

  @Override
  public boolean shouldContinue() {
    return done;
  }

  @Override
  public void initStep(Autonomous2 OpModeInstance, AutonomousHardware hardware) {
    if (didInit) {
      return;
    }
    didInit = true;
    startTime = new Date().getTime();
  }

  @Override
  public void runStep(Autonomous2 OpModeInstance, AutonomousHardware hardware) {
    hardware.collector.setPower(-1);

    double floorRed = OpModeInstance.getFloorColor()[0];
    double floorGreen = OpModeInstance.getFloorColor()[1];
    double floorBlue = OpModeInstance.getFloorColor()[2];
    double red = hardware.bottomColor.red();
    double green = hardware.bottomColor.green();
    double blue = hardware.bottomColor.blue();

    if (red == 0 && green == 0 && blue == 0) {
      OpModeInstance.addMessage("Color sensor returning all 0's. Probably not working");
      hardware.motorLeft.setPower(0);
      hardware.motorRight.setPower(0);
      return;
    }

    // modify drive speed to stay straight
    double headingError = hardware.gyro.getIntegratedZValue();
    Log.i("gyro", String.valueOf(headingError));
    double driveAdjustment = headingError * 0.08;

    boolean isWhite = true;

//        double brightness = hardware.lightSensor.getLightDetected();

    // decide if it is grey. max can only do two numbers at a time
    double maxColor = Math.max(red, green);
    maxColor = Math.max(maxColor, blue);

    double minColor = Math.min(red, green);
    minColor = Math.min(minColor, blue);

    // All colors should be about the same size, resulting in
    // a number near zero when dividing the max color by the min color
    if (maxColor / minColor > 1.5) {
      //  Log.i("test", "One number too large");
      isWhite = false;
    }

    //decide if it is a lighter grey than the floor
    // The white tape's color should be more than twice as high
    // as the floor's color.
    if (floorRed * 2 > red) {
      //  Log.i("test", "Red large");
      isWhite = false;
    }
    if (floorGreen * 2 > green) {
      // Log.i("test", "Green large");

      isWhite = false;
    }
    if (floorBlue * 2 > blue) {
      // Log.i("test", "Blue large");

      isWhite = false;
    }
    if (blue == 0 && green == 0 && red == 0) {
      // Log.i("test", "is zeros");

      isWhite = false;
    }

    Log.i("color", String.valueOf(red) + ',' + green + ',' + blue);

    if (isWhite) {
      Log.i("test", "Is white");
      Log.i("test", floorRed + "," + floorGreen + "," + floorBlue);
      Log.i("test", red + "," + green + "," + blue);
      hardware.motorLeft.setPower(0);
      hardware.motorRight.setPower(0);
      done = true;
    } else {
      double runTime = new Date().getTime() - startTime;
      double basePower;
      if (runTime < 5000) {
        basePower = -1;
      } else {
        basePower = -1 * Math.max(1 - (Math.pow(runTime, 2) / 60000000), 0.5);
      }
      basePower = -1;

      double powerLeft = basePower - driveAdjustment;
      double powerRight = basePower + driveAdjustment;

      Log.i("driveAdjustment", String.valueOf(driveAdjustment));

      powerLeft = Range.clip(powerLeft, -1, 1);
      powerRight = Range.clip(powerRight, -1, 1);

      hardware.motorRight.setPower(powerLeft);
      hardware.motorLeft.setPower(powerRight);



      OpModeInstance.addMessage("power - " + basePower);
    }
    OpModeInstance.addMessage("adjustment - " + driveAdjustment);
    OpModeInstance.addMessage("heading - " + headingError);
  }
}
