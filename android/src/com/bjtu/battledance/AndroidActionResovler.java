package com.bjtu.battledance;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;


public class AndroidActionResovler implements ActionResovler{
    FragmentActivity a;
    MyGdxGame myGdxGame;
    public AndroidActionResovler(FragmentActivity a) {
        this.a = a;
    }

    @Override
    public void changeFragment() {
        LevelSelectFragment fragment = new LevelSelectFragment();
        FragmentTransaction trans = ((AndroidLauncher)a).getSupportFragmentManager().beginTransaction();
        trans.replace(android.R.id.content, fragment);
        trans.commit();
    }

    private static Activity findActivity(@NonNull Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return findActivity(((ContextWrapper) context).getBaseContext());
        } else {
            return null;
        }
    }
}
