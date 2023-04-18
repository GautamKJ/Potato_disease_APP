package com.example.potato_disease;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

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
            holder.date.setText((CharSequence) forecast.getDate());
//            holder.text.setText((CharSequence) forecast.getText());
            holder.temp.setText((String) forecast.getMax_temp()+"°C");
//            holder.min_temp.setText((String) forecast.getMin_temp());
//            holder.icon.setImageURI((Uri) forecast.getIcon());
                 Picasso.get()
                .load("https://"+forecast.getIcon())
                .into(holder.icon);


    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView date;
        public TextView temp;
        public ImageView icon;
        public ViewHolder(View view) {
            super(view);

            date=view.findViewById(R.id.date);
            temp=view.findViewById(R.id.temp);
            icon=view.findViewById(R.id.icon);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
