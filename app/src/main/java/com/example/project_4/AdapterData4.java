package com.example.project_4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterData4 extends RecyclerView.Adapter<AdapterData4.HolderData> {
    List<DataModel4> listData;
    LayoutInflater inflater;
    Context context;

    public AdapterData4(Context context, List<DataModel4> listData) {
        this.listData = listData;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_data4, parent, false);
        return new HolderData(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        Glide.with(context).load(listData.get(position).getGambar()).into(holder.gmbr);
        holder.title.setText(listData.get(position).getJudul());
        holder.desc.setText(listData.get(position).getDesc());
        holder.parentLyout.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(listData.get(position).getLing()));
            context.startActivity(browserIntent);
        });
    }
    @Override
    public int getItemCount() {
        return listData.size();
    }
    public class HolderData extends RecyclerView.ViewHolder {
        TextView title;
        TextView desc;
        ImageView gmbr;
        LinearLayout parentLyout;

        public HolderData(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.jdl);
            desc = itemView.findViewById(R.id.desc);
            gmbr = itemView.findViewById(R.id.imgBer);
            parentLyout = itemView.findViewById(R.id.kliku);
        }
    }
}
