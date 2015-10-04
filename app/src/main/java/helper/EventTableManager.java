package helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.KeyFactory;
import java.util.ArrayList;

import harsu.atmos2k15.atmos.com.atmos2k15.EventChooserFragment;
import harsu.atmos2k15.atmos.com.atmos2k15.set.EventDataSet;
import harsu.atmos2k15.atmos.com.atmos2k15.set.EventSet;

/**
 * Created by harsu on 6/24/2015.
 */
public class EventTableManager {
    public static final String KEY_ID = "ID";
    public static final String KEY_EVENT_ID = "EventID";
    public static final String KEY_TAG = "EventTag";
    public static final String KEY_TAB = "EventTab";
    public static final String KEY_NAME = "Name";
    public static final String KEY_START_TIME = "StartTime";
    public static final String KEY_END_TIME = "EndTime";
    public static final String KEY_VENUE = "Venue";
    public static final String KEY_DETAILS = "Details";
    public static final String KEY_CONTACTS = "Contacts";
    public static final String KEY_IMAGE_LINK = "ImageLink";
    public static final String KEY_IMAGE_DOWNLOAD = "ImageDownloaded";
    public static final String KEY_COST = "Cost";
    public static final String KEY_FAVOURITE = "Favourite";


    public static final String TAG = "TransactionTable";

    private static final String DATABASE_TABLE = "EVENTLIST";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "EVENTDatabase";
    private Context context;
    private DBHelper ourHelper;
    private SQLiteDatabase ourDatabase;

    public EventTableManager(Context c) {
        context = c;

    }

    public EventTableManager open() {
        ourHelper = new DBHelper(context);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        ourHelper.close();
        ourDatabase.close();
    }

    public ArrayList<String> getDistinctTags(String tab) {
        ArrayList<String> temp=new ArrayList<>();
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT DISTINCT " + KEY_TAG + " FROM " + DATABASE_TABLE +
                        " WHERE "+KEY_TAB+" = '"+tab+"' " ,
                null);
        if (cursor.moveToFirst())
            do{
                temp.add(cursor.getString(0));
            }while (cursor.moveToNext());
        cursor.close();
        close();
        return  temp;
    }

    public ArrayList<EventSet> getEvents(String tag, String tab) {
        ArrayList<EventSet> events=new ArrayList<>();
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT " +KEY_EVENT_ID+", "+ KEY_NAME + ", "+ KEY_START_TIME+ ", "+
                        KEY_VENUE+", " + KEY_IMAGE_LINK +", "+KEY_IMAGE_DOWNLOAD+", "+KEY_FAVOURITE+" FROM " + DATABASE_TABLE +
                        " WHERE "+KEY_TAG+" = '"+tag+"' AND "+KEY_TAB+" = '"+tab+"' " ,
                null);
        if (cursor.moveToFirst())
            do{
                EventSet eventSet=new EventSet(
                        cursor.getInt(0),
                        cursor.getLong(2),
                        cursor.getString(1),
                        cursor.getString(4),
                        cursor.getString(3),
                        cursor.getInt(5),
                        cursor.getInt(6) == 1
                );
                events.add(eventSet);
            }while (cursor.moveToNext());
        cursor.close();
        close();
        return events;
    }
    public ArrayList<EventSet> getFavourites() {
        ArrayList<EventSet> events=new ArrayList<>();
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT " +KEY_EVENT_ID+", "+ KEY_NAME + ", "+ KEY_START_TIME+ ", "+
                        KEY_VENUE+", " + KEY_IMAGE_LINK +", "+KEY_IMAGE_DOWNLOAD+", "+KEY_FAVOURITE+" FROM " + DATABASE_TABLE +
                        " WHERE "+KEY_FAVOURITE+" = 1",
                null);
        if (cursor.moveToFirst())
            do{

                EventSet eventSet=new EventSet(
                        cursor.getInt(0),
                        cursor.getLong(2),
                        cursor.getString(1),
                        cursor.getString(4),
                        cursor.getString(3),
                        cursor.getInt(5),
                        cursor.getInt(6)==1

                );
                events.add(eventSet);
            }while (cursor.moveToNext());
        cursor.close();
        close();
        return events;

    }




    public void imageDownloaded(int id, String path) {
        ContentValues contentValues=new ContentValues();
        contentValues.put(KEY_IMAGE_LINK, path);
        contentValues.put(KEY_IMAGE_DOWNLOAD,1);
        open();
        ourDatabase.update(DATABASE_TABLE, contentValues, KEY_EVENT_ID + " = " + id, null);
        close();
    }

    public void updateSchedule(int event_id, long start_time, String venue) {
        ContentValues cv=new ContentValues();
        cv.put(KEY_START_TIME,start_time);
        cv.put(KEY_VENUE,venue);
        open();
        ourDatabase.update(DATABASE_TABLE, cv, KEY_EVENT_ID + " = " + event_id, null);
        close();
    }

    public Boolean checkFavourite(int event_id) {
        Boolean result=false;
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT " +KEY_FAVOURITE+" FROM " + DATABASE_TABLE +" WHERE "+KEY_EVENT_ID+" = "+event_id+" " ,
                null);
        if(cursor.moveToFirst()){
            if(cursor.getInt(0)==1)
                result=true;
        }
        cursor.close();
        close();
        return result;
    }

    public boolean toggleFavourite(int event_id) {
        ContentValues cv=new ContentValues();
        boolean result;
        if(checkFavourite(event_id)){
            cv.put(KEY_FAVOURITE,0);
            result=false;
        }
        else {
            cv.put(KEY_FAVOURITE,1);
            result=true;
        }
        open();
        ourDatabase.update(DATABASE_TABLE, cv, KEY_EVENT_ID + " = " + event_id, null);
        close();
        return result;
    }

    public String getEventName(int event_id) {
        String name="";
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT " + KEY_NAME + " FROM " + DATABASE_TABLE +
                    " WHERE "+KEY_EVENT_ID+" = "+event_id+" " ,
                null);
        if (cursor.moveToFirst())
           name=cursor.getString(0);
        cursor.close();
        close();
        return name;
    }

    public EventDataSet getEventData(int event_id) {
        open();

        EventDataSet eventDataSet=null;
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                        " WHERE "+KEY_EVENT_ID+" = "+event_id+" " ,
                null);
        if (cursor.moveToFirst()){
            eventDataSet=new EventDataSet(
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getLong(5),
                    cursor.getLong(6),
                    cursor.getString(7),
                    cursor.getString(8),
                    cursor.getString(9),
                    cursor.getString(10),
                    cursor.getInt(11),
                    cursor.getDouble(12),
                    cursor.getInt(13)
            );
        }
        close();
        cursor.close();
        return eventDataSet;

    }

    public boolean dataPresent() {
        boolean result=false;
        open();
        Cursor cursor=ourDatabase.rawQuery("SELECT * FROM "+DATABASE_TABLE,null);
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
                    KEY_ID              + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_EVENT_ID        + " INTEGER NOT NULL, " +
                    KEY_TAG             + " TEXT NOT NULL, " +      //cs, mech civil eco etc
                    KEY_TAB             + " TEXT NOT NULL, " +      //technical events, sciences, workshops(no tag) , others(no tag)
                    KEY_NAME            + " TEXT NOT NULL, " +
                    KEY_START_TIME      + " TEXT,  " +
                    KEY_END_TIME        + " TEXT,  " +
                    KEY_VENUE           + " TEXT,  " +
                    KEY_DETAILS         + " TEXT NOT NULL,  " +
                    KEY_CONTACTS        + " TEXT NOT NULL,  " +
                    KEY_IMAGE_LINK      + " TEXT,  " +
                    KEY_IMAGE_DOWNLOAD  + " TEXT,  " +
                    KEY_COST            + " TEXT NOT NULL,  " +
                    KEY_FAVOURITE       + " INTEGER NOT NULL); ";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public long addEntry( int       id,
                          String    tag,
                          String    tab,
                          String    name,
                          Long      start_time,
                          Long      end_time,
                          String    venue,
                          String    details,
                          String    contacts,
                          String    image_link,
                          int       image_downloaded,
                          Double    cost,
                          int       favourite      ) {
        long success=-1;

        ContentValues cv = new ContentValues();

        cv.put(KEY_EVENT_ID        ,id);
        cv.put(KEY_TAG             ,tag);
        cv.put(KEY_TAB             ,tab);
        cv.put(KEY_NAME            ,name);
        cv.put(KEY_START_TIME      ,start_time);
        cv.put(KEY_END_TIME        ,end_time);
        cv.put(KEY_VENUE           ,venue);
        cv.put(KEY_DETAILS         ,details);
        cv.put(KEY_CONTACTS        ,contacts);
        cv.put(KEY_IMAGE_LINK      ,image_link);
        cv.put(KEY_IMAGE_DOWNLOAD  ,image_downloaded);
        cv.put(KEY_COST            ,cost);
        cv.put(KEY_FAVOURITE       ,favourite);


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
                        " WHERE " + KEY_NAME + " = '" + cv.getAsString(KEY_NAME) + "' AND "+KEY_TAG+" = '"+cv.getAsString(KEY_TAG)+"'",
                null);

        if (cursor.moveToFirst())
            id = cursor.getInt(0);
        cursor.close();
        close();
        return id;

    }

   /* public void updateDeleteEntry(double amount, Long time, String name, String number) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_AMOUNT, amount);
        cv.put(KEY_Name, name);
        cv.put(KEY_TIME, time);
        cv.put(KEY_NUMBER, number);
        open();
        Cursor cursor = ourDatabase.rawQuery("SELECT " + KEY_AMOUNT + " FROM " + DATABASE_TABLE +
                        " WHERE " + KEY_NUMBER + " = '" + number+"' ",
                null);
        if (cursor.moveToFirst()) {
            Double temp = cursor.getDouble(0) + cv.getAsDouble(KEY_AMOUNT);
            cv.put(KEY_AMOUNT, temp);
            ourDatabase.update(DATABASE_TABLE, cv, KEY_NUMBER + " = '" + number+"' ", null);
        }
        cursor.close();
        close();

    }

    private int updateAddData(ContentValues cv, int id) {
        open();
        int i = -1;
        Cursor cursor = ourDatabase.rawQuery("SELECT " + KEY_AMOUNT + " , "+KEY_TIME+" FROM " + DATABASE_TABLE +
                        " WHERE " + KEY_ID + " = " + id,
                null);
        if (cursor.moveToFirst()) {
            Double amount = cursor.getDouble(0) + cv.getAsDouble(KEY_AMOUNT);
            cv.put(KEY_AMOUNT, amount);
            if(cursor.getLong(1)>cv.getAsLong(KEY_TIME)){
                cv.put(KEY_TIME,cursor.getLong(1));
            }

            i = ourDatabase.update(DATABASE_TABLE, cv, KEY_ID + " = " + id, null);
        }
        cursor.close();
        close();

        return i;

    }



    public ArrayList<TransactionSet> getAllChat() {
        ArrayList<TransactionSet> transactionSets = new ArrayList<>();
        open();

        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                        " ORDER BY CAST(" + KEY_TIME + " AS INTEGER)DESC ",
                null);
        if (cursor.moveToFirst()) {
            do {
                TransactionSet temp = new TransactionSet(getAmount(cursor.getString(1)),
                        cursor.getString(3),
                        cursor.getString(2),
                        toDate(cursor.getString(4)),
                        cursor.getInt(0));
                transactionSets.add(temp);
            } while (cursor.moveToNext());
        }

        cursor.close();
        close();
        return transactionSets;

    }

    private String getAmount(String amount) {
        double temp = Double.parseDouble(amount);
        if (temp < 0) {
            return "Credit " + amount;
        } else if (temp > 0) {
            return "Debit " + amount;
        } else
            return " ";
    }

    private String toDate(String time) {
        Long timeStamp = Long.parseLong(time);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeStamp);
        String date = calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                calendar.get(Calendar.MONTH) + "/" +
                String.valueOf(calendar.get(Calendar.YEAR)).substring(2);
        return date;

    }

    public void deleteEntry(String chat_number) {
        open();
        ourDatabase.delete(DATABASE_TABLE,KEY_NUMBER+" = '"+chat_number+"' ",null);
        close();
    }*/



    /*public Map<String, String> getChat(long insertID) {
        Map<String, String> chatData = new HashMap<>();
        open();
        chatData.put("success", "false");
        Cursor cursor = ourDatabase.rawQuery("SELECT * FROM " + DATABASE_TABLE +
                        " WHERE " + KEY_ID + " = " + insertID,
                null);
        if (cursor.moveToFirst()) {
            chatData.put("success", "true");
            chatData.put("amount", cursor.getString(1));
            if (cursor.getString(2) != null) {
                chatData.put("description", cursor.getString(2));
            } else
                chatData.put("description", "");
            chatData.put("time", cursor.getString(3));
            chatData.put("id", String.valueOf(insertID));

        }
        cursor.close();
        close();
        return chatData;


    }

    public void chatSentNotify(long id) {
        open();
        ContentValues cv = new ContentValues();
        cv.put(KEY_TAG, "S");
        ourDatabase.update(DATABASE_TABLE, cv, KEY_ID + "=" + id, null);
        close();
    }

    public void deleteChat(int id) {
        open();
        ourDatabase.delete(DATABASE_TABLE, KEY_ID + " = " + id, null);
        close();
    }*/


}
