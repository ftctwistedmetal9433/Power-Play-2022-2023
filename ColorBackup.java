package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.SleeveDetection.ParkingPosition.CENTER;
import static org.firstinspires.ftc.teamcode.SleeveDetection.ParkingPosition.LEFT;
import static org.firstinspires.ftc.teamcode.SleeveDetection.ParkingPosition.RIGHT;
import static org.openftc.easyopencv.OpenCvCameraRotation.SIDEWAYS_LEFT;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name = "ColorBackup")
public class ColorBackup extends LinearOpMode {

    private DcMotor fr = null;
    private DcMotor fl = null;
    private DcMotor bl = null;
    private DcMotor br = null;
    private SleeveDetection sleeveDetection;
    private OpenCvCamera camera;
    static final double TURN_SPEED = 0.6;     // Max Turn speed to limit turn rate

    SleeveDetection pipeline = new SleeveDetection();

    private ElapsedTime runtime = new ElapsedTime();

    static final double COUNTS_PER_MOTOR_REV = 312;
    static final double DRIVE_GEAR_REDUCTION = 1.0;     // No External Gearing.
    static final double WHEEL_DIAMETER_INCHES = 3.7;     // For figuring circumference
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double DRIVE_SPEED = 0.6;

    static final double COUNTS_PER_SPOOL_MOTOR_REV = 2786.2;
    static final double DRIVE_SPOOL_GEAR_REDUCTION = 1;     // This is < 1.0 if geared UP
    static final double SPOOL_DIAMETER_INCHES = 2.2;     // For figuring circumference
    static final double ROTATION_PER_INCH = (COUNTS_PER_SPOOL_MOTOR_REV * DRIVE_SPOOL_GEAR_REDUCTION) /
            (SPOOL_DIAMETER_INCHES * 3.1415);

    // Name of the Webcam to be set in the config
    private String webcamName = "Webcam 1";

    @Override
    public void runOpMode() throws InterruptedException {

        fr = hardwareMap.get(DcMotor.class, "frontRight");
        fl = hardwareMap.get(DcMotor.class, "frontLeft");
        br = hardwareMap.get(DcMotor.class, "backRight");
        bl = hardwareMap.get(DcMotor.class, "backLeft");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);
        sleeveDetection = new SleeveDetection();
        camera.setPipeline(sleeveDetection);

        fr.setDirection(DcMotor.Direction.REVERSE);
        fl.setDirection(DcMotor.Direction.FORWARD);
        br.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.FORWARD);



        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);



        fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FtcDashboard dashboard = FtcDashboard.getInstance();
        Telemetry dashboardTelemetry = dashboard.getTelemetry();



        telemetry.addData("Starting at", "%7d :%7d",
                fr.getCurrentPosition(),
                fl.getCurrentPosition(),
                br.getCurrentPosition(),
                bl.getCurrentPosition());
        telemetry.update();

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                camera.startStreaming(320, 240, SIDEWAYS_LEFT);
            }

            @Override
            public void onError(int errorCode) {
            }
        });

        while (!isStarted()) {
            telemetry.addData("ROTATION: ", sleeveDetection.getPosition());
            telemetry.update();
            FtcDashboard.getInstance().startCameraStream(camera, 60);


            dashboardTelemetry.addData("ROTATION: ", sleeveDetection.getPosition());
            dashboardTelemetry.update();
        }
        waitForStart();

        SleeveDetection.ParkingPosition P = sleeveDetection.getPosition();

        if (P == LEFT) {
            encoderDrive(DRIVE_SPEED, 20, 20, 5.0);
        } else if (P == CENTER) {
            encoderDrive(DRIVE_SPEED, 37, 37, 5.0);
        } else if (P == RIGHT) {
            encoderDrive(DRIVE_SPEED, 37, 37, 5.0);
            encoderDrive(DRIVE_SPEED, 40, -40, 5.0);
            encoderDrive(DRIVE_SPEED, 10, 10, 5.0);

        }

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(3000);

    }


    // Ensure that the opmode is still active
    // Ensure that the opmode is still active


    public void encoderDrive(double speed,
                             double leftInches, double rightInches,
                             double timeoutS) {


        int newflTarget;
        int newfrTarget;
        int newblTarget;
        int newbrTarget;
        // Ensure that the opmode is still active
        if (opModeIsActive()) {
            // Determine new target position, and pass to motor controller
            newflTarget = fl.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newfrTarget = fr.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            newblTarget = bl.getCurrentPosition() + (int) (leftInches * COUNTS_PER_INCH);
            newbrTarget = br.getCurrentPosition() + (int) (rightInches * COUNTS_PER_INCH);
            fl.setTargetPosition(newflTarget);
            fr.setTargetPosition(newfrTarget);
            bl.setTargetPosition(newblTarget);
            br.setTargetPosition(newbrTarget);
            // Turn On RUN_TO_POSITION
            fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            // reset the timeout time and start motion.
            runtime.reset();
            fr.setPower(Math.abs(speed));
            fl.setPower(Math.abs(speed));
            br.setPower(Math.abs(speed));
            bl.setPower(Math.abs(speed));
            // keep looping while we are still active, and there is time left, and both motors are running.
            // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
            // its target position, the motion will stop.  This is "safer" in the event that the robot will
            // always end the motion as soon as possible.
            // However, if you require that BOTH motors have finished their moves before the robot continues
            // onto the next step, use (isBusy() || isBusy()) in the loop test.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (fr.isBusy() && br.isBusy() && bl.isBusy() && fl.isBusy())) {
                // Display it for the driver.
                telemetry.addData("Running to", " %7d :%7d :%7d :%7d", newfrTarget, newflTarget, newbrTarget, newblTarget);
                telemetry.addData("Currently at", " at %7d :%7d :%7d :%7d", newfrTarget, newflTarget, newbrTarget, newblTarget,
                        fr.getCurrentPosition(), fl.getCurrentPosition(), br.getCurrentPosition(), bl.getCurrentPosition());
                telemetry.update();
            }
            // Stop all motion;
            fr.setPower(0);
            fl.setPower(0);
            br.setPower(0);
            bl.setPower(0);
            // Turn off RUN_TO_POSITION
            fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            sleep(250);
        }
    }
}
