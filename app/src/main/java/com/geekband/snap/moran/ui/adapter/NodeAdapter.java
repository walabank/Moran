package com.geekband.snap.moran.ui.adapter;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.geekband.snap.moran.R;
import com.geekband.snap.moran.model.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeAdapter extends BaseAdapter{
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Node> mNodes = new ArrayList<>();
    public NodeAdapter(Context context,List<Node> nodes){
         mContext = context;
         mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         mNodes = nodes;
    }
    @Override
    public int getCount() {
        return mNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return mNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = mLayoutInflater.inflate(R.layout.item_node_square,null);
            viewHolder = new ViewHolder();
            viewHolder.addressTextView = (TextView) convertView.findViewById(R.id.address_text);
            viewHolder.imageRecyclerView = (RecyclerView) convertView.findViewById(R.id.recycler_view);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.addressTextView.setText(mNodes.get(position).getAddress());

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        viewHolder.imageRecyclerView.setLayoutManager(layoutManager);
        ImageItemAdapter itemAdapter = new ImageItemAdapter(mNodes.get(position).getImages());
        viewHolder.imageRecyclerView.setAdapter(itemAdapter);
        return convertView;
    }
    class ViewHolder{
        TextView addressTextView;
        RecyclerView imageRecyclerView;
    }
    class MylayoutManager extends LinearLayoutManager{
        public MylayoutManager(Context context){
            super(context);
        }

        @Override
        public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
            View view = recycler.getViewForPosition(0);
            if(view != null){
                measureChild(view,widthSpec,heightSpec);
                int measureWidth = View.MeasureSpec.getSize(widthSpec);
                int measureHeight = view.getMeasuredHeight();
                setMeasuredDimension(measureWidth,measureHeight);
            }
        }
    }
}
