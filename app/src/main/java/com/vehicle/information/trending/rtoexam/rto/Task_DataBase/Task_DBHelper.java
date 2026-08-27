package com.vehicle.information.trending.rtoexam.rto.Task_DataBase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Task_DBHelper extends SQLiteOpenHelper {
    private static String DB_NAME = "rto_database.db";
    private static String DB_PATH = "/data/data/com.vehicle.information.trending.rtoexam.rto/";
    private final Context myContext;

    private SQLiteDatabase myDataBase;

    @Override
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public Task_DBHelper(Context context) {
        super(context, DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.myContext = context;
    }

    public void createDataBase() throws IOException {
        if (checkDataBase()) {
            return;
        }
        getReadableDatabase().close();
        try {
            copyDataBase();
        } catch (Exception unused) {
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, 0);
        } catch (SQLiteException unused) {
        }
        return sQLiteDatabase != null;
    }

    private void copyDataBase() throws IOException {
        InputStream open = this.myContext.getAssets().open(DB_NAME);
        FileOutputStream fileOutputStream = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = open.read(bArr);
            if (read > 0) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileOutputStream.flush();
                fileOutputStream.close();
                open.close();
                return;
            }
        }
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        SQLiteDatabase openDatabase = SQLiteDatabase.openDatabase(DB_PATH + DB_NAME, null, 0);
        this.myDataBase = openDatabase;
        return openDatabase;
    }

    @Override
    public synchronized void close() {
        SQLiteDatabase sQLiteDatabase = this.myDataBase;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
        super.close();
    }
}
