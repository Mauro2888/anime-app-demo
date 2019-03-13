package com.app.home.home;
import android.app.ProgressDialog;
import android.os.PersistableBundle;
import android.support.v7.widget.SearchView;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.home.home.Adapters.AdapterAnime;
import com.app.home.home.Interfaces.OnItemClick;
import com.app.home.home.Model.AnimeModel;
import com.app.home.home.Utils.Utils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClick {

    private Gson gson;
    private ProgressBar mProgress;
    private static final String LOG = MainActivity.class.getSimpleName();
    private RecyclerView mRecyclerMain;
    private RecyclerView.LayoutManager mLayoutManagerMain;
    private List<AnimeModel> mAnimeList;
    private AdapterAnime mAdapterAnime;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setEnvironments();
    }


    public  void setEnvironments(){
        MainActivity.this.setTitle(R.string.app_name);
        mProgress = findViewById(R.id.progressbar);
        mProgress.setVisibility(View.VISIBLE);
        mRecyclerMain = findViewById(R.id.recyclerView_main);
        mLayoutManagerMain = new GridLayoutManager(getApplicationContext(),Utils.calculateNoOfColumns(getApplicationContext()));
        mRecyclerMain.setLayoutManager(mLayoutManagerMain);
        mRecyclerMain.setHasFixedSize(true);
    }



    @Override
    public void onClick(View view, int pos) {
        Intent detailActivity = new Intent(MainActivity.this,DetailAnime.class);
        detailActivity.putExtra(getResources().getString(R.string.detail_data),mAnimeList.get(pos));
        startActivity(detailActivity);
    }

    public class AsyncTask extends android.os.AsyncTask<String,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgress.setVisibility(View.VISIBLE);
            mAnimeList = new ArrayList<>();
        }

        @Override
        protected String doInBackground(String... voids) {
            StringBuilder stringBuilder = new StringBuilder();

            try {

              if (Utils.getResponse(voids[0]).isSuccessful()){
                  String jsonObj = Utils.getResponse(voids[0]).body().string();
                  stringBuilder.append(jsonObj);
                  Log.d(LOG,jsonObj);
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
                JSONArray array = jsonObject.getJSONArray("results");
                for (int i = 0; i < array.length(); i++){
                    JSONObject getObjet = array.getJSONObject(i);
                    String title = getObjet.getString("title");
                    String image_url = getObjet.getString("image_url");
                    String description = getObjet.getString("synopsis");
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
            mProgress.setVisibility(View.INVISIBLE);

        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem itemMenu = menu.findItem(R.id.search_menu);
        searchView = (SearchView) itemMenu.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                    mAnimeList.clear();
                    new AsyncTask().execute(query);
                    mAdapterAnime.notifyDataSetChanged();
                    Utils.saveShared(MainActivity.this,"title",query);
                    Bundle bundle = new Bundle();
                    bundle.putString("title",query);
                    onSaveInstanceState(bundle);

                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty())
                    Toast.makeText(MainActivity.this,newText, Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return super.onPrepareOptionsMenu(menu);
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
                new AsyncTask().execute(Utils.getShared(MainActivity.this,"title",""));
                Toast.makeText(this, "Update in Progeress", Toast.LENGTH_SHORT).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (null != Utils.getShared(MainActivity.this,"title","")){
            new AsyncTask().execute(Utils.getShared(MainActivity.this,"title",""));
            Log.d(LOG,Utils.getShared(MainActivity.this,"title",""));
        }else {
            new AsyncTask().execute("OnePiece");
        }
    }


}
