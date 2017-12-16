package com.newkans.cjcuwater2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MMInsagramActivity extends AppCompatActivity {

    private JSONObject mJSONObject = null;
    private OkHttpClient mOkHttpClient = new OkHttpClient();
    private MMInstagramAdapter mMMInstagramAdapter = null;
//    private MMNewAdapter mMMNewAdapter = null;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mminsagram);

        mRecyclerView = (RecyclerView) findViewById(R.id.list_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);


        Request request = new Request.Builder()
                .url("https://www.instagram.com/jolin_cai/?__a=1")
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
                    mJSONObject = new JSONObject(data);
                    runOnUiThread(new Runnable() {
                        public void run() {
//                            mMMNewAdapter = new MMNewAdapter(mJSONObject);
//                            mRecyclerView.setAdapter(mMMNewAdapter);
                            mMMInstagramAdapter = new MMInstagramAdapter(mJSONObject);
                            mRecyclerView.setAdapter(mMMInstagramAdapter);
                        }

                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });

//  https://imgur.com/GG3Z9kw
//  https://imgur.com/pZWdxPZ


    }
}
