package com.example.studentnotesapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SQLLiteManager extends SQLiteOpenHelper
{
    private static SQLLiteManager sqLiteManager;

    private static final String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Student";
    private static final String COUNTER = "Counter";

    private static final String ID_FIELD = "id";
    private static final String COURSENAME_FIELD = "coursename";
    private static final String STUDENTNAME_FIELD = "studentname";
    private static final String PRIORITY_FIELD = "priority";

    private static final String DELETED_FIELD = "deleted";

    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLLiteManager(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLLiteManager instanceOfDatabase(Context context)
    {
        if(sqLiteManager == null)
            sqLiteManager = new SQLLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(COURSENAME_FIELD)
                .append(" TEXT, ")
                .append(STUDENTNAME_FIELD)
                .append(" TEXT, ")
                .append(PRIORITY_FIELD)
                .append(" TEXT, ")
                .append(DELETED_FIELD)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());
    }


    public void addNoteToDatabase(Student student)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, student.getId());
        contentValues.put(COURSENAME_FIELD, student.getCoursename());
        contentValues.put(STUDENTNAME_FIELD, student.getStudentname());
        contentValues.put(PRIORITY_FIELD, student.getPriority());
        contentValues.put(DELETED_FIELD, getStringFromDate(student.getDeleted()));

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    public void populateNoteListArray()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null))
        {
            if(result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(1);
                    String coursename = result.getString(2);
                    String studentname = result.getString(3);
                    String priority = result.getString(4);
                    String stringDeleted = result.getString(5);
                    Date deleted = getDateFromString(stringDeleted);
                    Student student = new Student(id,coursename,studentname,priority,deleted);
                    Student.studentArrayList.add(student);
                }
            }
        }
    }

    public void updateNoteInDB(Student student)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, student.getId());
        contentValues.put(COURSENAME_FIELD, student.getCoursename());
        contentValues.put(STUDENTNAME_FIELD, student.getStudentname());
        contentValues.put(PRIORITY_FIELD, student.getPriority());
        contentValues.put(DELETED_FIELD, getStringFromDate(student.getDeleted()));

        sqLiteDatabase.update(TABLE_NAME, contentValues, ID_FIELD + " =? ", new String[]{String.valueOf(student.getId())});
    }

    private String getStringFromDate(Date date)
    {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string)
    {
        try
        {
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e)
        {
            return null;
        }
    }
}
