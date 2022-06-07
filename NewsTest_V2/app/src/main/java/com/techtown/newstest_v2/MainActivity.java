package com.techtown.newstest_v2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    RecyclerView recyclerview;
    private MainAdapter adapter_news;
    NetResult netResult;
    private String myResponse;
    private ArrayList<Item> item_list;
    int position;
    String url;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.type_rank);
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("종합"));
        tabLayout.addTab(tabLayout.newTab().setText("시사"));
        tabLayout.setTabTextColors(Color.rgb(255,255,255),Color.rgb(255,255,255));
        recyclerview = (RecyclerView)findViewById(R.id.recyceler_view);
        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
        adapter_news = new MainAdapter();
        recyclerview.setAdapter(adapter_news);
        url = "https://ntiper.cafe24.com/TJunior/news/gets?type=A";

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                if(position==0){
                    textView.setText("종합 뉴스 랭킹 >");
                    url = "https://ntiper.cafe24.com/TJunior/news/gets?type=A";
                    Getnetwork(url);
                }
                if(position==1){
                    textView.setText("시사 뉴스 랭킹 >");
                    url = "https://ntiper.cafe24.com/TJunior/news/gets?type=B";
                    Getnetwork(url);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        Getnetwork(url);
    }

    public void Getnetwork(String url){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if(response.isSuccessful()){
                    myResponse = response.body().string();
                    netResult = new Gson().fromJson(myResponse, NetResult.class);
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(netResult.code.equals("R_OK")){
                                Log.e("TAG", "response : "+netResult.result);
                                NewsList newsList = new Gson().fromJson(new Gson().toJson(netResult.result),NewsList.class);
                                for(Item item : newsList.news){
                                    Log.e("TAG", "title : "+ item.title);
                                }
                                item_list = newsList.news;
                                new Handler(getMainLooper()).post(new Runnable() {
                                    @Override
                                    public void run() { adapter_news.setArrayList(item_list); }
                                });
                            }
                        }
                    });
                }
            }
        });
    }
}