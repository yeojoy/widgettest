package com.jei.widgettest.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jei.widgettest.R;

import java.util.List;

/**
 * Created by yeojoy on 15. 2. 5..
 */
public class VideoListAdapter extends BaseAdapter {

    private static final String TAG = VideoListAdapter.class.getSimpleName();
    
    private Context mContext;
    
    private int mLayoutId;
    
    private List<String> mVideoList;
    
    public VideoListAdapter(Context context, int resource, List<String> objects) {
        mContext = context;
        mLayoutId = resource;
        mVideoList = objects;
    }

    @Override
    public String getItem(int position) {
        return mVideoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mVideoList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mLayoutId, null);
            
            holder = new ViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        // Dto를 가져올 땐 getItem()이 좋다.
        String video = getItem(position);
        
        return convertView;
    }
    
    private class ViewHolder {
        public ImageView ivThumbnail;
        public TextView tvTitle;
        public TextView tvDesc;
        
    }
}
