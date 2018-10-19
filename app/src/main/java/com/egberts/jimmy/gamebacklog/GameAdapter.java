package com.egberts.jimmy.gamebacklog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameViewHolder> {

    final public GameClickListener mGameClickListener;
    private Context context;
    private List<Game> gameList;

    public GameAdapter(Context context, List<Game> gameList, GameClickListener mGameClickListener) {
        this.context = context;
        this.gameList = gameList;
        this.mGameClickListener = mGameClickListener;
    }

    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.game_grid_cell, viewGroup, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder gameViewHolder, int i) {
        final Game game = gameList.get(i);
        gameViewHolder.titleTextView.setText(game.getTitle());
        gameViewHolder.platformTextView.setText(game.getPlatform());
        gameViewHolder.statusTextView.setText(game.getStatus());
        gameViewHolder.dateTextView.setText(game.getDate());
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public void swapList(List<Game> newList) {
        gameList = newList;
        if (newList != null) {
            this.notifyDataSetChanged();
        }
    }

    public interface GameClickListener {
        void reminderOnClick(int i);
    }

    public class GameViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView cardView;
        public TextView titleTextView;
        public TextView platformTextView;
        public TextView statusTextView;
        public TextView dateTextView;

        public View view;

        public GameViewHolder(View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cardView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            platformTextView = itemView.findViewById(R.id.platformTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);

            view = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mGameClickListener.reminderOnClick(clickedPosition);
        }
    }
}
