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

    public static String fixMojibake(String input) {
        if (input == null || (!input.contains("\u00e0") && !input.contains("à"))) {
            return input;
        }
        try {
            byte[] bytes = new byte[input.length() * 2];
            int ptr = 0;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c <= 255) {
                    bytes[ptr++] = (byte) c;
                } else {
                    byte b = getWindows1252Byte(c);
                    if (b != 0) {
                        bytes[ptr++] = b;
                    } else {
                        byte[] utf = String.valueOf(c).getBytes(java.nio.charset.StandardCharsets.UTF_8);
                        for (byte ub : utf) bytes[ptr++] = ub;
                    }
                }
            }
            byte[] actualBytes = java.util.Arrays.copyOf(bytes, ptr);
            return new String(actualBytes, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            return input;
        }
    }

    private static byte getWindows1252Byte(char c) {
        switch (c) {
            case '\u20AC': return (byte) 0x80;
            case '\u201A': return (byte) 0x82;
            case '\u0192': return (byte) 0x83;
            case '\u201E': return (byte) 0x84;
            case '\u2026': return (byte) 0x85;
            case '\u2020': return (byte) 0x86;
            case '\u2021': return (byte) 0x87;
            case '\u02C6': return (byte) 0x88;
            case '\u2030': return (byte) 0x89;
            case '\u0160': return (byte) 0x8A;
            case '\u0161': return (byte) 0x9A;
            case '\u2039': return (byte) 0x8B;
            case '\u203A': return (byte) 0x9B;
            case '\u0152': return (byte) 0x8C;
            case '\u0153': return (byte) 0x9C;
            case '\u017D': return (byte) 0x8E;
            case '\u017E': return (byte) 0x9E;
            case '\u2018': return (byte) 0x91;
            case '\u2019': return (byte) 0x92;
            case '\u201C': return (byte) 0x93;
            case '\u201D': return (byte) 0x94;
            case '\u2022': return (byte) 0x95;
            case '\u2013': return (byte) 0x96;
            case '\u2014': return (byte) 0x97;
            case '\u02DC': return (byte) 0x98;
            case '\u2122': return (byte) 0x99;
            case '\u0178': return (byte) 0x9F;
            default: return 0;
        }
    }

    public void addQuestion(int i, String question, String answer) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", Integer.valueOf(i));
        contentValues.put("QUESTION", fixMojibake(question));
        contentValues.put("ANSWER", fixMojibake(answer));
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
            String string = fixMojibake(query.getString(1));
            String string2 = fixMojibake(query.getString(2));
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
        contentValues.put("question", fixMojibake(m_rtoTaskQueConstructor.getQuestion()));
        contentValues.put("answer", fixMojibake(m_rtoTaskQueConstructor.getAnswer()));
        contentValues.put("option1", fixMojibake(m_rtoTaskQueConstructor.getOption1()));
        contentValues.put("option2", fixMojibake(m_rtoTaskQueConstructor.getOption2()));
        contentValues.put("option3", fixMojibake(m_rtoTaskQueConstructor.getOption3()));
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
            this.quesList.add(new Task_QueConstructor(query.getInt(0), fixMojibake(query.getString(1)), fixMojibake(query.getString(2)), fixMojibake(query.getString(3)), fixMojibake(query.getString(4)), fixMojibake(query.getString(5)), query.getString(6)));
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
