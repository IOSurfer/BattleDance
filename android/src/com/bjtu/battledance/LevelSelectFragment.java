package com.bjtu.battledance;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LevelSelectFragment extends Fragment {
    //private ArrayList<MyJSON> myJSONList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //System.out.println("test");
        View view = inflater.inflate(R.layout.fragment_level, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        ArrayList<MyJSON> myJSONList = new ArrayList<MyJSON>();
        for (int i = 1; i < 100; i++) {
            if (i < 5) {
                myJSONList.add(new MyJSON(new Integer(i).toString(), "true", "0"));
            }
            else {
                myJSONList.add(new MyJSON(new Integer(i).toString(), "false", "0"));
            }
        }
        super.onActivityCreated(savedInstanceState);
        RecyclerView recyclerView = getActivity().findViewById(R.id.recycleview_gamepley);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        LevelAdapter levelAdapter = new LevelAdapter(myJSONList, getActivity());
        levelAdapter.setHasStableIds(true);
        recyclerView.setAdapter(levelAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        Button button_back = (Button) getActivity().findViewById(R.id.back_button);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartFragment fragment = new StartFragment();
                FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
                trans.replace(android.R.id.content, fragment);
                trans.commit();
            }
        });
    }
}
