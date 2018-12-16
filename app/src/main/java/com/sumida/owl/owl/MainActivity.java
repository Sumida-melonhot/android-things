package com.sumida.owl.owl;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.content.ContentValues.TAG;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;
import com.sumida.owl.owl.TearControler;
import com.sumida.owl.owl.TTSControler;


/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
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
public class MainActivity extends Activity {

    private static final String TEAR_GPIO_BCM = "BCM8";

    private Gpio gpio;
    private TextToSpeech ttsObject;
    private static final String UTTERANCE_ID = BuildConfig.APPLICATION_ID + ".UTTERANCE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try{
            PeripheralManager prphManager = PeripheralManager.getInstance();
            gpio = prphManager.openGpio(TEAR_GPIO_BCM);
        }catch (IOException e){
            Log.e(TAG, e.toString());
        }

        TextView textView = findViewById(R.id.test);

        ttsObject = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    ttsObject.setLanguage(Locale.US);
                    ttsObject.setPitch(1f);
                    ttsObject.setSpeechRate(1f);
                    Log.i(TAG, "TextToSpeech");
                }else{
                    ttsObject = null;
                }
            }
        });

        try{
            setGpioVal(true);
        }catch (IOException e){
            Log.e(TAG, e.toString());
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttsObject.speak("Life Life Life", TextToSpeech.QUEUE_ADD, null, UTTERANCE_ID);
            }
        });
    }

    private void setGpioVal(boolean isVal) throws IOException{
        gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);
        gpio.setActiveType(Gpio.ACTIVE_LOW);

        gpio.setValue(isVal);
    }

}
