package com.newkans.cjcuwater2017;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leo on 2017/12/16.
 */
public class MMInstagramAdapter extends RecyclerView.Adapter<MMInstagramAdapter.ViewHolder> {

    private JSONObject mJSONObject ;   //資料
    private LayoutInflater mLayoutInflater;    //加載layout
    private  Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
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

    public MMInstagramAdapter(JSONObject data) {
        mJSONObject = data;
    }

    @Override
    public MMInstagramAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
            String stringCommentCount =  jsonObject.getJSONObject("comments").getString("count");
            String stringLikeCount =  jsonObject.getJSONObject("likes").getString("count");
            holder.mTextViewLike.setText(stringLikeCount + " 讚 " + stringCommentCount +" 則留言");
            holder.mImageViewPhoto.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.mImageViewUser.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Picasso.with(holder.mImageViewUser.getContext()).load(mJSONObject.getJSONObject("user").getString("profile_pic_url")).transform(new CircleTransform()).into(holder.mImageViewUser);
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

//圖片切圓形的class
class CircleTransform implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "circle";
    }
}