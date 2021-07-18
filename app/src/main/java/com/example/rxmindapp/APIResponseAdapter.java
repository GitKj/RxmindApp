package com.example.rxmindapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class APIResponseAdapter extends BaseAdapter {

    public ArrayList<APIResponse> responses;
    private Context context;

    public APIResponseAdapter(ArrayList<APIResponse> responses, Context context){
        this.responses = responses;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.responses.size();
    }

    @Override
    public Object getItem(int position) {
        return this.responses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        APIResponseAdapter.ViewHolder holder = null;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.response, null);

            holder = new APIResponseAdapter.ViewHolder();
            holder.pillName = (TextView) convertView.findViewById(R.id.tv_PN);
            holder.quantity = (TextView) convertView.findViewById(R.id.tv_PA);
            holder.time = (TextView) convertView.findViewById(R.id.tv_PT);
            holder.description = (TextView) convertView.findViewById(R.id.tv_PD);
            holder.monday = (TextView) convertView.findViewById(R.id.tv_Monday);
            holder.tues = (TextView) convertView.findViewById(R.id.tv_Tues);
            holder.wed = (TextView) convertView.findViewById(R.id.tv_Wed);
            holder.thu = (TextView) convertView.findViewById(R.id.tv_TH);
            holder.fri = (TextView) convertView.findViewById(R.id.tv_Fri);
            holder.sat = (TextView) convertView.findViewById(R.id.tv_Sa);
            holder.sun = (TextView) convertView.findViewById(R.id.tv_Su);
            holder.pillImage = (ImageView) convertView.findViewById(R.id.iv_PillImage);

            convertView.setTag(holder);
        }
        else
        {
            holder = (APIResponseAdapter.ViewHolder) convertView.getTag();
        }

        UserReminder reminder = reminders.get(position);
        holder.pillName.setText(reminder.getPillName());
        holder.time.setText(reminder.getPillTime());
        holder.quantity.setText("Qnt: " + reminder.getPillQuantity());
        holder.description.setText(reminder.getPillDescription());

        if (reminder.isTakeOnMonday())
        {
            holder.monday.setTextColor(Color.GREEN);
        }
        if (reminder.isTakeOnTuesday())
        {
            holder.tues.setTextColor(Color.GREEN);
        }
        if (reminder.isTakeOnWednesday())
        {
            holder.wed.setTextColor(Color.GREEN);
        }
        if (reminder.isTakeOnThursday())
        {
            holder.thu.setTextColor(Color.GREEN);
        }
        if (reminder.isTakeOnFriday())
        {
            holder.fri.setTextColor(Color.GREEN);
        }
        if (reminder.isTakeOnSat())
        {
            holder.sat.setTextColor(Color.GREEN);
        }
        if (reminder.isTakeOnSun())
        {
            holder.sun.setTextColor(Color.GREEN);
        }

        //image handling logic
        if(!reminder.getPillImageURL().equals("")){ // leaves default if no associated image URL
            Picasso.get()
                    .load(reminder.getPillImageURL())
                    .resize(64,64)
                    .centerCrop()
                    .into(holder.pillImage);
        }

        return convertView;
    }

    private static class ViewHolder
    {
        public ImageView pillImage;
        TextView pillName;
        TextView time;
        TextView quantity;
        TextView description;
        TextView monday;
        TextView tues;
        TextView wed;
        TextView thu;
        TextView fri;
        TextView sat;
        TextView sun;
    }
}

