package Auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;

import Robot.Robot;

@Autonomous(name = "BlueSideNear", group = "Auto")
public class BlueSide extends LinearOpMode {
    Robot robot = new Robot();
    private Pipeline visionProcessor;
    private WebcamName webcamName;
    private VisionPortal visionPortal;

    private ElapsedTime runtime = new ElapsedTime();


    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        visionProcessor = new Pipeline(200, 0, 150, 210, 100, 420, 150);
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), visionProcessor);


        robot.frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        sleep(100);

        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        waitForStart();
        // Process a single frame and get the starting position

        Pipeline.StartingPosition startingPos = Pipeline.StartingPosition.NONE;


        runtime.reset();
        startingPos = visionProcessor.getStartingPosition();
        while (opModeIsActive()) {
            // Process a single frame and get the starting position
//                try {

//                    Thread.sleep(1000);
//                }
//                catch(InterruptedException e) {
//
//                }

            telemetry.addData("Identified", startingPos);
            telemetry.addData("FrontLeft", robot.frontLeft.getCurrentPosition());
            telemetry.addData("FrontRight", robot.frontRight.getCurrentPosition());
            telemetry.addData("BackLeft", robot.backLeft.getCurrentPosition());
            telemetry.addData("BackRight", robot.backRight.getCurrentPosition());
            telemetry.update();

            switch (startingPos) {
                case LEFT:
                    // Move the robot towards the left

                    strafeRight(0.3, -17);
                    DriveForward(0.8, -22);
                    DriveForward(0.3, 5);
                    intake(0.3, 10);

                    strafeRight(0.5, -25);
                    Turns(0.8, -25);
                    strafeRight(0.5, 7);
                    DriveForward(0.8, 22);
                    DriveForward(0.8 , -2);
                    linearSlide(0.5, -8);
                    sleep(500);

                    robot.scoringBucket.setPosition(0.9);
                    sleep(1000);
                    robot.stopper.setPosition(0.75);


                    linearSlide(0.7, 8);
                    strafeRight(0.3, 25);
                    DriveForward(0.5, -10);
                    // Adjust the speed as needed
                    startingPos = Pipeline.StartingPosition.NONE;
                    break;
                case RIGHT:
                    // Move the robot towards the right
                    DriveForward(0.6, -27);
                    Turns(0.8, -25);
                    DriveForward(0.4, -8);
                    DriveForward(0.5, 8);
                    intake(0.3, 10);

                    strafeRight(0.3, -5);
                    DriveForward(0.8, 46);
                    DriveForward(0.8, -1);
                    linearSlide(0.7, -8);
                    sleep(500);
                    robot.scoringBucket.setPosition(0.9);
                    sleep(1000);
                    robot.stopper.setPosition(0.75);

                    linearSlide(0.7, 8);
                    strafeRight(0.3, 25);
                    DriveForward(0.5, -10);
                    startingPos = Pipeline.StartingPosition.NONE;
                    break;
                case CENTER:
                    // Move the robot towards the center
                    // You might want to adjust the speed and direction based on your robot's configuration
                    DriveForward(0.8, -33);
                    DriveForward(0.8, 8);
                    intake(0.3, 10);
                    DriveForward(0.8, 4);
                    Turns(0.8, -25);


                    DriveForward(0.8, 48);
                    linearSlide(0.5, -8);
                    sleep(500);

                    robot.scoringBucket.setPosition(0.9);
                    sleep(1000);
                    robot.stopper.setPosition(0.75);
//                            if (runtime.seconds() > 15) {
//                                robot.linearSlideLeft.setPower(0);
//                                robot.linearSlideRight.setPower(0);  // Stop the intake
//                            }

                    linearSlide(0.7, 8);
                    strafeRight(0.3, 25);
                    DriveForward(0.5, -10);
                    startingPos = Pipeline.StartingPosition.NONE;
                    break;

                case NONE:
                    break;
            }



            visionPortal.stopStreaming();
        }


        // Add additional logic to stop the robot after performing actions
    }

    public int inchesToTicks(int inches){       //function to translate inches to ticks for the motors
        final double ticksPerRev = 537.7 , wheelDiameter = 4.0, ticksPerInch = ticksPerRev / (wheelDiameter * 3.1415);

        return (int)(inches * ticksPerInch);
    }

    public void DriveForward(double power, int distance)
    {
        distance = inchesToTicks(distance);

        robot.backLeft.setTargetPosition(distance);
        robot.backRight.setTargetPosition(distance);
        robot.frontLeft.setTargetPosition(distance);
        robot.frontRight.setTargetPosition(distance);

        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontRight.setPower(power);
        robot.frontLeft.setPower(power);
        robot.backRight.setPower(power);
        robot.backLeft.setPower(power);

        while (robot.backLeft.isBusy() || robot.backRight.isBusy() || robot.frontLeft.isBusy() || robot.frontRight.isBusy()) {
            // Keep this loop running while the motors are busy
        }

        robot.frontRight.setPower(0);
        robot.frontLeft.setPower(0);
        robot.backRight.setPower(0);
        robot.backLeft.setPower(0);

        robot.brake();

        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void Turns(double power, int distance) {
        distance = inchesToTicks(distance);

        robot.backLeft.setTargetPosition(-distance);
        robot.backRight.setTargetPosition(distance);
        robot.frontLeft.setTargetPosition(-distance);
        robot.frontRight.setTargetPosition(distance);

        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontRight.setPower(power);
        robot.frontLeft.setPower(power);
        robot.backRight.setPower(power);
        robot.backLeft.setPower(power);

        while (robot.backLeft.isBusy() || robot.backRight.isBusy() || robot.frontLeft.isBusy() || robot.frontRight.isBusy()) {
            // Keep this loop running while the motors are busy
        }

        robot.frontRight.setPower(0);
        robot.frontLeft.setPower(0);
        robot.backRight.setPower(0);
        robot.backLeft.setPower(0);

        robot.brake();

        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void intake (double power, int distance) {
        distance = inchesToTicks(distance);

        robot.intakeWheels.setTargetPosition(distance);

        robot.intakeWheels.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.intakeWheels.setPower(power);

        while(robot.intakeWheels.isBusy()) {

        }
        robot.intakeWheels.setPower(0);

        robot.intakeWheels.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void linearSlide(double power, int distance) {
        distance = inchesToTicks(distance);

        robot.linearSlideLeft.setTargetPosition(distance);
        robot.linearSlideRight.setTargetPosition(distance);

        robot.linearSlideLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.linearSlideRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        robot.linearSlideLeft.setPower(power);
        robot.linearSlideRight.setPower(power);

        while(robot.linearSlideLeft.isBusy() && robot.linearSlideRight.isBusy()) {

        }
        robot.linearSlideLeft.setPower(0);
        robot.linearSlideRight.setPower(0);

        robot.linearSlideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.linearSlideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void strafeRight(double power, int distance) {
        distance = inchesToTicks(distance);

        robot.backLeft.setTargetPosition(distance);
        robot.backRight.setTargetPosition(-distance);
        robot.frontLeft.setTargetPosition(-distance);
        robot.frontRight.setTargetPosition(distance);

        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontRight.setPower(power + 0.7);
        robot.frontLeft.setPower(power + 0.7);
        robot.backRight.setPower(power);
        robot.backLeft.setPower(power);

        while (robot.backLeft.isBusy() || robot.backRight.isBusy() || robot.frontLeft.isBusy() || robot.frontRight.isBusy()) {
            // Keep this loop running while the motors are busy
        }

        robot.frontRight.setPower(0);
        robot.frontLeft.setPower(0);
        robot.backRight.setPower(0);
        robot.backLeft.setPower(0);

        robot.brake();

        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void strafeLeft(double power, int distance) {
        distance = inchesToTicks(distance);

        robot.backLeft.setTargetPosition(-distance);
        robot.backRight.setTargetPosition(distance);
        robot.frontLeft.setTargetPosition(distance);
        robot.frontRight.setTargetPosition(-distance);

        robot.backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        robot.frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        robot.frontRight.setPower(power);
        robot.frontLeft.setPower(power);
        robot.backRight.setPower(power);
        robot.backLeft.setPower(power);

        while (robot.backLeft.isBusy() || robot.backRight.isBusy() || robot.frontLeft.isBusy() || robot.frontRight.isBusy()) {
            // Keep this loop running while the motors are busy
        }

        robot.frontRight.setPower(0);
        robot.frontLeft.setPower(0);
        robot.backRight.setPower(0);
        robot.backLeft.setPower(0);

        robot.brake();

        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

}