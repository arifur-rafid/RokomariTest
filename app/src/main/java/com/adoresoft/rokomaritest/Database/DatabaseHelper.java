package com.adoresoft.rokomaritest.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adoresoft.rokomaritest.TaskDetails;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "task.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS task_table (ID INTEGER PRIMARY KEY AUTOINCREMENT,name VARCHAR(100),description VARCHAR(500),createdDate VARCHAR(100),deadline VARCHAR(100),status VARCHAR(50),email VARCHAR(100),phone VARCHAR(20), url VARCHAR(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS task_table");
    }

    public boolean insertData(TaskDetails taskDetails) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", taskDetails.getName());
        contentValues.put("description", taskDetails.getDescription());
        contentValues.put("createdDate", taskDetails.getCreatedDate());
        contentValues.put("deadline", taskDetails.getDeadline());
        contentValues.put("status", taskDetails.getStatus());
        contentValues.put("email", taskDetails.getEmail());
        contentValues.put("phone", taskDetails.getPhone());
        contentValues.put("url", taskDetails.getUrl());
        long result = database.insert("task_table", null, contentValues);
        if (result == -1) {
            return false;
        } else return true;
    }

    public ArrayList<TaskDetails> getTask(String status) {
        SQLiteDatabase database = this.getWritableDatabase();
        ArrayList<TaskDetails> taskData = new ArrayList<>();
        String[] columns = {
                "ID",
                "name",
                "description",
                "createdDate",
                "deadline",
                "status",
                "email",
                "phone",
                "url"
        };
        try {
            Cursor c = database.rawQuery("select * from task_table where status='" + status + "'", null);
            TaskDetails l;

            if (c != null) {
                while (c.moveToNext()) {
                    l = new TaskDetails(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7), c.getString(8));
                    taskData.add(l);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        database.close();
        return taskData;
    }

    public boolean deleteSingleTask(int ID) {
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            database.execSQL("DELETE FROM task_table WHERE ID = '" + ID + "'");
            database.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteSingleTask(String taskName) {
        try {
            SQLiteDatabase database = this.getWritableDatabase();
            database.execSQL("DELETE FROM task_table WHERE name = '" + taskName + "'");
            database.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateData(TaskDetails taskDetails) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", taskDetails.getName());
        contentValues.put("description", taskDetails.getDescription());
        contentValues.put("createdDate", taskDetails.getCreatedDate());
        contentValues.put("deadline", taskDetails.getDeadline());
        contentValues.put("status", taskDetails.getStatus());
        contentValues.put("email", taskDetails.getEmail());
        contentValues.put("phone", taskDetails.getPhone());
        contentValues.put("url", taskDetails.getUrl());
        long result = database.update("task_table", contentValues, "ID=" + taskDetails.getId(), null);
        if (result == -1) {
            return false;
        } else return true;
    }
}
