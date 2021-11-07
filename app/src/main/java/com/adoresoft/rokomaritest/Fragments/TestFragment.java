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

import static com.adoresoft.rokomaritest.SplashScreenActivity.testTaskList;

public class TestFragment extends Fragment {
    RecyclerView testTaskrecyclerView;
    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        testTaskrecyclerView = view.findViewById(R.id.testTaskrecyclerView);
        taskAdapter = new TaskAdapter(testTaskList, getActivity());

        testTaskrecyclerView.setHasFixedSize(true);
        testTaskrecyclerView.setItemAnimator(new DefaultItemAnimator());
        testTaskrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        testTaskrecyclerView.setAdapter(taskAdapter);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        taskAdapter.notifyDataSetChanged();
    }
}