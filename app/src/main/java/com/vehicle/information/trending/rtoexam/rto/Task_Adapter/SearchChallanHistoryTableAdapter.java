package com.vehicle.information.trending.rtoexam.rto.Task_Adapter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.vehicle.information.trending.rtoexam.rto.Task_Extra.TradetuDatabaseHelper;
import com.vehicle.information.trending.rtoexam.rto.Task_Model.SearchChallanHistory;

import java.util.ArrayList;


public class SearchChallanHistoryTableAdapter {
    private static final String QUERY_SELECT_HISTORY = "SELECT * FROM SearchChallanHistory ORDER BY search_order DESC";
    private static final String QUERY_SELECT_HISTORY_BY_ID = "SELECT * FROM SearchChallanHistory WHERE _id = ?";
    private static final String QUERY_SELECT_HISTORY_By_NAME = "SELECT * FROM SearchChallanHistory WHERE reg_no = ?";
    private static final String TABLE_SEARCH_CHALLAN_HISTORY = "SearchChallanHistory";
    private Context context;
    private SQLiteDatabase database;
    private TradetuDatabaseHelper dbHelper;

    public SearchChallanHistoryTableAdapter(Context context2) {
        this.context = context2;
        open();
        close();
    }

    private void close() {
        this.dbHelper.close();
    }

    private void open() {
        try {
            TradetuDatabaseHelper tradetuDatabaseHelper = TradetuDatabaseHelper.getInstance(this.context);
            this.dbHelper = tradetuDatabaseHelper;
            this.database = tradetuDatabaseHelper.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SearchChallanHistory getSearchChallanHistoryById(String str, boolean z) {
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            open();
        }
        SQLiteDatabase sQLiteDatabase2 = this.database;
        SearchChallanHistory searchChallanHistory = null;
        if (sQLiteDatabase2 != null) {
            Cursor rawQuery = sQLiteDatabase2.rawQuery(QUERY_SELECT_HISTORY_BY_ID, new String[]{str});
            if (rawQuery != null && rawQuery.getCount() > 0) {
                rawQuery.moveToFirst();
                searchChallanHistory = convertCursorToObject(rawQuery);
            }
            if (z) {
                sQLiteDatabase2.close();
            }
        }
        return searchChallanHistory;
    }

    public SearchChallanHistory getSearchChallanHistoryByDetails(String str, boolean z) {
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            open();
        }
        SearchChallanHistory searchChallanHistory = null;
        if (str == null) {
            return null;
        }
        SQLiteDatabase sQLiteDatabase2 = this.database;
        if (sQLiteDatabase2 != null) {
            Cursor rawQuery = sQLiteDatabase2.rawQuery(QUERY_SELECT_HISTORY_By_NAME, new String[]{str});
            if (rawQuery != null && rawQuery.getCount() > 0) {
                rawQuery.moveToFirst();
                searchChallanHistory = convertCursorToObject(rawQuery);
                rawQuery.close();
            }
            if (z) {
                sQLiteDatabase2.close();
            }
        }
        return searchChallanHistory;
    }

    public ArrayList<SearchChallanHistory> getSearchChallanHistoryList(boolean z, int i) {
        Cursor rawQuery;
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            open();
        }
        SQLiteDatabase sQLiteDatabase2 = this.database;
        ArrayList<SearchChallanHistory> arrayList = new ArrayList<>();
        if (sQLiteDatabase2 != null) {
            if (i > 0) {
                rawQuery = sQLiteDatabase2.rawQuery("SELECT * FROM SearchChallanHistory ORDER BY search_order DESC LIMIT " + i, null);
            } else {
                rawQuery = sQLiteDatabase2.rawQuery(QUERY_SELECT_HISTORY, null);
            }
            if (rawQuery != null && rawQuery.getCount() > 0) {
                if (rawQuery.moveToFirst()) {
                    do {
                        SearchChallanHistory convertCursorToObject = convertCursorToObject(rawQuery);
                        if (convertCursorToObject != null) {
                            arrayList.add(convertCursorToObject);
                        }
                    } while (rawQuery.moveToNext());
                    rawQuery.close();
                } else {
                    rawQuery.close();
                }
            }
            if (z) {
                sQLiteDatabase2.close();
            }
        }
        return arrayList;
    }

    private int getLastSearchOrder(boolean z) {
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            open();
        }
        int i = 0;
        SQLiteDatabase sQLiteDatabase2 = this.database;
        if (sQLiteDatabase2 != null) {
            Cursor rawQuery = sQLiteDatabase2.rawQuery(QUERY_SELECT_HISTORY, null);
            if (rawQuery != null && rawQuery.getCount() > 0) {
                rawQuery.moveToFirst();
                i = rawQuery.getInt(rawQuery.getColumnIndex("search_order"));
                rawQuery.close();
            }
            if (z) {
                this.database.close();
            }
        }
        return i;
    }

    public void insertSearchChallanHistory(SearchChallanHistory searchChallanHistory, boolean z) {
        SQLiteDatabase sQLiteDatabase;
        if (searchChallanHistory != null) {
            SQLiteDatabase sQLiteDatabase2 = this.database;
            if (sQLiteDatabase2 == null || !sQLiteDatabase2.isOpen()) {
                open();
            }
            SearchChallanHistory searchChallanHistoryByDetails = getSearchChallanHistoryByDetails(searchChallanHistory.getRegistrationNo(), false);
            int lastSearchOrder = getLastSearchOrder(false);
            if (searchChallanHistoryByDetails == null) {
                searchChallanHistory.setSearchOrder(lastSearchOrder + 1);
                ContentValues createContentValues = createContentValues(searchChallanHistory);
                if (createContentValues != null) {
                    this.database.insert(TABLE_SEARCH_CHALLAN_HISTORY, null, createContentValues);
                }
            } else {
                searchChallanHistoryByDetails.setSearchOrder(lastSearchOrder + 1);
                if (searchChallanHistory.getSearchType() != null) {
                    searchChallanHistoryByDetails.setSearchType(searchChallanHistory.getSearchType());
                }
                ContentValues createContentValues2 = createContentValues(searchChallanHistoryByDetails);
                if (createContentValues2 != null) {
                    this.database.update(TABLE_SEARCH_CHALLAN_HISTORY, createContentValues2, "_id = ?", new String[]{String.valueOf(searchChallanHistoryByDetails.getId())});
                }
            }
            if (z && (sQLiteDatabase = this.database) != null && sQLiteDatabase.isOpen()) {
                this.database.close();
            }
        }
    }

    public void deleteHistoryById(String str, boolean z) {
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            open();
        }
        SQLiteDatabase sQLiteDatabase2 = this.database;
        if (sQLiteDatabase2 != null) {
            sQLiteDatabase2.delete(TABLE_SEARCH_CHALLAN_HISTORY, "_id=?", new String[]{str});
            if (z) {
                this.database.close();
            }
        }
    }

    public void deleteAllHistory(boolean z) {
        SQLiteDatabase sQLiteDatabase = this.database;
        if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
            open();
        }
        SQLiteDatabase sQLiteDatabase2 = this.database;
        if (sQLiteDatabase2 != null) {
            sQLiteDatabase2.delete(TABLE_SEARCH_CHALLAN_HISTORY, null, null);
            if (z) {
                this.database.close();
            }
        }
    }

    @SuppressLint("Range")
    private SearchChallanHistory convertCursorToObject(Cursor cursor) {
        SearchChallanHistory searchChallanHistory = new SearchChallanHistory();
        searchChallanHistory.setId(cursor.getInt(cursor.getColumnIndex("_id")));
        searchChallanHistory.setRegistrationNo(cursor.getString(cursor.getColumnIndex("reg_no")));
        searchChallanHistory.setSearchType(cursor.getString(cursor.getColumnIndex("searchType")));
        searchChallanHistory.setSearchOrder(cursor.getInt(cursor.getColumnIndex("search_order")));
        return searchChallanHistory;
    }

    private ContentValues createContentValues(SearchChallanHistory searchChallanHistory) {
        if (searchChallanHistory == null || searchChallanHistory.getRegistrationNo() == null) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("reg_no", searchChallanHistory.getRegistrationNo());
        contentValues.put("searchType", searchChallanHistory.getSearchType());
        contentValues.put("search_order", Integer.valueOf(searchChallanHistory.getSearchOrder()));
        return contentValues;
    }
}
