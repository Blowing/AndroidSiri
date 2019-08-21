package com.blowing.androidsiri;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.blowing.androidsiri.adapter.MyAdapter;
import com.blowing.androidsiri.adapter.RvAdapter;

/**
 * Created by wujie
 * on 2019/5/8/008.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private NestedScrollView nestedScrollView;
    private RecyclerView mContentRv;
    private LinearLayout emptyLayout;
    private LinearLayout divideView;
    private int nestHeight;
    private boolean isText;
    private boolean iszhiding;
    private int currentPosition;
    private MyAdapter mContentAdapter;
    private int count = 0;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_new_add).setOnClickListener(this);
        findViewById(R.id.btn_old_add).setOnClickListener(this);
        findViewById(R.id.btn_zhiding).setOnClickListener(this);
        imageView = findViewById(R.id.image_view);



        nestedScrollView = findViewById(R.id.speech_net_scroll);
        mContentRv = findViewById(R.id.speech_rv_content);
        emptyLayout = findViewById(R.id.speech_lv_empty);
//        divideView = findViewById(R.id.divide_view);

        nestedScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (nestHeight == 0) {
                    nestHeight = nestedScrollView.getHeight();
                    emptyLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            nestHeight));
                }
            }
        });

        mContentRv.setLayoutManager(new LinearLayoutManager(this));
        mContentAdapter = new MyAdapter();
//        mContentRv.setAdapter(mContentAdapter);
        RvAdapter rvAdapter = new RvAdapter(1, this);
        mContentRv.setHasFixedSize(true);
        mContentRv.setNestedScrollingEnabled(false);
        mContentRv.getRecycledViewPool().setMaxRecycledViews(0,100);
        mContentRv.setAdapter(rvAdapter);
//        mContentRv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//
//
//
//
//
//            @SuppressLint("RestrictedApi")
//            @Override
//            public void onGlobalLayout() {
//                LinearLayoutManager layoutManager = (LinearLayoutManager) mContentRv.getLayoutManager();
//                final int lastPosition = layoutManager.findLastVisibleItemPosition();
//                View lastView = layoutManager.findViewByPosition(lastPosition);
//                View firstView = layoutManager.findViewByPosition(currentPosition);
//
//                if (isText && !iszhiding) {
//                    if (firstView != null && lastView != null && lastPosition > currentPosition) {
//                        isText = false;
//                        int contentHight = lastView.getBottom() - firstView.getTop();
//                        int bottomHeight = nestHeight - contentHight;
//                        Log.i("wuwuwu2", "contentHight:   " + contentHight + "----"+"bottomHeight:" +
//                                "  " +
//                                ""+ bottomHeight + " --- nestHeight:  "+ nestHeight);
////                        emptyDivideView.setVisibility(View.GONE);
//                        if (bottomHeight > 0 && bottomHeight <= nestHeight) {
//                            emptyLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout
//                                    .LayoutParams.MATCH_PARENT,
//                                    bottomHeight)); // bottonHeight
//                        } else {
//                            emptyLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                                    nestHeight )); // bottonHeight
////                            emptyLayout.setVisibility(View.GONE);
//                        }
//                    }
//                } else {
//                    iszhiding = false;
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            nestedScrollView.smoothScrollTo(0, emptyLayout.getTop()+1);
//                        }
//                    }, 1000);
//
//                }
//            }
//        });
//
//        emptyLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//
//                if (iszhiding) {
//                    iszhiding = false;
//                    Log.i("wuwuwu1", "top: "+ emptyLayout.getTop()+"" + "bottom:" + mContentRv
//                            .getBottom() + "rvHeight"+ mContentRv.computeVerticalScrollRange() );
//
//
//
//                    currentPosition = mContentAdapter.getItemCount();
//                }
//            }
//        });



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_new_add:
                if (mContentAdapter.getListSize() % 2 == 0) {
                    iszhiding = true;
                   // divideView.setVisibility(View.INVISIBLE);
                    emptyLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                            .MATCH_PARENT, nestHeight));
//                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
//                    layoutParams.setMargins(0, 130, 0, 0);
//                    imageView.requestLayout();

                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isText = true;
                        mContentAdapter.addItem("haha" + count++);

                    }
                }, 50);

                break;
            case R.id.btn_old_add:

                break;
            case R.id.btn_zhiding:



                break;


        }
    }


}
