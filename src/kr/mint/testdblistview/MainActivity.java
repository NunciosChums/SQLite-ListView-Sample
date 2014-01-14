package kr.mint.testdblistview;

import java.util.Calendar;

import kr.mint.testdblistview.database.PeopleTable;

import org.apache.commons.lang3.time.DateFormatUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends Activity
{
   private EditText _editName;
   private ListView _listView;
   private Button _btnInput;
   private ListViewAdapter _adapter;
   private PeopleTable _table;
   
   private static final int INSERT_MODE = 1;
   private static final int UPDATE_MODE = 2;
   private PersonBean _updatePerson;
   
   
   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      
      _adapter = new ListViewAdapter(getApplicationContext());
      _listView = (ListView) findViewById(R.id.listview);
      _listView.setAdapter(_adapter);
      _listView.setOnItemClickListener(itemClickListener);
      _listView.setOnItemLongClickListener(itemLongClickListener);
      
      _editName = (EditText) findViewById(R.id.edit_name);
      _btnInput = (Button) findViewById(R.id.btn_input);
      _btnInput.setOnClickListener(onClickListener);
      
      _table = PeopleTable.instance(getApplicationContext());
      
      loadAllFromDB();
   }
   
   
   private void loadAllFromDB()
   {
      Cursor cursor = _table.loadByDate(true);
      cursor.moveToFirst();
      while (!cursor.isAfterLast())
      {
         String id = cursor.getString(cursor.getColumnIndex(PeopleTable.ID));
         String name = cursor.getString(cursor.getColumnIndex(PeopleTable.NAME));
         String date1 = cursor.getString(cursor.getColumnIndex(PeopleTable.CREATED_AT));
         PersonBean bean = new PersonBean(id, name, date1, null);
         _adapter.add(bean);
         cursor.moveToNext();
      }
      cursor.close();
      _adapter.notifyDataSetChanged();
   }
   
   
   private void insertOnDB()
   {
      int newId = _table.insert(_editName.getText().toString());
      
      Calendar cal = Calendar.getInstance();
      String date = DateFormatUtils.format(cal, "yyyy-MM-dd HH:mm:ss");
      PersonBean bean = new PersonBean(newId + "", _editName.getText().toString(), date, date);
      
      _adapter.insert(bean);
      _adapter.notifyDataSetChanged();
      _editName.setText("");
   }
   
   
   private void updateOnDB()
   {
      _updatePerson.name = _editName.getText().toString();
      _table.update(_updatePerson.id, _updatePerson.name);
      _btnInput.setTag(INSERT_MODE);
      _btnInput.setText("Input");
      _adapter.update(_updatePerson);
      _updatePerson = null;
      _editName.setText("");
   }
   
   
   private void removeOnDB(final int $uid)
   {
      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setTitle("delete?").setPositiveButton("OK", new DialogInterface.OnClickListener()
      {
         @Override
         public void onClick(DialogInterface dialog, int which)
         {
            _table.deleteById($uid);
            _adapter.delete($uid);
         }
      }).setNegativeButton("NO", new DialogInterface.OnClickListener()
      {
         public void onClick(DialogInterface dialog, int id)
         {
            dialog.cancel();
         }
      });
      
      alertDialogBuilder.create().show();
   }
   
   private View.OnClickListener onClickListener = new OnClickListener()
   {
      @Override
      public void onClick(View v)
      {
         Log.i("MainActivity.java | onClick", "|" + v.getTag().toString() + "|");
         switch (Integer.parseInt(v.getTag().toString()))
         {
            case INSERT_MODE:
               insertOnDB();
               break;
            
            case UPDATE_MODE:
               updateOnDB();
               break;
            
            default:
               break;
         }
      }
   };
   
   private OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener()
   {
      @Override
      public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long uid)
      {
         removeOnDB(Integer.parseInt(uid + ""));
         return true;
      }
   };
   private OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener()
   {
      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int position, long uid)
      {
         _updatePerson = (PersonBean) _adapter.getItem(position);
         _editName.requestFocus();
         _editName.setText(_updatePerson.name);
         _btnInput.setText("Update");
         _btnInput.setTag(UPDATE_MODE);
      }
   };
}
