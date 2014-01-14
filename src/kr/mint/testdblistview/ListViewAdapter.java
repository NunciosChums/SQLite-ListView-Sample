package kr.mint.testdblistview;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListViewAdapter extends BaseAdapter
{
   private ArrayList<PersonBean> _items;
   private LayoutInflater _inflater;
   
   
   public ListViewAdapter(Context $context)
   {
      super();
      _inflater = LayoutInflater.from($context);
      _items = new ArrayList<PersonBean>();
   }
   
   
   public void clear()
   {
      _items.clear();
      notifyDataSetChanged();
   }
   
   
   public void add(PersonBean $bean)
   {
      _items.add($bean);
   }
   
   
   public void insert(PersonBean $bean)
   {
      _items.add(0, $bean);
   }
   
   
   public void update(PersonBean $bean)
   {
      for (int i = 0; i < getCount(); i++)
      {
         PersonBean bean = _items.get(i);
         if (bean.id.equals($bean.id))
         {
            _items.remove(i);
            _items.add(i, $bean);
            notifyDataSetChanged();
            break;
         }
      }
   }
   
   
   public void delete(int id)
   {
      for (int i = 0; i < getCount(); i++)
      {
         PersonBean bean = _items.get(i);
         if (bean.id.equals(id + ""))
         {
            _items.remove(i);
            notifyDataSetChanged();
            break;
         }
      }
   }
   
   
   @Override
   public int getCount()
   {
      return _items.size();
   }
   
   
   @Override
   public Object getItem(int position)
   {
      return _items.get(position);
   }
   
   
   @Override
   public long getItemId(int position)
   {
      PersonBean bean = _items.get(position);
      return Long.parseLong(bean.id);
   }
   
   
   @Override
   public View getView(int position, View convertView, ViewGroup arg2)
   {
      PersonBean bean = _items.get(position);
      
      ViewHolderItem holder;
      if (convertView == null)
      {
         convertView = _inflater.inflate(R.layout.item_db, null);
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
