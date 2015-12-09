package com.qualcomm.ftcrobotcontroller.opmodes.FingerPuppetMafia;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * TeleOp for the competition on Dec. 12
 */
public class teleop extends OpMode {
    Servo sideArmLeft;
    public teleop() {

    }

    @Override
    public void init() {
        //TODO: This will need to be changed once the eclectronics are all on
        sideArmLeft = hardwareMap.servo.get("sideArmLeft");
    }

    @Override
    public void loop() {
        float sideArmLeftPos = 0;




        sideArmLeft.setPosition(sideArmLeftPos);
    }

    @Override
    public void stop() {

    }

    // helpers

    /**
     * Returns true if the state has changed
     * @param key
     * @return
     */
    public boolean toggled(String key) {
        return true;
    }
}
