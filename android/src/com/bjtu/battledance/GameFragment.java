package com.bjtu.battledance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import com.badlogic.gdx.backends.android.AndroidFragmentApplication;

public class GameFragment extends AndroidFragmentApplication {
    MyGdxGame myGdxGame;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myGdxGame = new MyGdxGame(null, "1");
        return initializeForView(myGdxGame);
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        AndroidActionResovler a = new AndroidActionResovler(getActivity());
        myGdxGame.setActionResovler(a);
        a.myGdxGame=myGdxGame;
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        //myGdxGame.dispose();
        super.onDestroy();

    }
}