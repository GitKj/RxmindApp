package com.example.rxmindapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


// THIS CLASS IS IMPORTANT
// We use this to have a custom adapter for our list view that can handle our specific objects.
// This also lets us personally customize each row of our list view so we can show/hide specific pill information for users.


// **This class should not need to be touched anymore**.

public class ReminderAdapter extends BaseAdapter {

    public ArrayList<UserReminder> reminders;
    private Context context;

    public ReminderAdapter(ArrayList<UserReminder> reminders, Context context) {
        this.reminders = reminders;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.reminders.size();
    }

    @Override
    public Object getItem(int position) {
        return this.reminders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row, null);

            holder = new ViewHolder();
            holder.pillName = (TextView) convertView.findViewById(R.id.tv_PN);
            holder.quantity = (TextView) convertView.findViewById(R.id.tv_PA);
            holder.time = (TextView) convertView.findViewById(R.id.tv_PT);
            holder.description = (TextView) convertView.findViewById(R.id.tv_PD);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        UserReminder reminder = reminders.get(position);
        holder.pillName.setText(reminder.getPillName());
        holder.time.setText(reminder.getPillTime());
        holder.quantity.setText(reminder.getPillQuantity());
        holder.description.setText(reminder.getPillDescription());

        return convertView;
    }

    private static class ViewHolder
    {
        TextView pillName;
        TextView time;
        TextView days;
        TextView quantity;
        TextView description;
    }
}
