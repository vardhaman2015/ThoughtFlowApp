package pcube.servey.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pcube.servey.model.AnswerQuestion;
import pcube.servey.postquestion.PostQuestion;

public class SqliteHelper extends SQLiteOpenHelper {

    //DATABASE NAME
    public static final String DATABASE_NAME = "loopwiki.com";

    //DATABASE VERSION
    public static final int DATABASE_VERSION = 1;

    //TABLE NAME
    public static final String TABLE_USERS = "users";
    public static final String TABLE_QUESTION = "question";
    public static final String TABLE_ANSWER = "for_answer";

    //TABLE USERS COLUMNS
    //ID COLUMN @primaryKey
    public static final String KEY_ID = "id";

    //COLUMN user name
    public static final String KEY_USER_NAME = "username";

    //COLUMN email
    public static final String KEY_EMAIL = "email";

    //COLUMN password
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_OPTIONA = "optiona";
    public static final String KEY_OPTIONB = "optionb";
    public static final String KEY_OPTIONC = "optionc";
    public static final String KEY_OPTIOND = "optiond";
    public static final String KEY_ANSWER = "answer";
    public static final String KEY_TYPE = "type";
    public static final String KEY_QID = "qid";
    public static final String KEY_AAID = "aaid";
    public static final String KEY_AANSWER= "aaansewr";

    //SQL for creating users table
    public static final String SQL_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + " ( "
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_USER_NAME + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_PASSWORD + " TEXT"
            + " ) ";

    public static final String SQL_TABLE_QUESTION = " CREATE TABLE " + TABLE_QUESTION
            + " ( "
            + KEY_QID + " TEXT, "
            + KEY_QUESTION + " TEXT, "
            + KEY_OPTIONA + " TEXT, "
            + KEY_OPTIONB + " TEXT, "
            + KEY_OPTIONC + " TEXT, "
            + KEY_OPTIOND + " TEXT, "
            + KEY_ANSWER + " TEXT,"
            + KEY_TYPE + " TEXT"
            + " ) ";
    public static final String SQL_TABLE_ANSWER = " CREATE TABLE " + TABLE_ANSWER
            + " ( "
            + KEY_AAID+ " TEXT, "
            + KEY_AANSWER + " TEXT"
            + " ) ";
    public SqliteHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        //Create Table when oncreate gets called
        sqLiteDatabase.execSQL(SQL_TABLE_USERS);
        sqLiteDatabase.execSQL(SQL_TABLE_QUESTION);
        sqLiteDatabase.execSQL(SQL_TABLE_ANSWER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop table to create new one if database version updated
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_USERS);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_QUESTION);
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_ANSWER);
    }

    //using this method we can add users to user table
    public void addUser(User user) {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_USER_NAME, user.userName);

        //Put email in  @values
        values.put(KEY_EMAIL, user.email);

        //Put password in  @values
        values.put(KEY_PASSWORD, user.password);

        // insert row
        long todo_id = db.insert(TABLE_USERS, null, values);
    }


    public void addQuestion(PostQuestionPost postQuestion)
    {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_QID, postQuestion.id);
        values.put(KEY_QUESTION, postQuestion.question);

        //Put email in  @values
        values.put(KEY_OPTIONA, postQuestion.optiona);
        values.put(KEY_OPTIONB, postQuestion.optionb);
        values.put(KEY_OPTIONC, postQuestion.optionc);
        values.put(KEY_OPTIOND, postQuestion.optiond);

        //Put password in  @values
        values.put(KEY_ANSWER, postQuestion.answer);
        values.put(KEY_TYPE, postQuestion.type);

        // insert row
        long todo_id = db.insert(TABLE_QUESTION, null, values);
        if(todo_id !=1)
        {
            Log.e("tableadd","true");
        }
        else {
            Log.e("tableadd","qqqqtrue");
        }

    }

    public void addAnswer(AnswerQuestion answerQuestion)
    {

        //get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        //create content values to insert
        ContentValues values = new ContentValues();

        //Put username in  @values
        values.put(KEY_AAID, answerQuestion.id);
        values.put(KEY_AANSWER, answerQuestion.answer);


        //Put password in  @values

        // insert row
        long todo_id = db.insert(TABLE_ANSWER, null, values);
        if(todo_id !=1)
        {
            Log.e("tableadd","true");
        }
        else {
            Log.e("tableadd","qqqqtrue");
        }

    }

    public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{user.email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }

        //if user password does not matches or there is no record with that email then return @false
        return null;
    }

    public boolean isEmailExists(String email)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,// Selecting Table
                new String[]{KEY_ID, KEY_USER_NAME, KEY_EMAIL, KEY_PASSWORD},//Selecting columns want to query
                KEY_EMAIL + "=?",
                new String[]{email},//Where clause
                null, null, null);

        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email so return true
            return true;
        }

        //if email does not exist return false
        return false;
    }



    public List<PostQuestionAdd> getAllBuffaloRateChartPtoPSnf()
    {
        List<PostQuestionAdd> P_To_P_SNF_Buffalo_ModelArrayList = new ArrayList<PostQuestionAdd>();

        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                PostQuestionAdd p_to_p_snf_buffalo = new PostQuestionAdd(c.getString(0),c.getString(1),c.getString(2),
                        c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7));


                // adding to P_To_P_SNF_Buffalo_ModelArrayList list
                P_To_P_SNF_Buffalo_ModelArrayList.add(p_to_p_snf_buffalo);
            }
            while (c.moveToNext());
        }

        return P_To_P_SNF_Buffalo_ModelArrayList;
    }


    public List<AnswerQuestion> getAllAnswer()
    {
        List<AnswerQuestion> P_To_P_SNF_Buffalo_ModelArrayList = new ArrayList<AnswerQuestion>();

        String selectQuery = "SELECT  * FROM " + TABLE_ANSWER;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                AnswerQuestion p_to_p_snf_buffalo = new AnswerQuestion(c.getString(0),c.getString(1)
                       );


                // adding to P_To_P_SNF_Buffalo_ModelArrayList list
                P_To_P_SNF_Buffalo_ModelArrayList.add(p_to_p_snf_buffalo);
            }
            while (c.moveToNext());
        }

        return P_To_P_SNF_Buffalo_ModelArrayList;
    }

}