package com.qualcomm.ftcrobotcontroller.opmodes.FingerPuppetMafia;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * TeleOp for the competition on Dec. 12
 */
public class teleop extends OpMode {
    String pressedKeys[] = new String[20];
    ArrayList<String> messages = new ArrayList<String>();
    Servo sideArmLeft;
    Servo sideArmRight;
    Servo wallLeft;
    Servo wallRight;
    Servo track;
    Servo tapeAngleServo;
    Servo catcherDoor;

    DcMotor tapeMotor;

    // State
    boolean leftWallIn = true;
    boolean rightWallIn = true;
    boolean sideArmLeftIn = true;
    boolean sideArmRightIn = true;
    boolean catcherDoorUp = true;
    double tapeAngle = 0.5;
    int trackState = 0;

    public teleop() {
    }

    @Override
    public void init() {
        //TODO: This will need to be changed once the eclectronics are all on
        sideArmLeft = hardwareMap.servo.get("sideArmLeft");
        sideArmRight = hardwareMap.servo.get("sideArmRight");

        wallLeft = hardwareMap.servo.get("wallLeft");
        wallRight = hardwareMap.servo.get("wallRight");

        track = hardwareMap.servo.get("track");

        tapeAngleServo = hardwareMap.servo.get("tapeAngle");
        catcherDoor = hardwareMap.servo.get("catcherDoor");


        //Motors
        // tapeMotor = hardwareMap.dcMotor.get("tape");

        // reset state
        leftWallIn = true;
    }

    @Override
    public void init_loop() {
        // initial positions for servos
        sideArmLeft.setPosition(0.8);
        sideArmRight.setPosition(0.7);

        wallLeft.setPosition(.86);
        wallRight.setPosition(0);

        track.setPosition(.5);
        tapeAngleServo.setPosition(.9);
        catcherDoor.setPosition(.51);
    }

    @Override
    public void loop() {

        //Catcher

        //Catcher door
        if (pressed("2y", gamepad2.y)) {
            catcherDoorUp = !catcherDoorUp;
            if (catcherDoorUp == true) {
                catcherDoor.setPosition(0.51);
            } else {
                catcherDoor.setPosition(0);
            }
        }

        //Left arm
        if (pressed("2x", gamepad2.x)) {
            if (sideArmLeftIn) {
                sideArmLeft.setPosition(0.1);
                messages.add("Left Arm In");
            } else {
                sideArmLeft.setPosition(0.8);
                messages.add("Left Arm Out");
            }
            sideArmLeftIn = !sideArmLeftIn;

        }

        //Right arm
        if (pressed("2b", gamepad2.b)) {
            if (sideArmRightIn) {
                sideArmRight.setPosition(0.1);
            } else {
                sideArmRight.setPosition(0.9);
            }
            sideArmRightIn = !sideArmRightIn;
        }

        //Left Wall
        if (pressed("1left_stick_x-20", gamepad1.left_stick_x < -.2) == true) {
            if (leftWallIn) {
                wallLeft.setPosition(0.4);
                messages.add("Left Wall In");
            } else {
                wallLeft.setPosition(0.86);
                messages.add("Left Wall Out");
            }
            leftWallIn = !leftWallIn;
        }

        //Belt
        if (pressed("1x", gamepad1.x)) {
            // move to next state
            switch (trackState) {
                case 0:
                    trackState = 1;
                    break;
                case 1:
                    trackState = 2;
                    break;
                case 2:
                    trackState = 0;
                    break;
                default:
                    Log.i("Test", "default");
            }
            Log.i("Test", String.valueOf(trackState));

            if (trackState == 0) {
                track.setPosition(0.5);
                messages.add("Track not moving");
            } else if (trackState == 1) {
                track.setPosition(1);
                messages.add("Track going left");
            } else if (trackState == 2) {
                track.setPosition(0);
                messages.add("Track going right");
            } else {
                messages.add("Track state not found");
            }
        }

        //Tape
        if (gamepad2.right_stick_y > .2) {
            tapeAngle += .01;
            if (tapeAngle > 1) {
                tapeAngle = 1;
            }
            tapeAngleServo.setPosition(tapeAngle);
        }

        if (gamepad1.right_stick_y < -.2) {
            tapeAngle -= .01;
            if (tapeAngle < 0) {
                tapeAngle = 0;
            }
            tapeAngleServo.setPosition(tapeAngle);
        }

        for (int i = 0; i < messages.size(); i++) {
            telemetry.addData(String.valueOf(i), messages.get(i));
        }
        messages.clear();
        telemetry.addData("leftWall In:", leftWallIn);
        telemetry.addData("leftArm In:", sideArmLeftIn);
        telemetry.addData("trackState", String.valueOf(trackState));
        telemetry.addData("Tape Angle", tapeAngle);
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
        if (pressed == false) {
            if (index > -1) {
                // button is no longer pressed
                pressedKeys[index] = null;
            }
            return false;
        }

        if (Arrays.asList(pressedKeys).contains(key)) {
            // it has been pressed. Don't trigger again
            return false;
        }
        // find index of a null and put it there
        int nullIndex = Arrays.asList(pressedKeys).indexOf(null);
        Log.i("Test", String.valueOf(pressedKeys));
        if (nullIndex > -1) {
            pressedKeys[nullIndex] = key;
        }
        return true;

    }
}
