package kr.susemi99.sqlitedblistview.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;

import kr.susemi99.sqlitedblistview.DateFormatter;
import kr.susemi99.sqlitedblistview.models.Person;

import static android.provider.BaseColumns._ID;

public class PeopleTable extends BaseTable {
  protected static PeopleTable instance;

  // 테이블
  public final static String TABLE_NAME = "people";

  // 필드
  public static final String ID = _ID;
  public static final String CREATED_AT = "created_at";
  public static final String UPDATED_AT = "updated_at";
  public static final String NAME = "name";

  // 주로 사용할 필드 목록
   private static final String[] COLUMNS = { _ID, CREATED_AT, NAME, UPDATED_AT};

  // 정렬
  private static final String ORDER_BY_DEFAULT = _ID + " asc";
  private static final String ORDER_BY_DEFAULT_DESC = _ID + " desc";
//   private static final String ORDER_BY_NAME = NAME + " asc, " + _ID + " asc";
//   private static final String ORDER_BY_DATE_DESC = CREATED_AT + " desc, " + _ID + " desc";
//   private static final String ORDER_BY_NAME_DESC = NAME + " desc, " + _ID + " desc";

  // 조회조건
  private static final String WHERE_BY_ID = _ID + "=?";
   private static final String WHERE_BY_DATE = "strftime('%Y'," + CREATED_AT + ")=?";

  // 최초 생성 sql
  public static final String createSql = "CREATE TABLE if not exists " + TABLE_NAME + "(" + _ID + " integer primary key autoincrement, " + CREATED_AT + " text," + NAME + " text, " + UPDATED_AT
    + " text);";

  public synchronized static PeopleTable instance(Context $context) {
    if(instance == null) {
      synchronized (PeopleTable.class) {
        if(instance == null)
          instance = new PeopleTable($context);
      }
    }

    return instance;
  }

  private PeopleTable(Context $context) {
    super($context);
  }

  /**
   * 입력
   *
   * @param name 이름
   * @return
   */
  public int insert(String name) {
    Calendar cal = Calendar.getInstance();
    String date = DateFormatter.format(cal, "yyyy-MM-dd HH:mm:ss");

    ContentValues values = new ContentValues();
    values.put(CREATED_AT, date);
    values.put(UPDATED_AT, date);
    values.put(NAME, name.trim());
    db().insertOrThrow(TABLE_NAME, null, values);
    return super.insert();
  }


  /**
   * 이름으로 정렬
   *
   * @param isDesc
   *          true=역순
   * @return
   */
//  public ArrayList<Person> loadByName(boolean $isDesc)
//  {
//    ArrayList<Person> result = new ArrayList<>();
//    
//    String order = ORDER_BY_NAME;
//    if ($isDesc)
//      order = ORDER_BY_NAME_DESC;
//    Cursor c = db().query(TABLE, COLUMNS, null, null, null, null, order);
//    
//    Log.i("TestTable | loadByName()", c.getCount() + "개");
//    
//    if (c.getCount() == 0)
//      return result;
//    
//    c.moveToFirst();
//    
//    while (!c.isAfterLast())
//    {
//      result.add(makeBean(c));
//      c.moveToNext();
//    }
//    
//    c.close();
//    
//    return result;
//  }

  /**
   * 날짜로 정렬
   *
   * @param isDesc true=역순
   * @return
   */
  public ArrayList<Person> loadByDate(boolean isDesc) {
    ArrayList<Person> result = new ArrayList<>();

    String order = ORDER_BY_DEFAULT;
    if(isDesc)
      order = ORDER_BY_DEFAULT_DESC;
    Cursor c = db().query(TABLE_NAME, null, null, null, null, null, order);

    Log.i("TestTable | loadByDate()", c.getCount() + "개");

    if(c.getCount() == 0)
      return result;

    c.moveToFirst();

    while (!c.isAfterLast()) {
      result.add(makeBean(c));
      c.moveToNext();
    }

    c.close();

    return result;
  }


  /**
   * 같은 년도 검색하기
   *
   * @param year 2011
   * @return
   */
  public ArrayList<Person> loadByDate(String year) {
    ArrayList<Person> result = new ArrayList<>();

    String[] selectionArgs = {year};
    Cursor c = db().query(TABLE_NAME, COLUMNS, WHERE_BY_DATE, selectionArgs, null, null, ORDER_BY_DEFAULT);

    Log.i("TestTable | loadByDate()", c.getCount() + "개");

    if(c.getCount() == 0)
      return result;

    c.moveToFirst();

    while (!c.isAfterLast()) {
      result.add(makeBean(c));
      c.moveToNext();
    }

    c.close();

    return result;
  }

  private Person makeBean(Cursor cursor) {
    String id = cursor.getString(cursor.getColumnIndex(ID));
    String name = cursor.getString(cursor.getColumnIndex(NAME));
    String createdAt = cursor.getString(cursor.getColumnIndex(CREATED_AT));
    String updatedAt = cursor.getString(cursor.getColumnIndex(UPDATED_AT));
    return new Person(id, name, createdAt, updatedAt);
  }

  /**
   * 업데이트
   *
   * @param id
   * @param name
   * @return
   */
  public int update(String id, String name) {
    Calendar cal = Calendar.getInstance();
    String date = DateFormatter.format(cal, "yyyy-MM-dd HH:mm:ss");
    ContentValues values = new ContentValues();
    values.put(NAME, name);
    values.put(UPDATED_AT, date);
    String[] whereArgs = {id};

    return db().update(TABLE_NAME, values, WHERE_BY_ID, whereArgs);
  }

  /**
   * 데이터 전체 삭제
   */
//   public void deleteAll()
//   {
//      db().delete(TABLE, null, null);
//   }

  /**
   * id 가 같은 row 삭제
   *
   * @param id
   * @return
   */
  public int deleteById(int id) {
    String[] whereArgs = {id + ""};
    return db().delete(TABLE_NAME, WHERE_BY_ID, whereArgs);
  }
}
