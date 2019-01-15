package com.app.home.home;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.home.home.Adapters.AdapterAnime;
import com.app.home.home.Interfaces.OnItemClick;
import com.app.home.home.Model.AnimeModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnItemClick {

    private static final String URL_ENDPOINT = "https://api.jikan.moe/search/anime/onepiece";
    private Gson gson;
    private static final String LOG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerMain;
    private RecyclerView.LayoutManager mLayoutManagerMain;
    private List<AnimeModel> mAnimeList;
    private AdapterAnime mAdapterAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setEnvironments();
        new AsyncTask().execute();
    }



    public Response getRequest(String Url) throws IOException {

        return null;

    }

    public  void setEnvironments(){
        MainActivity.this.setTitle(R.string.app_name);
        mRecyclerMain = findViewById(R.id.recyclerView_main);
        mLayoutManagerMain = new GridLayoutManager(getApplicationContext(),calculateNoOfColumns(getApplicationContext()));
        mRecyclerMain.setLayoutManager(mLayoutManagerMain);
        mRecyclerMain.setHasFixedSize(true);
    }

    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (dpWidth / 180);
        return noOfColumns >= 2 ? noOfColumns : 2;
    }

    @Override
    public void onClick(View view, int pos) {
        Intent detailActivity = new Intent(MainActivity.this,DetailAnime.class);
        detailActivity.putExtra(getResources().getString(R.string.detail_data),mAnimeList.get(pos));
        startActivity(detailActivity);
    }

    public class AsyncTask extends android.os.AsyncTask<Void,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mAnimeList = new ArrayList<>();
        }

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder stringBuilder = new StringBuilder();
            Request request = new Request.Builder()
                    .url(URL_ENDPOINT)
                    .build();

            OkHttpClient okHttpClient = new OkHttpClient();
            try {
                Response response = okHttpClient
                        .newCall(request)
                        .execute();

              if (response.isSuccessful()){
                  String jsonObj = response.body().string();
                  stringBuilder.append(jsonObj);
                  /*Type listType = new TypeToken<List<Result>>(){}.getType();
                  List<Result>list = gson.fromJson(anime2,listType);
                  for (Result result:list) {
                      String title = result.getTitle();
                      Log.d(LOG,title);
                  }*/
              }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray array = jsonObject.getJSONArray("result");
                for (int i = 0; i < array.length(); i++){
                    JSONObject getObjet = array.getJSONObject(i);
                    String title = getObjet.getString("title");
                    String image_url = getObjet.getString("image_url");
                    String description = getObjet.getString("description");
                    String episodes = getObjet.getString("episodes");
                    String score = getObjet.getString("score");
                    mAnimeList.add(new AnimeModel(title,image_url,description,episodes,score));
                    Log.d(LOG,title + " " + image_url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mAdapterAnime = new AdapterAnime(getApplicationContext(),mAnimeList);
            mAdapterAnime.setItemClick(MainActivity.this);
            mRecyclerMain.setAdapter(mAdapterAnime);


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_layout,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.btn_main_update:
                mAdapterAnime.notifyDataSetChanged();
                new AsyncTask().execute();
                Toast.makeText(this, "Update in Progeress", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
