package com.liyun.speechdemo;

import android.os.AsyncTask;
import android.os.Bundle;
//import android.speech.tts.Voice;

import java.io.InputStream;
import java.util.Arrays;


import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;

import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;

import org.json.JSONObject;

public class MainActivity extends Activity {
    private static final String TAG = "PollyDemo";

//    Polly pollyObject;
    InputStream speechStream;
    private AmazonPollyClient polly;
    private Voice voice;
    SynthesizeSpeechRequest synthReq;
    private BasicAWSCredentials mAwsCredentials;
    private BasicAWSCredentials credentials;
    private MediaPlayer mediaPlayer;
    private String AWS_ACCESS_KEY;
    private String AWS_SECRET_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");

        String textToRead = "one two three";

//        pollyObject = new Polly(this, Region.getRegion(Regions.US_WEST_1));

        setupNewMediaPlayer();

        Boolean hasAWSKey = getAwsConfiguration();

        if (!hasAWSKey) {
            Log.e(TAG, "Invalid aws keys");
            return;
        } else {
            Log.d(TAG, "Valid aws keys");
        }
        credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        Log.d(TAG, AWS_ACCESS_KEY);
        // create an Amazon Polly client in a specific region
//        AWSCredentials credentials = new BasicAWSCredentials(
//                "",
//                "");

        AwsSpeech awsSpeech = new AwsSpeech();

//        Handler handler = new Handler();
//        Runnable runnable = new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "checkkkkkkkkkkk 1");
//                polly = new AmazonPollyClient(credentials, new ClientConfiguration());
//                polly.setRegion(Region.getRegion(Regions.US_WEST_1));
//                // Create describe voices request.
//                DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();//en-IN
//
//                Log.d(TAG, "checkkkkkkkkkkk 1");
//
//                // DescribeVoicesRequest describeVoicesRequestIndian = new DescribeVoicesRequest().withLanguageCode("en-IN");
//                // Synchronously ask Amazon Polly to describe available TTS voices.
//                DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
//                voice = describeVoicesResult.getVoices().get(0);
//
//                Log.d(TAG, "checkkkkkkkkkkk 1");
//
//                SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest().withText(textToRead).withVoiceId(voice.getId())
//                        .withOutputFormat(OutputFormat.Mp3);
//
//                SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);
//
//
//                speechStream = synthRes.getAudioStream();
//
//                Log.d(TAG, "checkkkkkkkkkkk 1");
//            }
//        };

        Log.d(TAG, "checkkkkkkkkkkk 2");

        // speechStream = pollyObject.synthesize(welcome, OutputFormat.Mp3);

//        SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest().withText(textToRead).withVoiceId(voice.getId())
//                .withOutputFormat(OutputFormat.Mp3);
//
//        SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);
//
//
//        speechStream = synthRes.getAudioStream();

        // welcomeUser(textToRead);
//        fileUpload(textToRead);
//        fileUpdated(textToRead);
//        fileDelete(textToRead);
//        fileDownload(textToRead);
//        errorWithFileOperation(textToRead);
//        reminderSet(textToRead);
//        logOutUser(textToRead);

    }

    private class AwsSpeech extends AsyncTask<String, Void, Void> {
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }

        @Override
        protected Void doInBackground(String... params) {
            Log.d(TAG, "doInBackground " + params[0]);
            Log.d(TAG, "checkkkkkkkkkkk 1");

            polly = new AmazonPollyClient(credentials, new ClientConfiguration());
            // polly = new AmazonPollyClient(new DefaultAWSCredentialsProviderChain(), new ClientConfiguration());

            polly.setRegion(Region.getRegion(Regions.US_WEST_1));
            // Create describe voices request.
            DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();//en-IN

            Log.d(TAG, "checkkkkkkkkkkk 1");

            // DescribeVoicesRequest describeVoicesRequestIndian = new DescribeVoicesRequest().withLanguageCode("en-IN");
            // Synchronously ask Amazon Polly to describe available TTS voices.
            DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
            voice = describeVoicesResult.getVoices().get(0);

//            try {
//                polly = new AmazonPollyClient(credentials, new ClientConfiguration());
////                polly = new AmazonPollyClient(new DefaultAWSCredentialsProviderChain(), new ClientConfiguration());
//
//                polly.setRegion(Region.getRegion(Regions.US_WEST_1));
//                // Create describe voices request.
//                DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();//en-IN
//
//                Log.d(TAG, "checkkkkkkkkkkk 1");
//
//                // DescribeVoicesRequest describeVoicesRequestIndian = new DescribeVoicesRequest().withLanguageCode("en-IN");
//                // Synchronously ask Amazon Polly to describe available TTS voices.
//                DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
//                voice = describeVoicesResult.getVoices().get(0);
//            }
//            catch (Exception ex) {
//                Log.e(TAG, "errrrrrrrrrrrrrrror");
//            }


            Log.d(TAG, "checkkkkkkkkkkk 1");

            SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest().withText("one two").withVoiceId(voice.getId())
                    .withOutputFormat(OutputFormat.Mp3);

            SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);


            speechStream = synthRes.getAudioStream();

            Log.d(TAG, "checkkkkkkkkkkk 1");
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            Log.d(TAG, "onPostExecute");
        }
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
            Log.d(TAG, "aaa " + AWS_ACCESS_KEY + " " + AWS_SECRET_KEY);
        } catch (Exception e) {
            return false;
        }
        return true;
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
                mp.start();
                // playButton.setEnabled(true);
            }
        });
        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                // playButton.setEnabled(true);
                return false;
            }
        });
    }

    public void welcomeUser(String user)
    {
        String welcome="Welcome "+user;
//        try
//        {
//            speechStream= pollyObject.synthesize(welcome, OutputFormat.Mp3);

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

//        } catch ( IOException ex) {
//            Log.e(TAG, "welcome user");
//        }

    }
}