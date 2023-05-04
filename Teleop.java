<<<<<<< Updated upstream:Teleop.java
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Teleop")
public class Teleop extends OpMode {

    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor ViperLeft;
    DcMotor ViperRight;
    CRServo inTake;

    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");
        ViperLeft = hardwareMap.dcMotor.get("ViperLeft");
        ViperRight = hardwareMap.dcMotor.get("ViperRight");
        inTake = hardwareMap.crservo.get("inTake");
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        ViperRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ViperLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void loop() {
        //Front back Left
        if (Math.abs(-gamepad1.left_stick_y) > .2) {
            frontLeft.setPower(-gamepad1.left_stick_y * -1);
            backLeft.setPower(-gamepad1.left_stick_y * -1);
        } else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        //Front back Right
        if (Math.abs(-gamepad1.right_stick_y) > .2) {
            frontRight.setPower(-gamepad1.right_stick_y * 1);
            backRight.setPower(-gamepad1.right_stick_y * 1);
        } else {
            frontRight.setPower(0);
            backRight.setPower(0);
        }
        //Intake "Input"
        if (gamepad2.right_bumper) {
            inTake.setPower(1);
        }
        else {
            inTake.setPower(0);
        }
        //Intake "Output"
        if (gamepad2.left_bumper) {
            inTake.setPower(-1);
        }
        else {
            inTake.setPower(0);
        }
        //Side speed Right
        if (gamepad1.right_bumper) {
            frontLeft.setPower(-.9);
            backLeft.setPower(.9);
            frontRight.setPower(-.9);
            backRight.setPower(.9);
        }
        else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }
        //Side speed Left
        if (gamepad1.left_bumper) {
            frontLeft.setPower(.9);
            backLeft.setPower(-.9);
            frontRight.setPower(.9);
            backRight.setPower(-.9);
        }
        else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }
        //Cascade
        if (Math.abs(-gamepad2.left_stick_y) > .1) {
            ViperLeft.setPower(-gamepad2.left_stick_y * .5);
            ViperRight.setPower(-gamepad2.left_stick_y * -.5);
        }
        else {
            ViperLeft.setPower(0);
            ViperRight.setPower(0);
        }
    }
}
=======
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "Teleop")
public class Teleop extends OpMode {

    DcMotor frontLeft;
    DcMotor backLeft;
    DcMotor frontRight;
    DcMotor backRight;
    DcMotor outSlide;

    DcMotor crAzyTaxi;

    Servo inTake;

    @Override
    public void init() {
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backLeft = hardwareMap.dcMotor.get("backLeft");
        frontRight = hardwareMap.dcMotor.get("frontRight");
        backRight = hardwareMap.dcMotor.get("backRight");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        crAzyTaxi.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void loop() {
        //Front back Left
        if (Math.abs(-gamepad1.left_stick_y) > .2) {
            frontLeft.setPower(-gamepad1.left_stick_y * -1);
            backLeft.setPower(-gamepad1.left_stick_y * -1);
        } else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
        }
        //Front back Right
        if (Math.abs(-gamepad1.right_stick_y) > .2) {
            frontRight.setPower(-gamepad1.right_stick_y * 1);
            backRight.setPower(-gamepad1.right_stick_y * 1);
        } else {
            frontRight.setPower(0);
            backRight.setPower(0);
        }
        //Side speed Right
        if (gamepad1.right_bumper) {
            frontLeft.setPower(-.9);
            backLeft.setPower(.9);
            frontRight.setPower(-.9);
            backRight.setPower(.9);
        } else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }
        //Side speed Left
        if (gamepad1.left_bumper) {
            frontLeft.setPower(.9);
            backLeft.setPower(-.9);
            frontRight.setPower(.9);
            backRight.setPower(-.9);
        } else {
            frontLeft.setPower(0);
            backLeft.setPower(0);
            frontRight.setPower(0);
            backRight.setPower(0);
        }

        //GamePad 2
        if (Math.abs(gamepad2.right_stick_x) > .2) {
            outSlide.setPower(gamepad2.right_stick_x * 1);
        } else {
            outSlide.setPower(0);
        }
        if (Math.abs(gamepad2.left_stick_y) > .2) {
            crAzyTaxi.setPower(gamepad1.left_stick_y * 1);
        } else {
            outSlide.setPower(0);
        }
        if (gamepad2.right_bumper) {
                inTake.setPosition(90);

        }
        if (gamepad2.left_bumper) {
            inTake.setPosition(0);
        }
    }
}
>>>>>>> Stashed changes:TeamCode/src/main/java/org/firstinspires/ftc/teamcode/Teleop.java
