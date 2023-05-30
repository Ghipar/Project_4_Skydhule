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

public class AdapterData3 extends RecyclerView.Adapter<AdapterData3.HolderData> {
    List<DataModel3> listData;
    LayoutInflater inflater;
    Context context;

    public AdapterData3(Context context, List<DataModel3> listData) {
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_data3, parent, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        holder.title.setText(listData.get(position).getJudul());
        holder.desc.setText(listData.get(position).getDesc());
        holder.date.setText(listData.get(position).getDate() + " | ");
        holder.time.setText(listData.get(position).getTime());
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }
    public class HolderData extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        TextView date;
        TextView time;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.jdl);
            desc = itemView.findViewById(R.id.desc);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
