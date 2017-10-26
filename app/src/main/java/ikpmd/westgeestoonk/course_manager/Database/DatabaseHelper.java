package ikpmd.westgeestoonk.course_manager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static SQLiteDatabase mSQLDB;
    private static DatabaseHelper mInstance;
    public static final String dbName = "LocalCourses.db";
    public static final int dbVersion = 9;

    public DatabaseHelper(Context ctx) {
        super(ctx, dbName, null, dbVersion);
    }


    public static synchronized DatabaseHelper getHelper(Context ctx) {
        if (mInstance == null) {
            mInstance = new DatabaseHelper(ctx);
            mSQLDB = mInstance.getWritableDatabase();
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DatabaseInfo.CourseTables.COURSE + " (" +
//                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseInfo.CourseColumn.NAAM + " TEXT," +
                DatabaseInfo.CourseColumn.EC + " INTEGER," +
                DatabaseInfo.CourseColumn.VAKCODE + " TEXT PRIMARY KEY," +
                DatabaseInfo.CourseColumn.TOETSING + " TEXT," +
                DatabaseInfo.CourseColumn.PERIODE + " TEXT," +
                DatabaseInfo.CourseColumn.TOETSMOMENT + " TEXT," +
                DatabaseInfo.CourseColumn.CIJFER + " TEXT," +
                DatabaseInfo.CourseColumn.JAAR + " INTEGER);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
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

}



