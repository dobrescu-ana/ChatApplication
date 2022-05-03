package com.example.chatappdemo;

import androidx.room.Dao;
import androidx.room.Insert;

import Model.User;

@Dao
public interface MyDao {

    @Insert
    public void addUser(User user);
}