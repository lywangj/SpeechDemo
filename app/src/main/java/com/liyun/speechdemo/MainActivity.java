package com.liyun.speechdemo;

import android.os.Bundle;
//import android.speech.tts.Voice;

import java.io.IOException;
import java.io.InputStream;


import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyPresigningClient;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechPresignRequest;
import com.amazonaws.services.polly.model.Voice;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    private static final String TAG = "PollyDemo";

    Polly pollyObject;
    InputStream speechStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

        String textToRead = "one two three";

        pollyObject = new Polly(this, Region.getRegion(Regions.US_WEST_1));

        welcomeUser(textToRead);
//        fileUpload(textToRead);
//        fileUpdated(textToRead);
//        fileDelete(textToRead);
//        fileDownload(textToRead);
//        errorWithFileOperation(textToRead);
//        reminderSet(textToRead);
//        logOutUser(textToRead);

    }

    public void welcomeUser(String user)
    {
        String welcome="Welcome "+user;
        try
        {
            speechStream= pollyObject.synthesize(welcome, OutputFormat.Mp3);

//            AdvancedPlayer player = new AdvancedPlayer(speechStream,javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());
//
//            player.setPlayBackListener(new PlaybackListener() {
//                @Override
//                public void playbackStarted(PlaybackEvent evt) {
//                    System.out.println("Playback started");
//
//                }
//
//                @Override
//                public void playbackFinished(PlaybackEvent evt) {
//                    System.out.println("Playback finished");
//                }
//            });
//
//            player.play();

        } catch ( IOException ex) {
            Log.e(TAG, "welcome user");
        }

    }
}