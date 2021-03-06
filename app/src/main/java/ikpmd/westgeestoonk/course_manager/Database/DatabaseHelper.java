package ikpmd.westgeestoonk.course_manager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

import ikpmd.westgeestoonk.course_manager.Models.Course_Model;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static SQLiteDatabase mSQLDB;
    private static DatabaseHelper mInstance;
    public static String dbName;
    public static final int dbVersion = 28;

    public DatabaseHelper(Context ctx, String dbName) {
        super(ctx, dbName, null, dbVersion);
    }


    public static synchronized DatabaseHelper getHelper(Context ctx, String dbName) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx, dbName + ".db");
            mSQLDB = mInstance.getWritableDatabase();
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB", "Nieuwe tabel aanmaken....");
        db.execSQL("CREATE TABLE " + DatabaseInfo.CourseTables.COURSE + " (" +
//                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseInfo.CourseColumn.NAAM + " TEXT," +
                DatabaseInfo.CourseColumn.EC + " INTEGER," +
                DatabaseInfo.CourseColumn.VAKCODE + " TEXT PRIMARY KEY," +
                DatabaseInfo.CourseColumn.TOETSING + " TEXT," +
                DatabaseInfo.CourseColumn.PERIODE + " TEXT," +
                DatabaseInfo.CourseColumn.TOETSMOMENT + " TEXT," +
                DatabaseInfo.CourseColumn.CIJFER + " TEXT," +
                DatabaseInfo.CourseColumn.JAAR + " INTEGER," +
                DatabaseInfo.CourseColumn.KEUZEVAK + " INTEGER," +
                DatabaseInfo.CourseColumn.NOTITIE + " INTEGER);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DB", "Nieuwe database versie... upgrading....");
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseInfo.CourseTables.COURSE);
        onCreate(db);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public void insert(String table, String nullColumnHack, ContentValues values) {
        try {
            mSQLDB.insert(table, nullColumnHack, values);
        } catch (SQLException e) {
            Log.d("DEBUG", e.getMessage());
        }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectArgs, String groupBy, String having, String orderBy) {
        return mSQLDB.query(table, columns, selection, selectArgs, groupBy, having, orderBy);
    }

    public ArrayList<Course_Model> getAllCourses() {
        ArrayList<Course_Model> courses = new ArrayList<>();


        Cursor cs = mSQLDB.query(DatabaseInfo.CourseTables.COURSE, new String[]{"*"}, null, null, null, null, null);
        while(cs.moveToNext()) {
            courses.add(new Course_Model(cs.getString(cs.getColumnIndex(DatabaseInfo.CourseColumn.NAAM)),
                                        cs.getInt(cs.getColumnIndex(DatabaseInfo.CourseColumn.EC)),
                                        cs.getString(cs.getColumnIndex(DatabaseInfo.CourseColumn.VAKCODE)),
                                        cs.getString(cs.getColumnIndex(DatabaseInfo.CourseColumn.TOETSING)),
                                        cs.getString(cs.getColumnIndex(DatabaseInfo.CourseColumn.PERIODE)),
                                        cs.getString(cs.getColumnIndex(DatabaseInfo.CourseColumn.TOETSMOMENT)),
                                        cs.getString(cs.getColumnIndex(DatabaseInfo.CourseColumn.CIJFER)),
                                        cs.getString(cs.getColumnIndex(DatabaseInfo.CourseColumn.JAAR)),
                                        cs.getInt(cs.getColumnIndex(DatabaseInfo.CourseColumn.KEUZEVAK)),
                                        cs.getString(cs.getColumnIndex(DatabaseInfo.CourseColumn.NOTITIE))));
        }

        return courses;
    }

    public void updateCijfer(Course_Model course) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseInfo.CourseColumn.CIJFER, course.getCijfer());
        mSQLDB.update(DatabaseInfo.CourseTables.COURSE, cv, DatabaseInfo.CourseColumn.VAKCODE + "=\"" + course.getVakcode() + "\"", null);
    }

    public void updateNotitie(Course_Model course) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseInfo.CourseColumn.NOTITIE, course.getNotitie());
        mSQLDB.update(DatabaseInfo.CourseTables.COURSE, cv, DatabaseInfo.CourseColumn.VAKCODE + "=\"" + course.getVakcode() + "\"", null);
    }


}



