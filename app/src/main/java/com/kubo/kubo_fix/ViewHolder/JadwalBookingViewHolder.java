package com.kubo.kubo_fix.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kubo.kubo_fix.Interface.ItemClickListener;
import com.kubo.kubo_fix.R;

public abstract class JadwalBookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView jadwalShift;
    public TextView jadwal;

    private ItemClickListener itemClickListener;

    public JadwalBookingViewHolder(View itemView) {
        super(itemView);

        //edit sesuaikan service_item
        jadwalShift = itemView.findViewById(R.id.jadwal_shift);
        jadwal = itemView.findViewById(R.id.jadwal);

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

