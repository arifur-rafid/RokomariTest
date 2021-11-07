package com.adoresoft.rokomaritest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adoresoft.rokomaritest.Database.DatabaseHelper;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.adoresoft.rokomaritest.SplashScreenActivity.openTaskList;
import static com.adoresoft.rokomaritest.SplashScreenActivity.inProgressTaskList;
import static com.adoresoft.rokomaritest.SplashScreenActivity.testTaskList;
import static com.adoresoft.rokomaritest.SplashScreenActivity.doneTaskList;

public class EditTaskActivity extends AppCompatActivity {
    private String taskName = "", taskDescription = "", taskCreateDate = "", taskDeadline = "", taskStatus = "", taskEmail = "", taskPhone = "", taskURL = "", prevStatus = "";
    private String taskObject;
    private int position;
    private TaskDetails taskDetails;
    private EditText editTaskNameTv, editTaskDescriptionTv;
    private TextView editTaskDeadline;
    Dialog emailEditDialogbox, phoneEditDialogbox, UrlEditDialogbox;
    ImageButton editTaskEmailImageButton, editTaskPhoneImageButton, editTaskURLImageButton, editTaskCalenderBtn;
    Calendar myCalendar;
    Button editTaskSubmitButton;
    Spinner editTaskSpinner;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        extractData();
        editTaskNameTv = findViewById(R.id.editTaskNameTv);
        editTaskDescriptionTv = findViewById(R.id.editTaskDescriptionTv);
        editTaskDeadline = findViewById(R.id.editTaskDeadline);

        editTaskEmailImageButton = findViewById(R.id.editTaskEmailImageButton);
        editTaskPhoneImageButton = findViewById(R.id.editTaskPhoneImageButton);
        editTaskURLImageButton = findViewById(R.id.editTaskURLImageButton);
        editTaskCalenderBtn = findViewById(R.id.editTaskCalenderBtn);
        editTaskSubmitButton = findViewById(R.id.editTaskSubmitButton);

        myDB = new DatabaseHelper(this);

        editTaskSpinner = findViewById(R.id.editTaskSpinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.status_type));
        editTaskSpinner.setAdapter(arrayAdapter);

        int selectionPosition = arrayAdapter.getPosition(taskStatus);
        editTaskSpinner.setSelection(selectionPosition);

        editTaskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                taskStatus = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        emailEditDialogbox = new Dialog(this);
        phoneEditDialogbox = new Dialog(this);
        UrlEditDialogbox = new Dialog(this);

        myCalendar = Calendar.getInstance();

        showDetailsOnUI();
        OnClickEmailButtonListener();
        OnClickPhoneButtonListener();
        OnClickUrlButtonListener();
        OnClickCalendarButtonListener();
        OnClickEditTaskSubmitButtonListener();
    }

    private void extractData() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            taskObject = extras.getString("myTaskObject");
            position = extras.getInt("position");
        }
        taskDetails = new Gson().fromJson(taskObject, TaskDetails.class);
        taskName = taskDetails.getName();
        taskDescription = taskDetails.getDescription();
        taskCreateDate = taskDetails.getCreatedDate();
        taskDeadline = taskDetails.getDeadline();
        taskStatus = taskDetails.getStatus();
        prevStatus = taskDetails.getStatus();
        taskEmail = taskDetails.getEmail();
        taskPhone = taskDetails.getPhone();
        taskURL = taskDetails.getUrl();
    }

    void showDetailsOnUI() {
        editTaskNameTv.setText(taskName);
        editTaskDescriptionTv.setText(taskDescription);
        editTaskDeadline.setText(taskDeadline);
    }

    public void OnClickEmailButtonListener() {
        editTaskEmailImageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEmailInputDialogbox();
                    }
                }
        );
    }

    public void OnClickPhoneButtonListener() {
        editTaskPhoneImageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPhoneInputDialogbox();
                    }
                }
        );
    }

    public void OnClickUrlButtonListener() {
        editTaskURLImageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showURLDialogbox();
                    }
                }
        );
    }

    private void showEmailInputDialogbox() {
        emailEditDialogbox.setContentView(R.layout.email_dialogbox);
        emailEditDialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        emailEditDialogbox.setCancelable(true);
        emailEditDialogbox.show();

        Button saveEmailBtn = emailEditDialogbox.findViewById(R.id.saveEmailBtn);
        EditText enterEmailEt = emailEditDialogbox.findViewById(R.id.enterEmailEt);

        enterEmailEt.setText(taskEmail);

        saveEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskEmail = enterEmailEt.getText().toString();
                emailEditDialogbox.dismiss();
            }
        });
    }

    private void showPhoneInputDialogbox() {
        phoneEditDialogbox.setContentView(R.layout.phone_dialogbox);
        phoneEditDialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        phoneEditDialogbox.setCancelable(true);
        phoneEditDialogbox.show();

        Button savePhoneBtn = phoneEditDialogbox.findViewById(R.id.savePhoneBtn);
        EditText enterPhoneEt = phoneEditDialogbox.findViewById(R.id.enterPhoneEt);

        enterPhoneEt.setText(taskPhone);

        savePhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskPhone = enterPhoneEt.getText().toString();
                phoneEditDialogbox.dismiss();
            }
        });
    }

    private void showURLDialogbox() {
        UrlEditDialogbox.setContentView(R.layout.url_dialogbox);
        UrlEditDialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        UrlEditDialogbox.setCancelable(true);
        UrlEditDialogbox.show();

        Button saveUrlBtn = UrlEditDialogbox.findViewById(R.id.saveUrlBtn);
        EditText enterUrlEt = UrlEditDialogbox.findViewById(R.id.enterUrlEt);

        enterUrlEt.setText(taskURL);

        saveUrlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskURL = enterUrlEt.getText().toString();
                UrlEditDialogbox.dismiss();
            }
        });
    }

    public void OnClickCalendarButtonListener() {
        editTaskCalenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String x = taskDeadline.replace(".", " ");
                String[] split = x.split(" ", 0);
                int day = Integer.valueOf(split[0]);
                int month = Integer.valueOf(split[1]);
                int year = Integer.valueOf(split[2]);
                System.out.println(day + " " + month + " " + year);
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        myCalendar.set(Calendar.YEAR, i);
                        myCalendar.set(Calendar.MONTH, i1);
                        myCalendar.set(Calendar.DAY_OF_MONTH, i2);
                        updateLabel();
                    }
                }, year, month - 1, day);
                datePickerDialog.show();
            }
        });
    }

    /*    Formatting date for deadline */
    private void updateLabel() {
        String dateshowformat = "dd.MM.yyyy";
        SimpleDateFormat format = new SimpleDateFormat(dateshowformat, Locale.US);
        taskDeadline = format.format(myCalendar.getTime());
        editTaskDeadline.setText(taskDeadline);
    }

    public void OnClickEditTaskSubmitButtonListener() {
        editTaskSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskValidation()) {
                    TaskDetails task = new TaskDetails(taskDetails.getId(), taskName, taskDescription, taskCreateDate, taskDeadline, taskStatus, taskEmail, taskPhone, taskURL);
                    boolean result = myDB.updateData(task);
                    if (result == true) {
                        Toast.makeText(getApplicationContext(), "Task Updated", Toast.LENGTH_LONG).show();
                        updateValue(task);
                        Intent activity = new Intent(getApplicationContext(), MainActivity.class);
                        activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        activity.putExtra("status", taskStatus);
                        startActivity(activity);
                        finish();
                    }
                }
            }
        });
    }

    private boolean taskValidation() {
        boolean flag = true;
        taskName = editTaskNameTv.getText().toString();
        taskDescription = editTaskDescriptionTv.getText().toString();
        if (taskName.length() == 0) {
            editTaskNameTv.setError("Error");
            flag = false;
        }
        if (taskDescription.length() == 0) {
            editTaskDescriptionTv.setError("Error");
            flag = false;
        }
        return flag;
    }

    private void updateValue(TaskDetails task) {
        if (taskStatus.equals(prevStatus)) {
            if (taskStatus.equals("Open")) {
                openTaskList.set(position, task);
            } else if (taskStatus.equals("In-Progress")) {
                inProgressTaskList.set(position, task);
            } else if (taskStatus.equals("Test")) {
                testTaskList.set(position, task);
            } else if (taskStatus.equals("Done")) {
                doneTaskList.set(position, task);
            }
        } else {
            if (prevStatus.equals("Open")) {
                openTaskList.remove(position);
            } else if (prevStatus.equals("In-Progress")) {
                inProgressTaskList.remove(position);
            } else if (prevStatus.equals("Test")) {
                testTaskList.remove(position);
            } else if (prevStatus.equals("Done")) {
                doneTaskList.remove(position);
            }

            if (taskStatus.equals("Open")) {
                openTaskList.add(task);
            } else if (taskStatus.equals("In-Progress")) {
                inProgressTaskList.add(task);
            } else if (taskStatus.equals("Test")) {
                testTaskList.add(task);
            } else if (taskStatus.equals("Done")) {
                doneTaskList.add(task);
            }
        }
    }
}