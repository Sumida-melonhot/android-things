package com.sumida.owl.owl;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

public class TTSControler {

    private TextToSpeech ttsObject;

    public void TTSPlay(Context context, String ttsString){
        ttsObject = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS){
                    ttsObject.setLanguage(Locale.JAPAN);
                    ttsObject.setPitch(1f);
                    ttsObject.setSpeechRate(1f);
                }else{
                    ttsObject = null;
                }
            }
        });

        ttsObject.speak(ttsString, TextToSpeech.QUEUE_ADD, null, "UTTERANCE_ID");
    }
}
