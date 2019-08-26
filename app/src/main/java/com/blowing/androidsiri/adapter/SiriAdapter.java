package com.blowing.androidsiri.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.blowing.androidsiri.Constant;
import com.blowing.androidsiri.FragmentNew;
import com.blowing.androidsiri.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wujie
 * on 2019/5/14/014.
 */
public class SiriAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> contentList = new ArrayList<>();

    private Context mContext;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private FragmentManager fragmentManager;

    public void setRvHeight(int rvHeight) {
        this.rvHeight = rvHeight;
    }

    private int rvHeight;



    public void addItem(String content) {

        contentList.add(content);
        if(contentList.size() % 2 == 1) {
            if (recyclerView != null); {
                recyclerView.smoothScrollToPosition(getItemCount());
            }
        }

       notifyItemRangeChanged(getItemCount() -1 , 2);

    }
    public SiriAdapter(List<String> contentList, Context mContext, RecyclerView recyclerView) {
        this.contentList = contentList;
        this.mContext = mContext;
        this.recyclerView = recyclerView;
        this.layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        fragmentManager = ((AppCompatActivity)mContext).getSupportFragmentManager();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Log.i("wujie", "onCreateViewHolder" + i);
        View convertView = null;
        switch (i) {
            case Constant.IMAGE:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_image, viewGroup, false);
                break;
            case Constant.TEXT:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_text, viewGroup, false);
                return new MyHolder(convertView);

            case Constant.FOOTER:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout_footer, viewGroup,false);
                return new FooterHolder(convertView);

            case Constant.WEB:
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout,viewGroup, false);
//                FrameLayout frameLayout = new FrameLayout(mContext);
//                FrameLayout.LayoutParams F = new FrameLayout.LayoutParams(FrameLayout
//                        .LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
//                frameLayout.setLayoutParams(F);
//                frameLayout.setId(R.id.fragment_id);

                return new WebHolder(convertView,i);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder myHolder, int i) {
        if (myHolder instanceof MyHolder) {
            MyHolder holder = (MyHolder)myHolder;
            holder.showTv.setText(contentList.get(i));
        } else if (myHolder instanceof FooterHolder) {
//            Log.i("wujie", getFooterHeight()+"");

            FooterHolder footerHolder = (FooterHolder) myHolder;
            footerHolder.footerRv.setLayoutManager(new LinearLayoutManager(mContext));
            MyAdapter myAdapter = new MyAdapter();
            footerHolder.footerRv.setAdapter(myAdapter);
            footerHolder.btnFooter.setVisibility(View.GONE);
            int footerHeight = getFooterHeight();

            final DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
            footerHolder.itemView.measure(View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.AT_MOST),
                    View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.AT_MOST));
            int measuredHeight = footerHolder.itemView.getMeasuredHeight();
            Log.i("wujie", "measure" + measuredHeight +  "footerHeight" + footerHeight);

            if (footerHolder.itemView.getMeasuredHeight() < footerHeight) {

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, footerHeight);
                footerHolder.itemView.setLayoutParams(layoutParams);
            }
        } else if (myHolder instanceof WebHolder) {
            WebHolder webHolder = (WebHolder) myHolder;
            webHolder.fragmentNew.loadUrl();
        }
    }



    @Override
    public int getItemViewType(int position) {
        if (position == contentList.size()) {
            return Constant.FOOTER;
        } else {

//            if (position % 6 == 5) {
//                return Constant.WEB;
//            } else {

                return Constant.TEXT;
//            }
        }

    }

    private int getFooterHeight() {
        int size = contentList.size();


        layoutManager = recyclerView.getLayoutManager();
        if (size == 0) {
            return rvHeight;
        } else if (size % 2 == 0) {
            View firstView = layoutManager.findViewByPosition(size -1);
            View lastView = layoutManager.findViewByPosition(size -2);
            int viewHeight = 0;
            if (firstView != null);
            viewHeight = firstView.getHeight();
            if (lastView != null) {
                viewHeight = viewHeight + lastView.getHeight();
            }
            return rvHeight - viewHeight;
        } else {
            return rvHeight - layoutManager.findViewByPosition(size - 1).getHeight();
        }

    }

    @Override
    public int getItemCount() {
        return contentList.size()+ 1;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView showTv;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            showTv = itemView.findViewById(R.id.show_text);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {

        private RecyclerView footerRv;
        private Button btnFooter;
        public FooterHolder(@NonNull View itemView) {
            super(itemView);

            footerRv = itemView.findViewById(R.id.rv_footer);
            btnFooter = itemView.findViewById(R.id.btn_footer);
        }
    }

    class WebHolder extends RecyclerView.ViewHolder {

        private FragmentNew fragmentNew;
        WebHolder(@NonNull View itemView, int position) {
            super(itemView);
//            webView = (FrameLayout) itemView;
            fragmentNew = (FragmentNew) fragmentManager.findFragmentById(R.id.fragment_new_new);

        }
    }


}
