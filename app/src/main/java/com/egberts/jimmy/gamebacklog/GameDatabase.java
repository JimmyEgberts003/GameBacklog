package com.egberts.jimmy.gamebacklog;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Game.class}, version = 1)
public abstract class GameDatabase extends RoomDatabase {
    public abstract GameDAO gameDAO();

    private final static String DATABASE_NAME = "game_db";

    private static GameDatabase gameDBInstance;

    public static GameDatabase getInstance(Context context) {
        if (gameDBInstance == null) {
            gameDBInstance = Room.databaseBuilder(context, GameDatabase.class, DATABASE_NAME).build();
        }
        return gameDBInstance;
    }

}
