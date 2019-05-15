package com.blowing.androidsiri;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blowing.androidsiri.adapter.SiriAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainRvActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;

    private RecyclerView guideRv;

    private NestedScrollView nestedScrollView;

    private RelativeLayout bottomLayout;

    private RelativeLayout guideLayout;

    private LinearLayout scrollLayout;

    private MyListView myListView;

    private View dividView;

    private SiriAdapter siriAdapter;

    private SiriAdapter guideAdapter;

    private List<String> contents = new ArrayList<>();

    private List<String> guideContents = new ArrayList<>();

    private final String image = "image";
    private final String text = "text";
    private int nestHeight = 0;


    private boolean iszhiding = false;
    private boolean isAdd;

    private boolean isStart = true;
    private boolean isText;

    private int currentPosition = 0;

    private float y1;
    private float y2;
    private boolean isShowLast;
    private TranslateAnimation mShowAnimation;
    private TranslateAnimation mShowAnimation1;
    private TranslateAnimation mHideAnimation;
    private TranslateAnimation mHideAnimation1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rv);
        initView();
        initRv();
        initGuideRv();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        nestedScrollView = findViewById(R.id.net_scroll);
        bottomLayout = findViewById(R.id.bottom_layout);
        guideLayout  = findViewById(R.id.guid_layout);
        scrollLayout = findViewById(R.id.scroll_layout);
        dividView = findViewById(R.id.divide_view);
        recyclerView = findViewById(R.id.rv);
        findViewById(R.id.btn_add_image).setOnClickListener(this);
        findViewById(R.id.btn_add_text).setOnClickListener(this);
        findViewById(R.id.btn_add_restart).setOnClickListener(this);
        findViewById(R.id.btn_guide).setOnClickListener(this);

        mShowAnimation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAnimation1.setDuration(500);

         mShowAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAnimation.setDuration(500);

        mHideAnimation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHideAnimation1.setDuration(500);

         mHideAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHideAnimation.setDuration(500);
        nestedScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                nestHeight = nestedScrollView.getHeight();
                if (isStart) {
                    isStart = false;
                    bottomLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            nestHeight));
                }

            }
        });


        bottomLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (iszhiding) {
                    iszhiding = false;
                    nestedScrollView.smoothScrollTo(0, bottomLayout.getTop());
                    isText = true;
                    siriAdapter.addItem(text);
                    currentPosition = siriAdapter.getItemCount() -1 ;
                }
            }
        });

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) && !nestedScrollView.canScrollVertically(1)) {
                        Log.i("wujie", "BOTTOM SCROLL");
                        guideLayout.startAnimation(mShowAnimation);
                        guideLayout.setVisibility(View.VISIBLE);
                        nestedScrollView.startAnimation(mHideAnimation1);
                        nestedScrollView.setVisibility(View.GONE);
                    }


            }
        });







    }

    private void initRv() {
        siriAdapter = new SiriAdapter(contents, this, recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(siriAdapter);

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onGlobalLayout() {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                final int lastPosition = layoutManager.findLastVisibleItemPosition();
                View lastView = layoutManager.findViewByPosition(lastPosition);
                int firstPosition = layoutManager.findFirstVisibleItemPosition();
                View firstView = layoutManager.findViewByPosition(currentPosition);

                if (firstView != null) {
                    Log.i("wuwu", "currentPosition:   " + currentPosition + "----"+firstView.getTop());
                }
                if (lastView != null) {

                    Log.i("wuwu", "lastposition:   " + lastPosition + "----"+lastView.getBottom());
                }

                if (isText) {
                    isText = false;
                    if (firstView != null && lastView != null) {
                        int contentHight = lastView.getBottom() - firstView.getTop();
                        int bottomHeight = nestHeight - contentHight;
                        if (bottomHeight > 0) {
                            bottomLayout.setVisibility(View.VISIBLE);
                            bottomLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                    bottomHeight));
                        } else {
                            bottomLayout.setVisibility(View.GONE);
                            Log.i("wuwu", "main:" + recyclerView.computeVerticalScrollRange() + "   插值: "+ (nestedScrollView.computeVerticalScrollRange() -nestHeight));
                            if (nestedScrollView.computeVerticalScrollRange() - nestHeight > 360) {
                                nestedScrollView.smoothScrollTo(0, nestedScrollView.computeVerticalScrollRange() - nestHeight - 360);
                            }


                        }

                    }
                }

            }
        });



    }

    @SuppressLint("ClickableViewAccessibility")
    private void initGuideRv() {
        guideRv = findViewById(R.id.guide_rv);
        guideRv.setLayoutManager(new LinearLayoutManager(this));
        guideRv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        for (int i = 0; i < 50; i++) {
            guideContents.add(text);
        }

        guideAdapter = new SiriAdapter(guideContents, this, guideRv);
        guideRv.setAdapter(guideAdapter);



        guideRv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    y1 = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //当手指离开的时候
                    y2 = event.getY();
                    if (y2 - y1 > 50 && !guideRv.canScrollVertically(-1)) {
                        guideLayout.startAnimation(mHideAnimation);
                        guideLayout.setVisibility(View.GONE);
                       nestedScrollView.startAnimation(mShowAnimation1);
                        nestedScrollView.setVisibility(View.VISIBLE);

                    }

                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_image:
                siriAdapter.addItem(image);
                isText = true;
                break;
            case R.id.btn_add_text:
                isText = true;
                siriAdapter.addItem(text);
                break;
            case R.id.btn_add_restart:
                iszhiding = true;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bottomLayout.setVisibility(View.VISIBLE);
                        bottomLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                nestHeight));
//                        nestedScrollView.fullScroll(ScrollView.FOCUS_DOWN);

                    }
                });

                break;
            case R.id.btn_guide:
                nestedScrollView.setVisibility(View.GONE);
                guideLayout.setVisibility(View.VISIBLE);

                break;
        }
    }



    private static class MyAdapter extends BaseAdapter {

        private Context mContext;
        private int mType;
        private FragmentManager fragmentManager;
        private int count;

        public void setmCount(int mCount) {
            this.mCount = mCount;
            count++;
            notifyDataSetChanged();
        }

        private int mCount;

        MyAdapter(Context context, int type, int count, FragmentManager fragmentManager) {
            mContext = context;
            mType = type;
            mCount = count;
            this.fragmentManager = fragmentManager;
        }



        @Override
        public int getCount() {
            return mCount;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(mContext);
            if (mType == 1) {
//                textView.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
//                textView.setText("listview " + mType + " " + position);
            } else {
                Log.i("wuwu", position+"");
//                LayoutInflater layoutInflater = LayoutInflater.from(mContext);
//
//                return LayoutInflater.from(mContext).inflate(R.layout.item_layout, parent,false);
//                FrameLayout frameLayout = new FrameLayout(parent.getContext());
//                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                        300);
//                frameLayout.setLayoutParams(layoutParams);
//                frameLayout.setId(position);
//                FragmentNew fragmentNew = new FragmentNew();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(frameLayout.getId(), fragmentNew);
//                fragmentTransaction.commit();
//                return frameLayout;
//                textView.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
//                textView.setText("listview " +  count+"" + position);
            }
            textView.setText("listview " + mType + " " + position);
            convertView = textView;
            return convertView;
        }


    }
}
