package com.blowing.androidsiri;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import com.blowing.androidsiri.adapter.SiriAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainRvActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView recyclerView;

    private NestedScrollView nestedScrollView;

    private RelativeLayout bottomLayout;

    private SiriAdapter siriAdapter;

    private List<String> contents = new ArrayList<>();

    private final String image = "image";
    private final String text = "text";
    private int nestHeight = 0;

    private boolean iszhiding = false;
    private boolean isAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_rv);
        initView();
        initRv();
    }

    private void initView() {
        nestedScrollView = findViewById(R.id.net_scroll);
        bottomLayout = findViewById(R.id.bottom_layout);
        recyclerView = findViewById(R.id.rv);
        findViewById(R.id.btn_add_image).setOnClickListener(this);
        findViewById(R.id.btn_add_text).setOnClickListener(this);
        findViewById(R.id.btn_add_restart).setOnClickListener(this);

        nestedScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                nestHeight = nestedScrollView.getHeight();
            }
        });

        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (iszhiding) {
                    iszhiding = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            siriAdapter.addItem(text);
                        }
                    });
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
            @Override
            public void onGlobalLayout() {
                if (isAdd) {
                    isAdd = false;
                   View view =  recyclerView.getLayoutManager().findViewByPosition(siriAdapter.getItemCount() -1);
                    nestedScrollView.smoothScrollTo(0, view.getTop());
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_image:
                siriAdapter.addItem(image);

                isAdd = true;
                break;
            case R.id.btn_add_text:
                siriAdapter.addItem(text);
//                nestedScrollView.fullScroll(ScrollView.FOCUS_DOWN);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        nestedScrollView.smoothScrollTo(0, bottomLayout.getTop());
//                    }
//                });
                break;
            case R.id.btn_add_restart:
                iszhiding = true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bottomLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                nestHeight));
                        nestedScrollView.fullScroll(ScrollView.FOCUS_DOWN);

                    }
                });

                break;
        }
    }
}
