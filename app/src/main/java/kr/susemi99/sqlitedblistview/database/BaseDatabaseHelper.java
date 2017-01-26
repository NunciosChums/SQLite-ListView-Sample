package kr.susemi99.sqlitedblistview.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 예제니까 복사해서 쓰세요 ^^
 *
 * @author susemi99
 */
public class BaseDatabaseHelper extends SQLiteOpenHelper {
  public BaseDatabaseHelper(Context context, String name, CursorFactory factory, int version) {
    super(context, name, factory, version);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(PeopleTable.createSql);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // db.execSQL("drop table if exists "+TestTable.TABLE);
  }
}
