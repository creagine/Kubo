package com.kubo.kubo_fix.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kubo.kubo_fix.Interface.ItemClickListener;
import com.kubo.kubo_fix.R;

public abstract class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView serviceNama;
    public TextView serviceHarga;

    private ItemClickListener itemClickListener;

    public ServiceViewHolder(View itemView) {
        super(itemView);

        //edit sesuaikan service_item
        serviceNama = itemView.findViewById(R.id.service_name);
        serviceHarga = itemView.findViewById(R.id.service_harga);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);
    }

}

