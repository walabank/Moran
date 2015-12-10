package com.geekband.snap.moran.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geekband.snap.moran.R;
import com.geekband.snap.moran.model.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class UserInfoAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<UserInfo> mUserInfos = new ArrayList<>();
    public UserInfoAdapter(Context mContext,List<UserInfo> userInfos){
        this.mContext = mContext;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mUserInfos = userInfos;
    }
    @Override
    public int getCount() {
        return mUserInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return mUserInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_list_view_square,null);
            viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.user_name);
            viewHolder. ageTextView = (TextView) convertView.findViewById(R.id.user_age);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameTextView.setText(mUserInfos.get(position).getName());
        viewHolder.ageTextView.setText(String.valueOf(mUserInfos.get(position).getAge()));
        return convertView;
    }
    class ViewHolder{
        TextView nameTextView;
        TextView ageTextView;
    }
}
