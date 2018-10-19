package com.egberts.jimmy.gamebacklog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GameAdapter.GameClickListener {

    private RecyclerView recyclerView;
    private GameAdapter gameAdapter;
    public static List<Game> mGames;
    private static GameDatabase DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGames = new ArrayList<>();
        DB = GameDatabase.getInstance(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(0).setChecked(true);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDirection) {
                int index = (viewHolder.getAdapterPosition());
                new GameAsyncTask("DELETE").execute(mGames.get(index));
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        new GameAsyncTask("SELECT").execute();
        updateUI();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.navigation_add) {
                Intent intent = new Intent(MainActivity.this, AddGame.class);
                startActivityForResult(intent, 0);
                return true;
            } else {
                return false;
            }
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                Game game = data.getParcelableExtra("INSERT_GAME");
                new GameAsyncTask("INSERT").execute(game);
            } else if (requestCode == 1) {
                Game updatedGame = data.getParcelableExtra("UPDATE_GAME");
                new GameAsyncTask("UPDATE").execute(updatedGame);
            }
        } else {
            Toast.makeText(this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void reminderOnClick(int i) {
        Intent intent = new Intent(MainActivity.this, EditGame.class);
        intent.putExtra("SELECTED_GAME", mGames.get(i));
        startActivityForResult(intent, 1);
    }

    public class GameAsyncTask extends AsyncTask<Game, Void, List> {

        private String command;

        public GameAsyncTask(String command) {
            this.command = command;
        }

        protected List doInBackground(Game... games) {
            switch (command) {
                case "DELETE":
                    DB.gameDAO().deleteGame(games[0]);
                    break;
                case "INSERT":
                    DB.gameDAO().insertGame(games[0]);
                    break;
                case "UPDATE":
                    DB.gameDAO().updateGame(games[0]);
                    break;
            }
            return DB.gameDAO().getAllGames();
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            setGames(list);
        }
    }

    public void setGames(List games) {
        mGames = games;
        updateUI();
    }

    private void updateUI() {
        if (gameAdapter == null) {
            gameAdapter = new GameAdapter(this, mGames, this);
            recyclerView.setAdapter(gameAdapter);
        } else {
            gameAdapter.swapList(mGames);
        }
    }
}
