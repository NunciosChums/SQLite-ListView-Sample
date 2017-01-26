package kr.susemi99.sqlitedblistview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import kr.susemi99.sqlitedblistview.models.Person;

public class ListViewAdapter extends BaseAdapter {
  private ArrayList<Person> items = new ArrayList<>();

  public ListViewAdapter() {
    super();
  }

  public void clear() {
    items.clear();
    notifyDataSetChanged();
  }

  public void add(Person person) {
    items.add(person);
  }

  public void insert(Person person) {
    items.add(0, person);
  }

  public void update(Person person) {
    for (int i = 0; i < getCount(); i++) {
      Person bean = items.get(i);
      if(bean.id.equals(person.id)) {
        items.remove(i);
        items.add(i, person);
        notifyDataSetChanged();
        break;
      }
    }
  }

  public void delete(int id) {
    for (int i = 0; i < getCount(); i++) {
      Person bean = items.get(i);
      if(bean.id.equals(id + "")) {
        items.remove(i);
        notifyDataSetChanged();
        break;
      }
    }
  }

  @Override
  public int getCount() {
    return items.size();
  }

  @Override
  public Object getItem(int position) {
    return items.get(position);
  }

  @Override
  public long getItemId(int position) {
    Person bean = items.get(position);
    return Long.parseLong(bean.id);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Person bean = items.get(position);

    ViewHolderItem holder;
    if(convertView == null) {
      int resId = R.layout.list_item_person;
      convertView = LayoutInflater.from(parent.getContext()).inflate(resId, null);
      holder = new ViewHolderItem();
      holder.textName = (TextView) convertView.findViewById(R.id.text_name);
      holder.textDate1 = (TextView) convertView.findViewById(R.id.text_date1);
      convertView.setTag(holder);
    } else
      holder = (ViewHolderItem) convertView.getTag();

    holder.textName.setText(bean.name);
    holder.textDate1.setText(bean.created_at);

    return convertView;
  }

  static class ViewHolderItem {
    TextView textName;
    TextView textDate1;
  }
}
