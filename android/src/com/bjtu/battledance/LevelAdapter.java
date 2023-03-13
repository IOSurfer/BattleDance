package com.bjtu.battledance;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LevelAdapter extends RecyclerView.Adapter<LevelAdapter.ViewHolder> {

    private List<MyJSON> myJSONList;
    private FragmentActivity a;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View levelview;
        TextView level;
        TextView isfinish;
        TextView score;

        public ViewHolder(View itemView) {
            super(itemView);
            levelview = itemView;
            level = itemView.findViewById(R.id.text_level);
            isfinish = itemView.findViewById(R.id.text_isfinish);
            score = itemView.findViewById(R.id.text_score);
            setIsRecyclable(false);
        }
    }

    public LevelAdapter(List<MyJSON> JSONList, FragmentActivity a) {
        myJSONList = JSONList;
        this.a = a;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.level_item, parent, false);
        ViewHolder view_holder = new ViewHolder(view);

        //view_holder.levelview.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                try {
        //                    if (a != null && a instanceof FragmentActivity) {
        //                        GameFragment fragment = new GameFragment();
        //                        FragmentTransaction trans = ((AndroidLauncher) a).getSupportFragmentManager().beginTransaction();
        //                        trans.replace(android.R.id.content, fragment);
        //                        trans.commit();
        //                    }
        //                } catch (Exception e) {
        //                    e.printStackTrace();
        //                }
        //            }
        //        });

        return view_holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyJSON myJSON = myJSONList.get(position);
        holder.level.setText("Level:" + myJSON.getLevel());
        holder.score.setText("");
        holder.isfinish.setText("");
        if (myJSON.getIsfinish().equals("true")) {
            System.out.println(myJSON.getLevel()+myJSON.getIsfinish());
            holder.score.setBackgroundResource(0);
            holder.score.setBackgroundResource(R.color.black);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (a != null && a instanceof FragmentActivity) {
                            GameFragment fragment = new GameFragment();
                            FragmentTransaction trans = ((AndroidLauncher) a).getSupportFragmentManager().beginTransaction();
                            trans.replace(android.R.id.content, fragment);
                            trans.commit();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            holder.score.setBackgroundResource(R.mipmap.level_closed);
        }
        //holder.score.setText(myJSON.getScore());
        //holder.isfinish.setText(myJSON.getIsfinish());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return myJSONList.size();
    }

//    private static Activity findActivity(@NonNull Context context) {
//        if (context instanceof Activity) {
//            return (Activity) context;
//        } else if (context instanceof ContextWrapper) {
//            return findActivity(((ContextWrapper) context).getBaseContext());
//        } else {
//            return null;
//        }
//    }

}
