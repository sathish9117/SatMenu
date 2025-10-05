package com.smarttech.satmenu.multitouch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.smarttech.satmenu.R;

import java.util.List;

public class ListAdapter extends BaseAdapter {


    Context context;
    List<String> listItList;
    LayoutInflater layoutInflater;
    String list;

    public ListAdapter(Context context, List<String> listItem) {
        // TODO Auto-generated constructor stub
        this.listItList = listItem;
        this.context = context;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listItList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return listItList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.textView_list_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        list = listItList.get(position);
        holder.textView.setText(list);

        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }

}
