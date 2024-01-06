package com.liyun.speechdemo;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;

import org.json.JSONObject;


/**
 *
 * @author pratik
 */
public class Polly
{
    private static final String TAG = "Polly";

    private Context mContext;

    private AmazonPollyClient polly;
    private Voice voice;
    SynthesizeSpeechRequest synthReq;
    private BasicAWSCredentials mAwsCredentials;
    private String AWS_ACCESS_KEY;
    private String AWS_SECRET_KEY;

    public Polly() {
    }

    public InputStream synthesize(String text, OutputFormat format) throws IOException {



        if(text.contains("<speak>"))
        {
            synthReq =
                    new SynthesizeSpeechRequest().withTextType("ssml").withText(text).withVoiceId(voice.getId())
                            .withOutputFormat(format);
        }
        else
        {
            synthReq =
                    new SynthesizeSpeechRequest().withText(text).withVoiceId(voice.getId())
                            .withOutputFormat(format);
        }
        SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);


        return synthRes.getAudioStream();
    }

    public Polly(Context context, Region region)
    {
        mContext = context;

        Boolean hasAWSKey = getAwsConfiguration();

        if (!hasAWSKey) {
            Log.e(TAG, "Invalid aws keys");
            return;
        } else {
            Log.d(TAG, "Valid aws keys");
        }
        AWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY, AWS_SECRET_KEY);
        Log.d(TAG, AWS_ACCESS_KEY);


        // create an Amazon Polly client in a specific region
//        AWSCredentials credentials = new BasicAWSCredentials(
//                "",
//                "");

        Runnable runnable = new Runnable()
        {
            @Override
            public void run()
            {
                polly = new AmazonPollyClient(credentials,new ClientConfiguration());
                polly.setRegion(region);
                // Create describe voices request.
                DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();//en-IN

                // DescribeVoicesRequest describeVoicesRequestIndian = new DescribeVoicesRequest().withLanguageCode("en-IN");
                // Synchronously ask Amazon Polly to describe available TTS voices.
                DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
                voice = describeVoicesResult.getVoices().get(0);
            }
        };
    }

    private boolean getAwsConfiguration() {
        try {
            InputStream inputStream = mContext.getAssets().open("awsconfiguration.json");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, "UTF-8");

            JSONObject AWSConfig = new JSONObject(json);
            AWS_ACCESS_KEY = AWSConfig.getString("AWS_ACCESS_KEY");
            AWS_SECRET_KEY = AWSConfig.getString("AWS_SECRET_KEY");
            Log.d(TAG, "aaa " + AWS_ACCESS_KEY + " " + AWS_ACCESS_KEY);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}