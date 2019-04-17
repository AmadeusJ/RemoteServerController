package com.example.user_pc.remotedavidmanager_pi;

import android.util.Log;

import com.google.android.things.contrib.driver.pwmservo.Servo;

import java.io.IOException;

public class ServoMotor {
    private Servo mServo;

    public void runServo0(){
        try{
            mServo = new Servo("PWM0");
            mServo.setPulseDurationRange(0.7, 2.0);
            //mServo.setAngle(30);
            mServo.setAngleRange(-90, 90);
            mServo.setEnabled(true);
            Log.d("", "PWM0 is working.");
            //System.out.println("Servo can run!");

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Cannot start Servo" + e);
        }

    }

    public void runServo1(){
        try {
            mServo =  new Servo("PWM1");
            mServo.setPulseDurationRange(1.60, 3.30);
            mServo.setAngleRange(-90, 90);
            //mServo.setAngle(30);
            mServo.setEnabled(true);
            Log.d("", "PWM1 is working.");
            //System.out.println("Servo can run!");
        } catch (Exception e){
            e.printStackTrace();
            //System.out.println("Cannot start Servo" + e);
        }
    }

    public void setServoAngle(int angle){

        if (angle > mServo.getMaximumAngle())
            angle = (int) mServo.getMaximumAngle();

        if (angle < mServo.getMinimumAngle())
            angle = (int) mServo.getMinimumAngle();

        try {
            mServo.setAngle(angle);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public double getServoAngle(){
        //Log.d("[INFO]", "[ANNGLE]" + mServo.getAngle());
        return mServo.getAngle();
    }

    public void resetServo1(){
        try{
            mServo.setPulseDurationRange(0.7, 2.0);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void closeServo1(){
        try {
            mServo.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }


}
