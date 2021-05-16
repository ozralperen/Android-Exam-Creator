package tr.yildiz.ozeralperen17011069;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "examAppDB";
    private static final String TABLE_USERS = "users";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SURNAME = "surname";
    private static final String KEY_MAIL = "mail";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_BDAY = "birthday";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PROFILEPIC = "profilepic";

    private static final String TABLE_QUESTIONS = "questions";
    private static final String KEY_QUESTIONID = "id";
    private static final String KEY_QUESTIONTEXT = "question";
    private static final String KEY_ANSWER1 = "answer1";
    private static final String KEY_ANSWER2 = "answer2";
    private static final String KEY_ANSWER3 = "answer3";
    private static final String KEY_ANSWER4 = "answer4";
    private static final String KEY_ANSWER5 = "answer5";
    private static final String KEY_RIGHTANSWER = "right_answer";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SURNAME + " TEXT," + KEY_MAIL + " TEXT,"
                + KEY_PHONE + " TEXT," + KEY_BDAY + " TEXT,"
                + KEY_PASSWORD + " TEXT," + KEY_PROFILEPIC + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + "("
                + KEY_QUESTIONID + " INTEGER PRIMARY KEY," + KEY_QUESTIONTEXT + " TEXT,"
                + KEY_ANSWER1 + " TEXT," + KEY_ANSWER2 + " TEXT," + KEY_ANSWER3 + " TEXT," + KEY_ANSWER4 + " TEXT," + KEY_ANSWER5 + " TEXT,"
                + KEY_RIGHTANSWER + " TEXT" + ")";
        db.execSQL(CREATE_QUESTIONS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);

        onCreate(db);
    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_SURNAME, user.getSurname());
        values.put(KEY_MAIL, user.getMail());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_BDAY, user.getbDay());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_PROFILEPIC, user.getProfilePic());

        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public int login(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_ID }, KEY_MAIL + "=? and " + KEY_PASSWORD + "=?",
                new String[] {email, password}, null, null, null, null);

        cursor.moveToFirst();
        if (cursor == null || cursor.getCount() == 0)
            return -1;
        else
            cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public Boolean userMailExists(String email){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_ID }, KEY_MAIL + "=?", new String[] {email},
                null, null, null, null);
        if (cursor == null || cursor.getCount() == 0)
            return false;
        else
            return true;
    }

    public User getUser(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_ID,
                        KEY_NAME, KEY_SURNAME, KEY_MAIL, KEY_PHONE, KEY_BDAY, KEY_PASSWORD, KEY_PROFILEPIC}, KEY_ID + "=?" , new String[]{String.valueOf(id)},
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        User user = new User(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        user.setId(cursor.getInt(0));
        return user;
    }

    public void addQuestion(Question question){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QUESTIONTEXT, question.getQuestion());
        values.put(KEY_ANSWER1, question.getAnswer1());
        values.put(KEY_ANSWER2, question.getAnswer2());
        values.put(KEY_ANSWER3, question.getAnswer3());
        values.put(KEY_ANSWER4, question.getAnswer4());
        values.put(KEY_ANSWER5, question.getAnswer5());
        values.put(KEY_RIGHTANSWER, question.getRightAnswer());

        db.insert(TABLE_QUESTIONS, null, values);
        db.close();
    }

    public Question getQuestion(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUESTIONS, new String[] { KEY_QUESTIONID,
                        KEY_QUESTIONTEXT, KEY_ANSWER1, KEY_ANSWER2, KEY_ANSWER3, KEY_ANSWER4, KEY_ANSWER5,
                        KEY_RIGHTANSWER}, KEY_QUESTIONID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Question question = new Question(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
        question.setQuestionID(cursor.getInt(0));
        return question;
    }

    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<Question>();
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
                question.setQuestionID(Integer.parseInt(cursor.getString(0)));
                questionList.add(question);
            } while (cursor.moveToNext());
        }

        return questionList;
    }

    public void deleteQuestion(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QUESTIONS, KEY_QUESTIONID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public void updateQuestion(Question question){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_QUESTIONTEXT, question.getQuestion());
        values.put(KEY_ANSWER1, question.getAnswer1());
        values.put(KEY_ANSWER2, question.getAnswer2());
        values.put(KEY_ANSWER3, question.getAnswer3());
        values.put(KEY_ANSWER4, question.getAnswer4());
        values.put(KEY_ANSWER5, question.getAnswer5());
        values.put(KEY_RIGHTANSWER, question.getRightAnswer());

        db.update(TABLE_QUESTIONS, values, KEY_QUESTIONID + " = ?",
                new String[] { String.valueOf(question.getQuestionID()) });
    }

    public int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_SURNAME, user.getSurname());
        values.put(KEY_MAIL, user.getMail());
        values.put(KEY_PHONE, user.getPhone());
        values.put(KEY_BDAY, user.getbDay());
        values.put(KEY_PASSWORD, user.getPassword());
        values.put(KEY_PROFILEPIC, user.getProfilePic());

        return db.update(TABLE_USERS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
    }

}
