package com.example.myanime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ProgressBar progressBar;
    ArrayList<Anime> mAnime;
    CustomAdapter customAdapter;

    private int mPage = 1;
    private boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Not MyAnimeList");

        gridView = findViewById(R.id.gvGridView);
        progressBar = findViewById(R.id.loadingbar);
        mAnime = new ArrayList<>();
        customAdapter = new CustomAdapter(this, R.layout.anime_layout, mAnime);
        gridView.setAdapter(customAdapter);

        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int threshold = totalItemCount - visibleItemCount;

                if (firstVisibleItem >= threshold && totalItemCount > 0 && !isLoading) {
                    mPage = mPage + 1;
                    loadPage(mPage);
                }
            }
        });

        loadPage(mPage);


    }

    private void loadPage(int page) {
        String url = "https://api.jikan.moe/v3/top/anime/" + page + "/airing";

        isLoading = true;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d("!!!", "onFailure: No Internet");
                Toast.makeText(MainActivity.this, "Failed to connect to internet!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = response.body().string();

                    try {
                        JSONObject jsonObject = new JSONObject(json);

                        JSONArray tops = jsonObject.getJSONArray("top");

                        for(int i = 0; i < tops.length(); i++) {
                            JSONObject top = tops.getJSONObject(i);
                            int id = top.getInt("mal_id");
                            String title = top.getString("title");
                            String cover = top.getString("image_url");
                            float rating = top.getInt("score");

                            Log.d("!!!", "MAL ID: " + id);
                            Log.d("!!!", "Title: " + title);
                            Log.d("!!!", "Cover Link: " + cover);
                            Log.d("!!!", "Rating: " + rating);

                            Anime anime = new Anime(id, title, cover, rating);

                            mAnime.add(anime);

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    customAdapter.notifyDataSetChanged();
                                    progressBar.setVisibility(View.GONE);
                                    gridView.setVisibility(View.VISIBLE);

                                    isLoading = false;

                                }
                            });


                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });



    }
}
