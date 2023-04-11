package com.example.potato_disease;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Forecast> forecastList;

    public RecyclerViewAdapter(Context context,List<Forecast>forecastList)
    {
        this.context=context;
        this.forecastList=forecastList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.col,parent,false);
        return new ViewHolder(view);
        
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Forecast forecast=forecastList.get(position);
            holder.Date.setText((CharSequence) forecast.getDate());
            holder.text.setText((CharSequence) forecast.getText());
            holder.max_temp.setText((String) forecast.getMax_temp());
            holder.min_temp.setText((String) forecast.getMin_temp());


    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView text;
        public TextView Date;
        public TextView max_temp;
        public TextView min_temp;
        public ImageView icon;
        public ViewHolder(View view) {
            super(view);
            text=view.findViewById(R.id.text);
//            Date=view.findViewById(R.id.dates);
//            max_temp=view.findViewById(R.id.max_temp);
//            min_temp=view.findViewById(R.id.min_temp);
            icon=view.findViewById(R.id.icon);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
