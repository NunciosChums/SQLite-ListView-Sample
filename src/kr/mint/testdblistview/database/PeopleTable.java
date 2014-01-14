package kr.mint.testdblistview.database;

import static android.provider.BaseColumns._ID;

import java.util.Calendar;

import org.apache.commons.lang3.time.DateFormatUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class PeopleTable extends BaseTestTable
{
   protected static PeopleTable _instance;
   
   // 테이블
   public final static String TABLE = "people";
   
   // 필드
   public static final String ID = _ID;
   public static final String CREATED_AT = "created_at";
   public static final String UPDATED_AT = "updated_at";
   public static final String NAME = "name";
   
   // 주로 사용할 필드 목록
//   private static final String[] COLUMNS = { _ID, CREATED_AT, NAME, UPDATED_AT};
   
   // 정렬
   private static final String ORDER_BY_DEFAULT = _ID + " asc";
   private static final String ORDER_BY_DEFAULT_DESC = _ID + " desc";
//   private static final String ORDER_BY_NAME = NAME + " asc, " + _ID + " asc";
//   private static final String ORDER_BY_DATE_DESC = CREATED_AT + " desc, " + _ID + " desc";
//   private static final String ORDER_BY_NAME_DESC = NAME + " desc, " + _ID + " desc";
   
   // 조회조건
   private static final String WHERE_BY_ID = _ID + "=?";
//   private static final String WHERE_BY_DATE = "strftime('%Y'," + DATE + ")=?";
   
   // 최초 생성 sql
   public static final String createSql = "CREATE TABLE if not exists " + TABLE + "(" + _ID + " integer primary key autoincrement, " + CREATED_AT + " text," + NAME + " text, " + UPDATED_AT + " text);";
   
   
   public synchronized static PeopleTable instance(Context $context)
   {
      if (_instance == null)
         _instance = new PeopleTable($context);
      return _instance;
   }
   
   
   private PeopleTable(Context $context)
   {
      super($context);
   }
   
   
   /**
    * 입력
    * 
    * @param $date
    *           날짜
    * @param $name
    *           이름
    * @return
    */
   public int insert(String $name)
   {
      Calendar cal = Calendar.getInstance();
      String date = DateFormatUtils.format(cal, "yyyy-MM-dd HH:mm:ss");
      
      ContentValues values = new ContentValues();
      values.put(CREATED_AT, date);
      values.put(UPDATED_AT, date);
      values.put(NAME, $name.trim());
      db().insertOrThrow(TABLE, null, values);
      return super.insert();
   }
   
   
   /**
    * 이름으로 정렬
    * 
    * @param isDesc
    *           true=역순
    * @return
    */
//   public Cursor loadByName(boolean $isDesc)
//   {
//      String order = ORDER_BY_NAME;
//      if ($isDesc)
//         order = ORDER_BY_NAME_DESC;
//      Cursor c = db().query(TABLE, COLUMNS, null, null, null, null, order);
//      
//      Log.i("TestTable | getByName()", c.getCount() + "개");
//      
//      return c;
//   }
   
   /**
    * 날짜로 정렬
    * 
    * @param isDesc
    *           true=역순
    * @return
    */
   public Cursor loadByDate(boolean $isDesc)
   {
      String order = ORDER_BY_DEFAULT;
      if ($isDesc)
         order = ORDER_BY_DEFAULT_DESC;
      Cursor c = db().query(TABLE, null, null, null, null, null, order);
      
      Log.i("TestTable | getByDate()", c.getCount() + "개");
      
      return c;
   }
   
   
   /**
    * 같은 년도 검색하기
    * 
    * @param $year
    *           2011
    * @return
    */
//   public Cursor loadByDate(String $year)
//   {
//      String[] selectionArgs = { $year };
//      return db().query(TABLE, COLUMNS, WHERE_BY_DATE, selectionArgs, null, null, ORDER_BY_DEFAULT);
//   }
   
   /**
    * 업데이트
    * 
    * @param id
    * @param name
    * @param date
    * @return
    */
   public int update(String $id, String $name)
   {
      Calendar cal = Calendar.getInstance();
      String date = DateFormatUtils.format(cal, "yyyy-MM-dd HH:mm:ss");
      ContentValues values = new ContentValues();
      values.put(NAME, $name);
      values.put(UPDATED_AT, date);
      String[] whereArgs = { $id };
      
      return db().update(TABLE, values, WHERE_BY_ID, whereArgs);
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
    * @param $id
    * @return
    */
   public int deleteById(int $id)
   {
      String[] whereArgs = { $id + "" };
      return db().delete(TABLE, WHERE_BY_ID, whereArgs);
   }
}
