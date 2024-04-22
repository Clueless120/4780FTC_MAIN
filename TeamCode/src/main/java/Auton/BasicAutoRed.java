package Auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Robot.Robot;

@Autonomous(name = "RedEasel", group = "Auto")
public class BasicAutoRed extends LinearOpMode {
    public Robot robot = new Robot();

    @Override
    public void runOpMode() {
        //Called Upon When INIT Is Pressed
        robot.init(hardwareMap);

        waitForStart();

        //Strafe Right
        robot.mecDrive(-0.5, -0.5, 0.5, -0.62);
        sleep(1520); // (4 Seconds)
        this.stop();

        //Go towards the easel
        robot.mecDrive(0.41, -0.41, 0.41, 0.41);
        sleep(690); // (1 Second)

        robot.linearSlideLeft.setPower(-0.5);
        robot.linearSlideRight.setPower(-0.5);
        sleep(150);
        //Deposit Pixels into the Easel
        robot.scoringBucket.setPosition(0.77);
        sleep(1000);
        robot.brake();
        sleep(1);

        robot.stopper.setPosition(0.75);
        sleep(1000);
//
//        robot.linearSlideLeft.setPower(1);
//        robot.linearSlideRight.setPower(1);
//        sleep(700);
//
//        robot.mecDrive(-1, 1, -1, -1);
//        sleep(1520);
//
//        robot.intakeWheels.setPower(-1);
//        sleep(1000);
//        robot.brake();
//        sleep(10);
//        robot.mecDrive(0.2, -0.2, 0.2, 0.2);
//        sleep(400);
//        robot.intakeWheels.setPower(-1);
//        sleep(1800);
//        robot.intakeWheels.setPower(1);
//        sleep(1000);
//        robot.brake();
//        sleep(1);
//
//        robot.mecDrive(0.7, -0.7, 0.7, 0.7);
//        sleep(2300);


        telemetry.addData("Scoring Bucket", robot.scoringBucket.getPosition());
        telemetry.addData("Stopper", robot.stopper.getPosition());

        this.stop();
    }
}