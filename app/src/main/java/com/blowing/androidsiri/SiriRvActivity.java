package com.blowing.androidsiri;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import com.blowing.androidsiri.adapter.SiriAdapter;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class SiriRvActivity extends AppCompatActivity {


    RecyclerView contentRv;
    SiriAdapter siriAdapter;
    String[] contents;
    private int rvHeight;
    int length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_siri_rv);
        contentRv = findViewById(R.id.rv_content);


        siriAdapter = new SiriAdapter(new ArrayList<String>(), this, contentRv);
        contentRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        contentRv.getRecycledViewPool().setMaxRecycledViews(Constant.FOOTER, 0);
        contentRv.post(new Runnable() {
            @Override
            public void run() {
                siriAdapter.setRvHeight(contentRv.getHeight());
                contentRv.setAdapter(siriAdapter);
            }
        });

//       ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new MyCallBack());
//       itemTouchHelper.attachToRecyclerView(contentRv);
//
//       LinearSnapHelper pagerSnapHelper = new LinearSnapHelper();
//       pagerSnapHelper.attachToRecyclerView(contentRv);
//        LinearSnapHelper snapHelper = new LinearSnapHelper();
//
//        new DividerItemDecoration();
//        new DefaultItemAnimator();
//       GridLayoutManager manager = new GridLayoutManager(this, 6);
//       manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//           @Override
//           public int getSpanSize(int i) {
//               return 2;
//           }
//       });

        contents = getResources().getStringArray(R.array.chat);
        length = contents.length;

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                update();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void update() {
        int random = ThreadLocalRandom.current().nextInt(length);
        siriAdapter.addItem(contents[random]);

    }

     class MyCallBack extends ItemTouchHelper.Callback {

         @Override
         public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
             int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
             int swipeFlags = ItemTouchHelper.LEFT;
             return makeMovementFlags(dragFlags,swipeFlags);
         }

         @Override
         public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
             return true;
         }

         @Override
         public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

         }

         @Override
         public boolean isLongPressDragEnabled() {
             return true;
         }

         @Override
         public boolean isItemViewSwipeEnabled() {
             return true;
         }
     }

}
