
package TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

import Robot.Robot;

@TeleOp(name = "TeleOp4780[AS]" , group = "TeleOp")
public class TeleOp4780 extends LinearOpMode {
    public Robot robot = new Robot();

    ArrayList<DcMotor> dcNames = new ArrayList<>();

    public void zeroMode(double Strafe, double Drive) {
        if (Strafe == 0 && Drive == 0) {
            robot.brake();
        } else {
            robot.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            robot.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
    }



    public void runOpMode() {
        telemetry.addLine("Initialized, Press A + Start for Controller 1");

        robot.init(hardwareMap);
        robot.brake();

        dcNames.add(robot.frontLeft);
        dcNames.add(robot.frontRight);
        dcNames.add(robot.backLeft);
        dcNames.add(robot.backRight);
        dcNames.add(robot.linearSlideLeft);
        dcNames.add(robot.linearSlideRight);
        dcNames.add(robot.intakeWheels);

        boolean BucketPosition = false;
        boolean PreviousXState = false;

        boolean StopperPosition = false;
        boolean PreviousBState = false;

        boolean LauncherPosition = false;
        boolean PreviousAState = false;

        boolean IntakePosition = false;
        boolean PreviousYState = false;


        waitForStart();


        while (opModeIsActive()) {
            robot.frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            robot.frontLeft.setPower(frontLeftPower);
            robot.frontRight.setPower(frontRightPower);
            robot.backLeft.setPower(backLeftPower);
            robot.backRight.setPower(backRightPower);

//
//            //Variables for Movement (GamePad1)
//            double Drive = (-gamepad1.left_stick_y); //Left stick y value [-1,1]
//            double Turn = (gamepad1.right_stick_x); //Right stick x value [-1, 1]
//            double Strafe = (gamepad1.left_stick_x * 1.1); //Left stick x value [-1,1]
//
//            double Theta = Math.atan2(Drive, Strafe);
//            double Power = Math.hypot(Strafe, Drive);
//
//            //Input: Theta / Power / Turn
//            double Sin = Math.sin(Theta - Math.PI/4);
//            double Cos = Math.cos(Theta - Math.PI/4);
//
//            double Max = Math.max(Math.abs(Sin), Math.abs(Cos));
//
//            double frontLeft = Power * Cos/Max + Turn;
//            double frontRight = Power * Sin/Max - Turn;
//            double backLeft = Power * Sin/Max + Turn;
//            double backRight = Power * Cos/Max - Turn;
//
//            if ((Power + Math.abs(Turn)) > 1) {
//                frontLeft   /= Power + Math.abs(Turn);
//                frontRight /= Power + Math.abs(Turn);
//                backLeft    /= Power + Math.abs(Turn);
//                backRight  /= Power + Math.abs(Turn);
//            }
//
//            robot.mecDrive(frontLeft, frontRight, backLeft, backRight);
//            //zeroMode(Strafe, Drive);



            //Variables for Intake Wheels (GamePad1)
            boolean L2 = (gamepad1.left_bumper); // Left Trigger [0-1]
            boolean R2 = (gamepad1.right_bumper); // Right Trigger [0-1]

            //Linear Slide Mechanics
            if (L2) {
                robot.intakeWheels.setPower(-1);
            } else if (R2) {
                robot.intakeWheels.setPower(1);
            } else {
                robot.intakeWheels.setPower(0);
                robot.intakeWheels.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }


            //Variables for Linear Slide (GamePad2)
            double LR = (gamepad1.left_trigger); // Left Trigger [0-1]
            double RR = (gamepad1.right_trigger); // Right Trigger [0-1]

            //Linear Slide Mechanics
            if (LR > 0.75) {
                robot.linearSlideLeft.setPower(-1);
                robot.linearSlideRight.setPower(-1);
            } else if (RR > 0.75) {
                robot.linearSlideLeft.setPower(1);
                robot.linearSlideRight.setPower(1);
            } else {
                robot.linearSlideLeft.setPower(0);
                robot.linearSlideRight.setPower(0);
                robot.linearSlideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                robot.linearSlideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }



            //Variables for Scoring Bucket (GamePad1)
            boolean CurrentXState = (gamepad1.x); // X Button [True/False]
            boolean CurrentBState = (gamepad1.b);
            boolean CurrentAState = (gamepad1.dpad_up );
            boolean CurrentYState = (gamepad1.y);
//            boolean buttonPressed = false;

            //Scoring Bucket Mechanics
            if (CurrentXState && !PreviousXState) {
                BucketPosition = !BucketPosition;
            }
            if (!BucketPosition) {
                robot.scoringBucket.setPosition(0.28); /*This position should be the Initial posistion since your button wont be true when you start the code*/;
            } else if (BucketPosition) {
                robot.scoringBucket.setPosition(0.81);
            }
            PreviousXState = CurrentXState;


            //stopper
            if(CurrentBState && !PreviousBState) {
                StopperPosition = !StopperPosition;
            }
            if (!StopperPosition) {
                robot.stopper.setPosition(0.75);
            }
            else if (StopperPosition) {
                robot.stopper.setPosition(0.25);
            }

            PreviousBState = CurrentBState;

            //launcher
            if(CurrentAState && !PreviousAState) {
                LauncherPosition = !LauncherPosition;
            }
            if (!LauncherPosition) {
                robot.launcher.setPosition(0.75);
            }
            else if (LauncherPosition) {
                robot.launcher.setPosition(1);
            }
            PreviousAState = CurrentAState;


            //intake
            if(CurrentYState && !PreviousYState) {
                IntakePosition = !IntakePosition;
            }
            if(!IntakePosition) {
                robot.intake.setPosition(0.25);
            } else if (IntakePosition) {
                robot.intake.setPosition(0.75);
            }
            PreviousYState = CurrentYState;





            //Telemetry/Data
            telemetry.addData("Scoring Bucket", robot.scoringBucket.getPosition());
            telemetry.addData("Stopper", robot.stopper.getPosition());
            telemetry.addData("Launcher", robot.launcher.getPosition());
            telemetry.addData("Intake", robot.intake.getPosition());
            telemetry.addData("Switch Status:", BucketPosition);
            telemetry.addData("Stopper Status", StopperPosition);

            telemetry.addData("Power", robot.frontLeft.getPower());
            telemetry.addData("Power", robot.frontRight.getPower());
            telemetry.addData("Power", robot.backRight.getPower());
            telemetry.addData("Power", robot.backLeft.getPower());
            telemetry.addData("FrontLeft", robot.frontLeft.getCurrentPosition());
            telemetry.addData("FrontRight", robot.frontRight.getCurrentPosition());
            telemetry.addData("BackLeft", robot.backLeft.getCurrentPosition());
            telemetry.addData("BackRight", robot.backRight.getCurrentPosition());
            telemetry.addData("Intake", robot.intake.getPosition());

//            telemetry.addData("Drive", Drive);
//            telemetry.addData("Strafe", -Strafe);
//            telemetry.addData("Turn", Turn);

            telemetry.update();
        }
    }
}