package kr.mint.testdblistview.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 예제니까 복사해서 쓰세요 ^^
 * 
 * @author susemi99
 */
public class BaseTestTable
{
   protected SQLiteOpenHelper _helper;
   
   
   protected BaseTestTable(Context $context)
   {
      _helper = new BaseDatabaseHelper($context);
   }
   
   
   protected SQLiteDatabase db()
   {
      return _helper.getWritableDatabase();
   }
   
   
   /**
    * db 닫기
    */
   public void close()
   {
      db().close();
   }
   
   
   protected int insert()
   {
      SQLiteDatabase db = _helper.getWritableDatabase();
      // 마지막에 넣은 rowid 를 리턴한다
      String sql = "SELECT last_insert_rowid();";
      Cursor c = db.rawQuery(sql, null);
      
      int result = 0;
      c.moveToFirst();
      if (c.getCount() > 0)
         result = c.getInt(0);
      c.close();
      
      Log.i("BaseTestTable | insert()", "lowid : " + result + " inserted");
      return result;
   }
}
