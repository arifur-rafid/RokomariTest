package com.adoresoft.rokomaritest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.adoresoft.rokomaritest.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    public static List<TaskDetails> openTaskList;
    public static List<TaskDetails> inProgressTaskList;
    public static List<TaskDetails> testTaskList;
    public static List<TaskDetails> doneTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        openTaskList = new ArrayList<>();
        inProgressTaskList = new ArrayList<>();
        testTaskList = new ArrayList<>();
        doneTaskList = new ArrayList<>();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                delay();
                startActivity();
            }
        });
        thread.start();

        GetAllTaskAsyncTask getAllTaskAsyncTask = new GetAllTaskAsyncTask();
        getAllTaskAsyncTask.execute();
    }

    private void delay() {
        for (int i = 0; i < 2; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startActivity() {
        Intent activity = new Intent(getApplicationContext(), MainActivity.class);
        activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(activity);
        finish();
    }

    public class GetAllTaskAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... voids) {
            openTaskList = new DatabaseHelper(SplashScreenActivity.this).getTask("Open");
            inProgressTaskList = new DatabaseHelper(SplashScreenActivity.this).getTask("In-Progress");
            testTaskList = new DatabaseHelper(SplashScreenActivity.this).getTask("Test");
            doneTaskList = new DatabaseHelper(SplashScreenActivity.this).getTask("Done");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}