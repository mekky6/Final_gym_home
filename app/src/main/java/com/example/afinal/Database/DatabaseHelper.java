package com.example.afinal.Database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import androidx.annotation.Nullable;
public class DatabaseHelper extends SQLiteOpenHelper {
    //create table
    public static final String DATABASE_NAME="smartgym.db";
    public static final int DA_VERSION=1;
    public static final String TABLE_NAME="user";
    public static final String COL_1="ID";
    public static final String COL_2="user_name";
    public static final String COL_3="email";
    public static final String COL_4="password";
    public static final String COL_5="gender";
    public static final String COL_6="weight";
    public static final String COL_7="height";
    public static final String COL_8="birthday";
    public static final String COL_9="fitness_level";
    public static final String COL_10="activity_level";
    public DatabaseHelper(@Nullable Context context ) {
        super( context, DATABASE_NAME, null, DA_VERSION );
        SQLiteDatabase db=this.getReadableDatabase();
    }//end const
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE user(ID INTEGER PRIMARY KEY AUTOINCREMENT, "+" user_name TEXT,"+" email TEXT,"+" password TEXT,"+" config_password TEXT, "+" weight FLOAT, "+" height FLOAT)");
    }//end onCreate
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }//end onUpgrade()
    //function to insert data in database
    public long adduser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put( "user_name",username );
        contentValues.put( "email",email );
        contentValues.put( "password",password );
        long res1 =db.insert( "user",null,contentValues);
        db.close();
        return res1;
    }//end adduser()
    public long addgender(String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put( "gender",gender );
        long res2=db.insert( "user",null,contentValues);
        db.close();
        return res2;
    }//end addgender()
    public long addweight(String weight){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put( "weight",weight );
        long res3=db.insert( "user",null,contentValues);
        db.close();
        return res3;
    }//end addWeight()
    public long addHeight(String height){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put( "height",height );
        long res4=db.insert( "user",null,contentValues);
        db.close();
        return res4;
    }//end addHeight()
    public long addBirthday(String birthday){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put( "birthday",birthday );
        long res5=db.insert( "user",null,contentValues);
        db.close();
        return res5;
    }//end addBirthday()
    public long addFitnessLevel(String fitness_level){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put( "fitness_level",fitness_level );
        long res6=db.insert( "user",null,contentValues);
        db.close();
        return res6;
    }//end addFitnessLevel()
    public long addActivityLevel(String activity_level){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put( "activity_level",activity_level );
        long res7=db.insert( "user",null,contentValues);
        db.close();
        return res7;
    }//end addActivityLevel()
    //function to check data in database
    public Boolean CheckUser(String user_name,String password){
        String [] columns={COL_1};
        SQLiteDatabase db =getReadableDatabase();
        String  selection = COL_2 + " =?" + " and " + COL_4 + " =?" ;
        Cursor cursor=db.query(TABLE_NAME,columns,selection,new String[]{user_name,password},
                null,null,null);
        int count =cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return true;
        else
            return false;
    }//end CheckUser()
}//end class DatabaseHelper
