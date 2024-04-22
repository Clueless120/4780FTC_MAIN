package Auton;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import Robot.Robot;

@Autonomous(name = "BlueEasel" , group = "Auto")
public class BasicAutoBlue extends LinearOpMode {
    public Robot robot = new Robot();

    @Override
    public void runOpMode() {
        //Called Upon When INIT Is Pressed
        robot.init(hardwareMap);

        waitForStart();

        robot.mecDrive(0.5, 0.5, -0.5, 0.6);
        sleep(1520); // (4 Seconds)
        this.stop();

        //Go towards the easel
        robot.mecDrive(0.41, -0.41, 0.41, 0.41);
        sleep(695); // (1 Second)

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

        telemetry.addData("Scoring Bucket", robot.scoringBucket.getPosition());
        telemetry.addData("Stopper", robot.stopper.getPosition());



        this.stop();
    }
}