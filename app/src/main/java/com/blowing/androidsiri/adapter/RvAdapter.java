package com.blowing.androidsiri.adapter;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.blowing.androidsiri.FragmentNew;
import com.blowing.androidsiri.R;



/**
 * Created by wujie
 * on 2019/5/13/013.
 */
public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyHolder> {

    private int id = 0x00fffffe;
    private int count;

    private AppCompatActivity activity;
    private FragmentManager fragmentManager ;
    public RvAdapter(int count, AppCompatActivity activity) {
        this.count = count;
        this.activity = activity;
        fragmentManager = activity.getSupportFragmentManager();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        FrameLayout frameLayout = new FrameLayout(activity);
        FrameLayout.LayoutParams F = new FrameLayout.LayoutParams(FrameLayout
                .LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        frameLayout.setLayoutParams(F);
        frameLayout.setId(R.id.fragment_id);
//              View  convertView  = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout
//                        .item_fragment,viewGroup, false);
        return new MyHolder(frameLayout);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {

    }

    @Override
    public void onViewAttachedToWindow(@NonNull final MyHolder holder) {
        super.onViewAttachedToWindow(holder);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FragmentNew fragmentNew = new FragmentNew();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(holder.webView.getId(), fragmentNew);
                fragmentTransaction.commit();
            }
        }, 50);


    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private FrameLayout webView;
         MyHolder(@NonNull View itemView) {
            super(itemView);
            webView = (FrameLayout) itemView;
//            webView = itemView.findViewById(R.id.fragment_layout);
        }
    }

}
