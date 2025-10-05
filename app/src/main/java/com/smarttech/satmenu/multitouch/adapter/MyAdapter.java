package com.smarttech.satmenu.multitouch.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.smarttech.satmenu.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    // private LayoutInflater inflater;
    Context context;
    List<String> listGalleryImages;
    LayoutInflater inLayoutInflater;
    private int[] resImages;

    public MyAdapter(Context context, int[] resImage) {
        this.context = context;
        // this.listGalleryImages = listGalleryImages2;
        this.resImages = resImage;
    }

    @Override
    public int getCount() {
        return resImages.length;
    }

    @Override
    public Object getItem(int i) {
        return getItem(resImages[i]);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        String listImagesBeen;
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            inLayoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inLayoutInflater.inflate(R.layout.grid_item, null);
            viewHolder.imageViewItem = (ImageView) convertView
                    .findViewById(R.id.picture);
            viewHolder.progressBar = (ProgressBar) convertView
                    .findViewById(R.id.progress);
            viewHolder.textView1 = (TextView) convertView
                    .findViewById(R.id.textViewNew);
            viewHolder.textView2 = (TextView) convertView
                    .findViewById(R.id.textView1);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // listImagesBeen = listGalleryImages.get(position);
        viewHolder.imageViewItem.setImageResource(resImages[position]);
        viewHolder.textView1.setText(" Please Wait...");
        viewHolder.textView2.setText("Image Loading");

        return convertView;
    }

    class ViewHolder {

        ImageView imageViewItem;
        TextView textView1, textView2;
        ProgressBar progressBar;

    }
}