package com.blowing.androidsiri;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.*;

/**
 * Created by wujie
 * on 2019/5/8/008.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private NestedScrollView nestedScrollView;

    private MyListView listView1;

    private MyListView listView2;

    private RelativeLayout newLayout;
    private LinearLayout chatLayout;

    private LinearLayout linearLayout;
    private RelativeLayout bottomLayout;
    private RelativeLayout guideLayout;

    private RecyclerView recyclerView;


    private MyAdapter myAdapter1;
    private MyAdapter myAdapter2;
    private int oldCont = 0;
    private int newCont = 3;

    private String TAG = "wujie";

    private int contentHight = 0;

    private boolean newAdd = false;
    private boolean oldAdd = false;
    private boolean isZhiDing = false;

    private TextView fu_text;

    private float x1,x2,y1,y2;

    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        findViewById(R.id.btn_new_add).setOnClickListener(this);
        findViewById(R.id.btn_old_add).setOnClickListener(this);
        findViewById(R.id.btn_zhiding).setOnClickListener(this);
        chatLayout = findViewById(R.id.chat_layout);
        bottomLayout = findViewById(R.id.bottom_layout);
        guideLayout = findViewById(R.id.guid_layout);
//        fu_text = findViewById(R.id.fu_text);
//        ImageSpan imageSpan = new ImageSpan(this, R.drawable.ic_launcher_foreground);
//        String text = "今天很中哟暗淡索是，设计思路的连接手机的逻辑说的就是神秘祭祀闹洞房；收到";
//        SpannableString spannableString = new SpannableString(text);
//        spannableString.setSpan(imageSpan, 0,2,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        fu_text.setText(spannableString);
        listView1 = findViewById(R.id.list1);
        myAdapter1 = new MyAdapter(getApplicationContext(), 1, oldCont,fragmentManager);
        listView1.setAdapter(myAdapter1);
        listView2 = findViewById(R.id.list2);
        myAdapter2 = new MyAdapter(getApplicationContext(), 2, newCont, fragmentManager);
        listView2.setAdapter(myAdapter2);

//        recyclerView = findViewById(R.id.rv);
//        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//        RvAdapter rvAdapter = new RvAdapter(10, this);
//        recyclerView.setAdapter(rvAdapter);

        nestedScrollView = findViewById(R.id.net_scroll);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    Log.i(TAG, "Scroll DOWN");
//                    listView1.setVisibility(View.VISIBLE);

                }
                if (scrollY < oldScrollY) {
                    Log.i(TAG, "Scroll UP");
//                    listView1.setVisibility(View.VISIBLE);
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            listView1.setVisibility(View.VISIBLE);
//                        }
//                    });
                }

                if (scrollY == 0) {
                    Log.i(TAG, "TOP SCROLL");

                }

                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    Log.i(TAG, "BOTTOM SCROLL");
//                    if (!isZhiDing) {
//                        guideLayout.setVisibility(View.VISIBLE);
//                    }
//                    isZhiDing = false;
                }
            }
        });


        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    x1 = event.getX();
                    y1 = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //当手指离开的时候
                    x2 = event.getX();
                    y2 = event.getY();
                    if (y1 - y2 > 50) {
                        Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
                        guideLayout.setVisibility(View.VISIBLE);
                    } else if (y2 - y1 > 50) {
                        Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
                        guideLayout.setVisibility(View.GONE);

                    }

                }
            return false;
        }});

        final int[] lasty = {0};
        guideLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    //当手指按下的时候
                    x1 = event.getX();
                    y1 = event.getY();
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //当手指离开的时候
                    x2 = event.getX();
                    y2 = event.getY();
                    if (y1 - y2 > 50) {
                        Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
                    } else if (y2 - y1 > 50) {
                        guideLayout.setVisibility(View.GONE);
                        Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
                    }

                }
                return true;
            }
        });

        nestedScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (contentHight == 0) {
                    contentHight = nestedScrollView.getHeight();
                }
                if (listView2 == null) {
//                     linearLayout = new LinearLayout(getBaseContext());
//                    linearLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                            nestedScrollView.getHeight());
//                    chatLayout.addView(linearLayout, layoutParams);


//                    listView2 = new MyListView(getBaseContext());
//                    Log.i(TAG+123, nestedScrollView.getHeight()+"");
//                    chatLayout.addView(listView2, layoutParams);
//                    listView2.setBackgroundColor(getResources().getColor(R.color.colorAccent));
//                    listView2.setAdapter(myAdapter2);
//                    listView2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                        @Override
//                        public void onGlobalLayout() {
//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Log.i(TAG+123, listView2.getTop()+"");
////                                    nestedScrollView.fullScroll(ScrollView.FOCUS_DOWN);
//
//                                }
//                            });
//                        }
//                    });
                }

            }
        });


        listView2.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (newAdd) {
                    newAdd = false;
                    if (listView2.getHeight() < contentHight) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, contentHight - listView2.getHeight());
                        bottomLayout.setLayoutParams(layoutParams);
                    } else {
                        bottomLayout.setVisibility(View.GONE);
                    }
                    nestedScrollView.fullScroll(ScrollView.FOCUS_DOWN);

                }

                if (isZhiDing) {
                    isZhiDing = false;
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, contentHight - listView2.getHeight());
                    bottomLayout.setLayoutParams(layoutParams);
                    bottomLayout.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nestedScrollView.smoothScrollTo(0, listView2.getTop());
                        }
                    }, 200);

                }
            }
        });

        listView1.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (oldAdd) {
                    oldAdd = false;
                    if (listView2.getHeight() < contentHight) {
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, contentHight - listView2.getHeight());
                        bottomLayout.setLayoutParams(layoutParams);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                        listView1.setVisibility(View.GONE);
//                        nestedScrollView.fullScroll(ScrollView.FOCUS_UP);
//                        View view = listView2.getChildAt(0);
//                        Log.i(TAG+"123", listView2.getTop()+"");
                            nestedScrollView.smoothScrollTo(0, listView2.getTop());
//                        nestedScrollView.fullScroll(ScrollView.FOCUS_UP);
//                        nestedScrollView.smoothScrollTo(0, 57);

                        }
                    });
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_new_add:
                newAdd = true;
                newCont++;
                myAdapter2.setmCount(newCont);

                isZhiDing = true;
                oldCont+= myAdapter2.getCount();
                myAdapter1.setmCount(oldCont);
                newCont = 1;
                myAdapter2.setmCount(newCont);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        listView1.setVisibility(View.GONE);
//                        nestedScrollView.fullScroll(ScrollView.FOCUS_UP);
//                        View view = listView2.getChildAt(0);
//                        Log.i(TAG+"123", listView2.getTop()+"");
                        nestedScrollView.smoothScrollTo(0, listView2.getTop());
//                        nestedScrollView.fullScroll(ScrollView.FOCUS_UP);
//                        nestedScrollView.smoothScrollTo(0, 57);

                    }
                });
                break;
            case R.id.btn_old_add:
                newAdd = true;
                newCont++;
                myAdapter2.setmCount(newCont);

                break;
            case R.id.btn_zhiding:
//                Log.i(TAG, newLayout.getX() + " --" + newLayout.getY());
                isZhiDing = true;
                oldCont+= myAdapter2.getCount();
                myAdapter1.setmCount(oldCont);
                newCont = 1;
                myAdapter2.setmCount(newCont);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        listView1.setVisibility(View.GONE);
//                        nestedScrollView.fullScroll(ScrollView.FOCUS_UP);
//                        View view = listView2.getChildAt(0);
//                        Log.i(TAG+"123", listView2.getTop()+"");
                        nestedScrollView.smoothScrollTo(0, listView2.getTop());
//                        nestedScrollView.fullScroll(ScrollView.FOCUS_UP);
//                        nestedScrollView.smoothScrollTo(0, 57);

                    }
                });

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
