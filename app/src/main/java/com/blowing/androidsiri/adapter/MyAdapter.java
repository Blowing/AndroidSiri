package com.blowing.androidsiri.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import com.blowing.androidsiri.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wujie
 * on 2019/7/26/026.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private List<String> list = new ArrayList<>();



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



    @Override
    public int getItemCount() {
        return 50;
//        if (list.size() % 2 != 0) {
//            return list.size()+1;
//        } else {
//            return list.size();
//        }
    }

    class MyHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private WebView webView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.show_text);
//            webView = itemView.findViewById(R.id.web_test);

        }
    }
}
