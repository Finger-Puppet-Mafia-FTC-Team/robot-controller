package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.ftcrobotcontroller.opmodes.AutonomousHardware;

public class followTape extends step{

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
        double floorRed = OpModeInstance.getFloorColor()[0];
        double floorGreen = OpModeInstance.getFloorColor()[1];
        double floorBlue = OpModeInstance.getFloorColor()[2];
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

        if(maxColor / minColor > 1.5) {
            isWhite = false;
        }

        //decide if it is a lighter grey than the floor
        if(floorRed * 2 > red) {
            isWhite = false;
        }
        if(floorGreen * 2 > green) {
            isWhite = false;
        }
        if(floorBlue * 2 > blue) {
            isWhite = false;
        }


        if(isWhite) {
            hardware.motorLeft.setPower(0);
            hardware.motorRight.setPower(-0.5);
            OpModeInstance.addMessage("on tape");
        } else {
            OpModeInstance.addMessage("off tape");
            hardware.motorLeft.setPower(-0.5);
            hardware.motorRight.setPower(0);
        }
        if(hardware.sonicLeft.getUltrasonicLevel() < 15) {
            done = true;
        }
        if(hardware.sonicRight.getUltrasonicLevel() < 15) {
            done = true;
        }
        OpModeInstance.addMessage("sonic distance l" + hardware.sonicLeft.getUltrasonicLevel());
        OpModeInstance.addMessage("sonic distance r" + hardware.sonicRight.getUltrasonicLevel());

    }
}