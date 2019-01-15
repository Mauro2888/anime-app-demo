package com.app.home.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.home.home.Model.AnimeModel;
import com.bumptech.glide.Glide;

public class DetailAnime extends AppCompatActivity {
    private static final String TAG = DetailAnime.class.getSimpleName();
    private ImageView mImageDetail;
    private TextView mTitleDetail;
    private TextView mDescriptionDetail;
    private TextView mNumbersOfEpisodes;
    private TextView mVoteAnime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_anime);
        setupEnvironment();
        getDataFromMainIntent();
        setupDataFromMainActivity();
    }

    public void setupEnvironment(){
        mImageDetail = findViewById(R.id.detail_image_anime);
        mTitleDetail = findViewById(R.id.detail_title_anime);
        mNumbersOfEpisodes = findViewById(R.id.detail_episodes_anime);
        mVoteAnime = findViewById(R.id.detail_vote_anime);
        mDescriptionDetail = findViewById(R.id.detail_description_anime);
    }

    public AnimeModel getDataFromMainIntent(){
        Intent getData = getIntent();
        AnimeModel mAnimeModelSerialize = (AnimeModel) getData.getSerializableExtra(getResources().getString(R.string.detail_data));
        return mAnimeModelSerialize;
    }

    public void setupDataFromMainActivity(){
        String image_url = getDataFromMainIntent().getImageUrl();
        String vote = getDataFromMainIntent().getScore();
        String title = getDataFromMainIntent().getTitle();
        String description = getDataFromMainIntent().getDescription();
        String episodes = getDataFromMainIntent().getEpisodes();

        Glide.with(getApplicationContext()).load(image_url).into(mImageDetail);
        mTitleDetail.setText(title);
        mDescriptionDetail.setText(description);
        mVoteAnime.setText(vote);
        mNumbersOfEpisodes.setText(episodes);

        Log.d(TAG,vote + " " + description + " " + episodes);
    }
}
