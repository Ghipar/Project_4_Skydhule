package com.example.project_4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterData2 extends RecyclerView.Adapter<AdapterData2.HolderData> {
    List<DataModel2> listData;
    LayoutInflater inflater;
    Context context;

    public AdapterData2(Context context, List<DataModel2> listData) {
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_data2, parent, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        holder.clock.setText(listData.get(position).getJudul());
        holder.suhu_clock.setText(listData.get(position).getJudul2() + "°C");
        holder.humi_clock.setText(listData.get(position).getJudul3() + "%");
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView clock;
        TextView suhu_clock;
        TextView humi_clock;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            clock = itemView.findViewById(R.id.clockk);
            suhu_clock = itemView.findViewById(R.id.suhu_clock);
            humi_clock = itemView.findViewById(R.id.humi_clock);
        }
    }
}
