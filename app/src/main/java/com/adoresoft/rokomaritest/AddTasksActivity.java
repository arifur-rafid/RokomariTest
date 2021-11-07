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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.adoresoft.rokomaritest.SplashScreenActivity.doneTaskList;
import static com.adoresoft.rokomaritest.SplashScreenActivity.inProgressTaskList;
import static com.adoresoft.rokomaritest.SplashScreenActivity.openTaskList;
import static com.adoresoft.rokomaritest.SplashScreenActivity.testTaskList;

public class AddTasksActivity extends AppCompatActivity {
    private String taskName = "", taskDescription = "", taskDeadline = "", taskStatus = "", taskEmail = "", taskPhone = "", taskURL = "";
    Dialog emailInputDialogbox, phoneInputDialogbox, UrlInputDialogbox;
    ImageButton addTaskEmailImageButton, addTaskPhoneImageButton, addTaskURLImageButton, addTaskCalenderBtn;
    EditText addTaskNameTv, addTaskDescriptionTv;
    Calendar myCalendar;
    TextView addTaskDeadline;
    Button addTaskSubmitButton;
    Spinner addTaskSpinner;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tasks);
        addTaskEmailImageButton = findViewById(R.id.addTaskEmailImageButton);
        addTaskPhoneImageButton = findViewById(R.id.addTaskPhoneImageButton);
        addTaskURLImageButton = findViewById(R.id.addTaskURLImageButton);
        addTaskCalenderBtn = findViewById(R.id.addTaskCalenderBtn);
        addTaskDeadline = findViewById(R.id.addTaskDeadline);

        addTaskNameTv = findViewById(R.id.addTaskNameTv);
        addTaskDescriptionTv = findViewById(R.id.addTaskDescriptionTv);

        addTaskSubmitButton = findViewById(R.id.addTaskSubmitButton);

        myDB = new DatabaseHelper(this);

        addTaskSpinner = findViewById(R.id.addTaskSpinner);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.status_type));
        addTaskSpinner.setAdapter(arrayAdapter);

        addTaskSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                taskStatus = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                taskStatus = "Open";
            }
        });

        emailInputDialogbox = new Dialog(this);
        phoneInputDialogbox = new Dialog(this);
        UrlInputDialogbox = new Dialog(this);

        myCalendar = Calendar.getInstance();
        OnClickCalendarButtonListener();

        OnClickEmailButtonListener();
        OnClickPhoneButtonListener();
        OnClickUrlButtonListener();
        OnClickAddTaskSubmitButtonListener();
    }

    public void OnClickEmailButtonListener() {
        addTaskEmailImageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showEmailInputDialogbox();
                    }
                }
        );
    }

    public void OnClickPhoneButtonListener() {
        addTaskPhoneImageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPhoneInputDialogbox();
                    }
                }
        );
    }

    public void OnClickUrlButtonListener() {
        addTaskURLImageButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showURLDialogbox();
                    }
                }
        );
    }

    private void showEmailInputDialogbox() {
        emailInputDialogbox.setContentView(R.layout.email_dialogbox);
        emailInputDialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        emailInputDialogbox.setCancelable(true);
        emailInputDialogbox.show();

        Button saveEmailBtn = emailInputDialogbox.findViewById(R.id.saveEmailBtn);
        EditText enterEmailEt = emailInputDialogbox.findViewById(R.id.enterEmailEt);

        enterEmailEt.setText(taskEmail);

        saveEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskEmail = enterEmailEt.getText().toString();
                emailInputDialogbox.dismiss();
            }
        });
    }

    private void showPhoneInputDialogbox() {
        phoneInputDialogbox.setContentView(R.layout.phone_dialogbox);
        phoneInputDialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        phoneInputDialogbox.setCancelable(true);
        phoneInputDialogbox.show();

        Button savePhoneBtn = phoneInputDialogbox.findViewById(R.id.savePhoneBtn);
        EditText enterPhoneEt = phoneInputDialogbox.findViewById(R.id.enterPhoneEt);

        enterPhoneEt.setText(taskPhone);

        savePhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskPhone = enterPhoneEt.getText().toString();
                phoneInputDialogbox.dismiss();
            }
        });
    }

    private void showURLDialogbox() {
        UrlInputDialogbox.setContentView(R.layout.url_dialogbox);
        UrlInputDialogbox.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        UrlInputDialogbox.setCancelable(true);
        UrlInputDialogbox.show();

        Button saveUrlBtn = UrlInputDialogbox.findViewById(R.id.saveUrlBtn);
        EditText enterUrlEt = UrlInputDialogbox.findViewById(R.id.enterUrlEt);

        enterUrlEt.setText(taskURL);

        saveUrlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskURL = enterUrlEt.getText().toString();
                UrlInputDialogbox.dismiss();
            }
        });
    }

    public void OnClickCalendarButtonListener() {
        addTaskCalenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateLabel();
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTasksActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    /*    Formatting date for deadline */
    private void updateLabel() {
        String dateshowformat = "dd.MM.yyyy";
        SimpleDateFormat format = new SimpleDateFormat(dateshowformat, Locale.US);
        taskDeadline = format.format(myCalendar.getTime());
        addTaskDeadline.setText(taskDeadline);
    }

    public void OnClickAddTaskSubmitButtonListener() {
        addTaskSubmitButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (taskValidation()) {
                            TaskDetails taskDetails = new TaskDetails(1, taskName, taskDescription, getCurrentDate(), taskDeadline, taskStatus, taskEmail, taskPhone, taskURL);
                            boolean result = myDB.insertData(taskDetails);
                            if (result == true) {
                                updateValue(taskDetails);
                                Toast.makeText(getApplicationContext(), "Task Added", Toast.LENGTH_LONG).show();
                                Intent activity = new Intent(getApplicationContext(), MainActivity.class);
                                activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                activity.putExtra("status", taskStatus);
                                startActivity(activity);
                                finish();
                            }
                        }
                    }
                }
        );
    }

    public String getCurrentDate() {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String currentDateTime = format.format(new Date());
        return currentDateTime;
    }

    private boolean taskValidation() {
        boolean flag = true;
        taskName = addTaskNameTv.getText().toString();
        taskDescription = addTaskDescriptionTv.getText().toString();
        taskDeadline = addTaskDeadline.getText().toString();
        if (taskName.length() == 0) {
            addTaskNameTv.setError("Error");
            flag = false;
        }
        if (taskDescription.length() == 0) {
            addTaskDescriptionTv.setError("Error");
            flag = false;
        }
        if (taskDeadline.length() == 0) {
            addTaskDeadline.setError("Error");
            flag = false;
        }
        return flag;
    }

    private void updateValue(TaskDetails task) {
        if (taskStatus.equals("Open")) {
            task.setId(openTaskList.size());
            openTaskList.add(task);
        } else if (taskStatus.equals("In-Progress")) {
            task.setId(inProgressTaskList.size());
            inProgressTaskList.add(task);
        } else if (taskStatus.equals("Test")) {
            task.setId(testTaskList.size());
            testTaskList.add(task);
        } else if (taskStatus.equals("Done")) {
            task.setId(doneTaskList.size());
            doneTaskList.add(task);
        }

    }
}