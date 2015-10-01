package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;

import harsu.atmos2k15.atmos.com.atmos2k15.set.ScheduleSet;

/**
 * Created by harsu on 6/24/2015.
 */
public class ScheduleTableManager {
    public static final String KEY_ID = "ID";
    public static final String KEY_EVENT_ID = "EventID";
    public static final String KEY_EVENT_TAG = "tag";
    public static final String KEY_EVENT_NAME = "Name";
    public static final String KEY_START_TIME = "StartTime";
    public static final String KEY_VENUE = "Venue";

    public static final String TAG = "ScheduleTable";

    private static final String DATABASE_TABLE = "ScheduleTable";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ScheduleDatabase";
    private Context context;
    private DBHelper ourHelper;
    private SQLiteDatabase ourDatabase;

    public ScheduleTableManager(Context c) {
        context = c;

    }

    public ScheduleTableManager open() {
        ourHelper = new DBHelper(context);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
        ourDatabase.close();
    }

    public long addEntry(int event_id,
                         int event_tag,
                         String name,
                         Long start_time,
                         String venue) {
        long success = -1;

        ContentValues cv = new ContentValues();

        cv.put(KEY_EVENT_NAME, name);
        cv.put(KEY_EVENT_ID, event_id);
        cv.put(KEY_EVENT_TAG, event_tag);
        cv.put(KEY_START_TIME, start_time);
        cv.put(KEY_VENUE, venue);


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
                        " WHERE " + KEY_EVENT_ID + " = " + cv.getAsLong(KEY_EVENT_ID) + " AND " +
                        KEY_EVENT_TAG + "='" + cv.getAsString(KEY_EVENT_TAG) + "' ",
                null);
        if (cursor.moveToFirst()) {
            id=ourDatabase.update(DATABASE_TABLE, cv, KEY_ID + "=" + cursor.getInt(0), null);
        }
        cursor.close();
        close();
        return id;
    }

    public ArrayList<ScheduleSet> getSchedule(int day) {
        //todo return events on that day
        Calendar start=Calendar.getInstance(),end=Calendar.getInstance();
        start.set(2015,Calendar.OCTOBER,9+day,0,0);
        end.set(2015,Calendar.OCTOBER,10+day,0,0);

        ArrayList<ScheduleSet> scheduleSets = new ArrayList<>();
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                        " WHERE CAST("+KEY_START_TIME+" AS INTEGER) >= "+start.getTimeInMillis()+
                        " AND CAST("+KEY_START_TIME+" AS INTEGER) < "+end.getTimeInMillis()+
                        " ORDER BY CAST("+KEY_START_TIME+" AS INTEGER) ",
                null);
        if (cursor.moveToFirst())
            do {
                ScheduleSet scheduleSet = new ScheduleSet(
                        cursor.getInt(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getLong(4),
                        cursor.getString(5)
                );
                scheduleSets.add(scheduleSet);
            } while (cursor.moveToNext());
        cursor.close();
        close();
        return scheduleSets;
    }

    public boolean dataPresent() {
        open();
        boolean result=false;
        Cursor cursor=ourDatabase.rawQuery(" SELECT * FROM "+DATABASE_TABLE,null);
        if(cursor.moveToFirst()){
            result=true;
        }
        close();
        cursor.close();
        return result;

    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String query = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + " (" +
                    KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_EVENT_ID + " INTEGER NOT NULL, " +
                    KEY_EVENT_TAG + " INTEGER NOT NULL, " +
                    KEY_EVENT_NAME + " TEXT NOT NULL, " +
                    KEY_START_TIME + " TEXT NOT NULL, " +
                    KEY_VENUE + " TEXT NOT NULL);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

}
