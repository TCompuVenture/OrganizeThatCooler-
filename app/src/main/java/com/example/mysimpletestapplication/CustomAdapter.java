package com.example.mysimpletestapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Message>  {
    private ArrayList<Message> DataSet;
    Context mContext;
    private static class ViewHolder{
        TextView txtMessage;
        TextView textTime;
    }

    public CustomAdapter(ArrayList<Message> data, Context context){
        super(context, R.layout.row_item_old_possibly_drop, data);
        this.DataSet = data;
        this.mContext = context;
    }

    private int lastPosition = -1;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message msg = getItem(position);
        ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_old_possibly_drop, parent, false);
            viewHolder.txtMessage = (TextView) convertView.findViewById(R.id.row_msg);
        //    viewHolder.textTime = (TextView) convertView.findViewById(R.id.gps_textView);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }
        viewHolder.textTime.setText(msg.getDatetime());
        viewHolder.txtMessage.setText(msg.getText());
        // Return the completed view to render on screen
        return convertView;
    }
}
