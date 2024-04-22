

package Robot;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;



public class Robot {

    //Mecanum Drive Motors
    public DcMotor frontRight = null;
    public DcMotor backRight = null;
    public DcMotor frontLeft = null;
    public DcMotor backLeft = null;



    //Additional Motors And Servos
    public DcMotor linearSlideLeft = null;
    public DcMotor linearSlideRight = null;
    public DcMotor intakeWheels = null;
    public Servo scoringBucket = null;

    public Servo stopper = null;
    public Servo launcher = null;
    public Servo intake = null;



    public void init(HardwareMap hwMap) { //Mapping Motors and Sensors (The Names In Green Will Be Used When Configuring Robot On Driver Hub)
        //Establish Mecanum Drive Motors
        frontLeft = hwMap.dcMotor.get("frontLeft");
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontRight = hwMap.dcMotor.get("frontRight");
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backLeft = hwMap.dcMotor.get("backLeft");
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        backRight = hwMap.dcMotor.get("backRight");
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        //Reverse One Side Of Mecanum Drive (May Need To Swap)
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        //Establishing Additional Motors/Servos
        linearSlideLeft = hwMap.dcMotor.get("linearSlideLeft");
        linearSlideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linearSlideLeft.setDirection(DcMotorSimple.Direction.FORWARD); //Since The Slide Motors Are Facing Opposite Directions One Must Be Reversed (May Need To Swap)

        linearSlideRight = hwMap.dcMotor.get("linearSlideRight");
        linearSlideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linearSlideRight.setDirection(DcMotorSimple.Direction.REVERSE); //Since The Slide Motors Are Facing Opposite Directions One Must Be Reversed (May Need To Swap)


        intakeWheels = hwMap.dcMotor.get("intakeWheels");
        intakeWheels.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeWheels.setDirection(DcMotorSimple.Direction.FORWARD); //May Need To Change Direction Based On Which Way Intake Wheels Spin


        scoringBucket = hwMap.servo.get("scoringBucket");
        scoringBucket.setPosition(0.28);//This Is Position ZERO!!! Servos Range From 0-1 So Having 0.5 As The Starting Position Gives The Most Freedom To Code

        stopper = hwMap.servo.get("stopper");
        stopper.setPosition(0.25);

        launcher = hwMap.servo.get("launcher");
        launcher.setPosition(0.75);

        intake = hwMap.servo.get("intake");
        intake.setPosition(0.25);


    }

    public void mecDrive(double frontLeft, double frontRight, double backLeft, double backRight) {
        this.frontLeft.setPower(frontLeft);
        this.frontRight.setPower(frontRight);
        this.backLeft.setPower(backLeft);
        this.backRight.setPower(backRight);
    }

    public void brake() {
        this.mecDrive(0, 0, 0 , 0);
        this.frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}