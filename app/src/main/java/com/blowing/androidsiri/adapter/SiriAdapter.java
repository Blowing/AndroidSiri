package com.blowing.androidsiri.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.blowing.androidsiri.Constant;
import com.blowing.androidsiri.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wujie
 * on 2019/5/14/014.
 */
public class SiriAdapter extends RecyclerView.Adapter<SiriAdapter.MyHolder> {
    private List<String> contentList = new ArrayList<>();

    private Context mContext;
    private RecyclerView recyclerView;

    public void addItem(String content) {
        contentList.add(content);
        notifyItemChanged(getItemCount());
        if (recyclerView != null); {
            recyclerView.scrollToPosition(getItemCount());
        }

    }
    public SiriAdapter(List<String> contentList, Context mContext, RecyclerView recyclerView) {
        this.contentList = contentList;
        this.mContext = mContext;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View convertView = null;
        switch (i) {
            case Constant.IMAGE:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_image, viewGroup, false);
                break;
            case Constant.TEXT:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_text, viewGroup, false);
                break;
        }

        return new MyHolder(convertView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

    }

    @Override
    public int getItemViewType(int position) {
        switch (contentList.get(position)) {
            case "text":
                return Constant.TEXT;
            case "image":
                return Constant.IMAGE;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
