package com.example.user_pc.remotedavidmanager_pi;

import android.os.Build;
import android.util.Log;

import com.google.android.things.pio.PeripheralManager;

import java.util.List;

public class Boards {
    private static final String RASPBERRY = "rpi3";
    private static final String EDISON_ARDUINO = "edison_arduino";

    /* Check Board is enable  */
    public static boolean enableWebserver(){
        switch (getBoardName()){
            case RASPBERRY:
                return true;
            case EDISON_ARDUINO:
                return false;
            default:
                throw new IllegalArgumentException("Unsupported device");
        }
    }


    /* Get Board Info */
    private static String getBoardName(){

        String name = Build.BOARD;
        //
        Log.i("Board", name);
        //
        if (name.equals("rpi3")){
            PeripheralManager service = PeripheralManager.getInstance();
            List<String> pinList = service.getGpioList();
            List<String> pwmList = service.getPwmList();
            //pwmPin = service.openPwm(getBoardPin());
            if (pinList.size() > 0){
                //
                //System.out.println(pinList);
                System.out.println(pwmList);
                //
                String pinName = pinList.get(0);
                //
                //System.out.println("pwmList: " + pinList);
                //
                if (pinName.startsWith("3.3v"))
                    return RASPBERRY;
            }
        }
        return name;
    }

}
