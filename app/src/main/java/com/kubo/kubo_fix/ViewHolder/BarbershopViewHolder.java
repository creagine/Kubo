package com.kubo.kubo_fix.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kubo.kubo_fix.Interface.ItemClickListener;
import com.kubo.kubo_fix.R;

public abstract class BarbershopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView barbershopNama;
    public ImageView barbershopImage;

    private ItemClickListener itemClickListener;

    public BarbershopViewHolder(View itemView) {
        super(itemView);

        //edit sesuaikan service_item
        barbershopNama = itemView.findViewById(R.id.barbershop_name);
        barbershopImage = itemView.findViewById(R.id.barbershop_image);

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
