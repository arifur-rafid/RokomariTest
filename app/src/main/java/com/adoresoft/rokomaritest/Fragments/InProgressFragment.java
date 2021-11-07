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

import static com.adoresoft.rokomaritest.SplashScreenActivity.inProgressTaskList;

public class InProgressFragment extends Fragment {
    RecyclerView inProgressTaskrecyclerView;
    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_in_progress, container, false);
        inProgressTaskrecyclerView = view.findViewById(R.id.inProgressTaskrecyclerView);
        taskAdapter = new TaskAdapter(inProgressTaskList, getActivity());

        inProgressTaskrecyclerView.setHasFixedSize(true);
        inProgressTaskrecyclerView.setItemAnimator(new DefaultItemAnimator());
        inProgressTaskrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        inProgressTaskrecyclerView.setAdapter(taskAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        taskAdapter.notifyDataSetChanged();
    }
}