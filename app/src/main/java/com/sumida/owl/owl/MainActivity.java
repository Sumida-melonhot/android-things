package com.sumida.owl.owl;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.Locale;

import static android.content.ContentValues.TAG;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManager;


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
    private Gpio mLedGpio;
    private TextToSpeech ttsObject;
    private static final String UTTERANCE_ID = BuildConfig.APPLICATION_ID + ".UTTERANCE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ttsObject.speak("Life Life Life", TextToSpeech.QUEUE_ADD, null, UTTERANCE_ID);

                try {
                    mLedGpio = PeripheralManager.getInstance().openGpio("BCM8");
                    mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
                    mLedGpio.setValue(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                }, 2000);
            }
        });
    }
}
