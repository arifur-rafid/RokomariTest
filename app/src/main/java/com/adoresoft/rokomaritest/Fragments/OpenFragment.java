package com.adoresoft.rokomaritest.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adoresoft.rokomaritest.Adapter.TaskAdapter;
import com.adoresoft.rokomaritest.R;


import static com.adoresoft.rokomaritest.SplashScreenActivity.openTaskList;

public class OpenFragment extends Fragment {
    RecyclerView openTaskrecyclerView;
    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_open, container, false);
        openTaskrecyclerView = view.findViewById(R.id.openTaskrecyclerView);
        taskAdapter = new TaskAdapter(openTaskList, getActivity());

        openTaskrecyclerView.setHasFixedSize(true);
        openTaskrecyclerView.setItemAnimator(new DefaultItemAnimator());
        openTaskrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        openTaskrecyclerView.setAdapter(taskAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        taskAdapter.notifyDataSetChanged();
    }
}