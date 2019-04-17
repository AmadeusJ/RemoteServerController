package com.example.user_pc.remotedavidmanager_pi;

import com.google.android.things.contrib.driver.pwmservo.Servo;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 * <p>
 * <pre>{@code
 * PeripheralManagerService service = new PeripheralManagerService();
 * mLedGpio = service.openGpio("BCM6");
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * mLedGpio.setValue(true);
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 */
public class MainActivity extends Activity implements AndroidWebServer.WebserverListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private AndroidWebServer aws;

    /* ServoMotor Settings */
    private Servo mServo1;
    private Servo mServo2;


    /* Main() */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate");

        if (Boards.enableWebserver()) {
            // Server STart
            aws = new AndroidWebServer(8180, this);
            aws.setListener(this);
        }

        /* Run Servo motors */
        try{
            // Base
            mServo1 = new Servo("PWM1");
            mServo1.setPulseDurationRange(0.3, 1.0);
            mServo1.setAngleRange(-90, 90);
            mServo1.setAngle(0);
            mServo1.setEnabled(true);

            // Middle
            mServo2 = new Servo("PWM0");
            mServo2.setPulseDurationRange(1.3, 2.3);
            mServo2.setAngleRange(-90, 90);
            mServo2.setAngle(0);
            mServo2.setEnabled(true);
            Log.d(TAG, ""+ mServo2.getAngle());

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (aws != null)
            aws.stopServer();
    }

    @Override
    public void handleCommand(int action) {
        Log.d(TAG, "Action [" + action + "]");
        BoardController.getInstance().sendData(action);
        runRobotArm(action);
    }

    public void runRobotArm(int action) {
        if (action == 1){
            try{
                mServo1.setAngle(85);
                mServo2.setAngle(80);
                Log.d(TAG, ""+ mServo2.getAngle());

            } catch (Exception e){
                e.printStackTrace();
            }
            //BaseMotor.setServoAngle(15);
            //MiddleMotor.setServoAngle(10);

        } else if (action == 2) {
            try {
                mServo1.setAngle(0);
                mServo2.setAngle(0);
                Log.d(TAG, ""+ mServo2.getAngle());

            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
