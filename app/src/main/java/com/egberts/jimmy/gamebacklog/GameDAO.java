package com.egberts.jimmy.gamebacklog;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GameDAO {
    @Query("SELECT * FROM game")
    public List<Game> getAllGames();

    @Insert
    public void insertGame(Game game);

    @Delete
    public void deleteGame(Game game);

    @Update
    public void updateGame(Game game);
}
