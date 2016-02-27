package com.zxinsight.magicwindow.domain;

import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.zxinsight.magicwindow.activity.MWApplication;

import java.io.Serializable;

public class User implements Serializable {

    private static final String TAG = "User";
    private static final String SPKey = "UserInfo";
    public String username;
    public String password;

    private static User currentUser = null;

    public static User currentUser() {
        if (currentUser == null) {
            currentUser = new User();
        }

        return currentUser;
    }

    private User() {
        SharedPreferences sp = getSharedPreferences();
        this.username = sp.getString("username", null);
        this.password = sp.getString("password", null);
    }

    public boolean isLoggedIn(){
        return !TextUtils.isEmpty(this.username);
    }

    public void login(String username,String password) {
        SharedPreferences sp = getSharedPreferences();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();

        this.setUser(username,password);

        Log.i(TAG, "Login Success");
    }

    private void setUser(String username,String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * 用户退出
     */
    public void logout() {
        SharedPreferences sp = getSharedPreferences();

        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();

        reset();
    }

    private void reset() {
        this.username = null;
        this.password = null;

        Log.i(TAG, "reset User");
    }

    private SharedPreferences getSharedPreferences() {
        SharedPreferences sp = MWApplication.getInstance().getSharedPreferences(SPKey,0);
        return sp;
    }

    public String toString() {
        return "username : " + username;
    }
}