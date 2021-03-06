//
//package com.qualcomm.ftcrobotcontroller.opmodes;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorController;
//import com.qualcomm.robotcore.hardware.Servo;
//import com.qualcomm.robotcore.util.Range;
//
///**
// * TeleOp Mode
// * <p>
// * Enables control of the robot via the gamepad
// */
//public class motorcontrollertest extends OpMode {
//
//
//
//
//  DcMotorController.DeviceMode devMode;
//
//
//  motor motorRight;
//          motor motorLeft;
//                  motor Tapemotor;
//                          motor motorBall;
//
//
//
//
//  servo wallLeft;
//  servo wallRight;
//  servo catcherDoor;
//  servo sideArmLeft;
//  servo sideArmRight;
//  servo track;
//  servo tapeAngle;
//
//  /**
//   * Constructor
//   */
//  public motorcontrollertest() {
//
//  }
//
//  /*
//   * Code to run when the op mode is first enabled goes here
//   *
//   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
//   */
//  @Override
//  public void init() {
//
//
//		/*
//		 * Use the hardwareMap to get the dc motors and servos by name. Note
//		 * that the names of the devices must match the names used when you
//		 * configured your robot and created the configuration file.
//		 */
//
//		/*
//		 * For the demo Tetrix K9 bot we assume the following,
//		 *   There are two motors "motor_1" and "motor_2"
//		 *   "motor_1" is on the right side of the bot.
//		 *   "motor_2" is on the left side of the bot and reversed.
//		 *
//		 * We also assume that there are two servos "servo_1" and "servo_6"
//		 *    "servo_1" controls the arm joint of the manipulator.
//		 *    "servo_6" controls the claw joint of the manipulator.
//		 */
//    motorRight = hardwareMap.dcMotor.get("motor_2");
//    motorLeft = hardwareMap.dcMotor.get("motor_1");
//    motorLeft.setDirection(DcMotor.Direction.REVERSE);
//    Tapemotor = hardwareMap.dcMotor.get("motor_3");
//    motorBall = hardwareMap.dcmotor.get("motor_4");
//    wallLeft = hardwareMap.servo.get("wallLeft");
//    wallRight = hardwareMap.servo.get("wallRight");
//    catcherDoor = hardwareMap.servo.get("catcherDoor");
//    sideArmLeft = hardwareMap.servo.get("sideArmLeft");
//    track = hardwareMap.servo.get("track");
//    sideArmRight = hardwareMap.servo.get("sideArmRight");
//    wallRight = hardwareMap.servo.get("wallRight");
//    tapeAngle = hardwareMap.servo.get("tapeAngle");
//
//
//
//    // assign the starting position of the wrist and claw
//
//    wallLeft = 0;
//    wallRight = 0;
//    catcherDoor = 0;
//    sideArmLeft = 0;
//    sideArmRight = 0;
//    track = 0;
//    tapeAngle = 0;
//
//
//
//
//  }
//
//  /*
//   * This method will be called repeatedly in a loop
//   *
//   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
//   */
//  @Override
//  public void loop() {
//
//		/*
//		 * Gamepad 1
//		 *
//		 * Gamepad 1 controls the motors via the left stick, and it controls the
//		 * wrist/claw via the a,b, x, y buttons
//		 */
//
//    // throttle: left_stick_y ranges from -1 to 1, where -1 is full up, and
//    // 1 is full down
//    // direction: left_stick_x ranges from -1 to 1, where -1 is full left
//    // and 1 is full right
//
//
//    float right = gamepad1.left_stick_y;
//    float left = gamepad1.right_stick_y;
//
//
//
//    {
//
//    }
//    // clip the right/left values so that the values never exceed +/- 1
//
//    right = Range.clip(right, -1, 1);
//    left = Range.clip(left, -1, 1);
//
//
//
//
//
//
//    // scale the joystick value to make it easier to control
//    // the robot more precisely at slower speeds.
//
//    right = (float)scaleInput(right);
//    left =  (float)scaleInput(left);
//
//
//
//
//
//    // write the values to the motors
//    motorRight.setPower(gamepad1.left_stick_y);
//    motorLeft.setPower(gamepad1.right_stick_y);
//
//   // Winch2.setPower(gamepad2.left_stick_x);
//
//    if(gamepad2.a) {
//      motorBall = 1;
//    }
//
//    if(gamepad1.dpad_left) {
//
//      sideArmLeft = .9;
//    }
//
//    if (gamepad1.dpad_right) {
//
//      sideArmRight = .9;
//
//    }
//
//
//
//
//// update the position of the arm.
//    // update the position of the arm.
//
//
//
//    if (gamepad2.y) {
//      // if the A button is pushed on gamepad1, increment the position of
//      // the arm servo.
//      spatulaPosition += spatulaDelta;
//    }
//
//    if (gamepad2.x) {
//      // if the Y button is pushed on gamepad1, decrease the position of
//      // the arm servo.
//      spatulaPosition -= spatulaDelta;
//    }
//
//
//
//    // clip the position values so that they never exceed their allowed range.
//
//
//
//
//    // write position values to the wrist and claw servo
//    spatula.setPosition(spatulaPosition);
//    plow.setPosition(plowPosition);
//
//
//
//		/*
//		 * Send telemetry data back to driver station. Note that if we are using
//		 * a legacy NXT-compatible motor controller, then the getPower() method
//		 * will return a null value. The legacy NXT-compatible motor controllers
//		 * are currently write only.
//		 */
//  telemetry.addData("Text", "*** Robot Data***");
//  telemetry.addData("arm", "arm:  " + String.format("%.2f", spatulaPosition));
//  telemetry.addData("claw", "claw:  " + String.format("%.2f", plowPosition));
//  telemetry.addData("left tgt pwr",  "left  pwr: " + String.format("%.2f", left));
//  telemetry.addData("right tgt pwr", "right pwr: " + String.format("%.2f", right));
//    telemetry.addData("elbow", "elbow:  " + String.format("%.2f", elbowLeftPosition));
//  }
//
//  /*
//   * Code to run when the op mode is first disabled goes here
//   *
//   * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
//   */
//  @Override
//  public void stop() {
//
//  }
//
//
//  /*
//   * This method scales the joystick input so for low joystick values, the
//   * scaled value is less than linear.  This is to make it easier to drive
//   * the robot more precisely at slower speeds.
//   */
//  double scaleInput(double dVal)  {
//    double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
//            0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };
//
//    // get the corresponding index for the scaleInput array.
//    int index = (int) (dVal * 16.0);
//
//    // index should be positive.
//    if (index < 0) {
//      index = -index;
//    }
//
//    // index cannot exceed size of array minus 1.
//    if (index > 16) {
//      index = 16;
//    }
//
//    // get value from the array.
//    double dScale = 0.0;
//    if (dVal < 0) {
//      dScale = -scaleArray[index];
//    } else {
//      dScale = scaleArray[index];
//    }
//
//    // return scaled value.
//    return dScale;
//  }
//
//}
