package com.example.studentnotesapp;

import java.util.ArrayList;
import java.util.Date;

public class Student
{
    public static ArrayList<Student> studentArrayList = new ArrayList<>();
    public static String NOTE_EDIT_EXTRA =  "studentEdit";

    private int id;
    private String coursename;
    private String studentname;
    private String priority;

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    private Date deleted;

    public Student(int id, String coursename, String studentname,String priority,Date deleted)
    {
        this.id = id;
        this.coursename = coursename;
        this.studentname = studentname;
        this.priority = priority;
        this.deleted = deleted;
    }

    public Student(int id, String coursename, String studentname, String priority)
    {
        this.id = id;
        this.coursename = coursename;
        this.studentname = studentname;
        this.priority = priority;
        deleted = null;
    }

    public static Student getStudentForID(int passedStudentID)
    {
        for (Student student : studentArrayList)
        {
            if(student.getId() == passedStudentID)
                return student;
        }

        return null;
    }

    public static ArrayList<Student> nonDeletedStudent()
    {
        ArrayList<Student> nonDeleted = new ArrayList<>();
        for(Student student : studentArrayList)
        {
            if(student.getDeleted() == null)
                nonDeleted.add(student);
        }

        return nonDeleted;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getCoursename()
    {
        return coursename;
    }

    public void setCoursename(String coursename)
    {
        this.coursename = coursename;
    }

    public String getStudentname()
    {
        return studentname;
    }

    public void setStudentname(String studentname)
    {
        this.studentname = studentname;
    }

    public Date getDeleted()
    {
        return deleted;
    }

    public void setDeleted(Date deleted)
    {
        this.deleted = deleted;
    }
}