package com.kubo.kubo_fix.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kubo.kubo_fix.Interface.ItemClickListener;
import com.kubo.kubo_fix.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView barbershopNama, jadwal, service, price, status;

    private ItemClickListener itemClickListener;

    public OrderViewHolder(View itemView) {
        super(itemView);

        //edit sesuaikan service_item
        barbershopNama = itemView.findViewById(R.id.barbershop_name);
        jadwal = itemView.findViewById(R.id.textViewJadwal);
        service = itemView.findViewById(R.id.textViewService);
        price = itemView.findViewById(R.id.textViewPrice);
        status = itemView.findViewById(R.id.textViewStatus);

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
