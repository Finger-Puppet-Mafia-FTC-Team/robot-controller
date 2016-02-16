package com.qualcomm.ftcrobotcontroller.opmodes.autonomous;

import com.qualcomm.ftcrobotcontroller.opmodes.Autonomous2;
import com.qualcomm.ftcrobotcontroller.opmodes.AutonomousHardware;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

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
        double brightness = hardware.ods.getLightDetected();

        if(brightness > 0.15 + OpModeInstance.getFloorBrightness()) {
            hardware.motorLeft.setPower(0);
            hardware.motorRight.setPower(-0.5);
            OpModeInstance.addMessage("on tape");
        } else {
            OpModeInstance.addMessage("off tape");
            hardware.motorLeft.setPower(-0.5);
            hardware.motorRight.setPower(0);
        }
        if(hardware.sonicLeft.getUltrasonicLevel() < 8) {
            done = true;
        }
        if(hardware.sonicRight.getUltrasonicLevel() < 8) {
            done = true;
        }
        OpModeInstance.addMessage("sonic distance l" + hardware.sonicLeft.getUltrasonicLevel());
        OpModeInstance.addMessage("sonic distance r" + hardware.sonicRight.getUltrasonicLevel());

    }
}
