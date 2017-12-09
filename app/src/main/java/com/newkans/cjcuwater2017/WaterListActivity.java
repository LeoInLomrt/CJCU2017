package com.newkans.cjcuwater2017;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WaterListActivity extends AppCompatActivity {

    private ListView mListView;
    private ArrayList<String> mArrayList = new ArrayList<String>();
    private ArrayAdapter<String> mListAdapter;
    private JSONObject mJsonObject = null;
    private OkHttpClient mOkHttpClient = new OkHttpClient();

    JSONArray mJSONArray = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_list);

        mListView = (ListView)findViewById(R.id.listview);
        mListAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,mArrayList);
        mListView.setAdapter(mListAdapter);

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
                                JSONArray keys = mJsonObject.names ();

                                for (int i = 0; i < keys.length (); ++i) {
                                    mArrayList.add(keys.getString (i));
                                    mListAdapter.notifyDataSetChanged();
                                }

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




        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "你選擇的是" + mArrayList.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
