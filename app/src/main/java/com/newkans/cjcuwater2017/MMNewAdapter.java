package com.newkans.cjcuwater2017;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leo on 2017/12/16.
 */

public class MMNewAdapter extends RecyclerView.Adapter<MMNewAdapter.ViewHolder> {
    private JSONObject mJSONObject;

    //宣告 block_instagram.xml  的物件
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextViewName;
        public TextView mTextViewTime;
        public TextView mTextViewLike;
        public Button mButtonLike;
        public Button mButtonComment;
        public ImageView mImageViewUser;
        public ImageView mImageViewPhoto;

        public ViewHolder(View view) {
            super(view);
            mTextViewName = (TextView) view.findViewById(R.id.textView_name);
            mTextViewTime = (TextView) view.findViewById(R.id.textView_time);
            mTextViewLike = (TextView) view.findViewById(R.id.textView_like);
            mImageViewUser = (ImageView) view.findViewById(R.id.imageView_user);
            mImageViewPhoto = (ImageView) view.findViewById(R.id.imageView_photo);
        }
    }

    //塞入我們要的 JSONObject
    public MMNewAdapter(JSONObject data) {
        mJSONObject = data;
    }

    @Override
    public MMNewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 建立我們的 block_instagram.xml view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.block_instagram, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        try {

            JSONObject jsonObject = mJSONObject.getJSONObject("user").getJSONObject("media").getJSONArray("nodes").getJSONObject(position);

            holder.mTextViewName.setText(mJSONObject.getJSONObject("user").getString("full_name"));

            Picasso.with(holder.mImageViewPhoto.getContext()).load(jsonObject.getString("display_src")).into(holder.mImageViewPhoto);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        try {
            return mJSONObject.getJSONObject("user").getJSONObject("media").getJSONArray("nodes").length();
        } catch (JSONException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
