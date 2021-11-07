package com.adoresoft.rokomaritest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public BottomNavigationView navigation;
    public NavController navController;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        navigation.setItemIconTintList(null);
        navController = Navigation.findNavController(this, R.id.fragment);
        NavigationUI.setupWithNavController(navigation, navController);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        OnClickFloatingActionButtonListener();
    }

    public void OnClickFloatingActionButtonListener() {
        floatingActionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), AddTasksActivity.class));
                    }
                }
        );
    }
}