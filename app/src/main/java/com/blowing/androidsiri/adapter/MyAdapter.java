package com.blowing.androidsiri.adapter;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import com.blowing.androidsiri.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by wujie
 * on 2019/7/26/026.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private List<String> list = new ArrayList<>();
    int size;

    public MyAdapter() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            size = ThreadLocalRandom.current().nextInt(10,50);
        }
    }


    public void addItem(String ss) {
        list.add(ss);
        if(list.size() % 2 != 0) {
            notifyItemRangeChanged(list.size()-1, 2);
        } else {
            notifyItemChanged(list.size() -1 );
        }
    }

    public int getListSize() {
        return 50;
    }
    @NonNull
    @Override
    public MyAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_text, viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, int i) {
//        if (i < list.size()) {
//            myHolder.textView.setText(list.get(i));
//        } else {
                    String html = " ";
//        html += "<head>";
//        html += "<body><a href=http://www.google.com>google home</a></body>";
//        html += "</head>";
//            myHolder.webView.setWebViewClient(new WebViewClient());
//            myHolder.webView.setWebChromeClient(new WebChromeClient());
//            myHolder.webView.getSettings().setJavaScriptEnabled(true);
////           myHolder.webView.loadData(html, "text/html", "utf-8");
//            myHolder.webView.loadUrl("http://www.baidu.com");

            myHolder.textView.setText("你是瓜娃子你是瓜");
//        }
    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int getItemCount() {



return size;

    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private WebView webView;
        private Button footerBtn;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.show_text);
            footerBtn = itemView.findViewById(R.id.btn_footer);

        }
    }
}
