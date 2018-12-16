package com.sumida.owl.owl;

import android.os.Handler;
import android.util.Log;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;

import java.io.IOException;

import static android.content.ContentValues.TAG;

public class TearControler{

    private static String DROP_TEAR_GPIO_PIN = "BCM17";

    private Gpio tearGpio;
    private Handler tearHandler = new Handler();
    private boolean tearState = false;

    public void DropTearStart(){
        try{
            tearGpio = PeripheralManager.getInstance().openGpio(DROP_TEAR_GPIO_PIN);
            tearGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            tearHandler.post(tearRunnable);
        }catch (IOException e){
            Log.e(TAG, "owl_err", e);
        }finally {
            tearGpio = null;
        }

    }

    public void DropTearStop(){
        tearHandler.removeCallbacks(tearRunnable);
        try{
            tearState = false;

            tearGpio.setValue(tearState);
            tearGpio.close();
        }catch (IOException e){
            Log.e(TAG, "owl_err", e);
        }finally {
            tearGpio = null;
        }
    }

    private Runnable tearRunnable = new Runnable() {
        @Override
        public void run() {
            if(tearGpio == null){
                return;
            }
            try{
                tearState = true;
                tearGpio.setValue(tearState);
            }catch (IOException e){
                Log.e(TAG, "owl_err", e);
            }
        }
    };
}
