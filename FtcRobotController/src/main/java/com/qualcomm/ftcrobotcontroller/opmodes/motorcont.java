
package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class motorcont extends OpMode {

  final static double SPATULA_MIN_RANGE  = 0.20;
  final static double SPATULA_MAX_RANGE  = 0.90;
  final static double PLOW_MIN_RANGE  = 0.48;
  final static double PLOW_MAX_RANGE  = 0.75;
  //final static double ELBOWLEFT_MIN_RANGE  = 0.0;
  //final static double ELBOWLEFT_MAX_RANGE  =  1;
  //final static double ELBOWRIGHT_MIN_RANGE  = 0.0;
  //final static double ELBOWRIGHT_MAX_RANGE  =  1;

  // position of the arm servo.
  double spatulaPosition;

  // amount to change the arm servo position.
  double spatulaDelta = 0.1;

  // position of the claw servo
  double plowPosition;

  // amount to change the claw servo position by
  double plowDelta = 0.1;

  //double elbowRightPosition;

  //double elbowRightDelta;

  //double elbowLeftPosition;

 // double elbowLeftDelta;

  DcMotorController.DeviceMode devMode;
  DcMotor motorRight;
  DcMotor motorLeft;
  DcMotor ArmMotor;
  DcMotor Winch1;
  DcMotor Winch2;
  DcMotor motorPlow;
  Servo plow;
  Servo spatula;
  Servo elbowRight;
  Servo elbowLeft;

  /**
   * Constructor
   */
  public motorcont() {

  }

  /*
   * Code to run when the op mode is first enabled goes here
   *
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
   */
  @Override
  public void init() {


		/*
		 * Use the hardwareMap to get the dc motors and servos by name. Note
		 * that the names of the devices must match the names used when you
		 * configured your robot and created the configuration file.
		 */

		/*
		 * For the demo Tetrix K9 bot we assume the following,
		 *   There are two motors "motor_1" and "motor_2"
		 *   "motor_1" is on the right side of the bot.
		 *   "motor_2" is on the left side of the bot and reversed.
		 *
		 * We also assume that there are two servos "servo_1" and "servo_6"
		 *    "servo_1" controls the arm joint of the manipulator.
		 *    "servo_6" controls the claw joint of the manipulator.
		 */
    motorRight = hardwareMap.dcMotor.get("motor_2");
    motorLeft = hardwareMap.dcMotor.get("motor_1");
    motorLeft.setDirection(DcMotor.Direction.REVERSE);
    ArmMotor = hardwareMap.dcMotor.get("motor_3");
    motorPlow = hardwareMap.dcMotor.get("motor_6");
    Winch1 = hardwareMap.dcMotor.get("motor_4");
    Winch2 = hardwareMap.dcMotor.get("motor_5");
    Winch2.setDirection(DcMotor.Direction.REVERSE);
    spatula= hardwareMap.servo.get("servo_1");
    plow = hardwareMap.servo.get("servo_2");
    elbowRight = hardwareMap.servo.get("servo_3");
    elbowLeft = hardwareMap.servo.get("servo_4");
    elbowLeft.setDirection(Servo.Direction.REVERSE);


    // assign the starting position of the wrist and claw
    spatulaPosition = 0.2;
    plowPosition = 0.55;
    //elbowRightPosition = 0;
    //elbowLeftPosition = 0f;
  }

  /*
   * This method will be called repeatedly in a loop
   *
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
   */
  @Override
  public void loop() {

		/*
		 * Gamepad 1
		 *
		 * Gamepad 1 controls the motors via the left stick, and it controls the
		 * wrist/claw via the a,b, x, y buttons
		 */

    // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
    // 1 is full down
    // direction: left_stick_x ranges from -1 to 1, where -1 is full left
    // and 1 is full right
    float throttle = -gamepad1.left_stick_y;
    float direction = gamepad1.left_stick_x;

    float right = throttle - direction;
    float left = throttle + direction;
    float armup = gamepad2.right_stick_y;
    float plowC = gamepad1.right_stick_y;
    {

    }
    // clip the right/left values so that the values never exceed +/- 1

    right = Range.clip(right, -1, 1);
    left = Range.clip(left, -1, 1);
    armup = Range.clip(armup, -.65f, .55f);
    plowC = Range.clip(plowC, -.40f, .40f);
    // scale the joystick value to make it easier to control
    // the robot more precisely at slower speeds.

    right = (float)scaleInput(right);
    left =  (float)scaleInput(left);
    armup = (float)scaleInput(armup);
    plowC = (float)scaleInput(plowC);

    plowC = (plowC)/2;
    armup = (armup)/2;

    if (gamepad2.right_bumper) {

      right = (right)/3;
      left = (left)/3;
    }

    // write the values to the motors
    motorRight.setPower(right);
    motorLeft.setPower(left);
    ArmMotor.setPower(armup);
    Winch1.setPower(gamepad2.left_stick_y);
    Winch2.setPower(gamepad2.left_stick_y);
    motorPlow.setPower(plowC);
// update the position of the arm.
    // update the position of the arm.
    if (gamepad1.a) {
      // if the A button is pushed on gamepad1, increment the position of
      // the arm servo.
      //elbowLeftPosition += elbowLeftDelta;
    }

    if (gamepad1.y) {
      // if the Y button is pushed on gamepad1, decrease the position of
      // the arm servo.
     // elbowLeftPosition -= elbowLeftDelta;
    }
    if (gamepad1.a) {
      // if the A button is pushed on gamepad1, increment the position of
      // the arm servo.
      //elbowRightPosition += elbowRightDelta;
    }

    if (gamepad1.y) {
      // if the Y button is pushed on gamepad1, decrease the position of
      // the arm servo.
      //elbowRightPosition -= elbowRightDelta;
    }
    if (gamepad2.a) {
      // if the A button is pushed on gamepad1, increment the position of
      // the arm servo.
      spatulaPosition += spatulaDelta;
    }

    if (gamepad2.y) {
      // if the Y button is pushed on gamepad1, decrease the position of
      // the arm servo.
       spatulaPosition -= spatulaDelta;
    }

    // update the position of the claw
    if (gamepad1.left_bumper) {
      plowPosition += plowDelta;
    }

    if (gamepad1.left_trigger > 0.25) {
      plowPosition -= plowDelta;
    }

    if (gamepad1.b) {
      plowPosition -= plowDelta;
    }

    // update the position of the claw
    if (gamepad2.x) {
      plowPosition += plowDelta;
    }

    if (gamepad2.b) {
      plowPosition -= plowDelta;
    }

    // clip the position values so that they never exceed their allowed range.
    spatulaPosition = Range.clip(spatulaPosition, SPATULA_MIN_RANGE, SPATULA_MAX_RANGE);
    plowPosition = Range.clip(plowPosition, PLOW_MIN_RANGE, PLOW_MAX_RANGE);
   //elbowLeftPosition = Range.clip(elbowLeftPosition, ELBOWLEFT_MIN_RANGE, ELBOWLEFT_MAX_RANGE);
    //elbowRightPosition = Range.clip(elbowRightPosition, ELBOWRIGHT_MIN_RANGE, ELBOWRIGHT_MAX_RANGE);


    // write position values to the wrist and claw servo
    spatula.setPosition(spatulaPosition);
    plow.setPosition(plowPosition);
    elbowLeft.setPosition(gamepad1.right_stick_x);
    elbowRight.setPosition(gamepad1.right_stick_x);



		/*
		 * Send telemetry data back to driver station. Note that if we are using
		 * a legacy NXT-compatible motor controller, then the getPower() method
		 * will return a null value. The legacy NXT-compatible motor controllers
		 * are currently write only.
		 */
  telemetry.addData("Text", "*** Robot Data***");
  telemetry.addData("arm", "arm:  " + String.format("%.2f", spatulaPosition));
  telemetry.addData("claw", "claw:  " + String.format("%.2f", plowPosition));
  telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
  telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
  }

  /*
   * Code to run when the op mode is first disabled goes here
   *
   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
   */
  @Override
  public void stop() {

  }


  /*
   * This method scales the joystick input so for low joystick values, the
   * scaled value is less than linear.  This is to make it easier to drive
   * the robot more precisely at slower speeds.
   */
  double scaleInput(double dVal)  {
    double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
            0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };

    // get the corresponding index for the scaleInput array.
    int index = (int) (dVal * 16.0);

    // index should be positive.
    if (index < 0) {
      index = -index;
    }

    // index cannot exceed size of array minus 1.
    if (index > 16) {
      index = 16;
    }

    // get value from the array.
    double dScale = 0.0;
    if (dVal < 0) {
      dScale = -scaleArray[index];
    } else {
      dScale = scaleArray[index];
    }

    // return scaled value.
    return dScale;
  }

}
