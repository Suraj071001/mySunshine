package com.example.sunshine;

import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class WheatherAdapter extends RecyclerView.Adapter<WheatherAdapter.ViewHolder>{

    static String[] data;
    Cursor mCursor;
    final private ListItemClickListener mOnClickListener;

    public WheatherAdapter(ListItemClickListener clickListener){
        this.mOnClickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }

        @Override
        public void onClick(View view) {

        }
    }
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wheather_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(data[position]);

        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                mOnClickListener.onListItemClick(clickedPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(data == null){return 0;}
        return data.length;
    }
    public void setWeatherData(String[] weatherData) {
        data = weatherData;
        notifyDataSetChanged();
    }


    public static String[] getData() {
        return data;
    }
}
