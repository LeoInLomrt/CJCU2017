package com.newkans.cjcuwater2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import me.itangqi.waveloadingview.WaveLoadingView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    WaveLoadingView mWaveLoadingView = null;
    private TextView mTextViewPercentage = null;
    private JSONObject mJsonObject = null;
    private OkHttpClient mOkHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewPercentage = findViewById(R.id.textView_percentage);
        mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoadingView);

//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                OkHttpClient client = new OkHttpClient();
//
//                Request request = new Request.Builder()
//                        .url("http://chihsuan.github.io/data/data.json")
//                        .build();
//
//                try {
//                    Response response = client.newCall(request).execute();
//
//                    String data = response.body().string();
//                    Log.e("AAA", data);
//                    mJsonObject = new JSONObject(data);
//
//                    Log.e("JSON", mJsonObject.getJSONObject("牡丹水庫").getString("percentage"));
//
//                    runOnUiThread(new Runnable() {
//                        public void run() {
//                            try {
//                                mTextViewPercentage.setText(mJsonObject.getJSONObject("牡丹水庫").getString("percentage"));
//                                mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
//                                mWaveLoadingView.setCenterTitle(mJsonObject.getJSONObject("牡丹水庫").getString("percentage"));
//                                mWaveLoadingView.setCenterTitleColor(Color.WHITE);
//                                mWaveLoadingView.setBottomTitleSize(18);
//                                mWaveLoadingView.setProgressValue(mJsonObject.getJSONObject("牡丹水庫").getInt("percentage"));
//                                mWaveLoadingView.setBorderWidth(10);
//                                mWaveLoadingView.setAmplitudeRatio(60);
//                                mWaveLoadingView.setWaveColor(Color.GRAY);
//                                mWaveLoadingView.setBorderColor(Color.GRAY);
//                                mWaveLoadingView.setAnimDuration(3000);
//                                mWaveLoadingView.pauseAnimation();
//                                mWaveLoadingView.resumeAnimation();
//                                mWaveLoadingView.cancelAnimation();
//                                mWaveLoadingView.startAnimation();
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    });
//
//
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();






        Request request = new Request.Builder()
                .url("http://chihsuan.github.io/data/data.json")
                .build();
        //new call
        Call call = mOkHttpClient.newCall(request);
            //请求加入调度
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                try {
                    mJsonObject = new JSONObject(data);
                    Log.e("JSON", mJsonObject.getJSONObject("牡丹水庫").getString("percentage"));
                    runOnUiThread(new Runnable() {
                        public void run() {
                            try {
                                mTextViewPercentage.setText(mJsonObject.getJSONObject("牡丹水庫").getString("percentage"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });



    }


}
