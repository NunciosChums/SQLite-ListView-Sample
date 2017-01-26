package kr.susemi99.sqlitedblistview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import kr.susemi99.sqlitedblistview.database.PeopleTable;
import kr.susemi99.sqlitedblistview.models.Person;

public class MainActivity extends AppCompatActivity {
  private EditText editName;
  private Button btnInput;
  private ListViewAdapter adapter;
  private PeopleTable peopleTable;

  private static final int INSERT_MODE = 1;
  private static final int UPDATE_MODE = 2;
  private Person updatePerson;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    adapter = new ListViewAdapter();

    ListView listView = (ListView) findViewById(R.id.listview);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(itemClickListener);
    listView.setOnItemLongClickListener(itemLongClickListener);

    editName = (EditText) findViewById(R.id.edit_name);
    btnInput = (Button) findViewById(R.id.btn_input);
    btnInput.setOnClickListener(onClickListener);

    peopleTable = PeopleTable.instance(getApplicationContext());

    loadAllFromDB();
  }

  private void loadAllFromDB() {
    ArrayList<Person> people = peopleTable.loadByDate(true);
    for (Person person : people) {
      adapter.add(person);
    }
    adapter.notifyDataSetChanged();
  }

  private void insertToDB() {
    int newId = peopleTable.insert(editName.getText().toString());

    Calendar cal = Calendar.getInstance();
    String date = DateFormatter.format(cal, "yyyy-MM-dd HH:mm:ss");
    Person bean = new Person(newId + "", editName.getText().toString(), date, date);

    adapter.insert(bean);
    adapter.notifyDataSetChanged();
    editName.setText("");
  }

  private void updateOnDB() {
    updatePerson.name = editName.getText().toString();
    peopleTable.update(updatePerson.id, updatePerson.name);
    btnInput.setTag(INSERT_MODE);
    btnInput.setText("Input");
    adapter.update(updatePerson);
    updatePerson = null;
    editName.setText("");
  }

  private void removeOnDB(final int $uid) {
    new AlertDialog.Builder(this)
      .setTitle("delete")
      .setMessage("confirm?")
      .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
          peopleTable.deleteById($uid);
          adapter.delete($uid);
        }
      })
      .setNegativeButton(android.R.string.cancel, null)
      .create().show();
  }

  /**************************************************
   * listener
   ***************************************************/
  private View.OnClickListener onClickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      Log.i("MainActivity.java | onClick", "|" + v.getTag().toString() + "|");
      switch (Integer.parseInt(v.getTag().toString())) {
        case INSERT_MODE:
          insertToDB();
          break;

        case UPDATE_MODE:
          updateOnDB();
          break;

        default:
          break;
      }
    }
  };

  private AdapterView.OnItemLongClickListener itemLongClickListener = new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int position, long uid) {
      removeOnDB(Integer.parseInt(uid + ""));
      return true;
    }
  };

  private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int position, long uid) {
      updatePerson = (Person) adapter.getItem(position);
      editName.requestFocus();
      editName.setText(updatePerson.name);
      btnInput.setText("Update");
      btnInput.setTag(UPDATE_MODE);
    }
  };
}
