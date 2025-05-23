package com.b.aman.atable;

import android.speech.tts.TextToSpeech;

import java.util.List;

public class PahadaUtils {

    public static void speakPahada(long number, TextToSpeech textToSpeech, List<String> multipliers) {
        StringBuilder builder = new StringBuilder();

        for (int i = 1; i <= 10; i++) {
            long result = number * i;

            String numWord = HindiNumberUtils.convert(number);
            String resultWord = HindiNumberUtils.convert(result);

            String phrase;
            if (i <= multipliers.size()) {
                phrase = numWord + " " + multipliers.get(i - 1) + " " + resultWord;
            } else {
                String iWord = HindiNumberUtils.convert(i);
                phrase = numWord + " guna " + iWord + " hai " + resultWord;
            }

            builder.append(phrase).append(". ");
        }

        textToSpeech.speak(builder.toString(), TextToSpeech.QUEUE_FLUSH, null, "table");
    }

}

