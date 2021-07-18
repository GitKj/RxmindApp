package com.example.rxmindapp;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ResultAdapter extends BaseAdapter {

    public ArrayList<Result> results;
    private Context context;

    public ResultAdapter(ArrayList<Result> results, Context context){
        this.results = results;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.results.size();
    }

    @Override
    public Object getItem(int position) {
        return this.results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ResultAdapter.ViewHolder holder = null;

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.response, null);

            holder = new ResultAdapter.ViewHolder();
            holder.resultName = convertView.findViewById(R.id.tv_resultName);

            holder.resultName = convertView.findViewById(R.id.iv_resultImage);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ResultAdapter.ViewHolder) convertView.getTag();
        }

        Result result = results.get(position);
        holder.resultName.setText(result.name);

        Log.i("res", "name = " + result.name);

        //image handling logic
        if(!result.imageUrl.equals("")){ // leaves default if no associated image URL
            Picasso.get()
                    .load(result.imageUrl)
                    .resize(240,240)
                    .centerCrop()
                    .into(holder.resultImage);
        }

        return convertView;
    }

    private static class ViewHolder
    {
        public ImageView resultImage;
        TextView resultName;
    }
}

