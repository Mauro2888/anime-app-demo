package com.app.home.home.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.home.home.Interfaces.OnItemClick;
import com.app.home.home.Model.AnimeModel;
import com.app.home.home.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterAnime extends RecyclerView.Adapter<AdapterAnime.ViewHolderAnime> {

    private Context context;
    private List<AnimeModel>animeList;
    private OnItemClick itemClick;

    public void setItemClick(OnItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public AdapterAnime(Context context, List<AnimeModel> animeList) {
        this.context = context;
        this.animeList = animeList;
    }

    @NonNull
    @Override
    public ViewHolderAnime onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View root = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recyclerview_main_cardview,viewGroup,false);

        ViewHolderAnime holderAnime = new ViewHolderAnime(root);
        return holderAnime;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAnime viewHolderAnime, int position) {

        AnimeModel animeModel = animeList.get(position);
        Glide.with(context).load(animeModel.getImageUrl()).into(viewHolderAnime.image_holder);
        viewHolderAnime.title_holder.setText(animeModel.getTitle());
        viewHolderAnime.vote_holder.setText(animeModel.getScore());
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public class ViewHolderAnime extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title_holder;
        ImageView image_holder;
        TextView vote_holder;
        public ViewHolderAnime(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title_holder = itemView.findViewById(R.id.title_card);
            image_holder = itemView.findViewById(R.id.image_anime_card);
            vote_holder = itemView.findViewById(R.id.vote_card);
        }

        @Override
        public void onClick(View view) {
            if (itemClick != null){
                itemClick.onClick(view,getAdapterPosition());
            }
        }
    }
}
