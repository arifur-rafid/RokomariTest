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

import static com.adoresoft.rokomaritest.SplashScreenActivity.doneTaskList;

public class DoneFragment extends Fragment {
    RecyclerView doneTaskrecyclerView;
    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_done, container, false);
        doneTaskrecyclerView = view.findViewById(R.id.doneTaskrecyclerView);
        taskAdapter = new TaskAdapter(doneTaskList, getActivity());

        doneTaskrecyclerView.setHasFixedSize(true);
        doneTaskrecyclerView.setItemAnimator(new DefaultItemAnimator());
        doneTaskrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        doneTaskrecyclerView.setAdapter(taskAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        taskAdapter.notifyDataSetChanged();
    }
}