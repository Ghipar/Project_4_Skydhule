package com.example.project_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    List<DataModel> listData;
    LayoutInflater inflater;
    Context context;

    public AdapterData(Context context, List<DataModel> listData) {
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_data, parent, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        holder.day1.setText(listData.get(position).getJudul());
        holder.humiday.setText(listData.get(position).getJudul2() + "%");
        holder.lowday.setText(listData.get(position).getJudul3() + "°/");
        holder.highday.setText(listData.get(position).getJudul4() + "°");
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView day1;
        TextView humiday;
        TextView lowday;
        TextView highday;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            day1 = itemView.findViewById(R.id.day1);
            humiday = itemView.findViewById(R.id.humiday);
            lowday = itemView.findViewById(R.id.lowday);
            highday = itemView.findViewById(R.id.highday);
        }
    }
}
