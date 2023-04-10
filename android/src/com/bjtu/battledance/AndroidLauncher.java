package com.bjtu.battledance;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.os.PersistableBundle;

import android.view.*;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;
import com.bjtu.battledance.MyGdxGame;

import java.util.ArrayList;


public class AndroidLauncher extends FragmentActivity implements AndroidFragmentApplication.Callbacks {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE;
        window.setAttributes(params);
        ActionBar actionBar = getActionBar();
        if (actionBar != null) actionBar.hide();
        View decorView = getWindow().getDecorView();
        if (decorView != null) decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

//        GameFragment fragment = new GameFragment();
//        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
//        trans.replace(android.R.id.content, fragment);
//        trans.commit();


        StartFragment fragment = new StartFragment();
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.replace(android.R.id.content, fragment);
        trans.commit();

//        StartFragment fragment = new StartFragment();
//        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
//        trans.replace(android.R.id.content, fragment);
//        trans.commit();
//                GameFragment fragment = new GameFragment();
//                FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
//                trans.replace(android.R.id.content, fragment);
//                trans.commit();

    }

    @Override
    protected void onResume() {
        Intent musicintent = new Intent(this, MyMusicService.class);
        startService(musicintent);
        super.onResume();
    }

    @Override
    protected void onPause() {
        Intent musicintent = new Intent(this, MyMusicService.class);
        stopService(musicintent);
        super.onPause();
    }

    @Override
    public void exit() {

    }

}
