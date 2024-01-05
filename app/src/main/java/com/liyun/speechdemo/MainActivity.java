package com.liyun.speechdemo;

import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.speech.tts.Voice;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSCredentialsProviderChain;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.polly.AmazonPollyPresigningClient;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechPresignRequest;
import com.amazonaws.services.polly.model.Voice;

import org.json.JSONObject;

public class MainActivity extends Activity {
    private static final String TAG = "PollyDemo";
    private AmazonPollyPresigningClient polly;
    private Voice voice;
    private MediaPlayer mediaPlayer;
    private String AWS_ACCESS_KEY;
    private String AWS_SECRET_KEY;
    private String mTextToRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextToRead = "one two three";
        Log.d(TAG, "Input text to read: " + mTextToRead);
        setupNewMediaPlayer();
        boolean hasAWSKey = getAwsConfiguration();
        if (!hasAWSKey) {
            Log.e(TAG, "Invalid aws keys");
            return;
        } else {
            Log.d(TAG, "Valid aws keys");
        }
        AwsSpeech awsSpeech = new AwsSpeech();
        awsSpeech.execute();
    }

    private class AwsSpeech extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            Log.d(TAG, "doInBackground " + params[0]);

            ClientConfiguration cf = new ClientConfiguration();
            AWSCredentialsProvider credentials = new AWSCredentialsProviderChain(new StaticCredentialsProvider(
                    new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY)));
            polly = new AmazonPollyPresigningClient(credentials, cf);
            polly.setRegion(Region.getRegion(Regions.EU_WEST_1));

            DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();//en-IN
            DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
            voice = describeVoicesResult.getVoices().get(1);
            List<Voice> voices = describeVoicesResult.getVoices();
            Log.i(TAG, "Available Polly voices: " + voices);
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            Log.d(TAG, "onPostExecute");
            // Create speech synthesis request.
            SynthesizeSpeechPresignRequest synthesizeSpeechPresignRequest =
                    new SynthesizeSpeechPresignRequest()
                            .withText(mTextToRead)
                            // TODo: Set voice selected by the user.
                            .withVoiceId("Amy")
                            .withOutputFormat(OutputFormat.Mp3);

            // Get the presigned URL for synthesized speech audio stream.
            URL presignedSynthesizeSpeechUrl =
                    polly.getPresignedSynthesizeSpeechUrl(synthesizeSpeechPresignRequest);
            Log.i(TAG, "Playing speech from presigned URL: " + presignedSynthesizeSpeechUrl);

            // Create a media player to play the synthesized audio stream.
            if (mediaPlayer.isPlaying()) {
                setupNewMediaPlayer();
            }
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                // Set media player's data source to previously obtained URL.
                mediaPlayer.setDataSource(presignedSynthesizeSpeechUrl.toString());
            } catch (IOException e) {
                Log.e(TAG, "Unable to set data source for the media player! " + e.getMessage());
            }
            // Start the playback asynchronously (since the data source is a network stream).
            mediaPlayer.prepareAsync();
        }
    }

    void setupNewMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
                setupNewMediaPlayer();
            }
        });
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.d(TAG, "mediaPlayer.onPrepared " + mp);
                mp.start();
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
    }

    private boolean getAwsConfiguration() {
        try {
            InputStream inputStream = getAssets().open("awsconfiguration.json");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");
            JSONObject AWSConfig = new JSONObject(json);
            AWS_ACCESS_KEY = AWSConfig.getString("AWS_ACCESS_KEY");
            AWS_SECRET_KEY = AWSConfig.getString("AWS_SECRET_KEY");
            Log.d(TAG, "Using AWS keySet: " + AWS_ACCESS_KEY);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}