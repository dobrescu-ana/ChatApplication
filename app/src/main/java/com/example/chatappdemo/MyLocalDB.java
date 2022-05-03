package com.example.chatappdemo;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import Model.User;

@Database(entities = {User.class}, version = 1)
public abstract class MyLocalDB extends RoomDatabase {
    public abstract MyDao myDao();
}
