package com.example.administrator.coolweather2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/9/1.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_PROV="create table prov(" +
            "id1 integer primary key autoincrement," +
            "name text," +
            "provId text)";

    public static final String CREATE_CITY="create table city(" +
            "id1 integer primary key autoincrement," +
            "name text," +
            "Id text," +
            "prov text)";
    /**changed prov*/

    public static final String CREATE_COUNTRY="create table country(" +
            "id1 integer primary key autoincrement," +
            "name text," +
            "countryId text," +
            "provName text," +
            "sonId text,"+
            "cityId text)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
        mContext=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_PROV);
        db.execSQL(CREATE_CITY);
        db.execSQL(CREATE_COUNTRY);
        Toast.makeText(mContext,"all databases were been onCreate", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){}


}
