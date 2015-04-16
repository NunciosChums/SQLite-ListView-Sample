package kr.mint.testdblistview;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter
{
  private ArrayList<PersonBean> items = new ArrayList<PersonBean>();
  
  
  public ListViewAdapter(Context $context)
  {
    super();
  }
  
  
  public void clear()
  {
    items.clear();
    notifyDataSetChanged();
  }
  
  
  public void add(PersonBean $bean)
  {
    items.add($bean);
  }
  
  
  public void insert(PersonBean $bean)
  {
    items.add(0, $bean);
  }
  
  
  public void update(PersonBean $bean)
  {
    for (int i = 0; i < getCount(); i++)
    {
      PersonBean bean = items.get(i);
      if (bean.id.equals($bean.id))
      {
        items.remove(i);
        items.add(i, $bean);
        notifyDataSetChanged();
        break;
      }
    }
  }
  
  
  public void delete(int id)
  {
    for (int i = 0; i < getCount(); i++)
    {
      PersonBean bean = items.get(i);
      if (bean.id.equals(id + ""))
      {
        items.remove(i);
        notifyDataSetChanged();
        break;
      }
    }
  }
  
  
  @Override
  public int getCount()
  {
    return items.size();
  }
  
  
  @Override
  public Object getItem(int position)
  {
    return items.get(position);
  }
  
  
  @Override
  public long getItemId(int position)
  {
    PersonBean bean = items.get(position);
    return Long.parseLong(bean.id);
  }
  
  
  @Override
  public View getView(int position, View convertView, ViewGroup parent)
  {
    PersonBean bean = items.get(position);
    
    ViewHolderItem holder;
    if (convertView == null)
    {
      int resId = R.layout.list_item_person;
      convertView = LayoutInflater.from(parent.getContext()).inflate(resId, null);
      holder = new ViewHolderItem();
      holder.textName = (TextView) convertView.findViewById(R.id.text_name);
      holder.textDate1 = (TextView) convertView.findViewById(R.id.text_date1);
      convertView.setTag(holder);
    }
    else
      holder = (ViewHolderItem) convertView.getTag();
    
    holder.textName.setText(bean.name);
    holder.textDate1.setText(bean.created_at);
    
    return convertView;
  }
  
  static class ViewHolderItem
  {
    TextView textName;
    TextView textDate1;
  }
}
