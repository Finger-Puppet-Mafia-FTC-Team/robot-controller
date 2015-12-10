package com.qualcomm.ftcrobotcontroller.opmodes.FingerPuppetMafia;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;

/**
 * TeleOp for the competition on Dec. 12
 */
public class teleop extends OpMode {
    String pressedKeys[] = new String[20];
    Servo sideArmLeft;
    Servo wallLeft;
    Servo track;

    // State
    boolean leftWallIn = true;
    boolean sideArmLeftIn = true;

    public teleop() {}

    @Override
    public void init() {
        //TODO: This will need to be changed once the eclectronics are all on
        sideArmLeft = hardwareMap.servo.get("sideArmLeft");
        wallLeft = hardwareMap.servo.get("wallLeft");
        track = hardwareMap.servo.get("belt");
        
        // reset state
        leftWallIn = true;
    }

    @Override
    public void init_loop() {
        // initial positions for servos
        sideArmLeft.setPosition(0.9);
        wallLeft.setPosition(.94);
        track.setPosition(.5);
    }

    @Override
    public void loop() {

        //Left arm
        if(pressed("2x", gamepad2.x)) {
            if(sideArmLeftIn) {
                sideArmLeft.setPosition(0.1);
            } else {
                sideArmLeft.setPosition(0.9);
            }
            sideArmLeftIn = !sideArmLeftIn;
        }

        //Left Wall
        if(pressed("2a", gamepad2.a) == true){
            if(leftWallIn) {
                wallLeft.setPosition(0.4);
            } else {
                wallLeft.setPosition(0.94);
            }
            leftWallIn = !leftWallIn;
        }

        //Track
        if(gamepad2.left_stick_x > 0.20) {
            track.setPosition(1);
        } else if(gamepad2.left_stick_x < -0.20) {
            track.setPosition(0);
        } else {
            track.setPosition(0.5);
        }


        telemetry.addData("test", wallLeft.getPosition());

    }

    @Override
    public void stop() {

    }

    // helpers

    /**
     * Returns true if the user has been holding down a button for longer than one loop cycle.
     * Useful if using a button to toggle an action. This prevents the action getting toggled
     * multiple times if the user holds down a button too long.
     *
     * @param key
     * @return If the user is holding is still holding down the key
     */
    public boolean pressed(String key, boolean pressed) {
        int index = Arrays.asList(pressedKeys).indexOf(key);
        if(pressed == false) {
            if(index > -1){
                // button is no longer pressed
                pressedKeys[index] = null;
            }
            return false;
        }

        if(Arrays.asList(pressedKeys).contains(key)){
            // it has been pressed. Don't trigger again
            return false;
        }
        // find index of a null and put it there
        int nullIndex = Arrays.asList(pressedKeys).indexOf(null);
        Log.i("Test", String.valueOf(pressedKeys));
        if(nullIndex > -1) {
            pressedKeys[nullIndex] = key;
        }
        return true;

    }
}
