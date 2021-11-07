package com.adoresoft.rokomaritest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.adoresoft.rokomaritest.Database.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import static com.adoresoft.rokomaritest.SplashScreenActivity.doneTaskList;
import static com.adoresoft.rokomaritest.SplashScreenActivity.inProgressTaskList;
import static com.adoresoft.rokomaritest.SplashScreenActivity.openTaskList;
import static com.adoresoft.rokomaritest.SplashScreenActivity.testTaskList;

public class TaskDetailsActivity extends AppCompatActivity {
    private String taskObject;
    private int position;
    private TaskDetails taskDetails;
    private TextView taskDetailsCreatedDateTv, taskDetailsStatusTv, taskDetailsTaskNameTv, taskDetailsDescriptionTv, taskDetailsDeadlineTv;
    private ImageButton taskDetailsEmailImageButton, taskDetailsPhoneImageButton, taskDetailsUrlImageButton, taskDetailsDeleteimageButton;
    DatabaseHelper myDB;
    private FloatingActionButton taskDetailsfloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        taskDetailsCreatedDateTv = findViewById(R.id.taskDetailsCreatedDateTv);
        taskDetailsStatusTv = findViewById(R.id.taskDetailsStatusTv);
        taskDetailsTaskNameTv = findViewById(R.id.taskDetailsTaskNameTv);
        taskDetailsDescriptionTv = findViewById(R.id.taskDetailsDescriptionTv);
        taskDetailsDeadlineTv = findViewById(R.id.taskDetailsDeadlineTv);

        taskDetailsEmailImageButton = findViewById(R.id.taskDetailsEmailImageButton);
        taskDetailsPhoneImageButton = findViewById(R.id.taskDetailsPhoneImageButton);
        taskDetailsUrlImageButton = findViewById(R.id.taskDetailsUrlImageButton);
        taskDetailsDeleteimageButton = findViewById(R.id.taskDetailsDeleteimageButton);
        taskDetailsfloatingActionButton = findViewById(R.id.taskDetailsfloatingActionButton);

        myDB = new DatabaseHelper(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskObject = extras.getString("myTaskObject");
            position = extras.getInt("position");
            Toast.makeText(this, "position: " + position, Toast.LENGTH_SHORT).show();
        }
        taskDetails = new Gson().fromJson(taskObject, TaskDetails.class);
        showDetailsOnUI();
        OnClickEmailButtonListener();
        OnClickPhoneButtonListener();
        OnClickUrlButtonListener();
        OnClickTaskDetailsDeleteButtonListener();
        OnClickFloatiingActionButtonListener();
    }

    private void showDetailsOnUI() {
        taskDetailsCreatedDateTv.setText(taskDetails.getCreatedDate());
        taskDetailsStatusTv.setText(taskDetails.getStatus());
        taskDetailsTaskNameTv.setText(taskDetails.getName());
        taskDetailsDescriptionTv.setText(taskDetails.getDescription());
        taskDetailsDeadlineTv.setText(taskDetails.getDeadline());
    }

    private void OnClickEmailButtonListener() {
        taskDetailsEmailImageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendEmail();
                    }
                }
        );
    }

    private void OnClickPhoneButtonListener() {
        taskDetailsPhoneImageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        phoneCall();
                    }
                }
        );
    }

    private void OnClickUrlButtonListener() {
        taskDetailsUrlImageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openURL();
                    }
                }
        );
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{taskDetails.getEmail()});
        startActivity(intent);
    }

    private void phoneCall() {
        Uri uri = Uri.parse("tel:" + taskDetails.getPhone());
        Intent callIntent = new Intent(Intent.ACTION_DIAL, uri);
        try {
            getApplicationContext().startActivity(callIntent);
        } catch (ActivityNotFoundException activityNotFoundException) {
            // place code to handle users that have no call application installed, otherwise the app crashes
        }
    }

    private void openURL() {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(taskDetails.getUrl()));
        startActivity(i);
    }

    private void OnClickTaskDetailsDeleteButtonListener() {
        taskDetailsDeleteimageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean result = myDB.deleteSingleTask(taskDetails.getId());
                        if (result == true) {
                            updateValue(taskDetails);
                            Toast.makeText(getApplicationContext(), "Task Deleted", Toast.LENGTH_LONG).show();
                            Intent activity = new Intent(getApplicationContext(), MainActivity.class);
                            activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            activity.putExtra("status", taskDetails.getStatus());
                            startActivity(activity);
                            finish();
                        }
                    }
                }
        );
    }

    private void OnClickFloatiingActionButtonListener() {
        taskDetailsfloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activity = new Intent(TaskDetailsActivity.this, EditTaskActivity.class);
                activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.putExtra("myTaskObject", new Gson().toJson(taskDetails));
                activity.putExtra("position", position);
                startActivity(activity);
            }
        });
    }

    private void updateValue(TaskDetails task) {
        if (task.getStatus().equals("Open")) {
            openTaskList.remove(position);
        } else if (task.getStatus().equals("In-Progress")) {
            inProgressTaskList.remove(position);
        } else if (task.getStatus().equals("Test")) {
            testTaskList.remove(position);
        } else if (task.getStatus().equals("Done")) {
            doneTaskList.remove(position);
        }
    }
}