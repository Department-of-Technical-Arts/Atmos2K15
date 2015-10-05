package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import harsu.atmos2k15.atmos.com.atmos2015.set.FeedSet;

/**
 * Created by harsu on 6/24/2015.
 */
public class FeedTableManager {
    public static final String KEY_ID = "ID";
    public static final String KEY_EVENT_ID = "EventID";
    public static final String KEY_EVENT_NAME = "Name";
    public static final String KEY_RECEIVE_TIME = "ReceiveTime";
    public static final String KEY_MESSAGE = "Message";

    public static final String TAG = "FeedTable";

    private static final String DATABASE_TABLE = "FeedTable";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FeedDatabase";
    private Context context;
    private DBHelper ourHelper;
    private SQLiteDatabase ourDatabase;

    public FeedTableManager(Context c) {
        context = c;

    }

    public FeedTableManager open() {
        ourHelper = new DBHelper(context);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
        ourDatabase.close();
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String query = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" +
                    KEY_ID              + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_EVENT_ID        + " INTEGER NOT NULL, " +
                    KEY_EVENT_NAME      + " TEXT NOT NULL, " +
                    KEY_RECEIVE_TIME    + " TEXT NOT NULL, " +
                    KEY_MESSAGE         + " TEXT NOT NULL);" ;
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public long addEntry( int       event_id,
                          String    name,
                          Long      posted_time,
                          String    message){
        long success=-1;

        ContentValues cv = new ContentValues();

        cv.put(KEY_EVENT_NAME       ,name);
        cv.put(KEY_EVENT_ID        ,event_id);
        cv.put(KEY_RECEIVE_TIME    ,posted_time);
        cv.put(KEY_MESSAGE         ,message);


        int presence = checkData(cv);

        if (presence == -1) {
            open();
            success = ourDatabase.insert(DATABASE_TABLE, null, cv);
            close();
        }
        return success;
    }

    public int checkData(ContentValues cv) {
        open();
        int id = -1;
        Cursor cursor = ourDatabase.rawQuery("SELECT " + KEY_ID + " FROM " + DATABASE_TABLE +
                        " WHERE " + KEY_RECEIVE_TIME + " = " + cv.getAsLong(KEY_RECEIVE_TIME)+" AND "+
                KEY_MESSAGE+"='"+cv.getAsString(KEY_MESSAGE)+"' ",
                null);
        if (cursor.moveToFirst())
            id = cursor.getInt(0);
        cursor.close();
        close();
        return id;
    }
    public ArrayList<FeedSet> getFeeds() {
        ArrayList<FeedSet> feeds=new ArrayList<>();
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE ,
                null);
        if (cursor.moveToFirst())
            do{

                FeedSet feed=new FeedSet(
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(4),
                        cursor.getLong(3)
                );
                feeds.add(feed);
            }while (cursor.moveToNext());
        cursor.close();
        close();
        return feeds;
    }

}
