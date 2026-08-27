package com.vehicle.information.trending.rtoexam.rto.Task_DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vehicle.information.trending.rtoexam.rto.Task_Extra.Task_QueConstructor;

import java.util.ArrayList;


public class Task_DBHandler extends SQLiteOpenHelper {
    Context context;
    SQLiteDatabase db;
    ArrayList<Task_QueConstructor> quesList;

    public Task_DBHandler(Context context) {
        super(context, "RTODATABASE", (SQLiteDatabase.CursorFactory) null, 3);
        this.quesList = new ArrayList<>();
        this.db = getWritableDatabase();
        Log.d("TT", "DB CREATED ");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE  TABLE RTODATABASETABLE (ID INTEGER PRIMARY KEY,QUESTION TEXT,ANSWER TEXT)");
        sQLiteDatabase.execSQL("CREATE TABLE QuesImg(id INTEGER PRIMARY KEY , question TEXT, answer TEXT  , option1 TEXT,option2 TEXT,option3 TEXT,photo TEXT )");
        Log.d("TT", "TABLE CREATED");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS RTODATABASETABLE");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS QuesImg");
        Log.d("TT", "TABLE DROPPED");
        onCreate(sQLiteDatabase);
    }

    public int rowCount() {
        return this.db.rawQuery("Select * From QuesImg", null).getCount();
    }

    public void addQuestion(int i, String question, String answer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", Integer.valueOf(i));
        contentValues.put("QUESTION", question);
        contentValues.put("ANSWER", answer);
        if (this.db.insert("RTODATABASETABLE", null, contentValues) < 0) {
            Log.d("TT", "INSERT ISSUE");
        } else {
            Log.d("TT", "INSERT SUCCESS");
        }
    }

    public ArrayList getAllQuestions() {
        ArrayList arrayList = new ArrayList();
        Cursor query = this.db.query("RTODATABASETABLE", null, null, null, null, null, null);
        query.moveToFirst();
        if (query.getCount() <= 0) {
            return arrayList;
        }
        do {
            int i = query.getInt(0);
            String string = query.getString(1);
            String string2 = query.getString(2);
            Task_QueConstructor m_rtoTaskQueConstructor = new Task_QueConstructor();
            m_rtoTaskQueConstructor.setId(i);
            m_rtoTaskQueConstructor.setQuestion(string);
            m_rtoTaskQueConstructor.setAnswer(string2);
            arrayList.add(m_rtoTaskQueConstructor);
        } while (query.moveToNext());
        return arrayList;
    }

    public void addQuestion(Task_QueConstructor m_rtoTaskQueConstructor) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", Integer.valueOf(m_rtoTaskQueConstructor.getId()));
        contentValues.put("question", m_rtoTaskQueConstructor.getQuestion());
        contentValues.put("answer", m_rtoTaskQueConstructor.getAnswer());
        contentValues.put("option1", m_rtoTaskQueConstructor.getOption1());
        contentValues.put("option2", m_rtoTaskQueConstructor.getOption2());
        contentValues.put("option3", m_rtoTaskQueConstructor.getOption3());
        contentValues.put("photo", m_rtoTaskQueConstructor.getPhoto());
        this.db.insert("QuesImg", null, contentValues);
    }

    public ArrayList<Task_QueConstructor> getAllQuestions2() {
        Cursor query = this.db.query("QuesImg", null, null, null, null, null, null);
        query.moveToFirst();
        if (query.getCount() <= 0) {
            return this.quesList;
        }
        do {
            this.quesList.add(new Task_QueConstructor(query.getInt(0), query.getString(1), query.getString(2), query.getString(3), query.getString(4), query.getString(5), query.getString(6)));
        } while (query.moveToNext());
        return this.quesList;
    }

    public void deleteTable() {
        SQLiteDatabase sQLiteDatabase = this.db;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            this.db = getWritableDatabase();
        }
        this.db.execSQL("DROP TABLE IF EXISTS CARS");
    }

    public Task_DBHandler open() {
        this.db = getWritableDatabase();
        return this;
    }
}
